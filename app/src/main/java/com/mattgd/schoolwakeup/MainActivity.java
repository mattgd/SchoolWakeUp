package com.mattgd.schoolwakeup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    // Components
    RelativeLayout mainLayout;
    TextView alarmStatusTextView;
    private Button toggleAlarmButton;
    private ImageButton settingsButton;
    private Toast toast;
    boolean enabledWeatherBackground = false; // For debugging purposes, this is disabled.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        if (enabledWeatherBackground) {
            new Thread(new Runnable() {
                public void run() {
                    buildGoogleApiClient();
                }
            }).start();
        }

        alarmStatusTextView = (TextView) findViewById(R.id.alarmStatusTextView); // Set alarmStatusTextView

        toggleAlarmButton = (Button) findViewById(R.id.toggleAlarmButton); // Connect to "setAlarmButton"
        toggleAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);

                    if (toast != null) {
                        toast.cancel();
                    }

                    toast = Toast.makeText(getApplicationContext(), "Alarm for activity is set for 5 seconds from now.", Toast.LENGTH_LONG);
                    toast.show();
                } catch (NumberFormatException e) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG);
                    Log.i("Main", "Number format exception!");
                }
            }
        });

        settingsButton = (ImageButton) findViewById(R.id.settingsButton); // Connect to "settingsButton"
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG);
                    Log.i("Main", "Number format exception!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adds items to the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar component clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    // Google API Client
    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            Weather.latitude = mLastLocation.getLatitude();
            Weather.longitude = mLastLocation.getLongitude();
        }

        Resources res = getResources(); //resource handle
        Weather weather = new Weather();
        Drawable drawable = null;
        try {
            drawable = res.getDrawable(weather.execute().get()); // Weather image
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (drawable != null) {
            mainLayout.setBackground(drawable); // Set the background to the weather image
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // Called when connection has been interrupted.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Handles errors while attempting to connect with Google.
    }

}
