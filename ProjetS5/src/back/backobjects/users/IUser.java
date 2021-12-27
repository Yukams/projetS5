package back.backobjects.users;

import java.util.List;
import java.util.Map;

import back.api.Server;
import back.frontobjects.FrontUser;

import static back.main.mainBack.gson;


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

		FrontUser user = Server.createUser(username, name, surname, password);

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
}
