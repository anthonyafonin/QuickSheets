/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: Timesheet.java
 */

/**
 * Contains all models of database tables.
 */
package anthonyafonin.quicksheets.database.Model;

/**
 * The model class of Timesheet
 */
public class Timesheet {

    // Declare private variables
    private int id, yearDate, accountId;
    private String sheetTitle, startDate, endDate;

    /**
     * Empty Constructor
     */
    public Timesheet() {}

    /**
     * The constructors of the class.
     * @param sheetTitle Timesheet Title.
     * @param startDate Start Date.
     * @param endDate End Date.
     * @param yearDate Year.
     * @param accountId Account ID.
     */
    public Timesheet(String sheetTitle, String startDate,
                     String endDate, int yearDate, int accountId) {
        this.sheetTitle = sheetTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearDate = yearDate;
        this.accountId = accountId;
    }

    ////////////////////////////
    // Setters
    ///////////////////////////

    /**
     * Sets Timesheet ID.
     * @param id Timesheet ID.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets Timesheet Title
     * @param sheetTitle Timesheet Title.
     */
    public void setSheetTitle(String sheetTitle){
        this.sheetTitle = sheetTitle;
    }

    /**
     * Sets Timesheet Start Date.
     * @param startDate Start Date.
     */
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    /**
     * Sets Timesheet End Date.
     * @param endDate End Date.
     */
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    /**
     * Sets Timesheet Year.
     * @param yearDate Year.
     */
    public void setYearDate(int yearDate){
        this.yearDate = yearDate;
    }

    /**
     * Sets Timesheet Account ID.
     * @param accountId Account ID.
     */
    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    //////////////////////////
    // Getters
    //////////////////////////

    /**
     * Gets Timesheet ID.
     * @return Timesheet ID.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Gets Timesheet Title.
     * @return Title.
     */
    public String getSheetTitle(){
        return this.sheetTitle;
    }

    /**
     * Gets Timesheet Start Date.
     * @return Start Date.
     */
    public String getStartDate(){
        return this.startDate;
    }

    /**
     * Gets Timesheet End Date.
     * @return End Date.
     */
    public String getEndDate(){
        return this.endDate;
    }

    /**
     * Gets Timesheet Year.
     * @return Year.
     */
    public int getYearDate(){
        return this.yearDate;
    }

    /**
     * Gets Timesheet Account ID.
     * @return Account ID.
     */
    public int getAccountId(){
        return this.accountId;
    }

    /**
     * To String.
     * @return sheetTitle.
     */
    public String toString() {
        return this.sheetTitle;
    }
}

