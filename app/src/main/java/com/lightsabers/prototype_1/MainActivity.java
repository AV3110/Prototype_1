package com.lightsabers.prototype_1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tapadoo.alerter.Alerter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TimePicker timePicker;
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timePicker = findViewById(R.id.timePicker);


        Button addAlarmBtn = findViewById(R.id.deleteAlarmBtn);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
                Log.d(TAG, "onClick: addAlarmBtn");
            }
        });

        Button weatherButton = findViewById(R.id.button);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeatherSwitches.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog, onTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }

                //addNewAlarm();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                Toast.makeText(getApplicationContext(), "Set " + hourOfDay + minute, Toast.LENGTH_SHORT).show();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                setAlarm(calendar);

            }
        };
    }

    public void setAlarm(Calendar c) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), 1, intent, 0);
        if(c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), 1, intent, 0);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(getApplicationContext(), "AlarmCanceled", Toast.LENGTH_SHORT).show();
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
}


//    private void addNewAlarm() {
//        int hour = timePicker.getHour();
//        int minute = timePicker.getMinute();
//
//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
//        intent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
//        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Added Alarm for " + hour + " and " + minute);
//        startActivity(intent);
//    }
