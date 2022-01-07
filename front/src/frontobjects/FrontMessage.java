package frontobjects;


import java.util.Objects;

public class FrontMessage extends FrontObject {
    public FrontUser user;
    public String content;
    public long date;
    public String status;

    public FrontMessage(){}
    public FrontMessage(int id, FrontUser user, String text, long date, String status) {
        this.id = id;
        this.user = user;
        this.content = text;
        this.date = date;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontMessage that = (FrontMessage) o;
        return date == that.date && Objects.equals(user, that.user) && Objects.equals(content, that.content) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, content, date, status);
    }
}
