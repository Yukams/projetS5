package frontobjects;

import java.util.List;
import java.util.Objects;

public class FrontThread extends FrontObject {
    public String title;
    public FrontGroup group;
    public List<FrontMessage> messages;
    public int nbNotReadMessage;

    public FrontThread(){}

    public FrontThread(int id, String title, List<FrontMessage> messagesList, FrontGroup group, int nbNotReadMessage) {
        this.id = id;
        this.title = title;
        this.group = group;
        this.messages = messagesList;
        this.nbNotReadMessage = nbNotReadMessage;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontThread that = (FrontThread) o;
        return Objects.equals(title, that.title) && Objects.equals(group, that.group) && Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, group, messages);
    }
}
