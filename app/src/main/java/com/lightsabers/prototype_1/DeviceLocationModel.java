package com.lightsabers.prototype_1;

class DeviceLocationModel {
    private String mLatitude;
    private String mLongitude;



    DeviceLocationModel(String mLatitude, String mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    String getmLatitude() {
        return mLatitude;
    }

    String getmLongitude() {
        return mLongitude;
    }
}
