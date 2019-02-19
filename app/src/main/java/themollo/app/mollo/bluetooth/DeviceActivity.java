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
import themollo.app.mollo.diffuser.TimerActivity;


public class DeviceActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "BLEDevice";

    public static final String EXTRA_BLUETOOTH_DEVICE = "BT_DEVICE";
    private BluetoothAdapter mBTAdapter;
    private BluetoothDevice mDevice;
    private BluetoothGatt mConnGatt;
    private int mStatus;
    Button s1Button, s2Button, s3Button, t1Button, t2Button, t3Button;

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

        s1Button = (Button) findViewById(R.id.scent1_btn);
        s1Button.setOnClickListener(this);
        s2Button = (Button) findViewById(R.id.scent2_btn);
        s2Button.setOnClickListener(this);
        s3Button = (Button) findViewById(R.id.scent3_btn);
        s3Button.setOnClickListener(this);
        t1Button = (Button) findViewById(R.id.timer1_btn);
        t1Button.setOnClickListener(this);
        t2Button = (Button) findViewById(R.id.timer2_btn);
        t2Button.setOnClickListener(this);
        t3Button = (Button) findViewById(R.id.timer3_btn);
        t3Button.setOnClickListener(this);
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
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (v.getId() == R.id.scent1_btn) {
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

        }else if (v.getId() == R.id.scent3_btn) {

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


            characteristic.setValue(new byte[]{0x61, 0x74, 0x46, 0x36});
            Log.d("btcheck", "after btn1" + characteristic);

            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "write correct");
            }

        }else if (v.getId() == R.id.timer1_btn) {
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
            Log.d("btcheck", "timer" + characteristic);
            characteristic.setValue(new byte[]{0x61, 0x74, 0x46, 0x31});
            Log.d("btcheck", "timer" + characteristic);

            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "write correct");
            }
            Intent intent = new Intent(DeviceActivity.this, TimerActivity.class);
            intent.putExtra("countdown_val",20);
            startActivity(intent);

        }else if (v.getId() == R.id.timer2_btn) {
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
            characteristic.setValue(new byte[]{0x61, 0x74, 0x46, 0x32});
            Log.d("btcheck", "after btn1" + characteristic);

            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "write correct");
            }


        }else if (v.getId() == R.id.timer3_btn) {
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
            characteristic.setValue(new byte[]{0x61, 0x74, 0x46, 0x33});
            Log.d("btcheck", "after btn1" + characteristic);

            if (mConnGatt.writeCharacteristic(characteristic)) {
                Log.d("btcheck", "write correct");
            }
        }
//        else if (v.getId() == R.id.timer3_btn) {}

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