package shree.e.timetracker;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NewTask extends Fragment implements TimePickerDialog.OnTimeSetListener {

    CalendarView calendarViewNewTask;
    TextView textViewNewTaskDate;
    EditText editTextStartTime;
    EditText editTextEndTime;
    EditText editTextNote;
    RelativeLayout frameNewTask;
    Button buttonStartTime;
    Button buttonEndTime;
    Button buttonSubmitEntry;
    Spinner dropdown;
    SQLiteDatabase myDB;
    int lastCount;
    List<String> taskList = new ArrayList<>();
    public static final String MY_PREFS_NAME = "TaskList";
    SharedPreferences prefs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mylayout_new_task, null);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewNewTask = view.findViewById(R.id.calendarViewNewTask);
        textViewNewTaskDate = view.findViewById(R.id.textViewNewTaskDate);
        editTextStartTime = view.findViewById(R.id.editTextStartTime);
        editTextEndTime = view.findViewById(R.id.editTextEndTime);
        editTextNote = view.findViewById(R.id.editTextNote);
        frameNewTask = view.findViewById(R.id.frameNewTask);
        buttonStartTime = view.findViewById(R.id.buttonStartTime);
        buttonEndTime = view.findViewById(R.id.buttonEndTime);
        buttonSubmitEntry = view.findViewById(R.id.buttonSubmitEntry);
        dropdown = view.findViewById(R.id.editTextTaskList);
        prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        calendarViewNewTask.setVisibility(View.INVISIBLE);

        lastCount = 0;
        taskList.clear();

        for(int i = 1; i < 100; i++) {

            String key = "Task" + i;
            String savedTask = prefs.getString(key, "");

            if(savedTask.compareTo("") == 0) {
                lastCount = i;
                break;
            }

            taskList.add(savedTask);
        }

        Collections.sort(taskList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, taskList);
        dropdown.setAdapter(adapter);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEEE - MMMM dd, yyyy");
        String formattedDate = df.format(c);
        textViewNewTaskDate.setText(formattedDate);

        String currentTime = java.text.DateFormat.getTimeInstance().format(new Date());
        editTextStartTime.setText(currentTime);
        editTextEndTime.setText(currentTime);

        calendarViewNewTask.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date dateRepresentation = cal.getTime();
                SimpleDateFormat df = new SimpleDateFormat("EEEE - MMMM dd, yyyy");
                String formattedDate = df.format(dateRepresentation);

                textViewNewTaskDate.setText(formattedDate);
                calendarViewNewTask.setVisibility(View.INVISIBLE);
                frameNewTask.setVisibility(View.VISIBLE);
            }
        });


        textViewNewTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarViewNewTask.setVisibility(View.VISIBLE);
                frameNewTask.setVisibility(View.INVISIBLE);
            }
        });


        buttonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("Type", "Start");

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setArguments(args);
                timePicker.show(getFragmentManager(), "Start Time Picker");
            }
        });


        buttonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("Type", "End");

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setArguments(args);
                timePicker.show(getFragmentManager(), "End Time Picker");
            }
        });


        buttonSubmitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedDate = textViewNewTaskDate.getText().toString();
                String startTime = editTextStartTime.getText().toString();
                String endTime = editTextEndTime.getText().toString();
                String task = dropdown.getSelectedItem().toString();
                String note = editTextNote.getText().toString();

                int startT = getTime(startTime);
                int endT = getTime(endTime);
                int duration = endT - startT;

                String query = "INSERT INTO DataTable (Date, StartTime, EndTime, Duration, Task, Note) VALUES ('" + selectedDate + "', " + startT + ", " + endT + ", " + duration + ", '" + task + "', '" + note + "')";
                System.out.println(query);

                if(duration <= 0)           Toast.makeText(getActivity(), "For any task, End Time should be greater than the Start Time.", Toast.LENGTH_SHORT).show();
                else {

                    try  {
                        myDB = getActivity().openOrCreateDatabase("myDB", MODE_PRIVATE, null);
                        myDB.execSQL("CREATE TABLE IF NOT EXISTS DataTable (Date VARCHAR, StartTime INT(10), EndTime INT(10), Duration INT(10), Task VARCHAR, Note VARCHAR)");
                        myDB.execSQL(query);
                        Toast.makeText(getActivity(), "Task added successfully, visit Task Details page to review.", Toast.LENGTH_SHORT).show();
                        editTextNote.setText("");

                        /*Cursor c = myDB.rawQuery("SELECT * FROM DataTable", null);
                        c.moveToFirst();
                        int count = c.getCount();

                        for(int i = 0; i < count; i++) {

                            Log.i("Result - 1 ", c.getString(0));
                            Log.i("Result - 2 ", String.valueOf(c.getInt(1)));
                            Log.i("Result - 3 ", String.valueOf(c.getInt(2)));
                            Log.i("Result - 4 ", String.valueOf(c.getInt(3)));
                            Log.i("Result - 5 ", c.getString(4));
                            Log.i("Result - 6 ", c.getString(5));

                            c.moveToNext();
                        } */
                    }
                    catch(Exception e) {
                        Toast.makeText(getActivity(), "Database Error - " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public int getTime(String time) {

        int newTime;

        String[] arrOfStr = time.split("[: ]+");

        if(arrOfStr[2].compareTo("PM") == 0 && Integer.parseInt(arrOfStr[0]) != 12)             arrOfStr[0] = String.valueOf(Integer.parseInt(arrOfStr[0]) + 12);
        Toast.makeText(getActivity(), arrOfStr[0] + ", " + arrOfStr[1], Toast.LENGTH_SHORT).show();
        newTime = Integer.parseInt(arrOfStr[0] + arrOfStr[1]);

        return newTime;
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // don't delete this - @shree
    }
}
