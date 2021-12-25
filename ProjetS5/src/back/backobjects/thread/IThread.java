package back.backobjects.thread;

import back.api.Server;

import java.util.List;

public interface IThread {
	public String getTitle();

	public List<IMessage> getMessagesIdInChronologicalOrder();

	public static IThread createThread(String title, int authorId, int GroupId, String contenu) {
		Message message = (Message) Server.createMessage(contenu, authorId, GroupId);
		return Server.createThread(title, message.getAuthorId(), GroupId, message.getId());
	}

	public static IThread addMessageToThread(int idFil, String contenu, int authorId, int GroupId) {
		return Server.addMessageToThread(idFil, contenu, authorId, GroupId);
	}

	public static IThread modifyThread(String title, List<Integer> messagesId, int threadId) {
		return Server.modifyThread(title, messagesId, threadId);
	}

	public static IThread deleteThread(int threadId) {
		return Server.deleteThread(threadId);
	}

	public static IThread getThread(int threadId) {
		return Server.getThread(threadId);
	}

	public static List<IThread> getAllThreadForUser(int UserId) {
		return Server.getAllThreadForUser(UserId);
	}
}
