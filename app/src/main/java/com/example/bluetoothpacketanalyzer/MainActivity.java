package com.example.bluetoothpacketanalyzer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class represents the MainActivity of the app.
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 0;
    private BluetoothAdapter mBluetoothAdapter;
    private DeviceAdapter mAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback mScanCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the ListView with the device list using an DeviceAdapter.
        ListView listView = findViewById(R.id.list_view);
        final ArrayList<DeviceDetails> deviceList = new ArrayList<>();
        /*mAdapter = new FilteredArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, deviceList);*/
        mAdapter = new DeviceAdapter(this, deviceList);
        listView.setAdapter(mAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set a click listener on the list.
        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent readDataIntent = new Intent(context, ReadDataActivity.class);
                DeviceDetails deviceDetails = deviceList.get(position);

                readDataIntent.putExtra("name", deviceDetails.name);
                readDataIntent.putExtra("address", deviceDetails.address);

                startActivity(readDataIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if Bluetooth is turned on.
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            ArrayList<ScanFilter> scanFilters = new ArrayList<>();
            ScanSettings scanSettings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
            mScanCallback = new DeviceListScanCallback(mAdapter);
            mBluetoothLeScanner.startScan(scanFilters, scanSettings, mScanCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
        if (mBluetoothLeScanner != null) {
            mBluetoothLeScanner.stopScan(mScanCallback);
        }
    }
}
