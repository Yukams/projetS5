package back.backobjects.thread;

import back.api.Server;

import java.util.Date;

public interface IMessage {
	public int getAuthorId();

	public int getId();

	public String getContenu();

	public Date getDate();

	public static IMessage createMessage(String contenu, int authorId, int GroupId) {
		return Server.createMessage(contenu, authorId, GroupId);
	}

	public static IMessage modifyMessage(String contenu, int messageId) {
		return Server.modifyMessage(contenu, messageId);
	}

	public static IMessage deleteMessage(int messageId) {
		return Server.deleteMessage(messageId);
	}

	public static IMessage getMessage(int messageId) {
		return Server.getMessage(messageId);
	}
}
