package shree.e.timetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "ShriramSuryawanshi";
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    EditText editTextUsername;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity_main);

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void Login(View view) {

        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        if(username.compareTo("")== 0)            Toast.makeText(this, "Error! Username can not be blank.", Toast.LENGTH_LONG).show();
        else if(password.compareTo("") == 0)      Toast.makeText(this, "Error! Password can not be blank.", Toast.LENGTH_LONG).show();
        else {

            String savedUser = prefs.getString("savedUser", "");
            String savedPass = prefs.getString("savedPass", "");

            if (savedUser.compareTo("") == 0) {
                    editor.putString("savedUser", username);
                    editor.putString("savedPass", password);
                    editor.apply();
                    Toast.makeText(this, "Welcome to TimeTracker!", Toast.LENGTH_LONG).show();
                    // Show add tasklist page
                    startActivity(new Intent(MainActivity.this, TaskList.class));
            }
            else {

                if(username.compareTo(savedUser) == 0 && password.compareTo(savedPass) == 0) {
                    Toast.makeText(this, "Welcome back to TimeTracker!", Toast.LENGTH_LONG).show();
                    // Show main window
                    startActivity(new Intent(MainActivity.this, Today.class));
                }
                else {
                    editTextUsername.setText("");
                    editTextPassword.setText("");
                    Toast.makeText(this, "Error! Invalid login credentials! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
