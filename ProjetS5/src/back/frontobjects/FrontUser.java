package back.frontobjects;

public class FrontUser extends FrontObject  {
    public String name;
    public String surname;
    public boolean isAdmin;

    public FrontUser(String name, String surname, int id, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.isAdmin = isAdmin;
    }
}
