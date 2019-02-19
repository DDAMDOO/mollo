package themollo.app.mollo.bluetooth;

import java.util.UUID;


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import themollo.app.mollo.R;


public class DeviceActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "BLEDevice";

    public static final String EXTRA_BLUETOOTH_DEVICE = "BT_DEVICE";
    private BluetoothAdapter mBTAdapter;
    private BluetoothDevice mDevice;
    private BluetoothGatt mConnGatt;
    private int mStatus;
    Button start, s1Button, s2Button, s3Button;

    private final BluetoothGattCallback mGattcallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mStatus = newState;
                mConnGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mStatus = newState;
                runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                final int i = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);

                runOnUiThread(new Runnable() {
                    public void run() {
                        TextView tv = (TextView) findViewById(R.id.tv);
                        tv.setText("value=" + i);
                    }
                });


            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device);

        // state
        mStatus = BluetoothProfile.STATE_DISCONNECTED;

        start = (Button) findViewById(R.id.read_state);
        start.setOnClickListener(this);
        s1Button = (Button) findViewById(R.id.scent1_btn);
        s1Button.setOnClickListener(this);
        s2Button = (Button) findViewById(R.id.scent2_btn);
        s2Button.setOnClickListener(this);
        s3Button = (Button) findViewById(R.id.scent3_btn);
        s3Button.setOnClickListener(this);
//        mTurnOnLEDButton = (Button) findViewById(R.id.turn_on_led);
//        mTurnOnLEDButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnGatt != null) {
            if ((mStatus != BluetoothProfile.STATE_DISCONNECTING)
                    && (mStatus != BluetoothProfile.STATE_DISCONNECTED)) {
                mConnGatt.disconnect();
            }
            mConnGatt.close();
            mConnGatt = null;
        }
    }


    ////////////////////////////////////////////////여기부분////////////////////////////////////////////////////////


    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "value=", Toast.LENGTH_LONG);
        Log.d("aa", "aaaaaaaaaaaaaaaaaaaaaaaa");
        if (v.getId() == R.id.read_state) {
            Log.d("btcheck", "1111");
            BluetoothGattService disService = mConnGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));
            Log.d("btcheck", String.valueOf(disService));
            if (disService == null) {
                Log.d("UUID 에러", "Dis service not found!");
                return;
            }
            Log.d("btcheck", "2222");
            BluetoothGattCharacteristic characteristic = disService.getCharacteristic(UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e"));
            Log.d("btcheck", "3333");

            if (characteristic == null) {
                Log.d("characteristic 에러", "charateristic not found!");
                return;
            }

            Log.d("btcheck", "5555" + String.valueOf(characteristic) + " " + characteristic);
            characteristic.setValue(new byte[]{0x61, 0x74});
            Log.d("btcheck", "6666" + String.valueOf(characteristic.setValue(new byte[]{(byte) 0x61, (byte) 0x74})));
            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "7777" + String.valueOf(characteristic) + " " + characteristic);
            }
            Log.d("btcheck", "8888");
//            boolean result = mConnGatt.readCharacteristic(characteristic);
//            Log.d("btcheck","9999"+result);
        }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if (v.getId() == R.id.scent1_btn) {
            BluetoothGattService disService = mConnGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));
            if (disService == null) {
                Log.d("", "Dis service not found!");
                return;
            }

            BluetoothGattCharacteristic characteristic = disService.getCharacteristic(UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e"));
            if (characteristic == null) {
                Log.d("characteristic 에러", "charateristic not found!");
                return;
                //fb256b0
            }
            Log.d("btcheck", "before btn1" + characteristic);
            characteristic.setValue(new byte[]{0x61, 0x74, 0x46, 0x34});
            Log.d("btcheck", "after btn1" + characteristic);

            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "write correct");
            }

        } else if (v.getId() == R.id.scent2_btn) {

            BluetoothGattService disService = mConnGatt.getService(UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e"));
            if (disService == null) {
                Log.d("", "Dis service not found!");
                return;
            }


            BluetoothGattCharacteristic characteristic = disService.getCharacteristic(UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e"));

            if (characteristic == null) {
                Log.d("", "charateristic not found!");
                return;
            }


            characteristic.setValue(new byte[]{0x61, 0x74, 0x46, 0x35});
            Log.d("btcheck", "after btn1" + characteristic);

            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "write correct");
            }

        }
    }

    private void init() {
        // BLE check
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "ble_not_supported", Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        // BT check
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager != null) {
            mBTAdapter = manager.getAdapter();
        }
        if (mBTAdapter == null) {
            Toast.makeText(this, "bt_unavailable", Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        // check BluetoothDevice
        if (mDevice == null) {
            mDevice = getBTDeviceExtra();
            if (mDevice == null) {
                finish();
                return;
            }
        }


        // connect to Gatt
        if ((mConnGatt == null)
                && (mStatus == BluetoothProfile.STATE_DISCONNECTED)) {
            // try to connect
            mConnGatt = mDevice.connectGatt(this, false, mGattcallback);
            mStatus = BluetoothProfile.STATE_CONNECTING;
        } else {
            if (mConnGatt != null) {
                // re-connect and re-discover Services
                mConnGatt.connect();
                mConnGatt.discoverServices();
            } else {
                Log.e(TAG, "state error");
                finish();
                return;
            }
        }

    }

    private BluetoothDevice getBTDeviceExtra() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return null;
        }

        return extras.getParcelable(EXTRA_BLUETOOTH_DEVICE);
    }

}