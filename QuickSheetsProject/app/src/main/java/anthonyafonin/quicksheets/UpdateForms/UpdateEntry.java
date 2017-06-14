package anthonyafonin.quicksheets.UpdateForms;


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

import static anthonyafonin.quicksheets.Fragments.SheetEntries.entryId;
import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;


public class UpdateEntry extends AppCompatActivity {

    private EditText txtJob, txtCustomer, txtDescription, txtHours, txtDate;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnUpdateEntry;
    TimesheetEntry entry;
    int accountId;
    double entryHours, dblhours;
    String jobType, customer, description, dateText, hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final UpdateEntry context = this;

        // Loads account id from shared pref
        accountId = AccountSharedPref.loadAccountId(context);

        // Assigns buttons
        btnUpdateEntry = (Button)(findViewById(R.id.btnUpdateEntry));

        //Assign variables to text fields
        txtJob = (EditText) findViewById(R.id.txtJobUpdate);
        txtCustomer = (EditText) findViewById(R.id.txtCustomerUpdate);
        txtDescription = (EditText) findViewById(R.id.txtDescUpdate);
        txtHours =  (EditText) findViewById(R.id.txtHoursUpdate);
        txtDate =  (EditText) findViewById(R.id.txtDateUpdate);


        // Get Selected Entry and returns values into editText
        entry = db.getEntry(entryId);
        jobType = entry.getJobType();
        customer = entry.getCustomer();
        description = entry.getDescription();
        dblhours = entry.getEntryHours();
        hours = Double.toString(dblhours);
        dateText = entry.getEntryDate();

        // Displays values in editText fields and moves cursor to the right
        txtJob.setText(jobType);
        txtJob.setSelection(txtJob.getText().length());
        txtCustomer.setText(customer);
        txtCustomer.setSelection(txtCustomer.getText().length());
        txtDescription.setText(description);
        txtDescription.setSelection(txtDescription.getText().length());
        txtHours.setText(hours);
        txtHours.setSelection(txtHours.getText().length());
        txtDate.setText(dateText);
        txtDate.setSelection(txtDate.getText().length());

        // Action listener for button submit
        updateEntry();

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
    public void updateEntry() {
        btnUpdateEntry.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        do{
                            try{
                                //Breaks if all required fields are not filled
                                if(
                                        txtJob.getText().toString().matches("")
                                        || txtCustomer.getText().toString().matches("")
                                        || txtDescription.getText().toString().matches("")
                                        || txtDate.getText().toString().matches(""))
                                {
                                    Toast.makeText(
                                            UpdateEntry.this, "Please Fill In Required Fields",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                //Breaks if invalid hours input type
                                try {
                                    dblhours = Double.parseDouble(txtHours.getText().toString());
                                } catch(NumberFormatException nfe) {
                                    Toast.makeText(UpdateEntry.this,
                                            "Invalid 'Hours'",
                                            Toast.LENGTH_LONG).show();
                                    break;
                                }

                                // Create an Instance of an entry from user input
                                entry = new TimesheetEntry(
                                        txtJob.getText().toString(),
                                        txtCustomer.getText().toString(),
                                        txtDescription.getText().toString(),
                                        dblhours,
                                        txtDate.getText().toString(),
                                        entryId);

                                // Attempt to insert data into SQLite database
                                db.updateEntry(entry, entryId);


                                killActivity();

                                Toast.makeText(UpdateEntry.this,
                                        "Entry Saved", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(UpdateEntry.this,
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

