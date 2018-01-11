package com.example.bluetoothpacketanalyzer;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;

/**
 * A scan callback for MainActivity.
 */

public class DeviceListScanCallback extends ScanCallback {
    private DeviceAdapter mAdapter;

    public DeviceListScanCallback(DeviceAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);

        DeviceDetails deviceDetails = new DeviceDetails();

        // Get the device name, MAC address and signal strength.
        if (result.getDevice().getName() == null) {
            deviceDetails.name = "";
        } else {
            deviceDetails.name = result.getDevice().getName();
        }
        deviceDetails.address = result.getDevice().getAddress();
        deviceDetails.signalStrength = result.getRssi() + " dBm";

        mAdapter.add(deviceDetails);
    }
}
