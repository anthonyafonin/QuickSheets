package anthonyafonin.quicksheets.AddForms;


import android.content.Context;
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
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;


public class AddSheetForm extends AppCompatActivity {

    private EditText titleText, startDateText, endDateText, yearText;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnSubmit, btnCancel;
    Account acc;
    Timesheet sheet;
    Context context = this;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Loads account id from shared pref
        accountId = AccountSharedPref.loadAccountId(context);

        // Assigns buttons
        btnSubmit = (Button)(findViewById(R.id.btnSubmit));
        //btnCancel = (Button)findViewById(R.id.btnCancel);

        //Assign variables to text fields
        titleText = (EditText) findViewById(R.id.txtTitle);
        startDateText = (EditText) findViewById(R.id.txtStartDate);
        endDateText = (EditText) findViewById(R.id.txtEndDate);
        yearText =  (EditText) findViewById(R.id.txtYear);

        // Action listener for button submit
        createSheet();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
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
                                        AddSheetForm.this, "Please Fill In Required Fields",
                                        Toast.LENGTH_LONG).show();
                                    break;
                                }

                                //Breaks if invalid year input type
                                try {
                                    yearDate = Integer.parseInt(yearText.getText().toString());
                                } catch(NumberFormatException nfe) {
                                    Toast.makeText(AddSheetForm.this,
                                            "Invalid 'Year'",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                // Attempt to insert data into SQLite database
                                db.addTimesheet(sheet, accountId);

                                killActivity();

                                Toast.makeText(AddSheetForm.this,
                                        "Timesheet Created", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(AddSheetForm.this,
                                        "Error: Invalid Field Types", Toast.LENGTH_LONG).show();
                            }

                        }while(false);
                    }
                });
    }

    // Closes current activity and refreshes entry fragment
    public void killActivity()
    {
        finish();
    }
}

