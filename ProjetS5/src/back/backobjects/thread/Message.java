package back.backobjects.thread;

import java.util.Date;
import java.util.List;

public class Message implements IMessage {
	private int authorId;
	private int id;
	private String contenu;
	private Date date;
	private List<Integer> UserAyantLu;

	public Message(int authorId, String contenu) {
		this.authorId = authorId;
		this.contenu = contenu;
		this.date = new Date(System.currentTimeMillis());
	}

	@Override
	public int getAuthorId() {
		return authorId;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getContenu() {
		return contenu;
	}

	@Override
	public Date getDate() {
		return date;
	}
}
