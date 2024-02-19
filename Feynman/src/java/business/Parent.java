package business;

import java.io.Serializable;
import java.util.ArrayList;

public class Parent extends User implements Serializable  {
    ArrayList<Integer> childrenIDs;

    public Parent(ArrayList<Integer> childrenIDs, String username, 
            String password, Integer roleID, String firstName, String lastName) 
    {
        super(username, password, roleID, firstName, lastName);
        this.childrenIDs = childrenIDs;
    }

    public ArrayList<Integer> getChildrenIDs() {
        return childrenIDs;
    }

    public void setChildrenIDs(ArrayList<Integer> childrenIDs) {
        this.childrenIDs = childrenIDs;
    }
}
