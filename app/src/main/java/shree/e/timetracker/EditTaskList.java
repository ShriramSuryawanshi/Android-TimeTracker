package shree.e.timetracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class EditTaskList extends Fragment {

    Button buttonSubmitTask;
    EditText editTextTaskTitle;

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

        for(int i = 1; i < 11; i++) {

            String key = "Task" + i;
            String savedTask = prefs.getString(key, "");
            taskList.add(savedTask);

            if(savedTask.compareTo("") == 0) {
                lastCount = i;
                break;
            }
        }

        Collections.sort(taskList);

        buttonSubmitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTask = editTextTaskTitle.getText().toString();

                if(newTask.compareTo("") == 0)      Toast.makeText(getActivity(), "Error! Task name can not be blank.", Toast.LENGTH_SHORT).show();
                else {

                    if(lastCount >= 10)             Toast.makeText(getActivity(), "Only 10 tasks can be added to the list.", Toast.LENGTH_SHORT).show();
                    else {

                        String key = "Task" + lastCount;
                        editor.putString(key, newTask);
                        editor.apply();
                        Toast.makeText(getActivity(), "Task list is updated!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
