package com.lightsabers.prototype_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

public class MyAlarm extends BroadcastReceiver {
    MediaPlayer mediaPlayer;

      public void onReceive(Context context, Intent intent)
    {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
    }
}
