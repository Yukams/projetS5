package back.frontobjects;


public class FrontMessage extends FrontObject  {
    public FrontUser user;
    public String content;
    public long date;
    public String status;

    public FrontMessage(int id, FrontUser user, String text, long date, String status) {
        this.id = id;
        this.user = user;
        this.content = text;
        this.date = date;
        this.status = status;
    }
}
