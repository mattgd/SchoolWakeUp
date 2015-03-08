package com.mattgd.schoolwakeup;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.zip.DeflaterInputStream;

/**
 * Created by mattgd on 2/23/2015.
 */
public class Weather {

    public static double latitude = 0, longitude = 0;
    public static int background;

    // Returns a proper drawable for the current weather conditions
    public void getWeather() throws IOException, JSONException {
        new Thread(new Runnable() {
            public void run() {
                JSONReader jr = new JSONReader();

                System.out.println("LAT: " + latitude + ", LONG: " + longitude);

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
                } finally {
                    background = R.drawable.background_clear;
                }

                if (weather.contains("thunderstorm")) {
                    // Return thunderstorm background
                    background = R.drawable.background_thunderstorm;
                } else if (weather.contains("rain")) {
                    // Return rain background
                    background = R.drawable.background_rain;
                } else if (weather.contains("snow")) {
                    // Return snow background
                    background = R.drawable.background_snow;
                } else if (weather.contains("cloud")) {
                    // Return clouds background
                    background = R.drawable.background_cloud;
                } else {
                    // Return clear weather background
                    background = R.drawable.background_clear;
                }
            }
        }).start();

    }

}
