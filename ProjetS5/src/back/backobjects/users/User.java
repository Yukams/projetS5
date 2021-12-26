package back.backobjects.users;

import back.api.Server;
import back.frontobjects.FrontUser;
import com.google.gson.Gson;

import java.util.Map;

public abstract class User implements IUser {
	private int id;
	private String username;
	private String name;
	private String surname;
	private boolean connected = false;
	static Gson gson = new Gson();

	protected User(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSurname() {
		return this.surname;
	}

	@Override
	public boolean isConnected() {
		return this.connected;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public static String getUserById(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		FrontUser user = Server.getUser(id);

		return gson.toJson(user);
	}
}
