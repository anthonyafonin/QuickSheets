package anthonyafonin.quicksheets.UpdateForms;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;

import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;


public class UpdateSheet extends AppCompatActivity {

    private EditText titleText, startDateText, endDateText, yearText;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnSubmit, btnCancel;
    Account acc;
    Timesheet sheet;
    Context context = this;
    int accountId, year;
    String title, startDate, endDate;

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
        startDateText.setSelection(startDateText.getText().length());
        endDateText.setText(endDate);
        endDateText.setSelection(endDateText.getText().length());

        year =  Calendar.getInstance().get(Calendar.YEAR);

        // Action listener for button save
        updateSheet();

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
                                        year, accountId);

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

    // Closes current activity and refreshes sheet fragment
    public void killActivity()
    {
        finish();
    }
}

