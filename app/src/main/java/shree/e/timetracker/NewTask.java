package shree.e.timetracker;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        calendarViewNewTask.setVisibility(View.INVISIBLE);


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
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // don't delete this
    }
}
