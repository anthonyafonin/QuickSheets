package anthonyafonin.timecheck;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static anthonyafonin.timecheck.R.styleable.View;

public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assigns variables to button and return link
        Button btn1 = (Button) findViewById(R.id.btnRegister);
        Button btn2 = (Button) findViewById(R.id.btnReturn);

        //ActionListener for Register Button
        btn1.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        //ActionListener for Return Button
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
