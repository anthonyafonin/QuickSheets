package anthonyafonin.quicksheets;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import anthonyafonin.quicksheets.Fragments.Sheets;
import anthonyafonin.quicksheets.UpdateForms.UpdateAccount;
import anthonyafonin.quicksheets.database.DatabaseHelper;
import anthonyafonin.quicksheets.database.Model.Account;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper db = new DatabaseHelper(this);
    Context mContext = this;
    String name, email, phone;
    TextView nameText, emailText, phoneText;
    Account acc;
    NavigationView nav;

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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_acc) {
            Intent i = new Intent(this, UpdateAccount.class);
            this.startActivity(i);

        } else if (id == R.id.nav_backup) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout){

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
