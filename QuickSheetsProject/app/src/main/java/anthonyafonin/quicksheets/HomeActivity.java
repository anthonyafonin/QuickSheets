/*
 * Programmer: Afonin, Anthony
 * Chemeketa Community College
 * Created: Tuesday, June 13
 * Assignment: CIS234J, Final Project - QuickSheets
 * File Name: HomeActivity.java
 */

/**
 * An application that manages timesheets using a sqlite database.
 */
package anthonyafonin.quicksheets;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import anthonyafonin.quicksheets.Fragments.Sheets;
import anthonyafonin.quicksheets.UpdateForms.UpdateAccount;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;

/**
 * The home activity of the application. Contains a nav drawer and fragment container.
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Declare variables and objects.
    DatabaseHelper db = new DatabaseHelper(this);
    Context mContext = this;
    String name, email, phone;
    TextView nameText, emailText, phoneText;
    Account acc;
    NavigationView nav;
    private Menu menu;

    /**
     * The onCreate method of the activity.
     * Puts the activity together.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setActionBarTitle("QuickSheets");

        // Retrieves logged in account
        int accountId = AccountSharedPref.loadAccountId(mContext);
        acc = db.getAccount(accountId);

        //Assign account info to nav menu txt views
        nav = (NavigationView) findViewById(R.id.nav_view);
        View header = nav.getHeaderView(0);
        nameText = (TextView) header.findViewById(R.id.lblName);
        emailText = (TextView) header.findViewById(R.id.lblEmail);
        phoneText = (TextView) header.findViewById(R.id.lblPhone);

        // Creates a fragment manager and shows sheets fragment
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Sheets f1 = new Sheets();
        ft.add(R.id.fragment_container, f1);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Sets the title of the action bar.
     * @param title Title of action bar.
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Handles the drawer closing.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Clears nav items and populates account info in nav header.
     */
    @Override
    public void onStart() {
        super.onStart();

        // Deselect all nav items
        nav.getMenu().findItem(R.id.nav_acc).setChecked(false);
        nav.getMenu().findItem(R.id.nav_home).setChecked(false);
        nav.getMenu().findItem(R.id.nav_logout).setChecked(false);
        nav.getMenu().findItem(R.id.nav_backup).setChecked(false);
        nav.getMenu().findItem(R.id.nav_logout).setChecked(false);

        // Gets account info and displays in nav menu
        name = acc.getFirstName() + " " + acc.getLastName();
        email = acc.getEmail();
        phone = acc.getPhoneNumber();
        nameText.setText(name);
        emailText.setText(email);
        phoneText.setText(phone);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu Menu object.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        //getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    /**
     * Handle action bar item clicks here.
     * @param item Menuitem object.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle navigation view item clicks here.
     * @param item Menuitem object.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_acc) {
            Intent i = new Intent(this, UpdateAccount.class);
            this.startActivity(i);

        } else if (id == R.id.nav_backup) {

        }  else if (id == R.id.nav_logout){

            // Clears the Shared Preference
            AccountSharedPref.logoutUser(this);

            Intent homepage = new Intent(this, LoginActivity.class);
            this.startActivity(homepage);

        } else if (id == R.id.nav_home){

            //Redirect to HomeActivity
            Intent intent = new Intent(mContext, HomeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
