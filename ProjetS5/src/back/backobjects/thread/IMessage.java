package back.backobjects.thread;

import back.api.Server;
import back.frontobjects.FrontMessage;
import back.frontobjects.FrontUser;

import java.util.Date;
import java.util.Map;

import static back.main.mainBack.gson;

public interface IMessage {
	int getAuthorId();

	int getId();

	String getContenu();

	Date getDate();

	static String createMessage(Map<String, String> payload) {
		int authorId = Integer.parseInt(payload.get("authorId"));
		String content = payload.get("content");
		int threadId = Integer.parseInt(payload.get("threadId"));

		FrontMessage message = Server.createMessage(authorId, content, threadId);

		return gson.toJson(message);
	}

	static IMessage modifyMessage(String contenu, int messageId) {
		return Server.modifyMessage(contenu, messageId);
	}

	static IMessage deleteMessage(int messageId) {
		return Server.deleteMessage(messageId);
	}

	static IMessage getMessage(int messageId) {
		return Server.getMessage(messageId);
	}
}
