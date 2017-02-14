package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.format;

/**
 * Created by Anthony on 2/13/2017.
 */

public class AddSheetForm extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        setContentView(R.layout.add_sheet);

        //Stores buttons into variables for ActionListener
        //btnLogin = (Button)findViewById(R.id.btnLogin);
        //btnReg = (Button)findViewById(R.id.txtRegister);
        //emailText = (EditText) findViewById(R.id.email);
        //btnLogin();
/*
        btnReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Register.class);
                startActivity(intent);
            }
        });*/

    }

}
