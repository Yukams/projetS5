package back.api;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import back.backobjects.users.User;
import back.dbobjects.*;
import back.backobjects.thread.IThread;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.Thread;
import back.backobjects.groups.GroupType;
import back.backobjects.groups.IGroup;
import back.backobjects.users.IUser;
import back.frontobjects.FrontUser;
import com.google.gson.Gson;

public class Server {
	private static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
	private static final String DB_URL_SINGLE_QUERY = "jdbc:mysql://localhost:3306/projetS5";
	private static final String USER = "root";
	private static final String PASS = "root";

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

	/* Connexion User */
	public static IUser connect(String username, String password) {
		// TODO call SQL
		// Construire l'User OU renvoyer null si mdp incorrect
		return null;
	}

	/* Fil de discussion */
	public static IThread createThread(String title, int authorId, int GroupId, int messageId) {
		int id = back.utils.Utils.createRandomId();
		List<Integer> messagesId = new LinkedList<>();
		messagesId.add(messageId);
		// TODO call SQL
		return null;
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
	

	public static List<IThread> getAllThreadForUser(int UserId) {
		// TODO call SQL
		return null;
	}

	public static List<IThread> getAllThread(int UserId) {
		// TODO call SQL
		return null;
	}

	/* Message */
	public static IMessage createMessage(String contenu, int authorId, int GroupId) {
		int id = back.utils.Utils.createRandomId();
		// TODO call SQL
		return null;
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

	public static IGroup getGroup(int GroupId) {
		// TODO call SQL
		return null;
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
		Gson gson = new Gson();
		DbUser[] dbObject = gson.fromJson(jsonString, DbUser[].class);

		return new FrontUser(dbObject[0].name, dbObject[0].surname);
	}

}
