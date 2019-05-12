package shree.e.timetracker;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Today extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String datePickerType = "";
    String currentFrame = "Home";

    EditText editTextStartTime;
    EditText editTextEndTime;
    EditText editTextTaskTitle;

    RelativeLayout addTaskFrame;
    RelativeLayout editTaskFrame;

    SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);








        try  {
            myDB = this.openOrCreateDatabase("myDB", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS Summary (Date VARCHAR PRIMARY KEY, Task1 VARCHAR, Task2 VARCHAR, Task3 VARCHAR, Task4 VARCHAR, Task5 VARCHAR, Task6 VARCHAR, Task7 VARCHAR, Task8 VARCHAR, Task9 VARCHAR, Task10 VARCHAR)");
        }
        catch(Exception e) {
            Toast.makeText(this, "Database Error - " + e.toString(), Toast.LENGTH_LONG).show();
        }






    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.today, menu);
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

        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home)             fragment = new Home();
        else if (id == R.id.nav_new)         fragment = new NewTask();
        else if (id == R.id.nav_settings) {

            currentFrame = "Settings";


        }

        if(fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screen_area, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    /* -------------------------------- START : Home Screen ------------------------------------------------------------- */







    /* -------------------------------- END : Home Screen ------------------------------------------------------------- */


    /* -------------------------------- START : Add New Tasks ------------------------------------------------------------- */


  /*


    /* -------------------------------- END : Add New Tasks ------------------------------------------------------------- */


    /* -------------------------------- START : Edit Task List ------------------------------------------------------------- */

  /*  public void setEditTaskFrame() {


        addTaskFrame.setVisibility(View.INVISIBLE);
        editTaskFrame.setVisibility(View.VISIBLE);
    }


    public void submitNewTask() {


    }


    public void showTaskList() {


    }

    /* -------------------------------- END : Edit Task List ------------------------------------------------------------- */
}
