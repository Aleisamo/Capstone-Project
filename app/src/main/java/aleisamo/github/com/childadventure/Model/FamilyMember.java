package aleisamo.github.com.childadventure.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class FamilyMember implements Parcelable{

    private String memberName1;
    private String memberName2;
    private String phoneNumber1;
    private String phoneNumber2;
    private String email1;

    protected FamilyMember(Parcel in) {
        memberName1 = in.readString();
        memberName2 = in.readString();
        phoneNumber1 = in.readString();
        phoneNumber2 = in.readString();
        email1 = in.readString();
        email2 = in.readString();
        address1 = in.readString();
        address2 = in.readString();
    }

    public static final Creator<FamilyMember> CREATOR = new Creator<FamilyMember>() {
        @Override
        public FamilyMember createFromParcel(Parcel in) {
            return new FamilyMember(in);
        }

        @Override
        public FamilyMember[] newArray(int size) {
            return new FamilyMember[size];
        }
    };

    @Override
    public String toString() {
        return "FamilyMember{" +
                "memberName1='" + memberName1 + '\'' +
                ", memberName2='" + memberName2 + '\'' +
                ", phoneNumber1='" + phoneNumber1 + '\'' +
                ", phoneNumber2='" + phoneNumber2 + '\'' +
                ", email1='" + email1 + '\'' +
                ", email2='" + email2 + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                '}';
    }

    private String email2;
    private String address1;
    private String address2;

    public FamilyMember() {
    }

    public FamilyMember(String memberName1, String memberName2, String phoneNumber1, String phoneNumber2, String email1, String email2, String address1, String address2) {
        this.memberName1 = memberName1;
        this.memberName2 = memberName2;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.email1 = email1;
        this.email2 = email2;
        this.address1 = address1;
        this.address2 = address2;
    }


    public String getMemberName1() {
        return memberName1;
    }

    public void setMemberName1(String memberName1) {
        this.memberName1 = memberName1;
    }

    public String getMemberName2() {
        return memberName2;
    }

    public void setMemberName2(String memberName2) {
        this.memberName2 = memberName2;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberName1);
        dest.writeString(memberName2);
        dest.writeString(phoneNumber1);
        dest.writeString(phoneNumber2);
        dest.writeString(email1);
        dest.writeString(email2);
        dest.writeString(address1);
        dest.writeString(address2);
    }
}
