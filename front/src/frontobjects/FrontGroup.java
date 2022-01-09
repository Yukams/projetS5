package frontobjects;

import server.ServerInterface;

public class FrontGroup extends FrontObject {
    public String name;

    public FrontGroup(){
    }
    public FrontGroup(int id, String name, boolean isIn){
        this.id = id;
        this.name = isIn ? name+" (IN)" : name;
    }

    public FrontGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof FrontGroup frontGroup){
            return this.id == frontGroup.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31*this.id+this.name.hashCode();
    }


    @Override
    public String toString() {
        return this.name;
    }
}
