package frontobjects;

import java.util.Objects;

public class FrontUser extends FrontObject {
    public String name;
    public String surname;
    public boolean isAdmin;

    public FrontUser(){
    }

    public FrontUser(String name, String surname, int id, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof FrontUser){
            FrontUser frontUser = (FrontUser) o;
            return this.isAdmin == frontUser.isAdmin && this.name.equals(frontUser.name) && this.surname.equals(frontUser.surname);
        } return false;
    }

    @Override
    public int hashCode() {
        return 31 * this.name.hashCode() + this.surname.hashCode() + Boolean.hashCode(this.isAdmin);
    }

    @Override
    public String toString() {
        return name + " "+ surname;
    }
}
