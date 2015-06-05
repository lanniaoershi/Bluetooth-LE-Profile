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
    public void onRSCFeatureChange(boolean isStrideLengthMeasurementSupported,
                                   boolean isTotalDistanceMeasurementSupported,
                                   boolean isWalkingOrRunningStatusSupported,
                                   boolean isCalibrationProcedureSupported,
                                   boolean isMultipleSensorLocationSupported) {}
    public void onSensorLocationGet() {}
    public void onSensorLocationChange() {}
    public void onCumulativeValueSet() {}
    public void onUpdateSensorLocation() {}
    public void onStartCalibration() {}
    public void onRequestSupportedSensorLocation() {}
    public void onSupportedSensorLocationGet() {}
    public void onRequestOpCodeSet(int code) {}
    public void onResponseValueSet(int value) {}
    public void onResponseParameter(int param) {}




}
