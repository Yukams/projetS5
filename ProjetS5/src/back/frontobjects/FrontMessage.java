package back.frontobjects;

import java.util.Date;

public class FrontMessage {
    public FrontUser user;
    public String content;
    public Date date;
    public String status;

    public FrontMessage(FrontUser user, String text, Date date, String status) {
        this.user = user;
        this.content = text;
        this.date = date;
        this.status = status;
    }
}
