package com.example.bluetoothpacketanalyzer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An Adapter for the ListView in ReadDataActivity.
 */

public class DataAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mDataSource;

    public DataAdapter(Context context, ArrayList<String> dataList) {
        mContext = context;
        mDataSource = dataList;
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
        View rowView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        // Get the references to the Views.
        TextView dataTextView = (TextView) rowView.findViewById(android.R.id.text1);

        // Set texts to the Views.
        String data = (String) getItem(position);
        dataTextView.setText(data);

        return rowView;
    }

    /**
     *
     * @param data
     */
    public void add(String data) {
        mDataSource.add(0, data);
        this.notifyDataSetChanged();
    }
}
