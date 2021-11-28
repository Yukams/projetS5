package back.fil;

import java.util.Date;

public interface IMessage {
	public int getAuthorId();

	public int getId();

	public String getContenu();

	public Date getDate();

	public static IMessage createMessage(String contenu, int authorId, int groupeId) {
		return back.api.Serveur.createMessage(contenu, authorId, groupeId);
	}

	public static IMessage modifyMessage(String contenu, int messageId) {
		return back.api.Serveur.modifyMessage(contenu, messageId);
	}

	public static IMessage deleteMessage(int messageId) {
		return back.api.Serveur.deleteMessage(messageId);
	}

	public static IMessage getMessage(int messageId) {
		return back.api.Serveur.getMessage(messageId);
	}
}
