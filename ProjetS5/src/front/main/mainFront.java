package front.main;

import front.affichage.ConnexionWindow;
import front.utils.Utils;

public class mainFront {
	public static Utils utils = new Utils();
	public static void main(String[] args){
		launchConnexionWindow();
	}

	/*-------------------------------------------------------------------*/

	// Launch connexion window
	private static void launchConnexionWindow() {
		ConnexionWindow fen = new ConnexionWindow();
		fen.setVisible(true);
	}

	// Affiche la messagerie
	/*private static void afficherMessagerie(int userId) {
		// TODO
		// Etape 1 : Call back pour recuperer les fils de discussion
		getAllFilDeDiscussionForUser(userId);
		// Etape 2 : Gérer tous les affichages en fonction
	}*/
	
	// Call pour recuperer les fils de discussion
	/*private static List<FilDeDiscussion> getAllFilDeDiscussionForUser(int userId) {
		// TODO
		// Recupérer les données sous format JSON et les reconstruire
		String json = null;
		List<FilDeDiscussion> fils = FilDeDiscussion.buildFilDeDiscussionListFromJSON(json);
		return null;
	}*/
}
