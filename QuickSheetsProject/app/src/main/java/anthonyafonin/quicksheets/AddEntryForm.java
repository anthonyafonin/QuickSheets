package anthonyafonin.quicksheets;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anthonyafonin.quicksheets.Fragments.Sheets;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;

public class AddEntryForm extends AppCompatActivity {

    private EditText titleText, startDateText, endDateText, yearText;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnSubmit, btnCancel;
    Account acc;
    Timesheet sheet;
    //Context context = this;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AddEntryForm context = this;

        // Loads account id from shared pref
        accountId = AccountSharedPref.loadAccountId(context);

        // Assigns buttons
        btnSubmit = (Button)(findViewById(R.id.btnSubmitEntry));
        //btnCancel = (Button)findViewById(R.id.btnCancelEntry);

        //Assign variables to text fields
        /*titleText = (EditText) findViewById(R.id.txtTitle);
        startDateText = (EditText) findViewById(R.id.txtStartDate);
        endDateText = (EditText) findViewById(R.id.txtEndDate);
        yearText =  (EditText) findViewById(R.id.txtYear);*/

        // Action listener for button submit
        createSheet();

        /*// Action listener for Button Cancel, returns to home activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }

    // ActionListener for Register Button
    public void createSheet() {
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int yearDate = 0;

                        // Create an Instance of a Timesheet from user input
                        sheet = new Timesheet(
                                titleText.getText().toString(),
                                startDateText.getText().toString(),
                                endDateText.getText().toString(),
                                yearDate, accountId);
                        do{
                            try{
                                //Breaks if all required fields are not filled
                                if(
                                        titleText.getText().toString().matches("")
                                                || startDateText.getText().toString().matches("")
                                                || endDateText.getText().toString().matches(""))
                                {
                                    Toast.makeText(
                                            AddEntryForm.this, "Please Fill In Required Fields",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                //Breaks if invalid year input type
                                try {
                                    yearDate = Integer.parseInt(yearText.getText().toString());
                                } catch(NumberFormatException nfe) {
                                    Toast.makeText(AddEntryForm.this,
                                            "Invalid Year",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                // Attempt to insert data into SQLite database
                                db.addTimesheet(sheet, accountId);

                                //Redirects to login activity if successful
                                Intent i = new Intent(v.getContext(), HomeActivity.class);
                                startActivity(i);

                                Toast.makeText(AddEntryForm.this,
                                        "Timesheet Created", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(AddEntryForm.this,
                                        "Error: Invalid Field Types", Toast.LENGTH_LONG).show();
                            }

                        }while(false);
                    }
                });
    }
}

