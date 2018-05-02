package com.example.android.quakereport;

/**
 * Created by Arkady on 01-May-18.
 */

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private long mDate;

    public Earthquake(double magnitude,String location, long date){
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
    }

    public double getmMagnitude(){ return mMagnitude; }
    public String getmLoation(){ return mLocation; }
    public long getmDate(){ return mDate; }

}
