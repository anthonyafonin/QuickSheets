/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: Sheets.java
 */

/**
 * Contains List Fragments and custom listviews.
 */
package anthonyafonin.quicksheets.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.AddForms.AddSheetForm;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.UpdateForms.UpdateSheet;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;

/**
 * Displays listview of TimesheetEntries inside fragment.
 */
public class Sheets extends Fragment {

    // Declare variables and objects.
    Button addSheet;
    DatabaseHelper db;
    int accountId;
    public static int timesheetId;
    public static String timesheetTitle;
    Timesheet sheet;
    ListView listContent;
    ArrayList<Timesheet> sheets;
    View rootView;

    @Override
    public View onCreateView(
            final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.frag_sheets, container, false);
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
        listContent = (ListView) rootView.findViewById(R.id.sheetList);

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

    /**
     * Refreshes Adapter list onStart
     */
    @Override
    public void onStart() {
        super.onStart();

        // Displays custom listView
        sheets = (ArrayList<Timesheet>) db.getAllTimesheets(accountId);
        SheetList customAdapter = new SheetList(getActivity(), R.layout.list_sheet, sheets);
        listContent.setAdapter(customAdapter);

    }

    /**
     * Creates dialog when item is click and held, update/delete
     */
    public void createDialog() {
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

                                        db.deleteTimesheet(sheet, timesheetId);

                                        // Displays custom listView
                                        sheets = (ArrayList<Timesheet>)
                                                db.getAllTimesheets(accountId);
                                        SheetList customAdapter =
                                                new SheetList(getActivity(),
                                                R.layout.list_sheet, sheets);
                                        listContent.setAdapter(customAdapter);
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



