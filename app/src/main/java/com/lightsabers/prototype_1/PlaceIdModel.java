package com.lightsabers.prototype_1;

import org.json.JSONException;
import org.json.JSONObject;

class PlaceIdModel {
    private String place_id;

    static PlaceIdModel parseJson(JSONObject jsonObject) {
        PlaceIdModel model = new PlaceIdModel();
        try{
            model.place_id = jsonObject.getJSONArray("candidates").getJSONObject(0).getString("place_id");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return model;
    }

    public String getPlace_id() {
        return place_id;
    }
}
