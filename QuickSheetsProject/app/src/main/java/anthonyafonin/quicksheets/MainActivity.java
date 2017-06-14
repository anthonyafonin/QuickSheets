/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: MainActivity.java
 */

/**
 * An application that manages timesheet using a sqlite database.
 */
package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import anthonyafonin.quicksheets.database.DatabaseHelper;

/**
 * The Splash screen of the application
 */
public class MainActivity extends Activity {

    // Declare variables and objects.
    DatabaseHelper db;
    int timeout = 3000; //Timer for splashscreen, 3 seconds.
    Intent homepage;

    /**
     * The onCreate method of the activity.
     * Puts the activity together.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        setContentView(R.layout.activity_main);

        //Create Database Schema
        db = new DatabaseHelper(this);
        run();
    }

    /**
     * Redirects to LoginActivity after 3 seconds.
     */
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