/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: AddSheetForm.java
 */

/**
 * Contains all forms that create new table rows.
 */
package anthonyafonin.quicksheets.AddForms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;

/**
 * Inserts a new Timesheet in the database.
 */
public class AddSheetForm extends AppCompatActivity {

    // Create database objects and context
    DatabaseHelper db = new DatabaseHelper(this);
    Timesheet sheet;
    Context context = this;

    // Declare components
    private EditText titleText, startDateText, endDateText;
    private ImageView calStart, calEnd;
    Button btnSubmit;

    // Global Variables
    int accountId, year;
    public static int thisYear;
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
        setContentView(R.layout.add_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Loads account id from shared pref
        accountId = AccountSharedPref.loadAccountId(context);
        thisYear =  Calendar.getInstance().get(Calendar.YEAR);

        // Assigns buttons
        btnSubmit = (Button)(findViewById(R.id.btnSubmitSheet));
        calStart = (ImageView)(findViewById(R.id.imgCalStart)) ;
        calEnd = (ImageView)(findViewById(R.id.imgCalEnd)) ;

        //Assign variables to text fields
        titleText = (EditText) findViewById(R.id.txtTitle);
        startDateText = (EditText) findViewById(R.id.txtStartDate);
        endDateText = (EditText) findViewById(R.id.txtEndDate);
        year = thisYear;

        // Action listener for button submit, calender images
        createSheet();

        // Displays datepicker
        calStart.setOnClickListener(new View.OnClickListener() {
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

                // Saves selected date
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
                                startDateText.setText(monthString + " " + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Displays datepicker
        calEnd.setOnClickListener(new View.OnClickListener() {
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

                // Saves selected date
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
                                endDateText.setText(monthString + " " + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Returns to previous page on click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            /**
             * Returns to homeActivity
             * @param v Current view
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * ActionListener for create Button
     */
    public void createSheet() {
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    /**
                     * Attempts to insert new Timesheet in Database
                     * @param v Current view
                     */
                    @Override
                    public void onClick(View v) {

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

                                // Create an Instance of a Timesheet from user input
                                sheet = new Timesheet(
                                        titleText.getText().toString(),
                                        startDateText.getText().toString(),
                                        endDateText.getText().toString(),
                                        mYear, accountId);

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

    /**
     * Closes current activity and refreshes sheet fragment
     */
    public void killActivity()
    {
        finish();
    }
}

