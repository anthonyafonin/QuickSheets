package anthonyafonin.timecheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    int timeout = 3000; //Timer for splashscreen, 3 seconds.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        setContentView(R.layout.activity_main);
        run();
    }

    //Times out after 3 seconds.
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