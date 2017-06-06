package anthonyafonin.quicksheets.Fragments;

import android.app.AlertDialog;
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
import anthonyafonin.quicksheets.AddForms.AddEntryForm;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.UpdateForms.UpdateEntry;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;
import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetTitle;


public class SheetEntries extends Fragment {

    Button addSheet;
    DatabaseHelper db;
    int accountId;
    public static int entryId;
    public static String entryDate;
    ListView listContent;
    TimesheetEntry entry;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        // Creates view and db helper and renames action bar
        View rootView = inflater.inflate(R.layout.frag_entries, container, false);
        db = new DatabaseHelper(getActivity());
        accountId = AccountSharedPref.loadAccountId(getActivity());
        ((HomeActivity) getActivity()).setActionBarTitle(timesheetTitle);

        listContent = (ListView) rootView.findViewById(R.id.entryList);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.addEntry);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddEntryForm.class);
                startActivity(i);
            }
        });


        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> parent, View view, int position, long id) {

                entry = (TimesheetEntry) listContent.getItemAtPosition(position);
                entryDate = entry.getEntryDate();
                entryId = entry.getId();

            }
        });


        listContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> arg0, View arg1, int position, long id) {

                // Gets the timesheet id and deletes selected
                entry = (TimesheetEntry) listContent.getItemAtPosition(position);
                entryDate = entry.getEntryDate();
                entryId = entry.getId();

                createDialog();

                return true;
            }
        });

        return rootView;
    }

    // Refreshes Adapter list onStart
    @Override
    public void onStart() {
        super.onStart();

        // refreshes fragment and displays entries
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_list_item_1,
                db.getAllEntrys(timesheetId));
        listContent.setAdapter(adapter);
    }


    // Creates dialog when item is click and held, update/delete
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

                                        db.deleteEntry(entry, entry.getId());

                                        // Refreshes the adapter listview
                                        ArrayAdapter adapter = new ArrayAdapter(
                                                getActivity(), android.R.layout.simple_list_item_1,
                                                db.getAllEntrys(timesheetId));
                                        listContent.setAdapter(adapter);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                })
                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), UpdateEntry.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}