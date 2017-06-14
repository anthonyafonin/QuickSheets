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


public class SheetList extends ArrayAdapter{
    private Context context;
    private ArrayList<Timesheet> sheet;
    String dateRange;
    DatabaseHelper db;
    double sheetHours;

    public SheetList(Context context, int textViewResourceId, ArrayList sheets) {
        super(context,textViewResourceId, sheets);

        this.context = context;
        sheet = sheets;

    }

    private class ViewHolder
    {
        TextView textViewTitle;
        TextView textViewRange;
        TextView textViewHours;
    }

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
        else {
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
