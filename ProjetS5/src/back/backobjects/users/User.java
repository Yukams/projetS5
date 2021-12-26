package back.backobjects.users;

public abstract class User implements IUser {
	private int id;
	private String username;
	private String name;
	private String surname;
	private boolean connected = false;

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
}
