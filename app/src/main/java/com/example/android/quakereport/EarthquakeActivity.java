/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.R.*;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private List<Earthquake> mEarthquakes;
    private EarthquakeItemAdapter earthquakeItemAdapter;
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private String SAMPLE_JSON_RESPONSE;
    private ListView earthquakeListView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        SAMPLE_JSON_RESPONSE = QueryUtils.fetchEarthquakeData(USGS_URL);
//        earthquakes = QueryUtils.extractEarthquakes(SAMPLE_JSON_RESPONSE);
//
        earthquakeItemAdapter = new EarthquakeItemAdapter(this, new ArrayList<Earthquake>());

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(id.list);
        mProgress = (ProgressBar) findViewById(id.indeterminateBar);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        TextView emptyView = (TextView) findViewById(id.empty);

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(earthquakeItemAdapter);
//        getLoaderManager().initLoader(0,null,this).forceLoad();
            getLoaderManager().initLoader(0, null, this);  // WO forceLoad() working as well :)
            earthquakeListView.setEmptyView(emptyView);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Earthquake earthquake = mEarthquakes.get(position);
                    Uri webpage = Uri.parse(earthquake.getmUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        } else {
            emptyView.setText("No internet connection");
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "***In onCreateLoader");
//        mProgress.setVisibility(View.VISIBLE);
        return new EarthquakeLoader(this, USGS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        earthquakeItemAdapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            earthquakeItemAdapter.addAll(earthquakes);
            mEarthquakes = earthquakes;
        }
        Log.v(LOG_TAG, "***In onLoadFinished");
        TextView emptyScreen = (TextView) findViewById(id.empty);
        emptyScreen.setText("Nothing was loaded! :(");
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        earthquakeItemAdapter.clear();
        Log.v(LOG_TAG, "***In onLoaderReset (Clear adapter)");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
