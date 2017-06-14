/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: UpdateAccount.java
 */

/**
 * Contains all forms that update existing table rows.
 */
package anthonyafonin.quicksheets.UpdateForms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.LoginActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;

/**
 * Updates an existing Account in the database.
 */
public class UpdateAccount extends AppCompatActivity {

    // Declare variables and objects.
    private EditText txtFirst, txtMiddle, txtLast, txtPhone;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnUpdateAccount, btnDeleteAccount;
    Account acc;
    UpdateAccount context = this;
    int accountId;
    String first, middle, last, phone, email;

    /**
     * The onCreate method of the activity.
     * Puts the activity together.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Loads account id from shared pref
        accountId = AccountSharedPref.loadAccountId(context);

        // Assigns buttons
        btnUpdateAccount = (Button)(findViewById(R.id.btnUpdateAccount));
        btnDeleteAccount = (Button)(findViewById(R.id.btnDeleteAccount));

        //Assign variables to text fields
        txtFirst = (EditText) findViewById(R.id.txtFirstUpdate);
        txtMiddle = (EditText) findViewById(R.id.txtMiddleUpdate);
        txtLast = (EditText) findViewById(R.id.txtLastUpdate);
        txtPhone =  (EditText) findViewById(R.id.txtPhoneUpdate);

        // Get Selected Timesheet and returns values into editText
        acc =  db.getAccount(accountId);
        first = acc.getFirstName();
        middle = acc.getMiddleName();
        last = acc.getLastName();
        phone = acc.getPhoneNumber();
        email = acc.getEmail();

        // Displays values in editText fields and moves cursor to the right
        txtFirst.setText(first);
        txtFirst.setSelection(txtFirst.getText().length());
        txtMiddle.setText(middle);
        txtMiddle.setSelection(txtMiddle.getText().length());
        txtLast.setText(last);
        txtLast.setSelection(txtLast.getText().length());
        txtPhone.setText(phone);
        txtPhone.setSelection(txtPhone.getText().length());

        // Action listener for button update and delete
        updateAccount();
        deleteAccount();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            /**
             * Closes activity.
             */
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }

    /**
     * ActionListener for Update Button
     */
    public void updateAccount() {
        btnUpdateAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Create an Instance of an entry from user input
                        acc = new Account(
                                txtFirst.getText().toString(),
                                txtMiddle.getText().toString(),
                                txtLast.getText().toString(),
                                txtPhone.getText().toString(),
                                email);
                        do{
                            try{
                                //Breaks if all required fields are not filled
                                if(
                                        txtFirst.getText().toString().matches("")
                                        || txtLast.getText().toString().matches("")
                                        || txtPhone.getText().toString().matches(""))
                                {
                                    Toast.makeText(UpdateAccount.this,
                                            "Please Fill In Required Fields",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                // Attempt to update data into SQLite database
                                db.updateAccount(acc, accountId);

                                //Redirect to HomeActivity
                                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                                startActivity(intent);

                                Toast.makeText(UpdateAccount.this,
                                        "Account Saved", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(UpdateAccount.this,
                                        "Error: Invalid Field Types", Toast.LENGTH_LONG).show();
                            }

                        }while(false);
                    }
                });
    }

    /**
     * ActionListener for Delete Button
     */
    public void deleteAccount() {
        btnDeleteAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("Are you sure you want to delete?")
                                .setCancelable(false)
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        db.deleteAccount(accountId);

                                        // Clears the Shared Preference
                                        AccountSharedPref.logoutUser(v.getContext());

                                        //Redirect to HomeActivity
                                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                        startActivity(intent);

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
    }
}


