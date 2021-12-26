package back.api;

import java.sql.*;
import java.util.*;
import java.util.Date;

import back.dbobjects.*;
import back.backobjects.thread.IThread;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.Thread;
import back.backobjects.groups.GroupType;
import back.backobjects.groups.IGroup;
import back.backobjects.users.IUser;
import back.frontobjects.FrontGroup;
import back.frontobjects.FrontMessage;
import back.frontobjects.FrontThread;
import back.frontobjects.FrontUser;
import com.google.gson.Gson;

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

	private static StringBuilder groupStringToJsonList(DbObject[] objectList) {
		StringBuilder groupList = new StringBuilder("(");
		for(DbObject object: objectList) {
			groupList.append("'").append(object.id).append("',");
		}
		groupList.setLength(groupList.length() - 1);
		groupList.append(")");

		return groupList;
	}

	private static String getStatusFromMessageId(int id) {
		return "NOT_READ";
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

	/* Fil de discussion */
	public static FrontThread createThread(int authorId, int groupId, String content, String title) {
		int id = back.utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT INTO dbThread VALUES (" + id + ",'" + title + "'," + groupId + "," + authorId + ");");

		List<FrontMessage> messages = new ArrayList<>();
		// Create message
		FrontMessage firstMessage = createMessage(authorId, content, id);
		messages.add(firstMessage);

		return new FrontThread(authorId, title, messages, groupId);
	}

	public static IThread addMessageToThread(int idFil, String contenu, int authorId, int GroupId) {
		// TODO call SQL
		return null;
	}

	public static IThread modifyThread(String title, List<Integer> messagesId, int threadId) {
		// TODO call SQL
		return null;
	}

	public static IThread deleteThread(int threadId) {
		// TODO call SQL
		return null;
	}

	public static IThread getThread(int threadId) {
		// Get DbThread
		String jsonString = treatQuery("SELECT * FROM dbThread WHERE id=" + threadId + ";");
		Gson gson = new Gson();
		DbThread dbObject = gson.fromJson(jsonString, DbThread.class);

		return new Thread(dbObject.title);
	}

	public static List<FrontThread> getAllThreadForUser(int userId) {
		// Get Groups for the user
		String jsonString = Server.treatQuery("SELECT g.id, g.name FROM dbLinkUserGroup JOIN dbGroup g ON id WHERE userId=" + userId + " AND dbLinkUserGroup.groupId=g.id;");
		DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);

		// Transform groups into a JSON List
		StringBuilder groupList = groupStringToJsonList(objectList);

		// Get all Threads for the said User
		jsonString = Server.treatQuery("SELECT * FROM dbThread WHERE groupId IN " + groupList + " OR authorId=" + userId);
		DbThread[] dbThreadList = gson.fromJson(jsonString, DbThread[].class);

		List<FrontThread> threadsList = new ArrayList<>();
		// Build each thread
		for(DbThread thread: dbThreadList) {
			List<FrontMessage> messagesList = new ArrayList<>();
			// Get all messages related to the thread in date order (older first)
			jsonString = Server.treatQuery("SELECT DISTINCT m.id, m.authorId, m.text, m.date FROM dbLinkMessageThread l JOIN dbMessage m ON l.messageId=m.id WHERE l.threadId=" + thread.id + " ORDER BY m.date;\n");
			DbMessage[] dbMessageList = gson.fromJson(jsonString, DbMessage[].class);

			// Construct each message
			for(DbMessage message: dbMessageList) {
				// Get message user
				jsonString = Server.treatQuery("SELECT * FROM dbUser WHERE id=" + message.authorId + ";");
				DbUser[] dbUser = gson.fromJson(jsonString, DbUser[].class);

				// Construct author
				FrontUser user = new FrontUser(dbUser[0].name, dbUser[0].surname, dbUser[0].id);

				// Build status (later)
				String status = getStatusFromMessageId(message.id);

				FrontMessage m = new FrontMessage(message.id ,user, message.text, message.date, status);
				messagesList.add(m);
			}

			// Add the thread to the thread list
			threadsList.add(new FrontThread(thread.id, thread.title, messagesList, thread.groupId));
		}

		return threadsList;
	}

	public static FrontGroup getGroupFromThreadId(int threadId) {
		String jsonString = treatQuery("SELECT * FROM dbThread WHERE id=" + threadId + ";");
		DbThread[] dbObject = gson.fromJson(jsonString, DbThread[].class);

		return getGroup(dbObject[0].groupId);
	}

	public static List<IThread> getAllThread(int userId) {
		// TODO call SQL
		return null;
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
			treatQueryWithoutResponse("INSERT INTO dbLinkUserMessage VALUES (" + user.id + "," + id + ",'NOT_SEEN'" + ");");
		}

		return new FrontMessage(id, getUser(authorId), content, date, "NOT_READ");
	}

	public static IMessage modifyMessage(String contenu, int messageId) {
		// TODO call SQL
		return null;
	}

	public static IMessage deleteMessage(int messageId) {
		// TODO call SQL
		return null;
	}

	public static IMessage getMessage(int messageId) {
		// TODO call SQL
		return null;
	}

	/* Group */
	public static IGroup createGroup(String name, GroupType GroupType, List<Integer> StudentsId) {
		int id = back.utils.Utils.createRandomId();
		// TODO call SQL
		return null;
	}

	public static IGroup modifyGroup(List<Integer> StudentsId, int GroupId) {
		// TODO call SQL
		return null;
	}

	public static IGroup deleteGroup(int GroupId) {
		// TODO call SQL
		return null;
	}

	public static FrontGroup getGroup(int groupId) {
		String jsonString = treatQuery("SELECT * FROM dbGroup WHERE id=" + groupId + ";");
		DbGroup[] dbObject = gson.fromJson(jsonString, DbGroup[].class);

		return new FrontGroup(dbObject[0].id, dbObject[0].name);
	}

	/* Users */
	public static IUser createUser(String name, String surname, String username, Set<Integer> GroupsId) {
		int id = back.utils.Utils.createRandomId();
		String password = back.utils.Utils.createRandomPassword();
		// TODO call SQL
		return null;
	}

	public static IUser modifyUser(String name, String surname, Set<Integer> GroupsId, int UserId) {
		// TODO call SQL
		return null;
	}

	public static IUser deleteUser(int userId) {
		// TODO call SQL
		return null;
	}

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

}
