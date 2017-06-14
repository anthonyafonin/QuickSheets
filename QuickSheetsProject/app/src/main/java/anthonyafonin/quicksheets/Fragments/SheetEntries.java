package anthonyafonin.quicksheets.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import anthonyafonin.quicksheets.AccountSharedPref;
import anthonyafonin.quicksheets.AddForms.AddEntryForm;
import anthonyafonin.quicksheets.AddForms.AddSheetForm;
import anthonyafonin.quicksheets.EntryDetails;
import anthonyafonin.quicksheets.HomeActivity;
import anthonyafonin.quicksheets.R;
import anthonyafonin.quicksheets.UpdateForms.UpdateEntry;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Timesheet;
import anthonyafonin.quicksheets.database.Model.TimesheetEntry;

import static android.content.ContentValues.TAG;
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
    ArrayList<TimesheetEntry> entries;
    File outputFile;

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

            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddEntryForm.class);
                startActivity(i);
            }
        });



        FloatingActionButton fabSave = (FloatingActionButton) rootView.findViewById(R.id.saveSheet);
        fabSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                createPDF();
            }
        });

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

            //Step 1
            Document document = new Document();

            //Step 2
            PdfWriter.getInstance(document, output);

            //Step 3
            document.open();

            //Step 4 Add content
            document.add(new Paragraph("Test1"));

            //Step 5: Close the document
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


    private void viewPdf(){
        /*WebView webview =  new WebView(getActivity());
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);*/
    }


    // Refreshes Adapter list onStart
    @Override
    public void onStart() {
        super.onStart();

        // Displays custom listView
        entries = (ArrayList<TimesheetEntry>) db.getAllEntrys(timesheetId);
        EntryList customAdapter = new EntryList(getActivity(),R.layout.list_entry, entries);
        listContent.setAdapter(customAdapter);
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

                                        db.deleteEntry(entry, entryId);

                                        // Displays custom listView
                                        entries = (ArrayList<TimesheetEntry>) db.getAllEntrys(timesheetId);
                                        EntryList customAdapter = new EntryList(getActivity(),R.layout.list_entry, entries);
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