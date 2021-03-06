package backobjects.users;

import java.util.List;
import java.util.Map;

import api.Server;
import frontobjects.FrontUser;

import static main.mainBack.gson;


public interface IUser {
	static String getUserById(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		FrontUser user = Server.getUser(id);

		return gson.toJson(user);
	}

	static String createUser(Map<String, String> payload) {
		String username = payload.get("username");
		String name = payload.get("name");
		String surname = payload.get("surname");
		String password = payload.get("password");
		boolean isAdmin = Boolean.parseBoolean(payload.get("isAdmin"));

		FrontUser user = Server.createUser(username, name, surname, password, isAdmin);

		return gson.toJson(user);
	}

	static String getAllConnectedUsers() {
		List<FrontUser> users = Server.getAllConnectedUsers();
		return gson.toJson(users);
	}

	static String getAllDatabaseUsers() {
		List<FrontUser> users = Server.getAllDatabaseUsers();
		return gson.toJson(users);
	}

	static String deleteUser(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		FrontUser user = Server.deleteUser(id);

		return gson.toJson(user);
	}
}
