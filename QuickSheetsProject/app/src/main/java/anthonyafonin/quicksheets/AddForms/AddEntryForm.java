package anthonyafonin.quicksheets.AddForms;



import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;


public class AddEntryForm extends AppCompatActivity {

    private EditText jobType, customer, description, hoursText, dateText;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnSubmitEntry;
    TimesheetEntry entry;
    Context context = this;
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
        btnSubmitEntry = (Button)(findViewById(R.id.btnSubmitEntry));

        //Assign variables to text fields
        jobType = (EditText) findViewById(R.id.txtJobType);
        customer = (EditText) findViewById(R.id.txtCustomer);
        description = (EditText) findViewById(R.id.txtDescription);
        hoursText =  (EditText) findViewById(R.id.txtHours);
        dateText =  (EditText) findViewById(R.id.txtDate);

        // Action listener for button submit
        createEntry();

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
    public void createEntry() {
        btnSubmitEntry.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int hours = 0;

                        // Create an Instance of an entry from user input
                        entry = new TimesheetEntry(
                                jobType.getText().toString(),
                                customer.getText().toString(),
                                description.getText().toString(),
                                hours,
                                dateText.getText().toString(),
                                timesheetId);
                        do{
                            try{
                                //Breaks if all required fields are not filled
                                if(
                                        jobType.getText().toString().matches("")
                                                || customer.getText().toString().matches("")
                                                || description.getText().toString().matches("")
                                                || dateText.getText().toString().matches(""))
                                {
                                    Toast.makeText(
                                            AddEntryForm.this, "Please Fill In Required Fields",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                //Breaks if invalid hours input type
                                try {
                                    hours = Integer.parseInt(hoursText.getText().toString());
                                } catch(NumberFormatException nfe) {
                                    Toast.makeText(AddEntryForm.this,
                                            "Invalid 'Hours'",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                // Attempt to insert data into SQLite database
                                db.addEntry(entry, timesheetId);


                                killActivity();

                                Toast.makeText(AddEntryForm.this,
                                        "Entry Created", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(AddEntryForm.this,
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

