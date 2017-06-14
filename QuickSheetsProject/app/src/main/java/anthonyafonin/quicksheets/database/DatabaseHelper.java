/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: DatabaseHelper.java
 */

/**
 * Contains databaseHelper methods and entity model classes
 */
package anthonyafonin.quicksheets.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

/**
 * The database helper class that handles all SQL statements.
 */
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

    /**
     * Constructor.
     * @param context Context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates databse onCreate using concatenated static final Strings.
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_TIME_SHEETS);
        db.execSQL(CREATE_TABLE_TS_ENTRY);
    }

    /**
     * Creates tables if non existence.
     * @param db The database.
     * @param oldVersion Old version of database.
     * @param newVersion New version of database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_TABLE_ACCOUNT);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_TABLE_TIME_SHEETS);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_TABLE_TS_ENTRY);
    }

    //--------------------------------------------------
    // Account Create, Read, Update, Delete Operations
    //--------------------------------------------------

    /**
     * Add Account.
     * @param account Account object.
     */
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

    /**
     * Get Single Account.
     * @param accountId Account Id.
     * @return Account information of Account Id.
     */
    public Account getAccount(int accountId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] {
                        ACCOUNT_FIRST, ACCOUNT_MIDDLE, ACCOUNT_LAST, ACCOUNT_PHONE, ACCOUNT_EMAIL},
                ACCOUNT_ID + "=?", new String[] { String.valueOf(accountId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Account acc = new Account(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4));

        cursor.close();
        return acc;
    }

    /**
     * Get Account id from email
     * @param email Email.
     * @return The account Id matching the email address.
     */
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


        cursor.close();
        return accountId;
    }

    /**
     * Checks to see if email already exists.
     * @param email Email.
     * @return Boolean.
     */
    public boolean checkAccount(String email) {

        // array of columns to fetch
        String[] columns = { ACCOUNT_ID };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = ACCOUNT_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        Cursor cursor = db.query(TABLE_ACCOUNTS, //Table to query
                columns,                         //columns to return
                selection,                       //columns for the WHERE clause
                selectionArgs,                   //The values for the WHERE clause
                null,                            //group the rows
                null,                            //filter by row groups
                null);                           //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * Get All Accounts.
     * @return List of all Accounts.
     */
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

    /**
     * Update Account.
     * @param account Account object.
     * @param accountId Account Id.
     * @return Update SQL Statement.
     */
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

    /**
     * Delete Single Account.
     * @param accountId Account Id.
     */
    public void deleteAccount(int accountId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, ACCOUNT_ID + " = ?",
                new String[] { String.valueOf(accountId) });
        db.close();
    }

    //--------------------------------------------------
    // TimeSheet CRUD Operations
    //--------------------------------------------------

    /**
     * Add Timesheet.
     * @param tsheet Timesheet Object.
     * @param accountId Account Id.
     */
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

    /**
     * Get single Timesheet.
     * @param tsheetId Timesheet Object.
     * @return Timesheet Object.
     */
    public Timesheet getTimesheet(int tsheetId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TIME_SHEET, new String[] {
                TIMESHEET_TITLE, TIMESHEET_START, TIMESHEET_END, TIMESHEET_YEAR, TIMESHEET_ID},
                TIMESHEET_ID + "=?", new String[] { String.valueOf(tsheetId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Timesheet tsheet = new Timesheet(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)));

        cursor.close();
        return tsheet;
    }

    /**
     * Get All Timesheets.
     * @param accountId Account Id.
     * @return List of all Timesheets of Account Id.
     */
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

                tsheetList.add(tsheet);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return tsheetList;
    }

    /**
     * Get Timesheet Count.
     * @param accountId Account Id.
     * @return Timesheet Count.
     */
    public int getTimesheetCount(int accountId){
        String countQuery = "SELECT  * FROM " + TABLE_TIME_SHEET
                + " WHERE " + TIMESHEET_ACCOUNT_ID +  " = " + accountId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    /**
     * Update Single Timesheet.
     * @param tsheet Timesheet Object.
     * @param tsheetId Timesheet Id.
     * @return Update Timesheet SQL Statement.
     */
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

    /**
     * Delete Single Timesheet.
     * @param tsheet Timesheet Object.
     * @param tsheetId Timesheet Id.
     */
    public void deleteTimesheet(Timesheet tsheet, int tsheetId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIME_SHEET, TIMESHEET_ID + " = ?",
                new String[] { String.valueOf(tsheetId) });
        db.close();
    }

    //--------------------------------------------------
    // TimeSheet Entry CRUD Operations
    //--------------------------------------------------

    /**
     * Add New Entry
     * @param entry TimesheetEntry Object.
     * @param sheetId Timesheet Id.
     */
    public void addEntry(TimesheetEntry entry, int sheetId){
        SQLiteDatabase db = this.getWritableDatabase();

        // Adding Account attribute values
        ContentValues values = new ContentValues();
        values.put(ENTRY_JOB_TYPE, entry.getJobType());
        values.put(ENTRY_CUSTOMER, entry.getCustomer());
        values.put(ENTRY_DESCRIPTION, entry.getDescription());
        values.put(ENTRY_HOURS, entry.getEntryHours());
        values.put(ENTRY_DATE, entry.getEntryDate());
        values.put(ENTRY_TIMESHEET_ID, sheetId);

        // Inserting Row
        db.insert(TABLE_TS_ENTRY, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Get Single Entry
     * @param entryId Entry Id.
     * @return Entry Object.
     */
    public TimesheetEntry getEntry(int entryId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TS_ENTRY, new String[] {
                        ENTRY_JOB_TYPE, ENTRY_CUSTOMER, ENTRY_DESCRIPTION,
                        ENTRY_HOURS, ENTRY_DATE, ENTRY_ID },
                ENTRY_ID + "=?", new String[] { String.valueOf(entryId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TimesheetEntry entry = new TimesheetEntry(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)),
                cursor.getString(4),Integer.parseInt(cursor.getString(5)));

        cursor.close();
        return entry;
    }

    /**
     * Get All Entries.
     * @param sheetId Timesheet Id.
     * @return List of Entries of Timesheet Id.
     */
    public List<TimesheetEntry> getAllEntrys(int sheetId){
        //Create Timesheet list
        List<TimesheetEntry> entryList = new ArrayList<TimesheetEntry>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TS_ENTRY + " WHERE " + ENTRY_TIMESHEET_ID +
                " = " + sheetId + " ORDER BY " + ENTRY_ID + " DESC";

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
                entry.setEntryHours(Double.parseDouble(cursor.getString(4)));
                entry.setEntryDate(cursor.getString(5));

                //Add Account to list
                entryList.add(entry);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return entryList;
    }

    /**
     * Get Entry Count.
     * @param sheetId Timesheet Id.
     * @return Entry Count of Timesheet Id.
     */
    public int getEntryCount(int sheetId){
        String countQuery = "SELECT  * FROM " + TABLE_TS_ENTRY + " WHERE " + ENTRY_TIMESHEET_ID
                +  " = " + sheetId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    /**
     * Get Timesheet Hours.
     * @param sheetId Timesheet Id.
     * @return Sum of Entry Hours of Timesheet Id.
     */
    public double getSheetHours(int sheetId){

        double sum = 0;
        int count;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(" + ENTRY_HOURS + ") FROM " + TABLE_TS_ENTRY
                + " WHERE " + ENTRY_TIMESHEET_ID +  " = " + sheetId, null);

        c.moveToFirst();
        count = c.getCount();

        if(count > 0) {
            sum = c.getDouble(0);
        }
        return sum;
    }


    /**
     * Update Single Entry.
     * @param entry Timesheet Entry Object.
     * @param entryId Entry Id.
     * @return Update Entry SQL Statement.
     */
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

    /**
     * Delete Single Entry.
     * @param entry Timesheet Entry Object.
     * @param entryId Entry Id.
     */
    public void deleteEntry(TimesheetEntry entry, int entryId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TS_ENTRY, ENTRY_ID + " = ?",
                new String[] { String.valueOf(entryId) });
        db.close();
    }

}