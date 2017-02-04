package anthonyafonin.quicksheets.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import anthonyafonin.quicksheets.database.Model.Account;

public class AccountCrud {

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;
    private static final String LOGTAG = "EMP_MNGMNT_SYS";

    private static final String[] allColumns = {
            //DatabaseHelper.ACCOUNT_ID,
            DatabaseHelper.ACCOUNT_FIRST,
            DatabaseHelper.ACCOUNT_MIDDLE,
            DatabaseHelper.ACCOUNT_LAST,
            DatabaseHelper.ACCOUNT_PHONE,
            DatabaseHelper.ACCOUNT_EMAIL
    };

    public AccountCrud(Context context){
        dbhandler = new DatabaseHelper(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();

    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Account addAccount(Account Account){
        ContentValues values  = new ContentValues();
        values.put(DatabaseHelper.ACCOUNT_FIRST, Account.getFirstName());
        values.put(DatabaseHelper.ACCOUNT_MIDDLE, Account.getMiddleName());
        values.put(DatabaseHelper.ACCOUNT_LAST, Account.getLastName());
        values.put(DatabaseHelper.ACCOUNT_PHONE, Account.getPhoneNumber());
        values.put(DatabaseHelper.ACCOUNT_EMAIL, Account.getEmail());

        long insertid = database.insert(DatabaseHelper.TABLE_ACCOUNTS,null,values);
        Account.setId(insertid);
        return Account;

    }
}
