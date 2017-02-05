package anthonyafonin.quicksheets.database.Model;

public class Account {

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;

    // Constructors
    public Account(){}
    public Account(int id, String firstName, String middleName, String lastName,
                   String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public Account(String firstName, String middleName, String lastName,
                   String phoneNumber, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Setters
    public void setId(int id){
        this.id = id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email){
        this.email = email;
    }

    // Getters
    public long getId(){
        return this.id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getMiddleName(){
        return this.middleName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getEmail(){
        return this.email;
    }
}
