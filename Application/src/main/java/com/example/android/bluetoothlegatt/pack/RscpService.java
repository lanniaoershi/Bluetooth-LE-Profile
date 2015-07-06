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
    public static final String ACTION_RSC_CONNECTING = "com.example.android.bluetoothlegatt.ACTION_RSC_CONNECTING";
    public static final String ACTION_RSC_DISCONNECTING = "com.example.android.bluetoothlegatt.ACTION_RSC_DISCONNECTING";
    public static final String ACTION_RSC_SERVICES_DISCOVERED = "com.example.android.bluetoothlegatt.ACTION_RSC_SERVICES_DISCOVERED";
    public static final String ACTION_RSC_MEASUREMENT_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_MEASUREMENT_DATA_AVAILABLE";
    public static final String ACTION_RSC_FEATURE_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_FEATURE_DATA_AVAILABLE";
    public static final String ACTION_RSC_CURRENT_SENSOR_LOCATION_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_CURRENT_SENSOR_LOCATION_DATA_AVAILABLE";
    public static final String ACTION_RSC_CUMULATIVE_VALUE_SET = "com.example.android.bluetoothlegatt.ACTION_RSC_CUMULATIVE_VALUE_SET";
    public static final String ACTION_RSC_START_SENSOR_CALIBRATION = "com.example.android.bluetoothlegatt.ACTION_RSC_START_SENSOR_CALIBRATION";
    public static final String ACTION_RSC_UPDATE_SENSOR_LOCATION = "com.example.android.bluetoothlegatt.ACTION_RSC_UPDATE_SENSOR_LOCATION";
    public static final String ACTION_RSC_REQUEST_SUPPORTED_SENSOR_LOCATION = "com.example.android.bluetoothlegatt.ACTION_RSC_REQUEST_SUPPORTED_SENSOR_LOCATION";
    public static final String ACTION_RSC_SUPPORTED_SENSOR_LOCATION_DATA_AVAILABLE = "com.example.android.bluetoothlegatt.ACTION_RSC_SUPPORTED_SENSOR_LOCATION_DATA_AVAILABLE";
    public static final String ACTION_RSC_SET_NOTIFICATION = "com.example.android.bluetoothlegatt.ACTION_RSC_SET_NOTIFICATION";
    public static final String ACTION_RSC_SET_INDICATION = "com.example.android.bluetoothlegatt.ACTION_RSC_SET_INDICATION";


    public static final String RSC_SPEED_DATA = "com.example.android.bluetoothlegatt.RSC_SPEED_DATA";
    public static final String RSC_CADENCE_DATA = "com.example.android.bluetoothlegatt.RSC_CADENCE_DATA";
    public static final String RSC_STRIDE_LENGTH_DATA = "com.example.android.bluetoothlegatt.RSC_STRIDE_LENGTH_DATA";
    public static final String RSC_TOTAL_DISTANCE_DATA = "com.example.android.bluetoothlegatt.RSC_TOTAL_DISTANCE_DATA";
    public static final String RSC_STRIDE_LENGTH_PRESENT = "com.example.android.bluetoothlegatt.RSC_STRIDE_LENGTH_PRESENT";
    public static final String RSC_TOTAL_DISTANCE_PRESENT = "com.example.android.bluetoothlegatt.RSC_TOTAL_DISTANCE_PRESENT";
    public static final String RSC_WALKING_OR_RUNNING = "com.example.android.bluetoothlegatt.RSC_WALKING_OR_RUNNING";

    public static final String RSC_FEATURE_STRIDE_LENGTH_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_STRIDE_LENGTH_SUPPORTED";
    public static final String RSC_FEATURE_TOTAL_DISTANCE_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_TOTAL_DISTANCE_SUPPORTED";
    public static final String RSC_FEATURE_WALKING_OR_RUNNING_STATUS = "com.example.android.bluetoothlegatt.RSC_FEATURE_WALKING_OR_RUNNING_STATUS";
    public static final String RSC_FEATURE_CALIBRATION_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_CALIBRATION_SUPPORTED";
    public static final String RSC_FEATURE_MULTIPLE_SENSOR_SUPPORTED = "com.example.android.bluetoothlegatt.RSC_FEATURE_MULTIPLE_SENSOR_SUPPORTED";

    public static final String RSC_CURRENT_SENSOR_LOCATION_DATA = "com.example.android.bluetoothlegatt.RSC_SENSOR_LOCATION_DATA";
    public static final String RSC_SUPPORTED_SENSOR_LOCATION_DATA = "com.example.android.bluetoothlegatt.RSC_SUPPORTED_SENSOR_LOCATION_DATA";

    private final BluetoothRscpCallback mRscpCallback = new BluetoothRscpCallback() {
        @Override
        public void onConnectionStateChange(int state, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) {

                sendBroadcast(new Intent(ACTION_RSC_CONNECTED));

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                sendBroadcast(new Intent(ACTION_RSC_DISCONNECTED));

            } else if (newState == BluetoothProfile.STATE_CONNECTING) {

                sendBroadcast(new Intent(ACTION_RSC_CONNECTING));

            } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {

                sendBroadcast(new Intent(ACTION_RSC_DISCONNECTING));

            }
        }

        @Override
        public void onRSCMeasurementCharacChange(int speed, int cadence, int strideLength, int totalDistance, boolean isInstantaneousStrideLengthPresent,
                                                 boolean isTotalDistancePresent, String walkingOrRunning) {

            Intent intent = new Intent(ACTION_RSC_MEASUREMENT_DATA_AVAILABLE);
            intent.putExtra(RSC_SPEED_DATA, speed);
            intent.putExtra(RSC_CADENCE_DATA, cadence);
            intent.putExtra(RSC_STRIDE_LENGTH_DATA, strideLength);
            intent.putExtra(RSC_TOTAL_DISTANCE_DATA, totalDistance);
            intent.putExtra(RSC_STRIDE_LENGTH_PRESENT, isInstantaneousStrideLengthPresent);
            intent.putExtra(RSC_TOTAL_DISTANCE_PRESENT, isTotalDistancePresent);
            intent.putExtra(RSC_WALKING_OR_RUNNING, walkingOrRunning);

            sendBroadcast(intent);
        }

        @Override
        public void onRSCFeatureGet(boolean isStrideLengthMeasurementSupported,
                                       boolean isTotalDistanceMeasurementSupported,
                                       boolean isWalkingOrRunningStatusSupported,
                                       boolean isCalibrationProcedureSupported,
                                       boolean isMultipleSensorLocationSupported) {

            Intent intent = new Intent(ACTION_RSC_FEATURE_DATA_AVAILABLE);
            intent.putExtra(RSC_FEATURE_STRIDE_LENGTH_SUPPORTED, isStrideLengthMeasurementSupported);
            intent.putExtra(RSC_FEATURE_TOTAL_DISTANCE_SUPPORTED, isTotalDistanceMeasurementSupported);
            intent.putExtra(RSC_FEATURE_WALKING_OR_RUNNING_STATUS, isWalkingOrRunningStatusSupported);
            intent.putExtra(RSC_FEATURE_CALIBRATION_SUPPORTED, isCalibrationProcedureSupported);
            intent.putExtra(RSC_FEATURE_MULTIPLE_SENSOR_SUPPORTED, isMultipleSensorLocationSupported);

            sendBroadcast(intent);
        }

//        @Override
//        public void onSensorLocationChange() {
//            Intent intent = new Intent(ACTION_RSC_CURRENT_SENSOR_LOCATION_DATA_AVAILABLE);
//            intent.putExtra(RSC_CURRENT_SENSOR_LOCATION_DATA, mBluetoothRscp.getSensorLocation());
//            sendBroadcast(intent);
//        }

        @Override
        public void onSensorLocationGet(String location) {
            Intent intent = new Intent(ACTION_RSC_CURRENT_SENSOR_LOCATION_DATA_AVAILABLE);
            intent.putExtra(RSC_CURRENT_SENSOR_LOCATION_DATA, location);
            sendBroadcast(intent);
        }

        @Override
        public void onCumulativeValueSet() {
            Intent intent = new Intent(ACTION_RSC_CUMULATIVE_VALUE_SET);
            sendBroadcast(intent);
        }

        @Override
        public void onUpdateSensorLocation() {
            Intent intent = new Intent(ACTION_RSC_UPDATE_SENSOR_LOCATION);
            sendBroadcast(intent);
        }

        @Override
        public void onRequestSupportedSensorLocation() {
            Intent intent = new Intent(ACTION_RSC_REQUEST_SUPPORTED_SENSOR_LOCATION);
            sendBroadcast(intent);
        }

        @Override
        public void onSupportedSensorLocationGet() {
            Intent intent = new Intent(ACTION_RSC_REQUEST_SUPPORTED_SENSOR_LOCATION);
            intent.putExtra(RSC_SUPPORTED_SENSOR_LOCATION_DATA, "1248");
            sendBroadcast(intent);
        }

        @Override
        public void onStartCalibration() {
            Intent intent = new Intent(ACTION_RSC_START_SENSOR_CALIBRATION);
            sendBroadcast(intent);
        }

        @Override
        public void onServicesDiscovered(int state) {
            Intent intent = new Intent(ACTION_RSC_SERVICES_DISCOVERED);
            sendBroadcast(intent);
        }

        @Override
        public void onNotificationSet(int value) {
            Intent intent = new Intent(ACTION_RSC_SET_NOTIFICATION);
            intent.putExtra("value",value);
            sendBroadcast(intent);
        }

        @Override
        public void onIndicationSet(int value) {
            Intent intent = new Intent(ACTION_RSC_SET_INDICATION);
            intent.putExtra("value",value);
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
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            return false;
        }

        mBluetoothRscp = new BluetoothRscp(getBaseContext(), mRscpCallback);
        return true;
    }

    public boolean connect(final String address) {
        if (mBluetoothAdapter == null && address == null)
            return false;

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        mBluetoothRscp.connect(device, false);
        return true;
    }

    public void disconnect() {
        if (mBluetoothAdapter == null && mBluetoothRscp == null)
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

    public void setCharacteristicIndication(boolean enable) {
        if (mBluetoothAdapter != null && mBluetoothRscp != null) {
            mBluetoothRscp.setCharacteristicIndication(enable);
        }
    }

    public boolean setCumulativeValue(int cumulativeValue) {
        return mBluetoothRscp.setCumulativeValue(cumulativeValue);
    }

    public void startCalibration() {
        mBluetoothRscp.startCalibration();
    }

    public boolean updateSensorLocation(int location) {
        return mBluetoothRscp.updateSensorLocation(location);
    }

    public boolean getSensorLocation() {
        return mBluetoothRscp.getSensorLocation();
    }

    public void getSupportedFeature() {
        mBluetoothRscp.getSupportedFeature();
    }

    public void getSupportedSensorLocation() {
        mBluetoothRscp.getSupportedSensorLocation();
    }

}
