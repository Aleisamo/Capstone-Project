package aleisamo.github.com.childadventure;

public class ChildPicture {

    private String name;
    private String storageFileName;
    private String childPictureDescription;
    private String urlPicture;

    public ChildPicture() {
    }

    public ChildPicture(String key, String childPictureDescription, String storageFileName, String urlPicture) {
        this.name = key;
        this.storageFileName = storageFileName;
        this.childPictureDescription = childPictureDescription;
        this.urlPicture = urlPicture;
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

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String  urlPicture) {
        this.urlPicture = urlPicture;
    }
}
