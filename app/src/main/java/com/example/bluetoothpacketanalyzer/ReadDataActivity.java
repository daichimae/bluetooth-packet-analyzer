package com.example.bluetoothpacketanalyzer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReadDataActivity extends AppCompatActivity {
    //private ArrayAdapter<String> adapter; // Change the type to DeviceDetails
    private static final int REQUEST_ENABLE_BT = 0;
    private BluetoothAdapter mBluetoothAdapter;
    private DataAdapter mAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback mScanCallback;
    private String mMacAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        ListView listView = findViewById(R.id.list_view);
        final ArrayList<String> advertisements = new ArrayList<>();
        /*adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, advertisements);*/
        mAdapter = new DataAdapter(this, advertisements);
        listView.setAdapter(mAdapter);

        /*adapter.add("testing\nmore testing\nAnd more");
        adapter.add(this.getIntent().getExtras().getString("name")
        + " (" + this.getIntent().getExtras().getString("address") + ")");*/

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mMacAddress = this.getIntent().getExtras().getString("address");
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
            mScanCallback = new DataScanCallback(mAdapter, mMacAddress);
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
