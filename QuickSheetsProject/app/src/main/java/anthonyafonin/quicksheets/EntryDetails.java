package anthonyafonin.quicksheets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

import static anthonyafonin.quicksheets.Fragments.SheetEntries.entryId;


public class EntryDetails extends AppCompatActivity {

    Context context = this;
    private TextView lblJob, lblCustomer, lblDescription, lblHours, lblDate;
    DatabaseHelper db = new DatabaseHelper(this);
    TimesheetEntry entry;
    double entryHours;
    String jobType, customer, description, dateText, hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        final EntryDetails context = this;

        //Assign variables to text fields
        lblJob = (TextView) findViewById(R.id.lblJobDet);
        lblCustomer = (TextView) findViewById(R.id.lblCustomerDet);
        lblDescription = (TextView) findViewById(R.id.lblDescDet);
        lblHours =  (TextView) findViewById(R.id.lblHoursDet);
        lblDate =  (TextView) findViewById(R.id.lblDateDet);

        // Get Selected Entry and returns values into editText
        entry = db.getEntry(entryId);
        jobType = entry.getJobType();
        customer = entry.getCustomer();
        description = entry.getDescription();
        entryHours = entry.getEntryHours();
        hours = Double.toString(entryHours);
        dateText = entry.getEntryDate();

        // Displays values in editText fields and moves cursor to the right
        lblJob.setText("Job Type: " + jobType);
        lblCustomer.setText("Customer: " + customer);
        lblDescription.setText(description);
        lblHours.setText("Hours: " + hours);
        lblDate.setText("Date: " + dateText);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

    }
}
