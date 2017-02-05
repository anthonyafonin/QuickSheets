package anthonyafonin.quicksheets.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;

// Import Database Model Classes
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

public class DatabaseHelper extends SQLiteOpenHelper{

    // Logcat tag
    public static final String TAG = DatabaseHelper.class.getSimpleName();

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "QuickSheets.db";

    //--------------------------------------------------
    // Table Names and Attributes
    //--------------------------------------------------

    // Table Names
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_TIME_SHEET = "time_sheet";
    private static final String TABLE_TS_ENTRY = "ts_entry";

    // 'Accounts' Table Attributes
    private static final String ACCOUNT_ID = "account_id";
    private static final String ACCOUNT_FIRST = "first_name";
    private static final String ACCOUNT_MIDDLE = "middle_name";
    private static final String ACCOUNT_LAST = "last_name";
    private static final String ACCOUNT_PHONE = "phone_number";
    private static final String ACCOUNT_EMAIL = "email";

    // 'Time_Sheet' Table Attributes
    private static final String TIMESHEET_ID = "sheet_id";
    private static final String TIMESHEET_TITLE = "sheet_title";
    private static final String TIMESHEET_START = "start_date";
    private static final String TIMESHEET_END = "end_date";
    private static final String TIMESHEET_YEAR = "sheet_year";
    private static final String TIMESHEET_ACCOUNT_ID = "ts_account_id";

    // 'TS_Entry' Table Attributes
    private static final String ENTRY_ID = "entry_id";
    private static final String ENTRY_JOB_TYPE = "job_type";
    private static final String ENTRY_CUSTOMER = "customer";
    private static final String ENTRY_DESCRIPTION = "description";
    private static final String ENTRY_HOURS = "hours";
    private static final String ENTRY_DATE = "entry_date";
    private static final String ENTRY_TIMESHEET_ID = "entry_ts_id";

    //--------------------------------------------------
    // Table Create SQL Statements
    //--------------------------------------------------

    // Create 'Accounts' Table
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNTS
            + " ("
            + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ACCOUNT_FIRST + " VARCHAR(20) NOT NULL, "
            + ACCOUNT_MIDDLE + " VARCHAR(20), "
            + ACCOUNT_LAST + " VARCHAR(20) NOT NULL, "
            + ACCOUNT_PHONE + " VARCHAR(15) NOT NULL, "
            + ACCOUNT_EMAIL + " VARCHAR(25) NOT NULL "
            + ")";

    // Create 'Time_Sheets' Table
    private static final String CREATE_TABLE_TIME_SHEETS = "CREATE TABLE " + TABLE_TIME_SHEET
            + "("
            + TIMESHEET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TIMESHEET_TITLE + " VARCHAR(25) NOT NULL, "
            + TIMESHEET_START + " DATE NOT NULL, "
            + TIMESHEET_END + " DATE NOT NULL, "
            + TIMESHEET_YEAR + " INTEGER NOT NULL, "
            + TIMESHEET_ACCOUNT_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + TIMESHEET_ACCOUNT_ID + ") REFERENCES "
            + TABLE_ACCOUNTS + "(" + ACCOUNT_ID + ")"
            + ")";

    // Create 'TS_Entry' Table
    private static final String CREATE_TABLE_TS_ENTRY = "CREATE TABLE " + TABLE_TS_ENTRY
            + "("
            + ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ENTRY_JOB_TYPE + " VARCHAR(20) NOT NULL,"
            + ENTRY_CUSTOMER + " VARCHAR(25) NOT NULL, "
            + ENTRY_DESCRIPTION + " VARCHAR(50) NOT NULL,"
            + ENTRY_HOURS + " REAL NOT NULL, "
            + ENTRY_DATE + " DATE NOT NULL, "
            + ENTRY_TIMESHEET_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + ENTRY_TIMESHEET_ID + ") REFERENCES "
            + TABLE_TIME_SHEET + "(" + TIMESHEET_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_TIME_SHEETS);
        db.execSQL(CREATE_TABLE_TS_ENTRY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_TABLE_ACCOUNT);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_TABLE_TIME_SHEETS);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_TABLE_TS_ENTRY);

    }

    //--------------------------------------------------
    // Account Create, Read, Update, Delete Operations
    //--------------------------------------------------

    // Add Account
    public void addAccount(Account account){

        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_FIRST, account.getFirstName());
        values.put(ACCOUNT_MIDDLE, account.getMiddleName());
        values.put(ACCOUNT_LAST, account.getLastName());
        values.put(ACCOUNT_PHONE, account.getPhoneNumber());
        values.put(ACCOUNT_EMAIL, account.getEmail());

        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close(); // Closing database connection
    }

    // Get single Account
    public Account getAccount(int id){
        return null;
    }

    // Get all Accounts
    public List<Account> getAllAccounts(){
        return null;
    }

    // Get Account count
    public int getAccountCount(){
        return 0;
    }

    // Update single Account
    public int updateAccount(Account account){
        return 0;
    }

    // Delete single Account
    public void deleteAccount(Account account){}

    //--------------------------------------------------
    // TimeSheet CRUD Operations
    //--------------------------------------------------

    // Add Timesheet
    public void addTimesheet(Timesheet tsheet){}

    // Get single Timesheet
    public Timesheet getTimesheet(int id){
        return null;
    }

    // Get all Timesheets
    public List<Timesheet> getAllTimesheets(){
        return null;
    }

    // Get Timesheet count
    public int getTimesheetCount(){
        return 0;
    }

    // Update single Timesheet
    public int updateTimesheet(Timesheet tsheet){
        return 0;
    }

    // Delete single Timesheet
    public void deleteTimesheet(Timesheet tsheet){}

    //--------------------------------------------------
    // TimeSheet Entry CRUD Operations
    //--------------------------------------------------

    // Add Entry
    public void addEntry(TimesheetEntry entry){}

    // Get single Entry
    public TimesheetEntry getEntry(int id){
        return null;
    }

    // Get all Entrys
    public List<TimesheetEntry> getAllEntrys(){
        return null;
    }

    // Get Entry count
    public int getEntryCount(){
        return 0;
    }

    // Update single Entry
    public int updateEntry(TimesheetEntry entry){
        return 0;
    }

    // Delete single Entry
    public void deleteEntry(TimesheetEntry entry){}
}
