package com.mattgd.schoolwakeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Matt on 3/9/2015.
 */
public class SettingsActivity extends Activity {

    EditText schoolIdText;
    Button enrollButton;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        schoolIdText = (EditText) findViewById(R.id.schoolIdText);

        enrollButton = (Button) findViewById(R.id.enrollButton);
        enrollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String idRaw = schoolIdText.getText().toString();

                    // Check if the raw school ID is an Integer
                    if (isInteger(idRaw, 10)) {
                        enrollStudent(Integer.parseInt(idRaw)); // Enroll the student
                    }

                } catch (Exception e) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(SettingsActivity.this, "Error!", Toast.LENGTH_LONG);
                    Log.i("Settings", "Error with School ID exception!");
                }
            }
        });
    }

    public void enrollStudent(int schoolID) throws IOException, JSONException {
        /*
        * Ideally, there should be a sign-in system, so that the server can send the information to
        * the user when needed, instead of having the user request the data every so often.
        * */

        // Get update time for school. For example: 5 AM.
        // Manual update
        SchoolInfo school = new SchoolInfo(schoolID); // Enrolls device in school based on ID
     }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

}
