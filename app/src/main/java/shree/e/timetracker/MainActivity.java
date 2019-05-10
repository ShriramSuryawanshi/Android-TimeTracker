package shree.e.timetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void Login(View view) {

        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        Toast.makeText(this, "User - " + username + ", Pass - " + password, Toast.LENGTH_LONG).show();
    }
}
