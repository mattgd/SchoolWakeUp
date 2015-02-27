package com.mattgd.schoolwakeup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mattgd on 2/23/2015.
 */
public class Weather {

    public static double latitude, longitude;

    public void getUserLocation() {

    }

    // Returns the current weather conditions
    public int getWeather() throws IOException, JSONException {
        JSONReader jr = new JSONReader();
        JSONObject json = jr.getJSONObject("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude);
        JSONArray results = (JSONArray) json.get("weather");
        JSONObject resultObject = (JSONObject) results.get(0);

        String weather = resultObject.get("main").toString();
        weather = weather.toLowerCase();

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

        /*
        Types of weather - String.contains("")

        Thunderstorm
        Rain
        Snow
        Clouds

        All types at: http://openweathermap.org/weather-conditions
         */
    }

}
