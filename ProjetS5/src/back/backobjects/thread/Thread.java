package back.backobjects.thread;

import java.util.List;

public class Thread implements IThread {
	private int id;
	private String title;

	public Thread(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<IMessage> getMessagesIdInChronologicalOrder() {
		return null;
	}

}
