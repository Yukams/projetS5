package back.groups;

import java.util.List;

public abstract class Group implements IGroup {
	private String name;
	private int id;
	private GroupType GroupType;

	protected Group(String name, GroupType GroupType) {
		this.name = name;
		this.GroupType = GroupType;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public GroupType getGroupType() {
		return this.GroupType;
	}

	@Override
	public int getId() {
		return this.id;
	}

}
