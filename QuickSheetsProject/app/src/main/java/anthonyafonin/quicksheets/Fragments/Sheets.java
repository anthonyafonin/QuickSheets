package anthonyafonin.quicksheets.Fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.AddForms.AddSheetForm;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.UpdateForms.UpdateSheet;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;


public class Sheets extends Fragment {

    Button addSheet;
    DatabaseHelper db;
    int accountId;
    public static int timesheetId;
    public static String timesheetTitle;
    ListView listContent;
    Timesheet sheet;

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
        listContent = (ListView)rootView.findViewById(R.id.sheetList);

        // Handles when an item is clicked
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> parent, View view, int position, long id) {

                sheet = (Timesheet) listContent.getItemAtPosition(position);
                timesheetTitle = sheet.getSheetTitle();
                timesheetId = sheet.getId();

                // Creates a fragment manager and shows sheets fragment
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SheetEntries f2 = new SheetEntries();
                ft.replace(R.id.fragment_container, f2);
                ft.addToBackStack(null);
                ft.commit();


            }
        });

        // Handles when an item is clicked and held
        listContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {

                // Gets the timesheet id and updates/deletes
                sheet = (Timesheet) listContent.getItemAtPosition(position);
                timesheetId = sheet.getId();

                createDialog();

                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Defines listview in layout and displays timesheets
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_list_item_1,
                db.getAllTimesheets(accountId));
        listContent.setAdapter(adapter);

    }

    public void createDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Select an option")
                .setCancelable(true)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Are you sure you want to delete?")
                                .setCancelable(false)
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        db.deleteTimesheet(sheet, sheet.getId());

                                        // Refreshes the adapter listview
                                        ArrayAdapter adapter = new ArrayAdapter(
                                                getActivity(), android.R.layout.simple_list_item_1,
                                                db.getAllTimesheets(accountId));
                                        listContent.setAdapter(adapter);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                })
                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), UpdateSheet.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}



