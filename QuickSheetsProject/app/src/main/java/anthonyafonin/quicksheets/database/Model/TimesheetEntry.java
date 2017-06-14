/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: TimesheetEntry.java
 */

/**
 * Contains all models of database tables.
 */
package anthonyafonin.quicksheets.database.Model;

/**
 * The model class of Timesheet Entry.
 */
public class TimesheetEntry {

    // Declare private variables.
    private int id, tsheetId;
    private double entryHours;
    private String jobType, customer, description, entryDate;

    /**
     * Empty Constructor
     */
    public TimesheetEntry() {}

    /**
     * The constructors of the class.
     * @param jobType Job Type.
     * @param customer Customer.
     * @param description Job Description.
     * @param entryHours Entry Hours.
     * @param entryDate Entry Date.
     * @param tsheetId Timesheet ID.
     */
    public TimesheetEntry(String jobType, String customer,
                          String description, double entryHours, String entryDate, int tsheetId) {
        this.jobType = jobType;
        this.customer = customer;
        this.description = description;
        this.entryHours = entryHours;
        this.entryDate = entryDate;
        this.tsheetId = tsheetId;
    }


    ////////////////////////////
    // Setters
    ///////////////////////////

    /**
     * Sets Timesheet Entry ID.
     * @param id Timesheet Entry ID.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets Job Type
     * @param jobType Job Type.
     */
    public void setJobType(String jobType){
        this.jobType = jobType;
    }

    /**
     * Sets Customer
     * @param customer Customer.
     */
    public void setCustomer(String customer){
        this.customer = customer;
    }

    /**
     * Sets Job Description.
     * @param description Job Description.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Sets Entry Hours.
     * @param entryHours Entry Hours.
     */
    public void setEntryHours(double entryHours){ this.entryHours = entryHours;}

    /**
     * Sets Entry Date.
     * @param entryDate Entry Date.
     */
    public void setEntryDate(String entryDate){
        this.entryDate = entryDate;
    }

    /**
     * Sets Timesheet ID.
     * @param tsheetId Timesheet ID.
     */
    public void setTsheetId(int tsheetId){this.tsheetId = tsheetId;}

    //////////////////////////
    // Getters
    //////////////////////////

    /**
     * Gets Timesheet Entry ID.
     * @return id.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Gets Job Type.
     * @return Job Type.
     */
    public String getJobType(){
        return this.jobType;
    }

    /**
     * Gets Customer.
     * @return Customer.
     */
    public String getCustomer(){
        return this.customer;
    }

    /**
     * Gets Job Description.
     * @return Description.
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Gets Entry Hours.
     * @return Entry Hours.
     */
    public double getEntryHours(){
        return this.entryHours;
    }

    /**
     * Gets Entry Date.
     * @return Entry Date.
     */
    public String getEntryDate(){
        return this.entryDate;
    }

    /**
     * Gets Timesheet id.
     * @return Timesheet id.
     */
    public int getTsheetId(){ return this.tsheetId; }

    /**
     * Gets toString of Job description
     * @return Description.
     */
    public String toString() {
        return this.description;
    }
}