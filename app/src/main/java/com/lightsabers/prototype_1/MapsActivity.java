package com.lightsabers.prototype_1;
import androidx.fragment.app.FragmentActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";
    final String API_KEY = String.valueOf(R.string.google_maps_key);
    final String BASE_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
    final String PLACE_ID_URL = "https://maps.googleapis.com/maps/api/place/details/json";
    Double lat, lon, lat1, lon1;
    SearchView searchView;
    LatLngModel lngModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: " + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        String latitude = getIntent().getStringExtra("Latitude");
        String longitude = getIntent().getStringExtra("Longitude");
        Log.d(TAG, "onCreate: Map " + latitude);
        Log.d(TAG, "onCreate: Map " + longitude);
        lat = Double.parseDouble(latitude);
        lon = Double.parseDouble(longitude);


        RequestParams params = new RequestParams();
        params.put("key", "AIzaSyCrR9jchBYrmcbjzjkm17rhO1CzvOVvJcA");
        params.put("input", "NIT Raipur");
        params.put("inputtype", "textquery");
        networkCall(params);

    }

    private void networkCall(RequestParams params) {
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "onSuccess: JSON: " + response.toString());
                PlaceIdModel model = PlaceIdModel.parseJson(response);
                Log.d(TAG, "onSuccess: Model " + model.getPlace_id());

                RequestParams params = new RequestParams();
                params.put("placeid", model.getPlace_id());
                params.put("key", "AIzaSyCrR9jchBYrmcbjzjkm17rhO1CzvOVvJcA");
                AsyncHttpClient newClient = new AsyncHttpClient();
                newClient.get(PLACE_ID_URL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d(TAG, "onSuccess: JSON: " + response.toString());
                        lngModel = LatLngModel.parseJson(response);
                        Log.d(TAG, "onSuccess: Model " + lngModel.getmLatitude());
                        Log.d(TAG, "onSuccess: Model " + lngModel.getmLongitude());
                        lat1 = lngModel.getmLatitude();
                        lon1 = lngModel.getmLongitude();
                        Log.d(TAG, "onSuccess: lon1 " + lon1);
                    }
                });

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "onMapReady: " + lon1);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng currentLoc = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currentLoc).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));


//        LatLng desLoc = new LatLng(lat1, lon1);
//        mMap.addMarker(new MarkerOptions().position(desLoc).title("Marker"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(desLoc));

    }
}
