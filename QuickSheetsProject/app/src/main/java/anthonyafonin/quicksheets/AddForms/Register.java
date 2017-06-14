/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: Register.java
 */

/**
 * Contains all forms that create new table rows.
 */
package anthonyafonin.quicksheets.AddForms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anthonyafonin.quicksheets.LoginActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;

/**
 * Inserts a new Account in the database.
 */
public class Register extends Activity {

    // Declare Components
    private EditText firstNameText, middleNameText, lastNameText, phoneText, emailText, reEmailText;
    Button registerAccount, returnToLogin;

    // Create database objects
    DatabaseHelper db = new DatabaseHelper(this);
    Account acc;

    /**
     * The onCreate method of the activity.
     * Puts the activity together.
     * @param savedInstanceState The saved instance state.
     */
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
            /**
             * Redirects to LoginActivity
             * @param v Current view.
             */
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * ActionListener for Register Button
     */
    public void btnRegister() {
        registerAccount.setOnClickListener(new View.OnClickListener() {
                /**
                 * Attempts to insert new Account in Database
                 * @param v Current view
                 */
                @Override
                public void onClick(View v) {

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

                            // Checks if email address already exists
                            if(db.checkAccount(emailText.getText().toString())){
                                Toast.makeText(Register.this,
                                        "Email Already Exists",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }


                            // Create an Instance of an Account from user input
                            acc = new Account(firstNameText.getText().toString(),
                                    middleNameText.getText().toString(),
                                    lastNameText.getText().toString(),
                                    phoneText.getText().toString(),
                                    emailText.getText().toString());

                            // Attempt to insert data into SQLite database
                            db.addAccount(acc);


                            //Redirect to HomeActivity
                            Intent intent = new Intent(v.getContext(), LoginActivity.class);
                            startActivity(intent);

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
