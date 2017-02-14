package anthonyafonin.quicksheets.database.Model;

public class Timesheet {

    private int id, yearDate, accountId;
    private String sheetTitle, startDate, endDate;

    // Constructors
    public Timesheet() {}

    public Timesheet(String sheetTitle, String startDate,
                      String endDate, int yearDate) {
        this.sheetTitle = sheetTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearDate = yearDate;
    }

    public Timesheet(int id, String sheetTitle, String startDate,
                     String endDate, int yearDate) {
        this.id = id;
        this.sheetTitle = sheetTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearDate = yearDate;
    }

    public Timesheet(int id, String sheetTitle, String startDate,
                     String endDate, int yearDate, int accountId) {
        this.id = id;
        this.sheetTitle = sheetTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearDate = yearDate;
        this.accountId = accountId;
    }

    // Setters
    public void setId(int id){
        this.id = id;
    }

    public void setSheetTitle(String sheetTitle){
        this.sheetTitle = sheetTitle;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public void setYearDate(int yearDate){
        this.yearDate = yearDate;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    // Getters
    public int getId(){
        return this.id;
    }

    public String getSheetTitle(){
        return this.sheetTitle;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public int getYearDate(){
        return this.yearDate;
    }

    public int getAccountId(){
        return this.accountId;
    }
}

