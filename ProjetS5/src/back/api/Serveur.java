package back.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import back.fil.IFilDeDiscussion;
import back.fil.IMessage;
import back.groups.GroupeType;
import back.groups.IGroupe;
import back.users.IUtilisateur;

public class Serveur {
	/* Connexion utilisateur */
	public static IUtilisateur connect(String username, String password) {
		// TODO call SQL
		// Construire l'utilisateur OU renvoyer null si mdp incorrect
		return null;
	}

	/* Fil de discussion */
	public static IFilDeDiscussion createFilDeDiscussion(String titre, int authorId, int groupeId, int messageId) {
		int id = back.utils.Utils.createRandomId();
		List<Integer> messagesId = new LinkedList<>();
		messagesId.add(messageId);
		// TODO call SQL
		return null;
	}

	public static IFilDeDiscussion addMessageToFilDeDiscussion(int idFil, String contenu, int authorId, int groupeId) {
		// TODO call SQL
		return null;
	}

	public static IFilDeDiscussion modifyFilDeDiscussion(String titre, List<Integer> messagesId, int filId) {
		// TODO call SQL
		return null;
	}

	public static IFilDeDiscussion deleteFilDeDiscussion(int filId) {
		// TODO call SQL
		return null;
	}

	public static IFilDeDiscussion getFilDeDiscussion(int filId) {
		// TODO call SQL
		return null;
	}
	

	public static List<IFilDeDiscussion> getAllFilDeDiscussionForUser(int utilisateurId) {
		// TODO call SQL
		return null;
	}

	/* Message */
	public static IMessage createMessage(String contenu, int authorId, int groupeId) {
		int id = back.utils.Utils.createRandomId();
		// TODO call SQL
		return null;
	}

	public static IMessage modifyMessage(String contenu, int messageId) {
		// TODO call SQL
		return null;
	}

	public static IMessage deleteMessage(int messageId) {
		// TODO call SQL
		return null;
	}

	public static IMessage getMessage(int messageId) {
		// TODO call SQL
		return null;
	}

	/* Groupe */
	public static IGroupe createGroupe(String nom, GroupeType groupeType, List<Integer> etudiantsId) {
		int id = back.utils.Utils.createRandomId();
		// TODO call SQL
		return null;
	}

	public static IGroupe modifyGroupe(List<Integer> etudiantsId, int groupeId) {
		// TODO call SQL
		return null;
	}

	public static IGroupe deleteGroupe(int groupeId) {
		// TODO call SQL
		return null;
	}

	public static IGroupe getGroupe(int groupeId) {
		// TODO call SQL
		return null;
	}

	/* Utilisateurs */
	public static IUtilisateur createUtilisateur(String nom, String prenom, String username, Set<Integer> groupesId) {
		int id = back.utils.Utils.createRandomId();
		String password = back.utils.Utils.createRandomPassword();
		// TODO call SQL
		return null;
	}

	public static IUtilisateur modifyUtilisateur(String nom, String prenom, Set<Integer> groupesId, int utilisateurId) {
		// TODO call SQL
		return null;
	}

	public static IUtilisateur deleteUtilisateur(int utilisateurId) {
		// TODO call SQL
		return null;
	}

	public static IUtilisateur getUtilisateur(int utilisateurId) {
		// TODO call SQL
		return null;
	}

}
