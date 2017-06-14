/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: EntryList.java
 */

/**
 * Contains List Fragments and custom listviews.
 */
package anthonyafonin.quicksheets.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

/**
 * Custom listview of Timesheet Entries.
 */
public class EntryList extends ArrayAdapter{

    // Declare global variables and database objects.
    private Context context;
    private ArrayList<TimesheetEntry> entry;
    DatabaseHelper db;
    double entryHours;

    /**
     * The constructor of the class.
     * @param context Context.
     * @param textViewResourceId Index.
     * @param entry List of Timesheet Entry Objects.
     */
    public EntryList(Context context, int textViewResourceId, ArrayList entry) {
        super(context,textViewResourceId, entry);

        this.context = context;
        this.entry = entry;
    }

    /**
     * Anonymous inner class.
     */
    private class ViewHolder
    {
        TextView textViewJob;
        TextView textViewDate;
        TextView textViewHour;
    }

    /**
     * Changes each list item of the custom list.
     * @param position Index.
     * @param convertView View.
     * @param parent ViewGroup.
     * @return The custom list into SheetEntries Fragment.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater vi =
                    (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_entry, null);

            holder = new ViewHolder();
            holder.textViewJob = (TextView) convertView.findViewById(R.id.lblJobType);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.lblDate);
            holder.textViewHour = (TextView) convertView.findViewById(R.id.lblHour);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        TimesheetEntry tsEntry = entry.get(position);
        db = new DatabaseHelper(context);
        entryHours = tsEntry.getEntryHours();
        String hours = Double.toString(entryHours);

        // Creates a new list item.
        holder.textViewJob.setText(tsEntry.getJobType());
        holder.textViewDate.setText("\t\t\t\t\t\tDate: " + tsEntry.getEntryDate());
        holder.textViewHour.setText("Hours: " + hours);
        return convertView;

    }
}
