package com.example.android.bluetoothlegatt.pack;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


public class RscpService extends Service {

    private static final String TAG = RscpService.class.getSimpleName();

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothRscp mBluetoothRscp;

    public static final String ACTION_RSC_CONNECTED = "com.example.android.bluetoothlegatt.ACTION_RSC_CONNECTED";
    public static final String ACTION_RSC_DISCONNECTED = "com.example.android.bluetoothlegatt.ACTION_RSC_DISCONNECTED";
    public static final String ACTION_RSC_SERVICES_DISCOVERED = "com.example.android.bluetoothlegatt.ACTION_RSC_SERVICES_DISCOVERED";
    public static final String ACTION_RSC_MEASUREMENT_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_MEASUREMENT_DATA_AVAILABLE";
    public static final String ACTION_RSC_FEATURE_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_FEATURE_DATA_AVAILABLE";
    public static final String ACTION_RSC_SENSOR_LOCATION_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_SENSOR_LOCATION_DATA_AVAILABLE";
    public static final String ACTION_RSC_CUMULATIVE_VALUE_SET = "com.example.android.bluetoothlegatt.ACTION_RSC_CUMULATIVE_VALUE_SET";
    public static final String ACTION_RSC_START_SENSOR_CALIBRATION = "com.example.android.bluetoothlegatt.ACTION_RSC_START_SENSOR_CALIBRATION";
    public static final String ACTION_RSC_UPDATE_SENSOR_LOCATION = "com.example.android.bluetoothlegatt.ACTION_RSC_UPDATE_SENSOR_LOCATION";
    public static final String ACTION_RSC_REQUEST_SUPPORTED_SENSOR_LOCATION = "com.example.android.bluetoothlegatt.ACTION_RSC_REQUEST_SUPPORTED_SENSOR_LOCATION";


    public static final String RSC_SPEED_DATA = "com.example.android.bluetoothlegatt.RSC_SPEED_DATA";
    public static final String RSC_CADENCE_DATA = "com.example.android.bluetoothlegatt.RSC_CADENCE_DATA";
    public static final String RSC_STRIDE_LENGTH_DATA = "com.example.android.bluetoothlegatt.RSC_STRIDE_LENGTH_DATA";
    public static final String RSC_TOTAL_DISTANCE_DATA = "com.example.android.bluetoothlegatt.RSC_TOTAL_DISTANCE_DATA";
    public static final String RSC_STRIDE_LENGTH_PRESENT = "com.example.android.bluetoothlegatt.RSC_STRIDE_LENGTH_PRESENT";
    public static final String RSC_TOTAL_DISTANCE_PRESENT = "com.example.android.bluetoothlegatt.RSC_TOTAL_DISTANCE_PRESENT";
    public static final String RSC_WALKING_OR_RUNNING_STATE = "com.example.android.bluetoothlegatt.RSC_WALKING_OR_RUNNING_STATE";

    public static final String RSC_FEATURE_STRIDE_LENGTH_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_STRIDE_LENGTH_SUPPORTED";
    public static final String RSC_FEATURE_TOTAL_DISTANCE_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_TOTAL_DISTANCE_SUPPORTED";
    public static final String RSC_FEATURE_WALKING_OR_RUNNING_STATUS = "com.example.android.bluetoothlegatt.RSC_FEATURE_WALKING_OR_RUNNING_STATUS";
    public static final String RSC_FEATURE_CALIBRATION_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_CALIBRATION_SUPPORTED";
    public static final String RSC_FEATURE_MULTIPLE_SENSOR_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_MULTIPLE_SENSOR_SUPPORTED";

    public static final String RSC_SENSOR_LOCATION_DATA = "com.example.android.bluetoothlegatt.RSC_SENSOR_LOCATION_DATA";

//    public static final String RSC_SET_CUMULATIVE_VALUE = "com.example.android.bluetoothlegatt.RSC_SET_CUMULATIVE_VALUE";
//    public static final String RSC_START_SENSOR_CALIBRATION = "com.example.android.bluetoothlegatt.RSC_START_SENSOR_CALIBRATION";
//    public static final String RSC_UPDATE_SENSOR_LOCATION = "com.example.android.bluetoothlegatt.RSC_UPDATE_SENSOR_LOCATION";
//    public static final String RSC_REQUEST_SUPPORTED_SENSOR_LOCATION = "com.example.android.bluetoothlegatt.RSC_REQUEST_SUPPORTED_SENSOR_LOCATION";




    private final BluetoothRscpCallback mRscpCallback = new BluetoothRscpCallback() {
        @Override
        public void onConnectionStateChange(int state, int newState) {


            if (newState == BluetoothProfile.STATE_CONNECTED) {
                sendBroadcast(new Intent(ACTION_RSC_CONNECTED));
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                sendBroadcast(new Intent(ACTION_RSC_DISCONNECTED));
            }
        }

        @Override
        public void onRSCMeasurementCharacChange(int speed, int cadence, int strideLength, int totalDistance, boolean isInstantaneousStrideLengthPresent,
                                                 boolean isTotalDistancePresent, String walkingOrRunningState) {
            Intent intent = new Intent(ACTION_RSC_MEASUREMENT_DATA_AVAILABLE);
            intent.putExtra(RSC_SPEED_DATA, speed);
            intent.putExtra(RSC_CADENCE_DATA, cadence);
            intent.putExtra(RSC_STRIDE_LENGTH_DATA, strideLength);
            intent.putExtra(RSC_TOTAL_DISTANCE_DATA, totalDistance);
            intent.putExtra(RSC_STRIDE_LENGTH_PRESENT, isInstantaneousStrideLengthPresent);
            intent.putExtra(RSC_TOTAL_DISTANCE_PRESENT, isTotalDistancePresent);
            intent.putExtra(RSC_WALKING_OR_RUNNING_STATE, walkingOrRunningState);

            sendBroadcast(intent);

        }

        @Override
        public void onRSCFeatureChange(boolean strideLengthMeasurementSupported,
                                       boolean totalDistanceMeasurementSupported,
                                       boolean walkingOrRunningStatusSupported,
                                       boolean calibrationProcedureSupported,
                                       boolean multipleSensorLocationSupported) {
            Intent intent = new Intent(ACTION_RSC_FEATURE_DATA_AVAILABLE);
            intent.putExtra(RSC_FEATURE_STRIDE_LENGTH_SUPPORTED, strideLengthMeasurementSupported);
            intent.putExtra(RSC_FEATURE_TOTAL_DISTANCE_SUPPORTED, totalDistanceMeasurementSupported);
            intent.putExtra(RSC_FEATURE_WALKING_OR_RUNNING_STATUS, walkingOrRunningStatusSupported);
            intent.putExtra(RSC_FEATURE_CALIBRATION_SUPPORTED, calibrationProcedureSupported);
            intent.putExtra(RSC_FEATURE_MULTIPLE_SENSOR_SUPPORTED, multipleSensorLocationSupported);

            sendBroadcast(intent);
        }

        @Override
        public void onSensorLocationChange() {
            Intent intent = new Intent(ACTION_RSC_SENSOR_LOCATION_DATA_AVAILABLE);
            intent.putExtra(RSC_SENSOR_LOCATION_DATA, mBluetoothRscp.getSensorLocation());
            sendBroadcast(intent);
        }

        @Override
        public void onCumulativeValueSet() {
            Intent intent = new Intent(ACTION_RSC_CUMULATIVE_VALUE_SET);
            sendBroadcast(intent);
        }

        @Override
        public void onUpdateSensorLocation(int location) {
            Intent intent = new Intent(ACTION_RSC_UPDATE_SENSOR_LOCATION);
            sendBroadcast(intent);
        }
    };

    public class LocalBinder extends Binder {
        public RscpService getService() {
            return RscpService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize() {
        BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            return false;
        }

        mBluetoothRscp = new BluetoothRscp(getBaseContext(), mRscpCallback);
        return true;
    }

    public boolean connect(final String address) {
        if (mBluetoothAdapter == null && address ==null)
            return false;

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        mBluetoothRscp.connect(device, false);
        return true;
    }

    public void disconnect() {
        if (mBluetoothAdapter == null && mBluetoothRscp ==null)
            return;

        mBluetoothRscp.disconnect();
    }

    public void close() {
        if (mBluetoothRscp == null)
            return;
        mBluetoothRscp.close();
        mBluetoothRscp = null;
    }

    public void setCharacteristicNotification(boolean enable) {
        if (mBluetoothAdapter != null && mBluetoothRscp != null) {
            mBluetoothRscp.setCharacteristicNotification(enable);
        }
    }

    public boolean setCumulativeValue(int cumulativeValue) {
        return mBluetoothRscp.setCumulativeValue(cumulativeValue);
    }

    public void startCalibration() {
        mBluetoothRscp.startCalibration();
    }

    public boolean updateSensorLocation (int location) {
        return mBluetoothRscp.updateSensorLocation(location);
    }

    public String getSensorLocation() {
        return mBluetoothRscp.getSensorLocation();
    }

    public void getSupportedFeature() {
        mBluetoothRscp.getSupportedFeature();
    }

    public void getSupportedSensorLocation() {
        mBluetoothRscp.getSupportedSensorLocation();
    }


}
