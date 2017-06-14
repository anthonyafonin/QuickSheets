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
import anthonyafonin.quicksheets.database.Model.Account;
import anthonyafonin.quicksheets.database.Model.Timesheet;


public class AddSheetForm extends AppCompatActivity {

    private EditText titleText, startDateText, endDateText;
    DatabaseHelper db = new DatabaseHelper(this);
    Button btnSubmit;
    Timesheet sheet;
    Context context = this;
    int accountId, year;
    public static int thisYear;
    private int mYear, mMonth, mDay;
    String monthString;
    private ImageView calStart, calEnd;

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

        calStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

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

        calEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

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

    // ActionListener for Register Button
    public void createSheet() {
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

    // Closes current activity and refreshes entry fragment
    public void killActivity()
    {
        finish();
    }
}

