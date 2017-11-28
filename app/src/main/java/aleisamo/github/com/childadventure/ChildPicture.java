package aleisamo.github.com.childadventure;

public class ChildPicture {

    private String name;
    private String childPictureURl;
    private String childPictureDescription;

    public ChildPicture(String key, String childPictureURl, String childpictureDescription){

        this.name = key;
        this.childPictureURl = childPictureURl;
        this.childPictureDescription = childpictureDescription;
    }

    public String getChildPictureURl() {
        return childPictureURl;
    }

    public void setChildPictureURl(String childPictureURl) {
        this.childPictureURl = childPictureURl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildPictureDescription() {
        return childPictureDescription;
    }

    public void setChildPictureDescription(String childPictureDescription) {
        this.childPictureDescription = childPictureDescription;
    }
}
