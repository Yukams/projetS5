package front.main;

import java.util.LinkedList;
import java.util.List;

import front.affichage.FenetreConnexion;
import front.fil.FilDeDiscussion;
import front.users.Utilisateur;

public class mainFront {
	public static void main(String[] args) {
		// TODO
		// Etape 1 : Lancer l'interface UI
		lancerApplicationClient();
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

	private static int connexionUtilisateur(String username, String password) {
		// TODO
		return 0;
	}

	// Lance l'application cote client
	private static void lancerApplicationClient() {
		// TODO
		FenetreConnexion fen = new FenetreConnexion();
		fen.setVisible(true);
	}

	// Affiche la page d'accueil
	private static void afficherPageAccueil() {
		// TODO
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
