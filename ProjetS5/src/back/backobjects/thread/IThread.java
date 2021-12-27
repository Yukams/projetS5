package back.backobjects.thread;

import back.api.Server;
import back.frontobjects.FrontMessage;
import back.frontobjects.FrontThread;
import back.frontobjects.FrontUser;

import java.util.List;
import java.util.Map;

import static back.main.mainBack.gson;

public interface IThread {
	String getTitle();

	List<IMessage> getMessagesIdInChronologicalOrder();

	static String createThread(Map<String, String> payload) {
		int authorId = Integer.parseInt(payload.get("authorId"));
		int groupId = Integer.parseInt(payload.get("groupId"));
		String content = payload.get("content");
		String title = payload.get("title");

		FrontThread thread = Server.createThread(authorId, groupId, content, title);

		return gson.toJson(thread);
	}

	static IThread addMessageToThread(int idFil, String contenu, int authorId, int GroupId) {
		return Server.addMessageToThread(idFil, contenu, authorId, GroupId);
	}

	static IThread modifyThread(String title, List<Integer> messagesId, int threadId) {
		return Server.modifyThread(title, messagesId, threadId);
	}

	static IThread deleteThread(int threadId) {
		return Server.deleteThread(threadId);
	}

	static FrontThread getThread(int threadId) {
		return Server.getThread(threadId);
	}

	static String getAllThreadForUser(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		List<FrontThread> threads = Server.getAllThreadForUser(id);

		return gson.toJson(threads);
	}
}
