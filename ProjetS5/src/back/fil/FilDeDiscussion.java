package back.fil;

import java.util.List;

public class FilDeDiscussion implements IFilDeDiscussion {
	private int id;
	private String titre;
	private int groupeId;
	private int firstMessageId;
	private int authorId;
	private List<Integer> messagesId;

	// Data sent by clients
	public FilDeDiscussion(String titre, int authorId, int groupeId) {
		this.titre = titre;
		this.authorId = authorId;
		this.groupeId = groupeId;
	}

	@Override
	public String getTitre() {
		return titre;
	}

	@Override
	public int getGroupeId() {
		return groupeId;
	}

	@Override
	public int getFirstMessageId() {
		return firstMessageId;
	}

	@Override
	public int getAuthorId() {
		return authorId;
	}

	@Override
	public List<Integer> getMessagesId() {
		return messagesId;
	}

	@Override
	public List<IMessage> getMessagesIdInChronologicalOrder() {
		/*
		 * List<IMessage> messagesList = new LinkedList<>(new Comparator<IMessage>()
		 * (IMessage m1, IMessage m2) -> { m1. });
		 */
		return null;
	}

}
