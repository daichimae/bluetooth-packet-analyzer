package com.example.bluetoothpacketanalyzer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * An ArrayAdapter that filters out duplicate items.
 */
public class FilteredArrayAdapter<T> extends ArrayAdapter<T> {
    private List<T> mObjects;

    public FilteredArrayAdapter (Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.mObjects = objects;
    }

    @Override
    public void add(@Nullable T object) {
        if (object != null && !mObjects.contains(object))
            super.add(object);
    }
}
