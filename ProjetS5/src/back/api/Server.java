package back.api;

import java.sql.*;
import java.util.*;
import java.util.Date;

import back.dbobjects.*;
import back.frontobjects.FrontGroup;
import back.frontobjects.FrontMessage;
import back.frontobjects.FrontThread;
import back.frontobjects.FrontUser;

import static back.main.mainBack.gson;

public class Server {
	private static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
	private static final String DB_URL_SINGLE_QUERY = "jdbc:mysql://localhost:3306/projetS5";
	private static final String USER = "root";
	private static final String PASS = "root";

	public static void treatQueryWithoutResponse(String queryString) {
		try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			stmt.executeUpdate(queryString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String treatQuery(String queryString) {
		try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			ResultSet resultSet = stmt.executeQuery(queryString);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			StringBuilder jsonString = new StringBuilder();

			jsonString.append("[");
			while (resultSet.next()) {
				jsonString.append("\n\t{");
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) jsonString.append(",");
					String columnValue = resultSet.getString(i);
					jsonString.append("\n\t\t\"").append(rsmd.getColumnName(i)).append("\": \"").append(columnValue).append("\"");
				}
				if(resultSet.isLast()) { jsonString.append("\n\t}"); }
				else { jsonString.append("\n\t},"); }
			}
			jsonString.append("\n]");

			return jsonString.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static StringBuilder dbObjectToJsonList(DbObject[] objectList) {
		StringBuilder groupList = new StringBuilder("(");
		for(DbObject object: objectList) {
			groupList.append("'").append(object.id).append("',");
		}
		groupList.setLength(groupList.length() - 1);
		groupList.append(")");

		return groupList;
	}

	public static String getStatusFromMessageId(int id) {
		String jsonString = Server.treatQuery("SELECT messageId FROM dbLinkUserMessage WHERE messageId=" + id + " AND status='NOT_SEEN';");
		DbMessage[] messages = gson.fromJson(jsonString, DbMessage[].class);

		if(messages.length == 0) {
			return "SEEN";
		}

		return "NOT_SEEN";
	}

	/* Connect User */
	public static String connect(Map<String,String> payload) {
		String username = payload.get("username");
		String password = payload.get("password");
		System.out.println("SELECT * FROM dbUser WHERE username='" + username + "' AND password='" + password + "';");

		String jsonString = Server.treatQuery("SELECT * FROM dbUser WHERE username='" + username + "' AND password='" + password + "';");
		DbUser[] objectList = gson.fromJson(jsonString, DbUser[].class);
		System.out.println(Arrays.toString(objectList));

		if(objectList.length != 0) {
			createConnectionToken(objectList[0].id);
			return gson.toJson(objectList[0]);
		}

		return null;
	}

	public static void createConnectionToken(int userId) {
		int id = back.utils.Utils.createRandomId();
		treatQueryWithoutResponse("INSERT INTO dbConnectionToken VALUES (" + id + "," + userId + ");");
	}

	/* Threads */
	public static FrontThread createThread(int authorId, int groupId, String content, String title) {
		int id = back.utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT INTO dbThread VALUES (" + id + ",'" + title + "'," + groupId + "," + authorId + ");");

		List<FrontMessage> messages = new ArrayList<>();
		// Create message
		FrontMessage firstMessage = createMessage(authorId, content, id);
		messages.add(firstMessage);

		return new FrontThread(authorId, title, messages, groupId);
	}

	public static FrontThread getThread(int threadId) {
		String jsonString = Server.treatQuery("SELECT * FROM dbThread WHERE id=" + threadId + ";");
		DbThread thread = gson.fromJson(jsonString, DbThread[].class)[0];

		List<FrontMessage> messagesList = new ArrayList<>();
		// Get all messages related to the thread in date order (older first)
		jsonString = Server.treatQuery("SELECT m.id, m.authorId, m.text, m.date FROM dbLinkMessageThread l JOIN dbMessage m ON l.messageId=m.id WHERE l.threadId=" + threadId + " ORDER BY m.date;");
		DbMessage[] dbMessageList = gson.fromJson(jsonString, DbMessage[].class);

		// Build each message
		for(DbMessage message: dbMessageList) {
			// Get message user
			jsonString = Server.treatQuery("SELECT * FROM dbUser WHERE id=" + message.authorId + ";");
			DbUser[] dbUser = gson.fromJson(jsonString, DbUser[].class);

			// Build author
			FrontUser user = new FrontUser(dbUser[0].name, dbUser[0].surname, dbUser[0].id);

			// Build status (later)
			String status = getStatusFromMessageId(message.id);

			FrontMessage m = new FrontMessage(message.id ,user, message.text, message.date, status);
			messagesList.add(m);
		}

		// Add the thread to the thread list
		return new FrontThread(thread.id, thread.title, messagesList, thread.groupId);
	}

	public static List<FrontThread> getAllThreadForUser(int userId) {
		// Get Groups for the user
		String jsonString = Server.treatQuery("SELECT g.id, g.name FROM dbLinkUserGroup JOIN dbGroup g ON id WHERE userId=" + userId + " AND dbLinkUserGroup.groupId=g.id;");
		DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);

		// Transform groups into a JSON List
		StringBuilder groupList = dbObjectToJsonList(objectList);

		// Get all Threads for the said User
		jsonString = Server.treatQuery("SELECT id FROM dbThread WHERE groupId IN " + groupList + " OR authorId=" + userId);
		DbThread[] dbThreadList = gson.fromJson(jsonString, DbThread[].class);

		List<FrontThread> threadsList = new ArrayList<>();
		// Build each thread
		for(DbThread thread: dbThreadList) {
			threadsList.add(getThread(thread.id));
		}

		return threadsList;
	}

	public static FrontGroup getGroupFromThreadId(int threadId) {
		String jsonString = treatQuery("SELECT * FROM dbThread WHERE id=" + threadId + ";");
		DbThread[] dbObject = gson.fromJson(jsonString, DbThread[].class);

		return getGroup(dbObject[0].groupId);
	}

	public static FrontThread updateMessages(int userId, int threadId) {
		FrontThread thread = getThread(threadId);

		for(FrontMessage message: thread.messages) {
			if(!message.status.equals("SEEN")) {
				treatQueryWithoutResponse("UPDATE dbLinkUserMessage SET status = 'SEEN' WHERE messageId=" + message.id + " AND userId=" + userId + ";");
			}
		}

		return getThread(threadId);
	}

	/* Message */
	public static FrontMessage createMessage(int authorId, String content, int threadId) {
		int id = back.utils.Utils.createRandomId();
		// Add Message in database
		long date = new Date().getTime();
		treatQueryWithoutResponse("INSERT INTO dbMessage VALUES (" + id + "," + authorId + ",'" + content + "','" + date + "');");

		// Connect Message to its Thread
		treatQueryWithoutResponse("INSERT INTO dbLinkMessageThread VALUES (" + id + "," + threadId + ");");

		// Connect Message To Users
		FrontGroup group = getGroupFromThreadId(threadId);

		List<FrontUser> users = getUsersFromGroupId(group.id);
		for(FrontUser user: users) {
			if(user.id != authorId) {
				treatQueryWithoutResponse("INSERT INTO dbLinkUserMessage VALUES (" + user.id + "," + id + ",'NOT_SEEN'" + ");");
			}
		}
		// Author has SEEN status as he is the writer of the message
		treatQueryWithoutResponse("INSERT INTO dbLinkUserMessage VALUES (" + authorId + "," + id + ",'SEEN'" + ");");

		return new FrontMessage(id, getUser(authorId), content, date, "NOT_READ");
	}

	/* Group */
	public static FrontGroup getGroup(int groupId) {
		String jsonString = treatQuery("SELECT * FROM dbGroup WHERE id=" + groupId + ";");
		DbGroup[] dbObject = gson.fromJson(jsonString, DbGroup[].class);

		return new FrontGroup(dbObject[0].id, dbObject[0].name);
	}

	public static FrontGroup createGroup(String name) {
		int id = back.utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT INTO dbGroup VALUES (" + id + ",'" + name + "');");

		return new FrontGroup(id, name);
	}

	public static FrontGroup addUserToGroup(int groupId, int userId) {
		treatQueryWithoutResponse("INSERT INTO dbLinkUserGroup VALUES (" + userId + "," + groupId + ");");

		return getGroup(groupId);
	}

	public static List<FrontGroup> getAllDatabaseGroups() {
		String jsonString = treatQuery("SELECT id FROM dbGroup;");
		DbGroup[] dbGroups = gson.fromJson(jsonString, DbGroup[].class);

		List<FrontGroup> groups = new ArrayList<>();
		for(DbGroup group: dbGroups) {
			groups.add(getGroup(group.id));
		}

		return groups;
	}

	/* Users */
	public static FrontUser getUser(int userId) {
		String jsonString = treatQuery("SELECT * FROM dbUser WHERE id=" + userId + ";");
		DbUser[] dbObject = gson.fromJson(jsonString, DbUser[].class);

		return new FrontUser(dbObject[0].name, dbObject[0].surname, dbObject[0].id);
	}

	public static List<FrontUser> getUsersFromGroupId(int id) {
		String jsonString = treatQuery("SELECT u.id, u.username, u.name, u.surname, u.password FROM dbLinkUserGroup l JOIN dbUser u on l.userId WHERE l.groupId=" + id + " AND u.id=l.userId;");
		DbUser[] dbObject = gson.fromJson(jsonString, DbUser[].class);

		List<FrontUser> users = new ArrayList<>();
		for(DbUser user: dbObject) {
			users.add(new FrontUser(user.name, user.surname, user.id));
		}

		return users;
	}

	public static FrontUser createUser(String username, String name, String surname, String password) {
		int id = back.utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT INTO dbUser VALUES (" + id + ",'" + username + "','" + name + "','" + surname + "','" + password + "');");

		return new FrontUser(name, surname, id);
	}
}
