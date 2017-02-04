package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.Model.Account;

import anthonyafonin.quicksheets.database.AccountCrud;
import anthonyafonin.quicksheets.database.DatabaseHelper;

public class Register extends Activity {

    private SQLiteDatabase db;
    private AccountCrud accountCrud;

    private String firstNameText;
    private String middleNameText;
    private String lastNameText;
    private String phoneText;
    private String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assigns variables to button and return link
        Button registerAccount = (Button) findViewById(R.id.btnRegister);
        Button returnToLogin = (Button) findViewById(R.id.btnReturn);

        //Assign variables to text fields
        firstNameText = findViewById(R.id.txtFirst).toString();
        middleNameText = findViewById(R.id.txtMiddle).toString();
        lastNameText = findViewById(R.id.txtLast).toString();
        phoneText =  findViewById(R.id.txtPhone).toString();
        emailText = findViewById(R.id.txtEmail).toString();

        //ActionListener for Register Button
        registerAccount.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                try {
                ContentValues values = new ContentValues();
                Account acc = new Account(firstNameText, middleNameText,
                        lastNameText, phoneText, emailText);
                accountCrud.addAccount(acc);
                }
                catch (Exception e){
                    Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                    // this is the line that prints out the location in
                    // the code where the error occurred.
                    e.printStackTrace();


                };
                //Intent i = new Intent(v.getContext(), LoginActivity.class);
                //startActivity(i);
            }
        });

        //ActionListener for Return Button
        returnToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
