package shree.e.timetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TaskList extends AppCompatActivity {

    EditText editTextAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        editTextAddTask = findViewById(R.id.editTextAddTask);
    }


    public void addTask(View view) {

        String newTask = editTextAddTask.getText().toString();

        if(newTask.compareTo("") == 0)          Toast.makeText(this, "Error! Task name can not be blank.", Toast.LENGTH_LONG).show();
        else {

        }
    }
}
