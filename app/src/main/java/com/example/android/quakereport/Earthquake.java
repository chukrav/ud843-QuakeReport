package com.example.android.quakereport;

/**
 * Created by Arkady on 01-May-18.
 */

public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private String mDate;

    public Earthquake(String magnitude,String location, String date){
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
    }

    public String getmMagnitude(){ return mMagnitude; }
    public String getmLoation(){ return mLocation; }
    public String getmDate(){ return mDate; }

}
