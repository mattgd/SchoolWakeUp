package com.mattgd.schoolwakeup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mdzwonczyk on 3/3/2015.
 */
public class SchoolInfo {

    JSONReader jsonReader;
    JSONObject jsonObject;

    // If the constructor throw an exception, will be eligible for garbage collection.
    SchoolInfo(String name, String id) throws IOException, JSONException {
        jsonReader = new JSONReader();
        jsonObject = jsonReader.getJSONObject("URL"); //TODO SETUP URL
    }

    String getSchoolName() throws JSONException {
        return jsonObject.getJSONObject("name").toString();
    }

    String getSchoolWebsite() throws JSONException {
        return jsonObject.getJSONObject("website").toString();
    }

    String getSchoolStartTime() throws JSONException {
        return jsonObject.getJSONObject("start_time").toString();
    }

    String getSchoolMessage() throws JSONException {
        return jsonObject.getJSONObject("message").toString();
    }

}
