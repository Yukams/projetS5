package back.frontobjects;

import java.util.Date;

public class FrontMessage extends FrontObject  {
    public FrontUser user;
    public String content;
    public Date date;
    public String status;

    public FrontMessage(int id, FrontUser user, String text, Date date, String status) {
        this.id = id;
        this.user = user;
        this.content = text;
        this.date = date;
        this.status = status;
    }
}
