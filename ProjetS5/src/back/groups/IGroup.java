package back.groups;

import back.api.Server;

import java.util.List;

public interface IGroup {
	public String getName();

	public List<Integer> getStudentsId();

	public int getNbStudents() throws EmptyListException;

	public GroupType getGroupType();

	public boolean isEmpty();

	public int getId();

	public static IGroup createGroup(String name, GroupType GroupType, List<Integer> StudentsId) {
		return Server.createGroup(name, GroupType, StudentsId);
	}

	public static IGroup modifyGroup(List<Integer> StudentsId, int GroupId) {
		return Server.modifyGroup(StudentsId, GroupId);
	}

	public static IGroup deleteGroup(int GroupId) {
		return Server.deleteGroup(GroupId);
	}

	public static IGroup getGroup(int GroupId) {
		return Server.getGroup(GroupId);
	}
}
