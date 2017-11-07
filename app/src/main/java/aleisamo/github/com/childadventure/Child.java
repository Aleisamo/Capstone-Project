package aleisamo.github.com.childadventure;

public class Child {

    private String name;
    private String age;
    private String pictureUrl;
    private ChildDetails childDetails;


    public Child(){}

    public Child (String name, String age, String pictureUrl, ChildDetails chilcDetails){
        this.name =name;
        this.age =age;
        this.pictureUrl =pictureUrl;
        this.childDetails = chilcDetails;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setChildDetails(ChildDetails childDetails) {
        this.childDetails = childDetails;
    }

    public ChildDetails getChildDetails() {
        return childDetails;
    }
}
