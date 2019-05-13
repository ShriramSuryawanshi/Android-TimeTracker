package shree.e.timetracker;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class EditTaskList extends Fragment {

    Button buttonSubmitTask;
    EditText editTextTaskTitle;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String MY_PREFS_NAME = "TaskList";
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    int lastCount;
    List<String> taskList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mylayout_edit_tasklist, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTaskTitle = view.findViewById(R.id.editTextTaskTitle);
        buttonSubmitTask = view.findViewById(R.id.buttonSubmitTask);

        prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        buttonSubmitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String newTask = editTextTaskTitle.getText().toString();

            if(newTask.compareTo("") == 0)      Toast.makeText(getActivity(), "Error! Task name can not be blank.", Toast.LENGTH_SHORT).show();
            else {

                String key = "Task" + lastCount;
                editor.putString(key, newTask);
                editor.apply();
                editTextTaskTitle.setText("");
                Toast.makeText(getActivity(), "Task list is updated!", Toast.LENGTH_SHORT).show();

                lastCount = 0;
                taskList.clear();

                for(int i = 1; i < 100; i++) {

                    key = "Task" + i;
                    String savedTask = prefs.getString(key, "");

                    if(savedTask.compareTo("") == 0) {
                        lastCount = i;
                        break;
                    }
                    taskList.add(savedTask);
                }

                Collections.sort(taskList);

                mAdapter = new MyRecyclerViewAdapter(getDataSet());
                mRecyclerView.setAdapter(mAdapter);
            }

            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("shree", " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet() {

        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < taskList.size(); index++) {
            DataObject obj = new DataObject(taskList.get(index), "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }
}
