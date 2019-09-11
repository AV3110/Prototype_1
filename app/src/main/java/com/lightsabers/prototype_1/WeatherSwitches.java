package com.lightsabers.prototype_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class WeatherSwitches extends AppCompatActivity    {
    private static final String TAG = "WeatherSwitches";

    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    LocationManager mLocationManager;
    LocationListener mLocationListener;
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    AlarmManager alarmManager;

    String weatherText;
    long MIN_TIME = 5000;
    float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 123;
    final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    final String APP_ID = "b66a11414f95f5c737e8bc71d139bec8";
    double temp;

    TextView weatherTextView;
    TextView descriptionTv;
    TextView temperatureV;
    ImageView weatherImage;
    Button goToMapBtn;
    CheckBox c1, c2;
    ProgressDialog progressDialog;
    FloatingActionButton addAlarm;
    String latitude;
    String longitude;

    String cboxText1 = "Thunderstorm";
    String cboxText2 = "Blizzard";
    String cboxText3 = "Downburst";
    String cboxText4 = "Tornado";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = ProgressDialog.show(WeatherSwitches.this, "", "Loading Weather");

        setContentView(R.layout.activity_weather_switches);
        weatherTextView = findViewById(R.id.weatherTextView);
        weatherImage = findViewById(R.id.weatherImage);
        descriptionTv = findViewById(R.id.descriptionTv);
        temperatureV = findViewById(R.id.temperature);
        goToMapBtn = findViewById(R.id.goToMapBtn);
        c1 = findViewById(R.id.textview8);
        c2 = findViewById(R.id.textview9);
        addAlarm = findViewById(R.id.addAlarm);

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        goToMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherSwitches.this, MapsActivity.class);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        getLocation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
        //getLocation();
    }

    private void getLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: callback received");

                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());

                DeviceLocationModel deviceLocationModel = new DeviceLocationModel(latitude, longitude);
                Log.d(TAG, "onLocationChanged: latitude = " + deviceLocationModel.getmLatitude());
                Log.d(TAG, "onLocationChanged: longitude" + deviceLocationModel.getmLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", deviceLocationModel.getmLatitude());
                params.put("lon", deviceLocationModel.getmLongitude());
                params.put("appid", APP_ID);

                networkCall(params);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Log.d(TAG, "onProviderDisabled: Callback received");
            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    private void networkCall(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "onSuccess: JSON: " + response.toString());
                CurrentWeatherModel weatherModel = CurrentWeatherModel.parseJson(response);
                weatherTextView.setText(weatherModel.getmWeather());
                descriptionTv.setText(weatherModel.getmDescription());
                temp = weatherModel.getMtemperature()-273.15;
                temperatureV.setText(String.valueOf(temp));
                progressDialog.dismiss();
                weatherCondition(weatherModel);

                c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (c1.isChecked()) {
//                            Toast.makeText(getApplicationContext(), "Thunderstorm selected.", Toast.LENGTH_SHORT).show();
                            Snackbar.make(buttonView, "Alarm won't ring if there is a Thunderstorm", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                            weatherText = "Thunderstorm";
                            if(weatherText.equals(cboxText1)) {
                                Log.d(TAG, "weatherCondition: true");
                            }
                        } else {
                            Snackbar.make(buttonView, "Alarm won't check for a Thunderstorm", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });

                c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (c2.isChecked()) {
                            Toast.makeText(getApplicationContext(), "Blizzard selected", Toast.LENGTH_SHORT).show();
                            if(weatherText.equals(cboxText2)) {
                                Log.d(TAG, "weatherCondition: true");
                            }
                        }
                    }
                });

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: received: " + errorResponse.toString());
                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void weatherCondition(CurrentWeatherModel model) {

        String weatherText = model.getmWeather();
        Log.d(TAG, "weatherCondition: " + weatherText);

        Log.d(TAG, "weatherCondition: " + cboxText1);


        if(weatherText.equals(cboxText1)) {
            Log.d(TAG, "weatherCondition: true");
        } else if(weatherText.equals(cboxText2)) {
            Log.d(TAG, "weatherCondition: true2");
        }else {
            Log.d(TAG, "weatherCondition: false");
        }


//        if (weatherText.equals(cboxText1)) {
//            Log.d(TAG, "weatherCondition: dnt ring the alarm " + cboxText1);
//            Toast.makeText(getApplicationContext(), "Weather : " + cboxText1, Toast.LENGTH_SHORT).show();
//        } else {
//            Log.d(TAG, "weatherCondition: RINGGGGG!!");
//        }
//        if (weatherText.equals(cboxText2)) {
//            Log.d(TAG, "weatherCondition: dnt ring the alarm ");
//            Toast.makeText(getApplicationContext(), "Weather : " + cboxText2, Toast.LENGTH_SHORT).show();
//        } else{
//            Log.d(TAG, "weatherCondition: RINGGGGG!!");
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted");
                getLocation();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission Denied");
            }
        }
    }
}
