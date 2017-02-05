package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.Timer;
import java.util.TimerTask;

import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.R;

import static anthonyafonin.quicksheets.database.DatabaseHelper.DATABASE_NAME;

public class MainActivity extends Activity {

    DatabaseHelper db;
    int timeout = 3000; //Timer for splashscreen, 3 seconds.
    Intent homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        setContentView(R.layout.activity_main);

        //Create Database Schema
        //SQLiteDatabase db = new DatabaseHelper(this.getWritableDatabase());
        db = new DatabaseHelper(this);
        run();
    }

    // Redirects to LoginActivity after 3 seconds.
    public void run(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                // Checks if shared preference of Account Id, redirects accordingly
                if((AccountSharedPref.loadAccountId(MainActivity.this)) == 0) {
                    homepage = new Intent(MainActivity.this, LoginActivity.class);
                }
                else{
                    homepage = new Intent(MainActivity.this, HomeActivity.class);
                }
                    MainActivity.this.startActivity(homepage);
                    MainActivity.this.finish();
            }
        }, timeout);
    }
}