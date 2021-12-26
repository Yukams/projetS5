package back.frontobjects;

public class FrontUser extends FrontObject  {
    public String name;
    public String surname;

    public FrontUser(String name, String surname, int id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }
}
