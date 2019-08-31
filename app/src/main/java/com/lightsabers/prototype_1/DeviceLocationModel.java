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

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    @Override
    public String toString() {
        return "DeviceLocationModel{" +
                "mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                '}';
    }
}
