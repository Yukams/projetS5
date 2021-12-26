package back.backobjects.users;

import java.util.Map;
import java.util.Set;

import back.api.Server;
import back.frontobjects.FrontUser;

import static back.main.mainBack.gson;


public interface IUser {
	int getId();

	String getName();

	String getSurname();

	boolean isConnected();

	String getUsername();

	static IUser createUser(String name, String surname, String username, Set<Integer> GroupsId) {
		return Server.createUser(name, surname, username, GroupsId);
	}

	static IUser modifyUser(String name, String surname, Set<Integer> GroupsId, int UserId) {
		return Server.modifyUser(name, surname, GroupsId, UserId);
	}

	static IUser deleteUser(int userId) {
		return Server.deleteUser(userId);
	}

	static String getUserById(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		FrontUser user = Server.getUser(id);

		return gson.toJson(user);
	}
}
