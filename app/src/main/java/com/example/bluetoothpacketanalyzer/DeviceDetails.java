package com.example.bluetoothpacketanalyzer;

/**
 * This class holds bluetooth device information.
 */

public class DeviceDetails {
    public String name;
    public String address;
    public String signalStrength;

    @Override
    public String toString() {
        return name + "(" + address + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DeviceDetails deviceDetails = (DeviceDetails) obj;
        return (name.equals(deviceDetails.name) && address.equals(deviceDetails.address));
    }
}
