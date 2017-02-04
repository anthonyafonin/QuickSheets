package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.R;

public class MainActivity extends Activity {

    DatabaseHelper db;
    SQLiteDatabase sqldb;
    Context context;

    int timeout = 3000; //Timer for splashscreen, 3 seconds.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(context);
        db.onCreate(sqldb);
        run();
    }

    // Redirects to LoginActivity after 3 seconds.
    public void run(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                // TODO: Your application init goes here.
                Intent homepage = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(homepage);
                MainActivity.this.finish();
            }
        }, timeout);
    }
}