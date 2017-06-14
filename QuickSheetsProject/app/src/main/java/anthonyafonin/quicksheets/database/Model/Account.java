/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: Account.java
 */

/**
 * Contains all models of database tables.
 */
package anthonyafonin.quicksheets.database.Model;

/**
 * The model class of Account
 */
public class Account {

    // Declare private variables
    private int id;
    private String firstName, middleName, lastName, phoneNumber, email;

    /**
     * Empty Constructor
     */
    public Account(){}

    /**
     * The constructors of the class.
     * @param id Account ID.
     * @param firstName First name.
     * @param middleName Middle name.
     * @param lastName Last Name.
     * @param phoneNumber Phone Number.
     * @param email Email.
     */
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

    ////////////////////////////
    // Setters
    ///////////////////////////

    /**
     * Sets Account ID.
     * @param id Account ID.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets Account First Name.
     * @param firstName First Name.
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Sets Account Middle Name.
     * @param middleName First Name.
     */
    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    /**
     * Sets Account Last Name.
     * @param lastName First Name.
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Sets Account Phone Number.
     * @param phoneNumber Phone Number.
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets Account Email.
     * @param email Email Address.
     */
    public void setEmail(String email){
        this.email = email;
    }

    //////////////////////////
    // Getters
    //////////////////////////

    /**
     * Gets Account ID.
     * @return Account ID.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Gets Account First Name.
     * @return First Name.
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Gets Account Middle Name.
     * @return Middle Name.
     */
    public String getMiddleName(){
        return this.middleName;
    }

    /**
     * Gets Account Last Name.
     * @return Last Name.
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Gets Account Phone Number.
     * @return Phone Number.
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Gets Account Email.
     * @return Email.
     */
    public String getEmail(){
        return this.email;
    }
}
