package back.users;

import java.util.List;
import java.util.Set;

import back.fil.IFilDeDiscussion;

public interface IUtilisateur {
	public int getId();

	public String getNom();

	public String getPrenom();

	public Set<Integer> getGroupesId();

	public boolean isConnected();

	public String getUsername();

	public static IUtilisateur createUtilisateur(String nom, String prenom, String username, Set<Integer> groupesId) {
		return back.api.Serveur.createUtilisateur(nom, prenom, username, groupesId);
	}

	public static IUtilisateur modifyUtilisateur(String nom, String prenom, Set<Integer> groupesId, int utilisateurId) {
		return back.api.Serveur.modifyUtilisateur(nom, prenom, groupesId, utilisateurId);
	}

	public static IUtilisateur deleteUtilisateur(int utilisateurId) {
		return back.api.Serveur.deleteUtilisateur(utilisateurId);
	}

	public static IUtilisateur getUtilisateur(int utilisateurId) {
		return back.api.Serveur.getUtilisateur(utilisateurId);
	}

	public static List<IFilDeDiscussion> getAllFilDeDiscussionForUser(int utilisateurId) {
		return IFilDeDiscussion.getAllFilDeDiscussionForUser(utilisateurId);
	}
}
