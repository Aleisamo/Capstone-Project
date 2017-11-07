package aleisamo.github.com.childadventure;

class ChildDetails {

    private String name;
    private String age;
    private String pictureUrl;
    private String dob;
    private String parentsName;
    private String parentsPhone;
    private String address;
    private String languages;
    private String allergies;
    private String immunisation_record;
    private String relevantInformation;

public ChildDetails(String name, String age, String pictureUrl, String dob, String parentsName, String parentsPhone, String adresse, String languages, String allergies, String inmunisation_record, String relevantInformation){
    this.name = name;
    this.age = age;
    this.pictureUrl = pictureUrl;
    this.dob = dob;
    this.parentsName = parentsName;
    this.parentsPhone = parentsPhone;
    this.address = adresse;
    this.languages = languages;
    this.allergies = allergies;
    this.immunisation_record = inmunisation_record;
    this.relevantInformation = relevantInformation;
}
public ChildDetails(){}


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

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getParentsPhone() {
        return parentsPhone;
    }

    public void setParentsPhone(String parentsPhone) {
        this.parentsPhone = parentsPhone;
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

    public String getImmunisation_record() {
        return immunisation_record;
    }

    public void setImmunisation_record(String immunisation_record) {
        this.immunisation_record = immunisation_record;
    }

    public String getRelevantInformation() {
        return relevantInformation;
    }

    public void setRelevantInformation(String relevantInformation) {
        this.relevantInformation = relevantInformation;
    }
}
