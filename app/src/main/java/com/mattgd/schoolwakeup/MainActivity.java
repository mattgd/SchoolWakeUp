package com.mattgd.schoolwakeup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends ActionBarActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private PendingIntent pendingIntent;
    private boolean alarmEnabled = false;

    // Components
    static RelativeLayout mainLayout;
    static TextView alarmStatusTextView;
    static Button toggleAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        Resources res = getResources(); //resource handle
        Weather weather = new Weather();
        Drawable drawable = null;
        try {
            drawable = res.getDrawable(weather.getWeather()); // Weather image
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (drawable != null) {
            mainLayout.setBackground(drawable); // Set the background to the weather image
        }

        alarmStatusTextView = (TextView) findViewById(R.id.alarmStatusTextView); // Set alarmStatusTextView
        toggleAlarmButton = (Button) findViewById(R.id.toggleAlarmButton); // Connect to "setAlarmButton"

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        toggleAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmEnabled) {
                    setAlarm();
                } else {
                    cancelAlarm();
                }
            }
        });

        buildGoogleApiClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the next section.
    }

    public void setAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        //Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

}
