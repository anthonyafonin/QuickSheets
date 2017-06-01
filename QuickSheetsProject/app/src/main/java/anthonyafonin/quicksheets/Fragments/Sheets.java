package anthonyafonin.quicksheets.Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.AddSheetForm;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;


public class Sheets extends Fragment {

    Button addSheet;
    DatabaseHelper db;
    int accountId;
    public static int timesheetId;
    public static String timesheetTitle;

    @Override
    public View onCreateView(
            final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.frag_sheets, container, false);
        db = new DatabaseHelper(getActivity());
        accountId = AccountSharedPref.loadAccountId(getActivity());
        ((HomeActivity) getActivity()).setActionBarTitle("QuickSheets");


        // Action listener for add sheet button
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.addSheet);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddSheetForm.class);
                startActivity(i);
            }
        });

        // Defines listview in layout and displays timesheets
        final ListView listContent = (ListView)rootView.findViewById(R.id.sheetList);
        ArrayAdapter adapter = new ArrayAdapter(
                        getActivity(), android.R.layout.simple_list_item_1,
                        db.getAllTimesheets(accountId));
        listContent.setAdapter(adapter);

        // Handles when an item is clicked
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> parent, View view, int position, long id) {

                Timesheet sheet = (Timesheet) listContent.getItemAtPosition(position);

                timesheetTitle = sheet.getSheetTitle();
                timesheetId = sheet.getId();

                // Creates a fragment manager and shows sheets fragment
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SheetEntries f2 = new SheetEntries();
                ft.replace(R.id.fragment_container, f2);
                ft.addToBackStack(null);
                ft.commit();


                Toast.makeText(getActivity(),
                        "Sheet id: " + timesheetId, Toast.LENGTH_LONG).show();
            }
        });

        // Handles when an item is clicked and held
        listContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> arg0, View arg1, int position, long id) {

                // Gets the timesheet id and deletes selected
                Timesheet sheet = (Timesheet) listContent.getItemAtPosition(position);
                db.deleteTimesheet(sheet, sheet.getId());

                // Refreshes the adapter listview
                ArrayAdapter adapter = new ArrayAdapter(
                        getActivity(), android.R.layout.simple_list_item_1,
                        db.getAllTimesheets(accountId));
                listContent.setAdapter(adapter);

                return true;
            }
        });
        return rootView;
    }
}



