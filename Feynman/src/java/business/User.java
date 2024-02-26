package business;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    Integer userID;
    String username;
    String password;
    Integer roleID;
    String firstName;
    String lastName; 
    ArrayList<String> roles;

    public User(Integer userID, String username, String password, 
                Integer roleID, ArrayList<String> roles, String firstName, 
                String lastName) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.roleID = roleID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
   
    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }    
    
    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
