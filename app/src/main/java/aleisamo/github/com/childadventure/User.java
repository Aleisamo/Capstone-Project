package aleisamo.github.com.childadventure;

import java.util.ArrayList;

public class User {

    private String userName;
    private String email;
    private ArrayList<String>key;
    private Integer role;


    public User() {
    }


    public User(String userName, String email, ArrayList<String> key, Integer role) {
        this.userName = userName;
        this.email = email;
        this.key = key;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKey(ArrayList<String> key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getKey() {
        return key;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
