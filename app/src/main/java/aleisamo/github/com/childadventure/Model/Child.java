package aleisamo.github.com.childadventure.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class Child implements Parcelable {

    private String name;
    private String key;
    private String age;
    private String pictureUrl;
    private ChildDetails childDetails;

    public Child() {
    }

    public Child(String name, String key, String age, String pictureUrl, ChildDetails childDetails) {
        this.name = name;
        this.key = key;
        this.age = age;
        this.pictureUrl = pictureUrl;
        this.childDetails = childDetails;

    }

    protected Child(Parcel in, String key) {
        name = in.readString();
        age = in.readString();
        pictureUrl = in.readString();
        this.key = key;
    }


    protected Child(Parcel in) {
        name = in.readString();
        key = in.readString();
        age = in.readString();
        pictureUrl = in.readString();
        childDetails = in.readParcelable(ChildDetails.class.getClassLoader());
    }

    public static final Creator<Child> CREATOR = new Creator<Child>() {
        @Override
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }

        @Override
        public Child[] newArray(int size) {
            return new Child[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getKey() {return key;}

    public ChildDetails getChildDetails() {return childDetails;}

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setKey(String key) {this.key = key;}

    public void setChildDetails(ChildDetails childDetails) {
        this.childDetails = childDetails;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "Child{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(age);
        dest.writeString(pictureUrl);
        dest.writeParcelable(childDetails, flags);
    }
}
