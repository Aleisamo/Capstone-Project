package aleisamo.github.com.childadventure;

import java.util.Objects;

public class Menu {

    private String day;
    private String breakfast;
    private String lunch;
    private String snacks;
    private String dinner;

    public Menu(String breakfast, String snacks, String lunch, String dinner, String day){
        this.breakfast= breakfast;
        this.snacks= snacks;
        this.lunch= lunch;
        this.dinner= dinner;
        this.day= day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(day, menu.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day);
    }
}
