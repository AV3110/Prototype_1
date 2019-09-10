package com.lightsabers.prototype_1;

import org.json.JSONException;
import org.json.JSONObject;

class CurrentWeatherModel {
    private String mWeather;
    private String mDescription;
    private double mtemperature;



    static CurrentWeatherModel parseJson(JSONObject jsonObject) {
        CurrentWeatherModel weatherModel = new CurrentWeatherModel();
        try {
            weatherModel.mWeather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherModel.mDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            weatherModel.mtemperature = jsonObject.getJSONObject("main").getDouble("temp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherModel;
    }

    String getmWeather() {
        return mWeather;
    }

    String getmDescription() {
        return mDescription;
    }

    double getMtemperature(){ return mtemperature;}
}
