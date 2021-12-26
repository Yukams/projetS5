package back.frontobjects;

import java.util.ArrayList;
import java.util.List;

public class FrontThread {
    public List<FrontMessage> messages;
    public int id;
    public String title;


    public FrontThread(int id, String title, List<FrontMessage> messagesList) {
        this.id = id;
        this.title = title;
        this.messages = messagesList;
    }
}
