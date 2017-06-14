/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: SheetList.java
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


/**
 * Custom listview of Timesheets.
 */
public class SheetList extends ArrayAdapter{

    // Declare variables and objects.
    private Context context;
    private ArrayList<Timesheet> sheet;
    String dateRange;
    DatabaseHelper db;
    double sheetHours;

    /**
     * The constructor of the class.
     * @param context Context.
     * @param textViewResourceId Index.
     * @param sheets List of Timesheet Objects.
     */
    public SheetList(Context context, int textViewResourceId, ArrayList sheets) {
        super(context,textViewResourceId, sheets);

        this.context = context;
        sheet = sheets;
    }

    /**
     * Anonymous inner class.
     */
    private class ViewHolder
    {
        TextView textViewTitle;
        TextView textViewRange;
        TextView textViewHours;
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
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_sheet, null);

            holder = new ViewHolder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.lblTitle);
            holder.textViewRange = (TextView) convertView.findViewById(R.id.lblDateRange);
            holder.textViewHours = (TextView) convertView.findViewById(R.id.lblHours);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Timesheet tsheet = sheet.get(position);

        db = new DatabaseHelper(context);
        sheetHours = db.getSheetHours(tsheet.getId());
        String hours = Double.toString(sheetHours);
        dateRange = tsheet.getStartDate() + " \u2014 " + tsheet.getEndDate() + ", " + tsheet.getYearDate();

        holder.textViewTitle.setText(  tsheet.getSheetTitle());
        holder.textViewRange.setText("\t\t\t\t\t\t" + dateRange);
        holder.textViewHours.setText("Total: " + hours);
        return convertView;
    }
}
