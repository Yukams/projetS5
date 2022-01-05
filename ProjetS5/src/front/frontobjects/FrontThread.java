package front.frontobjects;

import java.util.List;

public class FrontThread extends FrontObject {
    public String title;
    public int groupId;
    public List<FrontMessage> messages;

    public FrontThread(){}

    public FrontThread(int id, String title, List<FrontMessage> messagesList, int groupId) {
        this.id = id;
        this.title = title;
        this.groupId = groupId;
        this.messages = messagesList;
    }
}
