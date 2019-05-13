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

public class Summary extends Fragment {

    CalendarView calendarViewHome;
    TextView textViewHomeDate;
    SQLiteDatabase myDB;
    private RecyclerView mRecyclerView2;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;
    String[] row = new String[4];
    List<String[]> taskList = new ArrayList<String[]>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mylayout_summary, null);
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

        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.my_recycler_view2);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager2 = new LinearLayoutManager(getActivity());
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mAdapter2 = new MyRecyclerViewAdapter2(getDataSet());
        mRecyclerView2.setAdapter(mAdapter2);
        mRecyclerView2.setVisibility(View.VISIBLE);

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
            mRecyclerView2.setVisibility(View.VISIBLE);
            mAdapter2 = new MyRecyclerViewAdapter2(getDataSet());
            mRecyclerView2.setAdapter(mAdapter2);
            }
        });


        textViewHomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarViewHome.setVisibility(View.VISIBLE);
                mRecyclerView2.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter2) mAdapter2).setOnItemClickListener(new MyRecyclerViewAdapter2
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("shree", " Clicked on Item " + position);
            }
        });
    }


    private ArrayList<DataObject2> getDataSet() {
        ArrayList results = new ArrayList<DataObject2>();
        for (int index = 0; index < taskList.size(); index++) {
            row = taskList.get(index);
            DataObject2 obj = new DataObject2(row[0], row[1]);
            results.add(index, obj);
        }
        return results;
    }


    public void fetchData(String date) {

        try  {

            myDB = getActivity().openOrCreateDatabase("myDB", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS DataTable (Date VARCHAR, StartTime VARCHAR, EndTime VARCHAR, Duration INT, Task VARCHAR, Note VARCHAR)");
            Cursor c = myDB.rawQuery("SELECT Task, SUM(Duration) FROM DataTable WHERE Date = '" + date + "' " + "GROUP BY Task", null);
            c.moveToFirst();

            int count = c.getCount();

            if (count == 0)         Toast.makeText(getActivity(), "No activity recorded for " + date, Toast.LENGTH_SHORT).show();
            else {
                for (int i = 0; i < count; i++) {

                    String task =  c.getString(0);
                    String duration = getDuration(c.getInt(1));

                    taskList.add(new String[] {task, duration});
                    c.moveToNext();
                }
            }
        }catch(Exception e) {
            Toast.makeText(getActivity(), "Database Error - " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getDuration(int time) {

        String duration;
        duration = (time/100) + "." + (time%100) + " hrs";
        return duration;
    }
}
