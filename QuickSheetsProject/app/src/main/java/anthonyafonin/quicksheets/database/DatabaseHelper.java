package anthonyafonin.quicksheets.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "quickSheetDatabase";

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
            + "("
            + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ACCOUNT_FIRST + "VARCHAR(20) NOT NULL,"
            + ACCOUNT_MIDDLE + "VARCHAR(20),"
            + ACCOUNT_LAST + "VARCHAR(20) NOT NULL,"
            + ACCOUNT_PHONE + "VARCHAR(15) NOT NULL,"
            + ACCOUNT_EMAIL + "VARCHAR(25) NOT NULL"
            + ")";

    // Create 'Time_Sheets' Table
    private static final String CREATE_TABLE_TIME_SHEETS = "CREATE TABLE " + TABLE_TIME_SHEET
            + "("
            + TIMESHEET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TIMESHEET_TITLE + "VARCHAR(25) NOT NULL,"
            + TIMESHEET_START + "DATE NOT NULL"
            + TIMESHEET_END + "DATE NOT NULL,"
            + TIMESHEET_YEAR + " INTEGER NOT NULL,"
            + TIMESHEET_ACCOUNT_ID + "INTEGER NOT NULL,"
            + "FOREIGN KEY(" + TIMESHEET_ACCOUNT_ID + ") REFERENCES "
            + TABLE_ACCOUNTS + "(" + ACCOUNT_ID + ")"
            + ")";

    // Create 'TS_Entry' Table
    private static final String CREATE_TABLE_TS_ENTRY = "CREATE TABLE " + TABLE_TS_ENTRY
            + "("
            + ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ENTRY_JOB_TYPE + "VARCHAR(20) NOT NULL,"
            + ENTRY_CUSTOMER + " VARCHAR(25) NOT NULL"
            + ENTRY_DESCRIPTION + " VARCHAR(50) NOT NULL,"
            + ENTRY_HOURS + " REAL NOT NULL"
            + ENTRY_DATE + "DATE NOT NULL,"
            + ENTRY_TIMESHEET_ID + "INTEGER NOT NULL,"
            + "FOREIGN KEY(" + ENTRY_TIMESHEET_ID + ") REFERENCES "
            + TABLE_TIME_SHEET + "(" + TIMESHEET_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_TIME_SHEETS);
        db.execSQL(CREATE_TABLE_TS_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
