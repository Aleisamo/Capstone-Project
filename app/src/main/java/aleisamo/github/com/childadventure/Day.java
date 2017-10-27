package aleisamo.github.com.childadventure;

public class Day {

    private String id;
    private String dayName;

    public Day(){
    }

    public Day(String id, String nameDay) {
        this.id = id;
        this.dayName = nameDay;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getKey() {
        return id;
    }

    public void setKey(String key) {
        this.id = key;
    }


}
