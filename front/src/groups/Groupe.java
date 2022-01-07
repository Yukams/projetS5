package groups;

import java.util.List;

import users.Utilisateur;

public class Groupe {
	private String nom;
	private int id;
	private GroupeType groupeType;
	private List<Utilisateur> etudiants;

	public Groupe(String nom, int id, GroupeType groupeType, List<Utilisateur> etudiants) {
		this.nom = nom;
		this.id = id;
		this.groupeType = groupeType;
		this.etudiants = etudiants;
	}

	public static Groupe buildGroupFromJSON(String jsonGroup) {
		// Construire un utilisateur
		String jsonUser = null;
		Utilisateur user = Utilisateur.buildUserFromJSON(jsonUser);
		return null;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GroupeType getGroupeType() {
		return groupeType;
	}

	public void setGroupeType(GroupeType groupeType) {
		this.groupeType = groupeType;
	}

	public List<Utilisateur> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Utilisateur> etudiants) {
		this.etudiants = etudiants;
	}
}
