package frontobjects;

import java.util.List;

public class FrontThread extends FrontObject {
    public String title;
    public FrontGroup group;
    public List<FrontMessage> messages;
    public int nbNotReadMessage;


    public FrontThread(int id, String title, List<FrontMessage> messagesList, FrontGroup group, int nbNotReadMessage) {
        this.id = id;
        this.title = title;
        this.group = group;
        this.messages = messagesList;
        this.nbNotReadMessage = nbNotReadMessage;
    }
}
