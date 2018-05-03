package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Arkady on 01-May-18.
 */

public class EarthquakeItemAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeItemAdapter.class.getSimpleName();

    public EarthquakeItemAdapter(Activity context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Earthquake currentEQ = getItem(position);
        TextView magnitude_tv = (TextView) listItemView.findViewById(R.id.txt_magnitude);
        double mag = currentEQ.getmMagnitude();
        magnitude_tv.setText(formatMagnitude(mag));

        String offset = "";
        String location = currentEQ.getmLoation();
        int idx = location.indexOf("of");
        if (idx > 0) {
            offset = location.substring(0, idx);
            location = location.substring(idx + 2).trim();
        }

        TextView offset_tv = (TextView) listItemView.findViewById(R.id.txt_offset);
        offset_tv.setText(offset);

        TextView location_tv = (TextView) listItemView.findViewById(R.id.txt_location);
        location_tv.setText(location);

        Date date = new Date(currentEQ.getmDate());
        TextView date_tv = (TextView) listItemView.findViewById(R.id.txt_date);
        date_tv.setText(formatDate(date));

        TextView time_tv = (TextView) listItemView.findViewById(R.id.txt_time);
        time_tv.setText(formatTime(date));

        return listItemView;

    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(mag);
    }
}
