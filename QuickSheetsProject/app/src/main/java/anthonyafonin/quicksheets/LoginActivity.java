package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;

public class LoginActivity extends Activity {

    DatabaseHelper db = new DatabaseHelper(this);
    Button btnReg, btnLogin;
    private EditText emailText;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        setContentView(R.layout.activity_login);

        //Stores buttons into variables for ActionListener
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnReg = (Button)findViewById(R.id.txtRegister);
        emailText = (EditText) findViewById(R.id.email);
        btnLogin();

        btnReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Register.class);
                startActivity(intent);
            }
        });

    }
    public void btnLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if(db.checkAccount(emailText.getText().toString().trim())){
                        //Get Account Id
                        accountId = db.getAccountIdByEmail(emailText.getText().toString().trim());

                        //Store the retrieved SQLite Account ID in a shared preference
                        AccountSharedPref.saveId(LoginActivity.this, accountId);

                        //Redirect to HomeActivity
                        Intent intent = new Intent(v.getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,
                                "Email does not exist", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(LoginActivity.this,
                            "Error: Invalid Input", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
