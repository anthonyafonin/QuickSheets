package anthonyafonin.quicksheets.database.Model;

public class TimesheetEntry {

    private int id, tsheetId, entryHours;
    private String jobType, customer, description, entryDate;

    // Constructor
    public TimesheetEntry() {}

    public TimesheetEntry(String jobType, String customer,
                          String description, int entryHours, String entryDate) {
        this.jobType = jobType;
        this.customer = customer;
        this.description = description;
        this.entryHours = entryHours;
        this.entryDate = entryDate;
    }

    public TimesheetEntry(int id, String jobType, String customer,
                    String description, int entryHours, String entryDate) {
        this.id = id;
        this.jobType = jobType;
        this.customer = customer;
        this.description = description;
        this.entryHours = entryHours;
        this.entryDate = entryDate;
    }

    // Setters
    public void setId(int id){
        this.id = id;
    }

    public void setJobType(String jobType){
        this.jobType = jobType;
    }

    public void setCustomer(String customer){
        this.customer = customer;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setEntryHours(int entryHours){
        this.entryHours = entryHours;
    }

    public void setEntryDate(String entryDate){
        this.entryDate = entryDate;
    }

    // Getters
    public int getId(){
        return this.id;
    }

    public String getJobType(){
        return this.jobType;
    }

    public String getCustomer(){
        return this.customer;
    }

    public String getDescription(){
        return this.description;
    }

    public double getEntryHours(){
        return this.entryHours;
    }

    public String getEntryDate(){
        return this.entryDate;
    }
}