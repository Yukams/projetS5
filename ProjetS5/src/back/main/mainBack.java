package back.main;

import java.util.Set;

import back.users.Utilisateur;

public class mainBack {

	public static void main(String[] args) {
		// TODO
		// Etape 1 : Creer la database
		createDatabase();
		// Etape 2 : Peupler la database
		fillDatabase();
		// Etape 3 : Lancer l'application
		lancerApplication();
	}

	// Lance l'application
	private static void lancerApplication() {
		// Etape 1 : En mode ecoute pasive
		// Etape 2 : Recevoir une demande du l'UI
		// Etape 3 : Traiter la demande
		/* ex :
		int id = 0;
		Utilisateur user = (Utilisateur) IUtilisateur.getUtilisateur(id);
		Set<Integer> set = user.getGroupesId();
		for (int groupeId : set) {
			Groupe groupe = (Groupe) IGroupe.getGroupe(id);
			groupe.getNom();
		}*/
	}
	
	private static int connexionUtilisateur(String username, String password) {
		Utilisateur user = (Utilisateur) back.api.Serveur.connect(username, password);
		return user.getId();
	}

	// Remplis la base de donnee
	private static void fillDatabase() {

	}

	// Cree la base de donnee
	private static void createDatabase() {

	}

}
