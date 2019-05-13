package shree.e.timetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Home extends Fragment {

    CalendarView calendarViewHome;
    TextView textViewHomeDate;
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager1;
    SQLiteDatabase myDB;

    String[] row = new String[4];
    List<String[]> taskList = new ArrayList<String[]>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mylayout_home, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewHome = view.findViewById(R.id.calendarViewHome);
        textViewHomeDate = view.findViewById(R.id.textViewHomeDate);
        calendarViewHome.setVisibility(View.INVISIBLE);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEEE - MMMM dd, yyyy");
        String formattedDate = df.format(c);
        textViewHomeDate.setText(formattedDate);

        taskList.clear();
        fetchData(textViewHomeDate.getText().toString());

        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.my_recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mAdapter1 = new MyRecyclerViewAdapter1(getDataSet());
        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView1.setVisibility(View.VISIBLE);

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

                taskList.clear();
                fetchData(textViewHomeDate.getText().toString());
                mRecyclerView1.setVisibility(View.VISIBLE);
                mAdapter1 = new MyRecyclerViewAdapter1(getDataSet());
                mRecyclerView1.setAdapter(mAdapter1);
            }
        });


        textViewHomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarViewHome.setVisibility(View.VISIBLE);
                mRecyclerView1.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter1) mAdapter1).setOnItemClickListener(new MyRecyclerViewAdapter1
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("shree", " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject1> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < taskList.size(); index++) {
            row = taskList.get(index);
            DataObject1 obj = new DataObject1(row[0], row[1], row[2], row[3]);
            results.add(index, obj);
        }
        return results;
    }


    public void fetchData(String date) {

        try  {

            myDB = getActivity().openOrCreateDatabase("myDB", MODE_PRIVATE, null);
            Cursor c = myDB.rawQuery("SELECT * FROM DataTable WHERE Date = '" + date + "'", null);
            c.moveToFirst();

            int count = c.getCount();

            if (count == 0)         Toast.makeText(getActivity(), "No activity recorded for " + date, Toast.LENGTH_SHORT).show();
            else {
                for (int i = 0; i < count; i++) {

                    String dbStartTime =  c.getString(1);
                    String dbEndTime = c.getString(2);
                    String finalTime = "Time - " + dbStartTime + " to " + dbEndTime;
                    String dbDuration = "for " + c.getString(3);
                    String dbTask =  "Task - " + c.getString(4);
                    String dbNote =  "Note - " + c.getString(5);

                    taskList.add(new String[] {dbTask, finalTime, dbDuration, dbNote});
                    c.moveToNext();
                }
            }
        }catch(Exception e) {
            Toast.makeText(getActivity(), "Database Error - " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
