package api;

import java.sql.*;
import java.util.*;
import java.util.Date;

import dbobjects.*;
import frontobjects.FrontGroup;
import frontobjects.FrontMessage;
import frontobjects.FrontThread;
import frontobjects.FrontUser;
import main.ClientHandler;

import static main.mainBack.*;

public class Server {
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
		if(objectList.length == 0) {return new StringBuilder("");}

		StringBuilder groupList = new StringBuilder("(");
		for(DbObject object: objectList) {
			groupList.append("'").append(object.id).append("',");
		}
		groupList.setLength(groupList.length() - 1);
		groupList.append(")");

		return groupList;
	}

	public static String getStatusFromMessageId(int id) {
		String jsonString = Server.treatQuery("SELECT messageId FROM dbLinkUserMessage WHERE messageId=" + id + " AND (status='NOT_SEEN' OR status='NOT_SENT');");
		DbMessage[] messages = gson.fromJson(jsonString, DbMessage[].class);

		if(messages.length == 0) {
			return "SEEN";
		}

		jsonString = Server.treatQuery("SELECT messageId FROM dbLinkUserMessage WHERE messageId=" + id + " AND status='NOT_SENT';");
		messages = gson.fromJson(jsonString, DbMessage[].class);

		if(messages.length != 0) {
			return "NOT_SENT";
		}

		return "HALF_SEEN";
	}

	/* Connect User */
	public static String connect(ClientHandler clientHandler, Map<String,String> payload) {
		String username = payload.get("username");
		String password = payload.get("password");

		String jsonString = Server.treatQuery("SELECT * FROM dbUser WHERE username='" + username + "' AND password='" + password + "';");
		DbUser[] objectList = gson.fromJson(jsonString, DbUser[].class);

		List<FrontUser> connectedUsers = getAllConnectedUsers();
		for(FrontUser user: connectedUsers) {
			if(objectList.length != 0 && user.id == objectList[0].id) {
				return null;
			}
		}


		if(objectList.length != 0) {
			// Save id for disconnection
			clientHandler.setClientId(objectList[0].id);
			// Save adminRole for updates
			clientHandler.setClientIsAdmin(objectList[0].isAdmin.equals("1"));

			createConnectionToken(objectList[0].id);
			return gson.toJson(new FrontUser(objectList[0].name, objectList[0].surname, objectList[0].id, objectList[0].isAdmin.equals("1")));
		}

		return null;
	}

	public static void disconnect(ClientHandler clientHandler) {
		treatQueryWithoutResponse("DELETE FROM dbConnectionToken WHERE userId=" + clientHandler.getClientId() + ";");
	}

	public static void createConnectionToken(int userId) {
		int id = utils.Utils.createRandomId();
		treatQueryWithoutResponse("INSERT INTO dbConnectionToken VALUES (" + id + "," + userId + ");");
	}

	/* Threads */
	public static FrontThread createThread(int authorId, int groupId, String content, String title) {
		int id = utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT INTO dbThread VALUES (" + id + ",\"" + title + "\"," + groupId + "," + authorId + ");");

		List<FrontMessage> messages = new ArrayList<>();
		// Create message
		FrontMessage firstMessage = createMessage(authorId, content, id);
		messages.add(firstMessage);

		return new FrontThread(authorId, title, messages, getGroup(groupId), 0);
	}

	public static FrontThread getThread(int threadId, int userId) {
		String jsonString = Server.treatQuery("SELECT * FROM dbThread WHERE id=" + threadId + ";");
		DbThread thread = gson.fromJson(jsonString, DbThread[].class)[0];

		List<FrontMessage> messagesList = new ArrayList<>();
		// Get all messages related to the thread in date order (older first)
		jsonString = Server.treatQuery("SELECT m.id, m.authorId, m.text, m.date FROM dbLinkMessageThread l JOIN dbMessage m ON l.messageId=m.id WHERE l.threadId=" + threadId + " ORDER BY m.date;");
		DbMessage[] dbMessageList = gson.fromJson(jsonString, DbMessage[].class);

		// if userId is given, update messages status from NOT_SENT to NOT_SEEN
		if(userId != -1) {
			for(DbMessage message: dbMessageList) {
				Server.treatQueryWithoutResponse("UPDATE dbLinkUserMessage SET status='NOT_SEEN' WHERE userId=" + userId + " AND status='NOT_SENT';");
			}
		}

		int nbNotReadMessage = 0;
		// Build each message
		for(DbMessage message: dbMessageList) {
			// Get message user
			jsonString = Server.treatQuery("SELECT * FROM dbUser WHERE id=" + message.authorId + ";");
			DbUser[] dbUser = gson.fromJson(jsonString, DbUser[].class);

			// Build author
			FrontUser user = new FrontUser(dbUser[0].name, dbUser[0].surname, dbUser[0].id, dbUser[0].isAdmin.equals("1"));

			// Build status
			String status = getStatusFromMessageId(message.id);

			// Check read status for ACTUAL USER if given
			if(userId != -1) {
				jsonString = Server.treatQuery("SELECT * FROM dbLinkUserMessage WHERE userId=" + userId + " AND messageId=" + message.id + ";");
				DbLinkUserMessage[] dbLinkUserMessages = gson.fromJson(jsonString, DbLinkUserMessage[].class);
				if(dbLinkUserMessages != null && dbLinkUserMessages.length != 0 && !dbLinkUserMessages[0].status.equals("SEEN")) {
					nbNotReadMessage++;
				}
			}

			FrontMessage m = new FrontMessage(message.id ,user, message.text, message.date, status);
			messagesList.add(m);
		}

		// Add the thread to the thread list
		return new FrontThread(thread.id, thread.title, messagesList, getGroup(thread.groupId), nbNotReadMessage);
	}

	public static List<FrontThread> getAllThreadForUser(int userId) {
		// Get Groups for the user
		String jsonString = Server.treatQuery("SELECT g.id, g.name FROM dbLinkUserGroup JOIN dbGroup g ON id WHERE userId=" + userId + " AND dbLinkUserGroup.groupId=g.id;");
		DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);

		List<FrontThread> threadsList = new ArrayList<>();

		// Transform groups into a JSON List if user is in at least one group
		if (objectList.length != 0) {
			StringBuilder groupList = dbObjectToJsonList(objectList);

			// Get all Threads for the said User
			jsonString = Server.treatQuery("SELECT id FROM dbThread WHERE groupId IN " + groupList + " OR authorId=" + userId + ";");
			DbThread[] dbThreadList = gson.fromJson(jsonString, DbThread[].class);


			// Build each thread
			for (DbThread thread : dbThreadList) {
				threadsList.add(getThread(thread.id, userId));
			}
		}

		// add threads in which the author is the user
		List<FrontThread> threads = getAllOwnWrittenThreadsForUser(userId);
		for(FrontThread thread : threads) {
			// do not add duplicated threads
			if(threadsList.stream().noneMatch(groupThread -> groupThread.id == thread.id)) {
				threadsList.add(thread);
			}
		}

		return threadsList;
	}

	public static FrontGroup getGroupFromThreadId(int threadId) {
		String jsonString = treatQuery("SELECT * FROM dbThread WHERE id=" + threadId + ";");
		DbThread[] dbObject = gson.fromJson(jsonString, DbThread[].class);

		return getGroup(dbObject[0].groupId);
	}

	public static FrontThread updateMessagesStatus(int userId, int threadId) {
		FrontThread thread = getThread(threadId, userId);

		for(FrontMessage message: thread.messages) {
			if(!message.status.equals("SEEN")) {
				treatQueryWithoutResponse("UPDATE dbLinkUserMessage SET status='SEEN' WHERE messageId=" + message.id + " AND userId=" + userId + ";");
			}
		}

		return getThread(threadId, userId);
	}

	public static FrontThread deleteThread(int id) {
		FrontThread thread = getThread(id, -1);

		for(FrontMessage message: thread.messages) {
			deleteMessage(message.id);
		}

		treatQueryWithoutResponse("DELETE FROM dbThread WHERE id=" + id + ";");
		return thread;
	}

	private static List<FrontThread> getAllOwnWrittenThreadsForUser(int authorId) {
		String jsonString = treatQuery("SELECT * FROM dbThread WHERE authorId=" + authorId + ";");
		DbThread[] dbThreadList = gson.fromJson(jsonString, DbThread[].class);

		List<FrontThread> threadsList = new ArrayList<>();
		// Build each thread
		for(DbThread thread: dbThreadList) {
			threadsList.add(getThread(thread.id, authorId));
		}

		return threadsList;
	}

	private static List<FrontThread> getAllThreadsForGroup(int groupId) {
		String jsonString = treatQuery("SELECT * FROM dbThread WHERE groupId=" + groupId + ";");
		DbThread[] dbThreadList = gson.fromJson(jsonString, DbThread[].class);

		List<FrontThread> threadsList = new ArrayList<>();
		// Build each thread
		for(DbThread thread: dbThreadList) {
			threadsList.add(getThread(thread.id, -1));
		}

		return threadsList;
	}

	public static List<FrontThread> clientGetThreadsAtConnection(int clientId) {
		return getAllThreadForUser(clientId);
	}

	/* Message */
	public static FrontMessage createMessage(int authorId, String content, int threadId) {
		int id = utils.Utils.createRandomId();
		// Add Message in database
		long date = new Date().getTime();
		treatQueryWithoutResponse("INSERT INTO dbMessage VALUES (" + id + "," + authorId + ",\"" + content + "\",'" + date + "');");

		// Connect Message to its Thread
		treatQueryWithoutResponse("INSERT INTO dbLinkMessageThread VALUES (" + id + "," + threadId + ");");

		// Connect Message To Users
		FrontGroup group = getGroupFromThreadId(threadId);

		// Get all users from the group
		List<FrontUser> users = getUsersFromGroupId(group.id);
		// Check if author is from the group, otherwise add him
		FrontUser threadAuthor = getAuthorFromThread(threadId);
		if(!users.contains(threadAuthor)) {
			users.add(threadAuthor);
		}


		for(FrontUser user: users) {
			
			if(user.id != authorId) {
				treatQueryWithoutResponse("INSERT IGNORE INTO dbLinkUserMessage VALUES (" + user.id + "," + id + ",'NOT_SENT'" + ");");
			}
		}
		// Author has SEEN status as he is the writer of the message
		treatQueryWithoutResponse("INSERT IGNORE INTO dbLinkUserMessage VALUES (" + authorId + "," + id + ",'SEEN'" + ");");

		return new FrontMessage(id, getUser(authorId), content, date, "HALF_SEEN");
	}

	private static FrontMessage getMessage(int id) {
		String jsonString = Server.treatQuery("SELECT * FROM dbMessage WHERE id=" + id + ";");
		DbMessage message = gson.fromJson(jsonString, DbMessage[].class)[0];

		return new FrontMessage(message.id, getUser(message.authorId), message.text, message.date, getStatusFromMessageId(id));
	}

	public static FrontMessage deleteMessage(int id) {
		FrontMessage message = getMessage(id);
		Server.treatQueryWithoutResponse("DELETE FROM dbMessage WHERE id=" + id + ";");

		return message;
	}

	/* Group */
	public static FrontGroup getGroup(int groupId) {
		String jsonString = treatQuery("SELECT * FROM dbGroup WHERE id=" + groupId + ";");
		DbGroup[] dbObject = gson.fromJson(jsonString, DbGroup[].class);

		if(dbObject.length == 0) {
			return null;
		}

		return new FrontGroup(dbObject[0].id, dbObject[0].name);
	}

	public static FrontGroup createGroup(String name) {
		int id = utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT INTO dbGroup VALUES (" + id + ",'" + name + "');");

		return new FrontGroup(id, name);
	}

	public static FrontGroup addUserToGroup(int groupId, int userId) {
		treatQueryWithoutResponse("INSERT IGNORE INTO dbLinkUserGroup VALUES (" + userId + "," + groupId + ");");

		// We consider that the new added user don't need read old messages

		return getGroup(groupId);
	}

	public static FrontGroup removeUserFromGroup(int groupId, int userId) {
		treatQueryWithoutResponse("DELETE FROM dbLinkUserGroup WHERE userId=" + userId + ";");

		// Get all threads related to the group
		List<FrontThread> threads = getAllThreadsForGroup(groupId);
		for(FrontThread thread: threads) {
			// Get all messages related to each group's thread
			for(FrontMessage message: thread.messages) {
				// Delete any reference of a link between each message and removed user
				treatQueryWithoutResponse("DELETE FROM dbLinkUserMessage WHERE messageId=" + message.id + " AND userId=" + userId + ";");
			}
		}
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

	public static FrontGroup deleteGroup(int id) {
		FrontGroup group = getGroup(id);

		List<FrontThread> threads = getAllThreadsForGroup(id);
		for(FrontThread thread : threads) {
			deleteThread(thread.id);
		}

		Server.treatQueryWithoutResponse("DELETE FROM dbGroup WHERE id=" + id + ";");

		return group;
	}

	public static List<FrontGroup> getGroupsOfUserById(int userId) {
		String jsonString = treatQuery("SELECT g.id FROM dbGroup g JOIN dbLinkUserGroup l ON l.groupId WHERE l.groupId=g.id AND userId=" + userId + ";");
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

		return new FrontUser(dbObject[0].name, dbObject[0].surname, dbObject[0].id, dbObject[0].isAdmin.equals("1"));
	}

	private static FrontUser getAuthorFromThread(int threadId) {
		String jsonString = treatQuery("SELECT u.id, u.name, u.surname, u.isAdmin FROM dbUser u JOIN dbThread t ON t.authorId WHERE t.authorId=u.id AND t.id=" + threadId + ";");
		DbUser[] dbObject = gson.fromJson(jsonString, DbUser[].class);

		return new FrontUser(dbObject[0].name, dbObject[0].surname, dbObject[0].id, dbObject[0].isAdmin.equals("1"));

	}

	public static List<FrontUser> getUsersFromGroupId(int id) {
		String jsonString = treatQuery("SELECT u.id, u.name, u.surname, u.isAdmin FROM dbLinkUserGroup l JOIN dbUser u on l.userId WHERE l.groupId=" + id + " AND u.id=l.userId;");
		return getFrontUsers(jsonString);
	}

	public static FrontUser createUser(String username, String name, String surname, String password, boolean isAdmin) {
		int id = utils.Utils.createRandomId();

		treatQueryWithoutResponse("INSERT IGNORE INTO dbUser VALUES (" + id + ",'" + username + "','" + name + "','" + surname + "','" + password + "'," + isAdmin + ");");

		return new FrontUser(name, surname, id, isAdmin);
	}

	public static List<FrontUser> getAllConnectedUsers() {
		String jsonString = treatQuery("SELECT u.id, u.name, u.surname, u.isAdmin FROM dbConnectionToken l JOIN dbUser u ON l.userId WHERE l.userId=u.id;");
		return getFrontUsers(jsonString);
	}

	private static List<FrontUser> getFrontUsers(String jsonString) {
		DbUser[] dbObject = gson.fromJson(jsonString, DbUser[].class);

		List<FrontUser> users = new ArrayList<>();
		for(DbUser user: dbObject) {
			users.add(new FrontUser(user.name, user.surname, user.id, user.isAdmin.equals("1")));
		}

		return users;
	}

	public static List<FrontUser> getAllDatabaseUsers() {
		String jsonString = treatQuery("SELECT * FROM dbUser;");
		return getFrontUsers(jsonString);
	}

	public static FrontUser deleteUser(int id) {
		FrontUser user = getUser(id);

		List<FrontThread> threads = getAllOwnWrittenThreadsForUser(id);
		for(FrontThread thread : threads) {
			deleteThread(thread.id);
		}

		treatQueryWithoutResponse("DELETE FROM dbUser WHERE id=" + id + ";");

		return user;
	}
}
