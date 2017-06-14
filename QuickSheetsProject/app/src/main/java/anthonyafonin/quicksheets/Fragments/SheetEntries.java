/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: SheetEntries.java
 */

/**
 * Contains List Fragments and custom listviews.
 */
package anthonyafonin.quicksheets.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
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
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.AddForms.AddEntryForm;
import anthonyafonin.quicksheets.EntryDetails;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.UpdateForms.UpdateEntry;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetId;
import static anthonyafonin.quicksheets.Fragments.Sheets.timesheetTitle;

/**
 * Displays listview of TimesheetEntries inside fragment.
 */
public class SheetEntries extends Fragment {

    // Declare variables and objects.
    Button addSheet;
    ListView listContent;
    DatabaseHelper db;
    TimesheetEntry entry;
    int accountId;
    public static int entryId;
    public static String entryDate;
    ArrayList<TimesheetEntry> entries;
    File outputFile;

    /**
     * Creates the List.
     * @param inflater LayoutInflater.
     * @param container ViewGroup.
     * @param savedInstanceState Saved Instance State.
     */
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        // Creates view and db helper and renames action bar
        final View rootView = inflater.inflate(R.layout.frag_entries, container, false);
        db = new DatabaseHelper(getActivity());
        accountId = AccountSharedPref.loadAccountId(getActivity());
        ((HomeActivity) getActivity()).setActionBarTitle(timesheetTitle);

        listContent = (ListView) rootView.findViewById(R.id.entryList);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.addEntry);
        fab.setOnClickListener(new View.OnClickListener() {
            /**
             * Redirects to AddEntryForm Activity.
             * @param view View.
             */
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddEntryForm.class);
                startActivity(i);
            }
        });



        FloatingActionButton fabSave = (FloatingActionButton) rootView.findViewById(R.id.saveSheet);
        fabSave.setOnClickListener(new View.OnClickListener() {

            /**
             * Creates PDF of Timesheet.
             */
            public void onClick(View view) {

                createPDF();
            }
        });

        // Action listener when list item is clicked.
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(
                    AdapterView<?> parent, View view, int position, long id) {

                entry = (TimesheetEntry) listContent.getItemAtPosition(position);
                entryDate = entry.getEntryDate();
                entryId = entry.getId();
                Intent i = new Intent(getActivity(), EntryDetails.class);
                startActivity(i);

            }
        });

        // Action listener when list item is clicked and held.
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

    /**
     * Creates a PDF of the selected Timesheet.
     */
    private void createPDF() {

        try {
            // create a File object for the parent directory
            File sheetDirectory = new File(getActivity().getFilesDir(), "Sheets");

            // have the object build the directory structure, if needed.
            sheetDirectory.mkdirs();

            // create a File object for the output file
            outputFile = new File(sheetDirectory, "Timesheet1");

            //now attach OutputStream to the file object, instead of a String representation

            FileOutputStream output = new FileOutputStream(outputFile);

            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();
            document.add(new Paragraph("Test1"));
            document.close();
            Toast.makeText(getActivity(),
                    "Document Saved", Toast.LENGTH_LONG).show();
        }
        catch(FileNotFoundException e){
            Toast.makeText(getActivity(),
                    "File not found", Toast.LENGTH_LONG).show();
        }
        catch(DocumentException e){
            Toast.makeText(getActivity(),
                    "Invalid Document", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Refreshes Adapter list onStart
     */
    @Override
    public void onStart() {
        super.onStart();

        // Displays custom listView
        entries = (ArrayList<TimesheetEntry>) db.getAllEntrys(timesheetId);
        EntryList customAdapter = new EntryList(getActivity(),R.layout.list_entry, entries);
        listContent.setAdapter(customAdapter);
    }

    /**
     * Creates dialog when item is click and held, update/delete
     */
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

                                        db.deleteEntry(entry, entryId);

                                        // Displays custom listView
                                        entries = (ArrayList<TimesheetEntry>)
                                                db.getAllEntrys(timesheetId);
                                        EntryList customAdapter =
                                                new EntryList(getActivity(),
                                                R.layout.list_entry, entries);
                                        listContent.setAdapter(customAdapter);
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