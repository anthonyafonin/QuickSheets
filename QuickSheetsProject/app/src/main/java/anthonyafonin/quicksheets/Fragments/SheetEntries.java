package anthonyafonin.quicksheets.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import anthonyafonin.quicksheets.AddEntryForm;
import anthonyafonin.quicksheets.AddSheetForm;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;

import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;
import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetTitle;


public class SheetEntries extends Fragment {

    Button addSheet;
    DatabaseHelper db;
    int accountId;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        // Creates view and db helper and renames action bar
        View rootView = inflater.inflate(R.layout.frag_entries, container, false);
        db = new DatabaseHelper(getActivity());
        accountId = AccountSharedPref.loadAccountId(getActivity());
        ((HomeActivity) getActivity()).setActionBarTitle(timesheetTitle);

        Toast.makeText(getActivity(),
                "Sheet id: " + timesheetId, Toast.LENGTH_LONG).show();


        // EXPERIMENT TOOLBAR TITLE LISTENER -- BACK --
//        final int abTitleId = getResources().getIdentifier(timesheetTitle, "id", "android");
//        rootView.findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().popBackStackImmediate();
//            }
//        });

        ///////////////////////////////////////////
        // Action listener for add sheet button
        ///////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.addEntry);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddEntryForm.class);
                startActivity(i);
            }
        });


        // Defines listview in layout and displays timesheets
        final ListView listContent = (ListView) rootView.findViewById(R.id.sheetList);
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_list_item_1,
                db.getAllEntrys());
        //listContent.setAdapter(adapter);

       /* listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> parent, View view, int position, long id) {

                Timesheet sheet = (Timesheet) listContent.getItemAtPosition(position);

                Toast.makeText(getActivity(),
                        "Sheet id: " + sheet.getId(), Toast.LENGTH_LONG).show();
            }
        });

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
        });*/
        return rootView;
    }

}