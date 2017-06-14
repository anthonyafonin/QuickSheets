/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: AddEntryForm.java
 */

/**
 * Contains all forms that create new table rows.
 */
package anthonyafonin.quicksheets.AddForms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;

/**
 * Inserts a new Timesheet Entry in the database.
 */
public class AddEntryForm extends AppCompatActivity {

    // Declare database objects
    DatabaseHelper db = new DatabaseHelper(this);
    TimesheetEntry entry;

    // Declare components
    private EditText jobType, customer, description, hoursText, dateText;
    ImageView calDate;
    Button btnSubmitEntry;

    // Global variables
    int accountId;
    double dblhours;
    private int mYear, mMonth, mDay;
    String monthString;


    /**
     * The onCreate method of the activity.
     * Puts the activity together.
     * @param savedInstanceState The saved instance state.
     */
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
        calDate = (ImageView)(findViewById(R.id.imgCalDate)) ;

        // Action listener for button submit
        createEntry();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Closes on back pressed
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            /**
             * Closes Activity
             */
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

        // Displays datepicker
        calDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Displays datepicker
             * @param v Current view
             */
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            /**
                             * Action listener for datepicker.
                             * @param view The view of the activity.
                             * @param year Selected year.
                             * @param month Selected Month.
                             * @param dayOfMonth Selected Day.
                             */
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int dayOfMonth) {

                                monthString = new DateFormatSymbols().getMonths()[month];
                                dateText.setText(monthString + " " + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

   /**
    * ActionListener for Register Button
    */
    public void createEntry() {
        btnSubmitEntry.setOnClickListener(
                new View.OnClickListener() {
                    /**
                     * Attempts to insert new entry.
                     * @param v Current View
                     */
                    @Override
                    public void onClick(View v) {
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
                                dblhours = Double.parseDouble(hoursText.getText().toString());
                            } catch(NumberFormatException nfe) {
                                Toast.makeText(AddEntryForm.this,
                                        "Invalid 'Hours'",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }

                            // Create an Instance of an entry from user input
                            entry = new TimesheetEntry(
                                    jobType.getText().toString(),
                                    customer.getText().toString(),
                                    description.getText().toString(),
                                    dblhours,
                                    dateText.getText().toString(),
                                    timesheetId);

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

   /**
    * Closes current activity and refreshes entry fragment
    */
    public void killActivity()
    {
        finish();
    }
}

