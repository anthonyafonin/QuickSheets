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


public class EntryList extends ArrayAdapter{
    private Context context;
    private ArrayList<TimesheetEntry> entry;
    DatabaseHelper db;
    double entryHours;

    public EntryList(Context context, int textViewResourceId, ArrayList entry) {
        super(context,textViewResourceId, entry);

        this.context = context;
        this.entry = entry;

    }

    private class ViewHolder
    {
        TextView textViewJob;
        TextView textViewDate;
        TextView textViewHour;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
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

        holder.textViewJob.setText(tsEntry.getJobType());
        holder.textViewDate.setText("\t\t\t\t\t\tDate: " + tsEntry.getEntryDate());
        holder.textViewHour.setText("Hours: " + hours);
        return convertView;

    }
}
