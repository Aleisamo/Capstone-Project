package aleisamo.github.com.childadventure;

public class Menu {

    private String breakfast;
    private String lunch;
    private String snacks;
    private String dinner;

    public Menu() {
    }

    public Menu(String breakfast, String snacks, String lunch, String dinner) {
        this.breakfast = breakfast;
        this.snacks = snacks;
        this.lunch = lunch;
        this.dinner = dinner;
    }


    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getSnacks() {
        return snacks;
    }

    public void setSnacks(String snacks) {
        this.snacks = snacks;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

}
