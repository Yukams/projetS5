package back.backobjects.users;

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
}
