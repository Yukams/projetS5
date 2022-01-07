package users;

import java.util.Set;

import groups.Groupe;

public class Utilisateur {
	private int id;
	private String username;
	private String nom;
	private String prenom;
	private Set<Groupe> groupes;

	public Utilisateur(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public static Utilisateur buildUserFromJSON(String jsonAuthor) {
		// TODO Auto-generated method stub
		// Construction d'un groupe
		// Construction du groupe
		String jsonGroup = null;
		Groupe groupe = Groupe.buildGroupFromJSON(jsonGroup);
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Set<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(Set<Groupe> groupes) {
		this.groupes = groupes;
	}
	
	
}
