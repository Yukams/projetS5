package front.main;

import java.util.LinkedList;
import java.util.List;

import front.fil.FilDeDiscussion;
import front.users.Utilisateur;

public class mainFront {
	public static void main(String[] args) {
		// TODO
		// Etape 1 : Lancer l'interface UI
		lancerApplicationClient();
		// Etape 2 : Gerer la page d'accueil
		afficherPageAccueil();
		// Etape 3 : Se connecter
		int userId;
		String password;
		String username;
		do {
			username = getUsername();
			password = getPassword();

			if ((userId = connexionUtilisateur(username, password)) == 0) {
				afficherMessageErreur();
			}
		} while (userId == 0);
		// Etape 4 : Affiche la page de messagerie
		afficherMessagerie(userId);
	}

	// Affiche la messagerie
	private static void afficherMessagerie(int userId) {
		// TODO
		// Etape 1 : Call back pour recuperer les fils de discussion
		getAllFilDeDiscussionForUser(userId);
		// Etape 2 : Gérer tous les affichages en fonction
	}
	
	// Call pour recuperer les fils de discussion
	private static List<FilDeDiscussion> getAllFilDeDiscussionForUser(int userId) {
		// TODO
		// Recupérer les données sous format JSON et les reconstruire
		String json = null;
		List<FilDeDiscussion> fils = FilDeDiscussion.buildFilDeDiscussionListFromJSON(json);
		return null;
	}

	// Affiche le message d'erreur
	private static void afficherMessageErreur() {
		// TODO
	}

	// Lance l'application cote client
	private static void lancerApplicationClient() {
		// TODO
	}

	// Affiche la page d'accueil
	private static void afficherPageAccueil() {
		// TODO
	}

	// Call verifiant le password et le username
	private static int connexionUtilisateur(String username, String password) {
		// TODO
		return 0;
	}

	// Recupere le username de l'UI
	private static String getUsername() {
		return null;
	}

	// Recupere le password de l'UI
	private static String getPassword() {
		return null;
	}

}
