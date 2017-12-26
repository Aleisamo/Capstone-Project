package aleisamo.github.com.childadventure;

public class Day {

    private String name;
    private String childKey;
    private Menu menu;

    public Day() {
    }

    public Day(String name, Menu menu, String childKey) {
        this.menu = menu;
        this.name = name;
        this.childKey = childKey;
    }

    public String getChildKey() {
        return childKey;
    }

    public void setChildKey(String childKey) {
        this.childKey = childKey;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
