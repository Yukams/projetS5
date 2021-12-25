package back.backobjects.users;

import java.util.List;
import java.util.Set;

import back.api.Server;
import back.backobjects.thread.IThread;

public interface IUser {
	public int getId();

	public String getName();

	public String getSurname();

	public boolean isConnected();

	public String getUsername();

	public static IUser createUser(String name, String surname, String username, Set<Integer> GroupsId) {
		return Server.createUser(name, surname, username, GroupsId);
	}

	public static IUser modifyUser(String name, String surname, Set<Integer> GroupsId, int UserId) {
		return Server.modifyUser(name, surname, GroupsId, UserId);
	}

	public static IUser deleteUser(int UserId) {
		return Server.deleteUser(UserId);
	}

	public static List<IThread> getAllThreadForUser(int UserId) {
		return IThread.getAllThreadForUser(UserId);
	}
}
