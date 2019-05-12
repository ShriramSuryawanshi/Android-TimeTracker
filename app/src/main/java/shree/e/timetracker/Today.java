package shree.e.timetracker;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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

public class Today extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    String datePickerType = "";

    EditText editTextStartTime;
    EditText editTextEndTime;

    TextView textViewHomeDate;

    CalendarView calendarViewHome;

    RelativeLayout addTaskFrame;

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


        addTaskFrame = findViewById(R.id.AddTaskFrame);

        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);

        textViewHomeDate = findViewById(R.id.textViewHomeDate);

        calendarViewHome = findViewById(R.id.calendarViewHome);


        setHomeFrame();


        calendarViewHome.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date dateRepresentation = cal.getTime();
                SimpleDateFormat df = new SimpleDateFormat("EEEE - MMMM dd, yyyy");
                String formattedDate = df.format(dateRepresentation);
                textViewHomeDate.setText(formattedDate);
                calendarViewHome.setVisibility(View.INVISIBLE);
            }
        });
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

        if (id == R.id.nav_home) {

            setHomeFrame();

        } else if (id == R.id.nav_new) {

            setAddTaskFrame();
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /* -------------------------------- Home Screen ------------------------------------------------------------- */

    public void setHomeFrame() {

        calendarViewHome.setVisibility(View.INVISIBLE);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEEE - MMMM dd, yyyy");
        String formattedDate = df.format(c);
        textViewHomeDate.setText(formattedDate);

        addTaskFrame.setVisibility(View.INVISIBLE);
    }



    /* -------------------------------- Add New Tasks ------------------------------------------------------------- */


    public void setAddTaskFrame() {

        calendarViewHome.setVisibility(View.INVISIBLE);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEEE - MMMM dd, yyyy");
        String formattedDate = df.format(c);
        textViewHomeDate.setText(formattedDate);

        String currentTime = java.text.DateFormat.getTimeInstance().format(new Date());
        editTextStartTime.setText(currentTime);
        editTextEndTime.setText(currentTime);

        addTaskFrame.setVisibility(View.VISIBLE);
    }


    public void changeDate(View view) {

        calendarViewHome.setVisibility(View.VISIBLE);
    }


    public void getStartTime(View view) {

        datePickerType = "start";
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "Start Time Picker");
    }


    public void getEndTime(View view) {

        datePickerType = "end";
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "End Time Picker");
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        boolean isPM = (hourOfDay >= 12);

        if(datePickerType.compareTo("") == 0) {

            editTextStartTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            editTextEndTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
        }
        else if(datePickerType.compareTo("start") == 0)     editTextStartTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
        else if(datePickerType.compareTo("end") == 0)       editTextEndTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
    }
}
