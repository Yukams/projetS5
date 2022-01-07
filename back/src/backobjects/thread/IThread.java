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

	static String updateMessages(Map<String, String> payload) {
		int userId = Integer.parseInt(payload.get("clientId"));
		int threadId = Integer.parseInt(payload.get("threadId"));
		FrontThread thread = Server.updateMessages(userId, threadId);

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
}
