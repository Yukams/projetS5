package front.frontobjects;

public class FrontGroup extends FrontObject {
    public String name;

    public FrontGroup(){
    }

    public FrontGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof FrontGroup){
            FrontGroup frontGroup = (FrontGroup) obj;
            return this.name.equals(frontGroup.name) && this.id == frontGroup.id;
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
