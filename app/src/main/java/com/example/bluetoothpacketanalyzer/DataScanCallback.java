package com.example.bluetoothpacketanalyzer;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;

/**
 * A scan callback for ReadDataActivity.
 */

public class DataScanCallback extends ScanCallback {
    private DataAdapter mAdapter;
    private String mAddress;

    public DataScanCallback(DataAdapter adapter, String address) {
        mAdapter = adapter;
        mAddress = address;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);

        ScanRecord scanRecord = result.getScanRecord();

        if (scanRecord != null && result.getDevice().getAddress().equals(mAddress)) {
            String rowText = "";

            if (scanRecord.getServiceUuids() != null) {
                final StringBuilder rowTextBuilder = new StringBuilder();
                for (ParcelUuid uuid : scanRecord.getServiceUuids()) {
                    String serviceText = "Service: " + uuid + "\n";
                    rowTextBuilder.append(serviceText);

                    byte[] byteData = scanRecord.getServiceData(uuid);
                    if (byteData != null) {
                        final StringBuilder hexBuilder = new StringBuilder();
                        for (byte b : byteData) {
                            hexBuilder.append(String.format("%02x", b));
                        }
                        String dataText = "Data: " + hexBuilder.toString() + "\n";
                        rowTextBuilder.append(dataText);
                    }
                }
                mAdapter.add(rowTextBuilder.toString());
            } else {
                final StringBuilder hexBuilder = new StringBuilder();
                for (byte b : scanRecord.getBytes()) {
                    hexBuilder.append(String.format("%02X", b));
                }
                mAdapter.add(hexBuilder.toString());
            }
        }
    }
}
