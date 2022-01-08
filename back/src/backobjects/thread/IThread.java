package backobjects.thread;

import api.Server;
import frontobjects.FrontMessage;
import frontobjects.FrontThread;
import frontobjects.FrontUser;

import java.util.List;
import java.util.Map;

import static main.mainBack.gson;

public interface IThread {
	static String createThread(Map<String, String> payload) {
		int authorId = Integer.parseInt(payload.get("authorId"));
		int groupId = Integer.parseInt(payload.get("groupId"));
		String content = payload.get("content");
		String title = payload.get("title");

		FrontThread thread = Server.createThread(authorId, groupId, content, title);

		return gson.toJson(thread);
	}

	static String updateMessagesStatus(Map<String, String> payload) {
		int userId = Integer.parseInt(payload.get("clientId"));
		int threadId = Integer.parseInt(payload.get("threadId"));
		FrontThread thread = Server.updateMessagesStatus(userId, threadId);

		return gson.toJson(thread);
	}

	static String getAllThreadForUser(Map<String, String> payload) {
		int userId = Integer.parseInt(payload.get("id"));
		List<FrontThread> threads = Server.getAllThreadForUser(userId);

		return gson.toJson(threads);
	}

	static String getAllThreadForUserById(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		List<FrontThread> threads = Server.getAllThreadForUser(id);

		return gson.toJson(threads);
	}

    static String deleteThread(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		FrontThread thread = Server.deleteThread(id);

		return gson.toJson(thread);
    }

	static String clientGetThreadsAtConnection(Map<String, String> payload) {
		int clientId = Integer.parseInt(payload.get("clientId"));
		List<FrontThread> thread = Server.clientGetThreadsAtConnection(clientId);

		return gson.toJson(thread);
	}
}
