package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arkady on 01-May-18.
 */

public class EarthquakeItemAdapter extends ArrayAdapter<Earthquake>{

    private static final String LOG_TAG = EarthquakeItemAdapter.class.getSimpleName();

    public EarthquakeItemAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake currentEQ = getItem(position);
        TextView magnitude_tv = (TextView) listItemView.findViewById(R.id.txt_magnitude);
        magnitude_tv.setText(currentEQ.getmMagnitude());

        TextView location_tv = (TextView) listItemView.findViewById(R.id.txt_location);
        location_tv.setText(currentEQ.getmLoation());

        TextView date_tv = (TextView) listItemView.findViewById(R.id.txt_date);
        date_tv.setText(currentEQ.getmDate());

        return listItemView;

    }
}