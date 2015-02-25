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
    public void getWeather() throws IOException, JSONException {
        JsonReader jr = new JsonReader();
        JSONObject json = jr.getJSONObject("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude);
        JSONArray results = (JSONArray) json.get("weather");
        JSONObject resultObject = (JSONObject) results.get(0);

        String weather = resultObject.get("main").toString();
        weather = weather.toLowerCase();

        if (weather.contains("thunderstorm")) {
            // Return thunderstorm background
        } else if (weather.contains("rain")) {
            // Return rain background
        } else if (weather.contains("snow")) {
            // Return snow background
        } else if (weather.contains("cloud")) {
            // Return clouds background
        } else {
            // Return clear weather background
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
