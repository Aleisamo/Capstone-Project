package aleisamo.github.com.childadventure;

class ChildDetails {

    private String name;
    private String age;
    private String pictureUrl;
    private String dob;
    private FamilyMember familyMember;
    private String address;
    private String languages;
    private String allergies;
    private String relevantInformation;

    public ChildDetails(String name, String age, String pictureUrl, String dob, FamilyMember familyMember, String address, String languages, String allergies, String relevantInformation) {
        this.name = name;
        this.age = age;
        this.pictureUrl = pictureUrl;
        this.dob = dob;
        this.familyMember = familyMember;
        this.address = address;
        this.languages = languages;
        this.allergies = allergies;
        this.relevantInformation = relevantInformation;
    }

    public ChildDetails() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getRelevantInformation() {
        return relevantInformation;
    }

    public void setRelevantInformation(String relevantInformation) {
        this.relevantInformation = relevantInformation;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }
}
