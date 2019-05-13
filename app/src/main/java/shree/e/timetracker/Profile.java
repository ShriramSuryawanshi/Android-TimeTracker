package shree.e.timetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class Profile extends Fragment {

    public static final String MY_PREFS_NAME = "ShriramSuryawanshi";
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    EditText editTextUsernameProfile;
    EditText editTextFirstname;
    EditText editTextLastName;
    EditText editTextPassword2;
    EditText editTextPassword3;
    Button buttonUpdateProfile;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mylayout_profile, null);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editTextUsernameProfile = view.findViewById(R.id.editTextUsernameProfile);
        editTextFirstname = view.findViewById(R.id.editTextFirstname);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextPassword2 = view.findViewById(R.id.editTextPassword2);
        editTextPassword3 = view.findViewById(R.id.editTextPassword3);
        buttonUpdateProfile = view.findViewById(R.id.buttonUpdateProfile);

        String savedUser = prefs.getString("savedUser", "");
        String firstName = prefs.getString("firstName", "");
        String lastName = prefs.getString("lastName", "");

        editTextUsernameProfile.setText(savedUser);
        editTextFirstname.setText(firstName);
        editTextLastName.setText(lastName);


        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newUser = editTextFirstname.getText().toString();
                String newFirst = editTextFirstname.getText().toString();
                String newLast = editTextLastName.getText().toString();
                String newPass1 = editTextPassword2.getText().toString();
                String newPass2 = editTextPassword3.getText().toString();

                if (newUser.compareTo("") == 0)                  Toast.makeText(getActivity(), "Username can not be blank.", Toast.LENGTH_SHORT).show();
                else if (newFirst.compareTo("") == 0)            Toast.makeText(getActivity(), "First Name can not be blank.", Toast.LENGTH_SHORT).show();
                else if (newLast.compareTo("") == 0)             Toast.makeText(getActivity(), "Last Name can not be blank.", Toast.LENGTH_SHORT).show();
                else if (newPass1.compareTo("") == 0)            Toast.makeText(getActivity(), "Password can not be blank.", Toast.LENGTH_SHORT).show();
                else if (newPass1.compareTo(newPass2) != 0)      Toast.makeText(getActivity(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                else {

                    editor.putString("savedUser", newUser);
                    editor.putString("firstName", newFirst);
                    editor.putString("lastName", newLast);
                    editor.putString("savedPass", newPass1);
                    editor.apply();
                    Toast.makeText(getActivity(), "Profile details updated successfully", Toast.LENGTH_SHORT).show();
                }

                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
    }
}
