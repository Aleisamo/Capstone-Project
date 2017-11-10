package aleisamo.github.com.childadventure;

public class FamilyMember {

    private String memberName1;
    private String memberName2;
    private String phoneNumber1;
    private String phoneNumber2;
    private String email1;
    private String email2;
    private String address1;
    private String address2;

    public FamilyMember(){}

    public FamilyMember(String memberName1, String memberName2, String phoneNumber1, String phoneNumber2, String email1, String email2, String address1, String address2){
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
}
