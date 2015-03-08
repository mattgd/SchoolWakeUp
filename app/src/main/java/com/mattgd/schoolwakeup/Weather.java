package com.mattgd.schoolwakeup;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mattgd on 2/23/2015.
 */
public class Weather extends AsyncTask<Editable, Void, Integer> {

    public static double latitude = 0, longitude = 0;

    protected Integer doInBackground(Editable... params) {
        //Editable editable = params[0];

        JSONReader jr = new JSONReader();

        //System.out.println("LAT: " + params[0] + ", LONG: " + params[1]);

        String weather = "";
        try {
            JSONObject json = jr.getJSONObject("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude);
            //JSONObject json = jr.getJSONObject("http://api.openweathermap.org/data/2.5/weather?lat=0&lon=0");
            JSONArray results = (JSONArray) json.get("weather");
            JSONObject resultObject = (JSONObject) results.get(0);

            weather = resultObject.get("main").toString();
            weather = weather.toLowerCase();
        } catch (Exception e) {
            Log.i("Weather", "JSON/IO exception!");
        }

        if (weather.contains("thunderstorm")) {
            // Return thunderstorm background
            return R.drawable.background_thunderstorm;
        } else if (weather.contains("rain")) {
            // Return rain background
            return R.drawable.background_rain;
        } else if (weather.contains("snow")) {
            // Return snow background
            return R.drawable.background_snow;
        } else if (weather.contains("cloud")) {
            // Return clouds background
            return R.drawable.background_cloud;
        } else {
            // Return clear weather background
            return R.drawable.background_clear;
        }
    }

    protected void onPostExecute(Integer result) {
        // here you have the result
    }

}
