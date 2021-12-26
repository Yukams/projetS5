package back.frontobjects;

import java.util.List;

public class FrontThread extends FrontObject {
    public List<FrontMessage> messages;
    public String title;


    public FrontThread(int id, String title, List<FrontMessage> messagesList) {
        this.id = id;
        this.title = title;
        this.messages = messagesList;
    }
}
