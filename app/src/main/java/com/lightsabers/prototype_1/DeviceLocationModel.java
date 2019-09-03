package com.lightsabers.prototype_1;

public class DeviceLocationModel {
    private String mLatitude;
    private String mLongitude;

    public DeviceLocationModel(String mLatitude, String mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getmLatitude() {
        return mLatitude;
    }


     String getmLongitude() {
        return mLongitude;
    }
}
