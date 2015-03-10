package com.mattgd.schoolwakeup;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mdzwonczyk on 3/3/2015.
 */
public class SchoolInfo extends Activity {

    JSONReader jsonReader;
    JSONObject jsonObject;
    SharedPreferences prefs;

    // If the constructor throw an exception, will be eligible for garbage collection.
    SchoolInfo(int id) throws IOException, JSONException {
        jsonReader = new JSONReader();
        jsonObject = jsonReader.getJSONObject("URL"); //TODO SETUP URL

        String value = jsonObject.getJSONObject("name").toString();
        SharedPreferences prefs = this.getSharedPreferences(
                getString(R.string.school_message), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("com.mattgd.app.school_name", value);

        value = jsonObject.getJSONObject("website").toString();
        editor.putString("com.mattgd.app.school_website", value);

        value = jsonObject.getJSONObject("start_time").toString();
        editor.putString("com.mattgd.app.school_start_time", value);

        value = jsonObject.getJSONObject("message").toString();
        editor.putString("com.mattgd.app.school_message", value);

        value = jsonObject.getJSONObject("update-time").toString();
        editor.putString("com.mattgd.app.school_update_time", value);

        editor.commit(); // Save the changes
    }

    String getSchoolName() {
        prefs = this.getSharedPreferences(getString(R.string.school_name), Context.MODE_PRIVATE);
        return prefs.getString("com.mattgd.app.school", "school_name");
    }

    String getSchoolWebsite() {
        prefs = this.getSharedPreferences(getString(R.string.school_website), Context.MODE_PRIVATE);
        return prefs.getString("com.mattgd.app.school", "school_website");
    }

    String getSchoolStartTime() {
        prefs = this.getSharedPreferences(getString(R.string.school_start_time), Context.MODE_PRIVATE);
        return prefs.getString("com.mattgd.app.school", "school_start_time");
    }

    String getSchoolMessage() {
        prefs = this.getSharedPreferences(getString(R.string.school_message), Context.MODE_PRIVATE);
        return prefs.getString("com.mattgd.app.school", "school_message");
    }

    String getSchoolUpdateTime() {
        prefs = this.getSharedPreferences(getString(R.string.school_update_time), Context.MODE_PRIVATE);
        return prefs.getString("com.mattgd.app.school", "school_update_time");
    }

    /*String getSchoolWebsite() throws JSONException {
        return jsonObject.getJSONObject("website").toString();
    }

    String getSchoolStartTime() throws JSONException {
        return jsonObject.getJSONObject("start_time").toString();
    }

    String getSchoolMessage() throws JSONException {
        return jsonObject.getJSONObject("message").toString();
    }

    String getSchoolUpdateTime() throws JSONException {
        return jsonObject.getJSONObject("update-time").toString();
    }*/

}
