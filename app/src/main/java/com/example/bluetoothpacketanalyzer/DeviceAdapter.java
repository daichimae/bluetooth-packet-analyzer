package com.example.bluetoothpacketanalyzer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An Adapter for the ListView in MainActivity.
 */

public class DeviceAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DeviceDetails> mDataSource;

    public DeviceAdapter(Context context, ArrayList<DeviceDetails> deviceList) {
        mContext = context;
        mDataSource = deviceList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the View for the row.
        View rowView = mInflater.inflate(R.layout.list_item_device, parent, false);

        // Get the references to the Views.
        TextView titleTextView = (TextView) rowView.findViewById(R.id.device_list_title);
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.device_list_subtitle);
        TextView detailTextView = (TextView) rowView.findViewById(R.id.device_list_detail);

        // Set texts to the Views.
        DeviceDetails device = (DeviceDetails) getItem(position);
        titleTextView.setText(device.name);
        subtitleTextView.setText(device.address);
        detailTextView.setText(device.signalStrength);

        return rowView;
    }

    /**
     * If the given device is not in the list add it otherwise refresh the signal
     * strength of the corresponding device in the list.
     *
     * @param device device to be added or refreshed
     */
    public void add(DeviceDetails device) {
        int index = mDataSource.indexOf(device);

        if (index >= 0) {
            mDataSource.get(index).signalStrength = device.signalStrength;
        } else {
            mDataSource.add(device);
        }

        this.notifyDataSetChanged();
    }
}
