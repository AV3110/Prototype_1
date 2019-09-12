package com.lightsabers.prototype_1;

import org.json.JSONException;
import org.json.JSONObject;

 class LatLngModel {
    private double mLatitude;
    private double mLongitude;

    static LatLngModel parseJson(JSONObject jsonObject) {

        LatLngModel model = new LatLngModel();
        try {
            model.mLatitude = jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat");

            model.mLongitude = jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }

      double getmLatitude() {
         return mLatitude;
     }

      double getmLongitude() {
         return mLongitude;
     }
 }
