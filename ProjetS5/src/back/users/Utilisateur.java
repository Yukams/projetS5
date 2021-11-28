package back.users;

import java.util.Set;

public abstract class Utilisateur implements IUtilisateur {
	private int id;
	private String username;
	private String nom;
	private String prenom;
	private Set<Integer> groupesId;
	private boolean connected = false;

	protected Utilisateur(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public Set<Integer> getGroupesId() {
		return this.groupesId;
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
