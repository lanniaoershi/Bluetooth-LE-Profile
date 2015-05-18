package com.example.android.bluetoothlegatt.pack;

/**
 *  This abstract class is used to implement {@link BluetoothRscp} callbacks.
 */
public abstract class BluetoothRscpCallback {

    public void onConnectionStateChange(int state, int newState) {}
    public void onRSCMeasurementCharacChange(int speed, int cadence, int strideLength, int totalDistance,
                              boolean isInstantaneousStrideLengthPresent,
                              boolean isTotalDistancePresent,
                              String walkingOrRunningState) {}
    public void onRSCFeatureChange(boolean strideLengthMeasurementSupported,
                                   boolean totalDistanceMeasurementSupported,
                                   boolean walkingOrRunningStatusSupported,
                                   boolean calibrationProcedureSupported,
                                   boolean multipleSensorLocationSupported) {}
    public void onSensorLocationChange(String location) {}
    public void onCumulativeValueSet() {}
    public void onUpdateSensorLocation(int location) {}
    public void onRequestOpCodeSet(int code) {}
    public void onResponseValueSet(int value) {}
    public void onResponseParameter(int param) {}




}
