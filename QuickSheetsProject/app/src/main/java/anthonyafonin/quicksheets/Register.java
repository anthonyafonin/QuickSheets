package anthonyafonin.quicksheets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;

public class Register extends Activity {
    private EditText firstNameText, middleNameText, lastNameText, phoneText, emailText, reEmailText;
    DatabaseHelper db = new DatabaseHelper(this);
    Button registerAccount, returnToLogin;
    Account acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assigns variables to button and return link
        registerAccount = (Button) findViewById(R.id.btnRegister);
        returnToLogin = (Button) findViewById(R.id.btnReturn);

        //Assign variables to text fields
        firstNameText = (EditText) findViewById(R.id.txtFirst);
        middleNameText = (EditText) findViewById(R.id.txtMiddle);
        lastNameText = (EditText) findViewById(R.id.txtLast);
        phoneText =  (EditText) findViewById(R.id.txtPhone);
        emailText = (EditText) findViewById(R.id.txtEmail);
        reEmailText = (EditText) findViewById(R.id.txtReEmail);

        //Creates btnRegister method onCreate
        btnRegister();

        //ActionListener for Return Button
        returnToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // ActionListener for Register Button
    public void btnRegister() {
        registerAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            // Create an Instance of an Account from user input
                            acc = new Account(firstNameText.getText().toString(),
                                    middleNameText.getText().toString(),
                                    lastNameText.getText().toString(),
                                    phoneText.getText().toString(),
                                    emailText.getText().toString());
                        do{
                            try{
                                //Breaks if all required fields are not filled
                                if(firstNameText.getText().toString().matches("")
                                        || lastNameText.getText().toString().matches("")
                                        || phoneText.getText().toString().matches("")
                                        || emailText.getText().toString().matches("")
                                        || reEmailText.getText().toString().matches("")){
                                    Toast.makeText(Register.this,
                                            "Please Fill In Required Fields",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }
                                //Breaks if Email Addresses do not match
                                if (!emailText.getText().toString().equals(
                                        reEmailText.getText().toString())){
                                    Toast.makeText(Register.this,
                                            "Email Addresses Do Not Match",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                // Attempt to insert data into SQLite database
                                db.addAccount(acc);

                                //Redirects to login activity if successful
                                Intent i = new Intent(v.getContext(), LoginActivity.class);
                                startActivity(i);

                                Toast.makeText(Register.this,
                                        "Account Created", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(Register.this,
                                        "Error: Invalid Field Types", Toast.LENGTH_LONG).show();
                            }

                        }while(false);
                    }
                });
    }
}
