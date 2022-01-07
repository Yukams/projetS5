package backobjects.thread;

import api.Server;
import frontobjects.FrontMessage;
import frontobjects.FrontThread;
import frontobjects.FrontUser;

import java.util.Date;
import java.util.Map;

import static main.mainBack.gson;

public interface IMessage {
	static String createMessage(Map<String, String> payload) {
		int authorId = Integer.parseInt(payload.get("authorId"));
		String content = payload.get("content");
		int threadId = Integer.parseInt(payload.get("threadId"));

		FrontMessage message = Server.createMessage(authorId, content, threadId);

		return gson.toJson(message);
	}

    static String deleteMessage(Map<String, String> payload) {
		int id = Integer.parseInt(payload.get("id"));
		FrontMessage message = Server.deleteMessage(id);

		return gson.toJson(message);
    }
}
