/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: UpdateSheet.java
 */

/**
 * Contains all forms that update existing table rows.
 */
package anthonyafonin.quicksheets.UpdateForms;

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
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;

import static anthonyafonin.quicksheets.AddForms.AddSheetForm.thisYear;
import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;

/**
 * Updates an existing Timesheet in the database.
 */
public class UpdateSheet extends AppCompatActivity {

    //Declare variables and objects.
    private EditText titleText, startDateText, endDateText, yearText;
    private ImageView calStart, calEnd;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnSubmit, btnCancel;
    Account acc;
    Timesheet sheet;
    Context context = this;
    int accountId, year;
    String title, startDate, endDate;
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
        setContentView(R.layout.update_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Loads account id from shared pref
        accountId = AccountSharedPref.loadAccountId(context);

        // Assigns buttons
        btnSubmit = (Button)(findViewById(R.id.btnUpdateSheet));
        calStart = (ImageView)(findViewById(R.id.imgCalStartUpdate)) ;
        calEnd = (ImageView)(findViewById(R.id.imgCalEndUpdate)) ;

        //Assign variables to text fields
        titleText = (EditText) findViewById(R.id.txtTitleUpdate);
        startDateText = (EditText) findViewById(R.id.txtStartUpdate);
        endDateText = (EditText) findViewById(R.id.txtEndUpdate);

        // Get Selected Timesheet and returns values into editText
        sheet = db.getTimesheet(timesheetId);
        title = sheet.getSheetTitle();
        startDate = sheet.getStartDate();
        endDate = sheet.getEndDate();

        // Displays values in editText fields and moves cursor to the right
        titleText.setText(title);
        titleText.setSelection(titleText.getText().length());
        startDateText.setText(startDate);
        //startDateText.setSelection(startDateText.getText().length());
        endDateText.setText(endDate);
        //endDateText.setSelection(endDateText.getText().length());
        year = thisYear;

        // Action listener for button save
        updateSheet();

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

        // Displays Datepicker
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * ActionListener for Update Button
     */
    public void updateSheet() {
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
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
                                        UpdateSheet.this, "Please Fill In Required Fields",
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
                                db.updateTimesheet(sheet, timesheetId);

                                killActivity();

                                Toast.makeText(UpdateSheet.this,
                                        "Timesheet Saved", Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e){
                                Toast.makeText(UpdateSheet.this,
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

