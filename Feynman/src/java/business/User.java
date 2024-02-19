package business;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    String password;
    Integer roleID;
    String firstName;
    String lastName; 

    public User(String username, String password, Integer roleID, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.roleID = roleID;
        this.firstName = firstName;
        this.lastName = lastName;
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
