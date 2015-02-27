package com.mattgd.schoolwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mdzwonczyk on 2/27/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        MainActivity.alarmStatusTextView.setText("Alarm is running.");
        //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }

}
