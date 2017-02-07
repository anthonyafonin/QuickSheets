package anthonyafonin.quicksheets.database.Model;

public class Timesheet {

    private long id;
    private String sheetTitle;
    private String startDate;
    private String endDate;
    private int yearDate;

    // Constructors
    public Timesheet() {}

    public Timesheet(String sheetTitle, String startDate,
                      String endDate, int yearDate) {
        this.sheetTitle = sheetTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearDate = yearDate;
    }

    public Timesheet(long id, String sheetTitle, String startDate,
                     String endDate, int yearDate) {
        this.id = id;
        this.sheetTitle = sheetTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.yearDate = yearDate;
    }

    // Setters
    public void setId(long id){
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

    // Getters
    public long getId(){
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
}

