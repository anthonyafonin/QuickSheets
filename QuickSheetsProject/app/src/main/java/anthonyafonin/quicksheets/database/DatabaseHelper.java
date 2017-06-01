package anthonyafonin.quicksheets.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

// Import Database Model Classes
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

public class DatabaseHelper extends SQLiteOpenHelper{

    // Logcat tag
    public static final String TAG = DatabaseHelper.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

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

    // Get Account id from email
    public int getAccountIdByEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        //Initialize query() parameters
        String[] account = { ACCOUNT_ID };
        String[] selectionArgs ={ email };
        String selection = ACCOUNT_EMAIL + " = ?";

        Cursor cursor = db.query(TABLE_ACCOUNTS,
                account, selection, selectionArgs, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();
        int accountId = Integer.parseInt(cursor.getString(0));

        //Account account = new Account(Integer.parseInt(cursor.getString(0)),
         //       cursor.getString(1),cursor.getString(2),cursor.getString(3),
          //      cursor.getString(4),cursor.getString(5));

        cursor.close();
        return accountId;
    }

    public boolean checkAccount(String email) {

        // array of columns to fetch
        String[] columns = { ACCOUNT_ID };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = ACCOUNT_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_ACCOUNTS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //Get all Accounts
    public List<Account> getAllAccounts(){

        //Create account list
        List<Account> accountList = new ArrayList<Account>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ACCOUNTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Loop through rows and add to list
        if(cursor.moveToFirst()){
            do{
                Account account = new Account();
                account.setId(Integer.parseInt(cursor.getString(0)));
                account.setFirstName(cursor.getString(1));
                account.setMiddleName(cursor.getString(2));
                account.setLastName(cursor.getString(3));
                account.setPhoneNumber(cursor.getString(4));
                account.setEmail(cursor.getString(5));

                //Add Account to list
                accountList.add(account);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return accountList;
    }

    // Get Account count
    public int getAccountCount(){
        return 0;
    }

    // Update single Account
    public int updateAccount(Account account, int accountId){
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_FIRST, account.getFirstName());
        values.put(ACCOUNT_MIDDLE, account.getMiddleName());
        values.put(ACCOUNT_LAST, account.getLastName());
        values.put(ACCOUNT_PHONE, account.getPhoneNumber());
        values.put(ACCOUNT_EMAIL, account.getEmail());

        // updating row
        return db.update(TABLE_ACCOUNTS, values, ACCOUNT_ID + " = ?",
                new String[] { String.valueOf(accountId) });
    }

    // Delete single Account
    public void deleteAccount(Account account, int accountId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, ACCOUNT_ID + " = ?",
                new String[] { String.valueOf(accountId) });
        db.close();
    }

    //--------------------------------------------------
    // TimeSheet CRUD Operations
    //--------------------------------------------------

    // Add Timesheet
    public void addTimesheet(Timesheet tsheet, int accountId){
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(TIMESHEET_TITLE, tsheet.getSheetTitle());
        values.put(TIMESHEET_START, tsheet.getStartDate());
        values.put(TIMESHEET_END, tsheet.getEndDate());
        values.put(TIMESHEET_YEAR, tsheet.getYearDate());
        values.put(TIMESHEET_ACCOUNT_ID, accountId);

        // Inserting Row
        db.insert(TABLE_TIME_SHEET, null, values);
        db.close(); // Closing database connection
    }

    // Get single Timesheet
    public Timesheet getTimesheet(int accountId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TIME_SHEET, new String[] {
                TIMESHEET_ID, TIMESHEET_TITLE, TIMESHEET_START, TIMESHEET_END, TIMESHEET_YEAR },
                TIMESHEET_ID + "=?", new String[] { String.valueOf(accountId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Timesheet tsheet = new Timesheet(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)));

        cursor.close();
        return tsheet;
    }

    // Get all Timesheets
    public List<Timesheet> getAllTimesheets(int accountId){

        //Create Timesheet list
        List<Timesheet> tsheetList = new ArrayList<Timesheet>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TIME_SHEET + " WHERE ts_account_id = "
                                + accountId + " ORDER BY sheet_id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Loop through rows and add to list
        if(cursor.moveToFirst()){
            do{
                Timesheet tsheet = new Timesheet();
                tsheet.setId(Integer.parseInt(cursor.getString(0)));
                tsheet.setSheetTitle(cursor.getString(1));
                tsheet.setStartDate(cursor.getString(2));
                tsheet.setEndDate(cursor.getString(3));
                tsheet.setYearDate(Integer.parseInt(cursor.getString(4)));
                tsheet.setAccountId(Integer.parseInt(cursor.getString(5)));

                //Add Sheet to list
                int sheetID = cursor.getInt(cursor.getColumnIndex(TIMESHEET_ID));
                String title = cursor.getString(cursor.getColumnIndex(TIMESHEET_TITLE));
                String start = cursor.getString(cursor.getColumnIndex(TIMESHEET_START));
                String end = cursor.getString(cursor.getColumnIndex(TIMESHEET_END));

                //title += "\n\tDates: " + start + " - " + end;
                tsheetList.add(tsheet);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return tsheetList;
    }

    // Get Timesheet count
    public int getTimesheetCount(int accountId){
        String countQuery = "SELECT  * FROM " + TABLE_TIME_SHEET + " WHERE " + TIMESHEET_ACCOUNT_ID +  " = "
        + accountId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
        return cursor.getCount();
    }

    // Update single Timesheet
    public int updateTimesheet(Timesheet tsheet, int tsheetId){
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(TIMESHEET_TITLE, tsheet.getSheetTitle());
        values.put(TIMESHEET_START, tsheet.getStartDate());
        values.put(TIMESHEET_END, tsheet.getEndDate());
        values.put(TIMESHEET_YEAR, tsheet.getYearDate());

        // updating row
        return db.update(TABLE_TIME_SHEET, values, TIMESHEET_ID + " = ?",
                new String[] { String.valueOf(tsheetId) });
    }

    // Delete single Timesheet
    public void deleteTimesheet(Timesheet tsheet, int tsheetId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIME_SHEET, TIMESHEET_ID + " = ?",
                new String[] { String.valueOf(tsheetId) });
        db.close();
    }

    //--------------------------------------------------
    // TimeSheet Entry CRUD Operations
    //--------------------------------------------------

    // Add Entry
    public void addEntry(TimesheetEntry entry){
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(ENTRY_JOB_TYPE, entry.getJobType());
        values.put(ENTRY_CUSTOMER, entry.getCustomer());
        values.put(ENTRY_DESCRIPTION, entry.getDescription());
        values.put(ENTRY_HOURS, entry.getEntryHours());
        values.put(ENTRY_DATE, entry.getEntryDate());

        // Inserting Row
        db.insert(TABLE_TS_ENTRY, null, values);
        db.close(); // Closing database connection
    }

    // Get single Entry
    public TimesheetEntry getEntry(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TS_ENTRY, new String[] {
                        ENTRY_ID, ENTRY_JOB_TYPE, ENTRY_CUSTOMER, ENTRY_DESCRIPTION,
                        ENTRY_HOURS, ENTRY_DATE },
                ENTRY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TimesheetEntry entry = new TimesheetEntry(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),cursor.getString(5));

        cursor.close();
        return entry;
    }

    // Get all Entrys
    public List<TimesheetEntry> getAllEntrys(){
        //Create Timesheet list
        List<TimesheetEntry> entryList = new ArrayList<TimesheetEntry>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TS_ENTRY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Loop through rows and add to list
        if(cursor.moveToFirst()){
            do{
                TimesheetEntry entry = new TimesheetEntry();
                entry.setId(Integer.parseInt(cursor.getString(0)));
                entry.setJobType(cursor.getString(1));
                entry.setCustomer(cursor.getString(2));
                entry.setDescription(cursor.getString(3));
                entry.setEntryHours(Integer.parseInt(cursor.getString(4)));
                entry.setEntryDate(cursor.getString(5));

                //Add Account to list
                entryList.add(entry);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return entryList;
    }

    // Get Entry count
    public int getEntryCount(){
        String countQuery = "SELECT  * FROM " + TABLE_TS_ENTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Update single Entry
    public int updateEntry(TimesheetEntry entry, int entryId){
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(ENTRY_JOB_TYPE, entry.getJobType());
        values.put(ENTRY_CUSTOMER, entry.getCustomer());
        values.put(ENTRY_DESCRIPTION, entry.getDescription());
        values.put(ENTRY_HOURS, entry.getEntryHours());
        values.put(ENTRY_DATE, entry.getEntryDate());

        // updating row
        return db.update(TABLE_TS_ENTRY, values, ENTRY_ID + " = ?",
                new String[] { String.valueOf(entryId) });
    }

    // Delete single Entry
    public void deleteEntry(TimesheetEntry entry, int entryId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TS_ENTRY, ENTRY_ID + " = ?",
                new String[] { String.valueOf(entryId) });
        db.close();
    }

    /* Work in progress, Join query
    public void getJoinedEntrySheet(){
        //Create new querybuilder
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = this.getReadableDatabase();

        qb.setTables(TABLE_TIME_SHEET +
                " LEFT OUTER JOIN " + TABLE_TS_ENTRY + " ON " +
                TIMESHEET_ID + " = " + ENTRY_TIMESHEET_ID);

        String orderBy = ENTRY_ID + " ASC";


        Cursor cursor = qb.query(db, null, null, null, null, null, orderBy);
        return result;
    }
    */
}