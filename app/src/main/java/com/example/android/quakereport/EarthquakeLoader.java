package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    private String mUrl;
    List<Earthquake> earthquakes;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        String jsonResponce = QueryUtils.fetchEarthquakeData(mUrl);
        earthquakes = QueryUtils.extractEarthquakes(jsonResponce);
        Log.v(LOG_TAG,"***In loadInBackground");
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.v(LOG_TAG,"***In onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }
}
