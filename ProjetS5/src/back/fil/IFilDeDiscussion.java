package back.fil;

import java.util.List;

public interface IFilDeDiscussion {
	public String getTitre();

	public int getGroupeId();

	public int getFirstMessageId();

	public int getAuthorId();

	public List<Integer> getMessagesId();

	public List<IMessage> getMessagesIdInChronologicalOrder();

	public static IFilDeDiscussion createFilDeDiscussion(String titre, int authorId, int groupeId, String contenu) {
		Message message = (Message) back.api.Serveur.createMessage(contenu, authorId, groupeId);
		return back.api.Serveur.createFilDeDiscussion(titre, message.getAuthorId(), groupeId, message.getId());
	}

	public static IFilDeDiscussion addMessageToFilDeDiscussion(int idFil, String contenu, int authorId, int groupeId) {
		return back.api.Serveur.addMessageToFilDeDiscussion(idFil, contenu, authorId, groupeId);
	}

	public static IFilDeDiscussion modifyFilDeDiscussion(String titre, List<Integer> messagesId, int filId) {
		return back.api.Serveur.modifyFilDeDiscussion(titre, messagesId, filId);
	}

	public static IFilDeDiscussion deleteFilDeDiscussion(int filId) {
		return back.api.Serveur.deleteFilDeDiscussion(filId);
	}

	public static IFilDeDiscussion getFilDeDiscussion(int filId) {
		return back.api.Serveur.getFilDeDiscussion(filId);
	}

	public static List<IFilDeDiscussion> getAllFilDeDiscussionForUser(int utilisateurId) {
		return back.api.Serveur.getAllFilDeDiscussionForUser(utilisateurId);
	}
}
