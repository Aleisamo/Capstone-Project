package aleisamo.github.com.childadventure.Model;

public class Childminder {

    private String name_Childminder;
    private String id;
    private int role;
    private User user;

    public void setName_Childminder(String name_Childminder) {
        this.name_Childminder = name_Childminder;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {

        return role;
    }

    public void setUser(User user) {
        this.user = user;

    }

    public String getName_Childminder() {

        return name_Childminder;
    }

    public User getUser() {
        return user;
    }

    public Childminder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Childminder(String name_childminder, String id, int role, User user) {
        name_Childminder = name_childminder;
        this.id = id;

        this.role = role;
        this.user = user;
    }


}
