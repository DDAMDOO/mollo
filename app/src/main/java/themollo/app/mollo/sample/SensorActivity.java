package themollo.app.mollo.sample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import themollo.app.mollo.R;

/**
 * Sensor Manager 사용 방법
 * 1. 센서 매니저 얻기
 * 2. 센서 선언
 * 3. 리스너 구성하기
 * 4. 리스너 등록하기
 * 5. 리스너 해제하기
 */

public class SensorActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    @BindView(R.id.tvAccel)
    TextView tvAccel;
    @BindView(R.id.tvGravity)
    TextView tvGravity;
    @BindView(R.id.tvGyro)
    TextView tvGyro;
    @BindView(R.id.tvLight)
    TextView tvLight;
    @BindView(R.id.tvPressure)
    TextView tvPressure;
    @BindView(R.id.tvProxy)
    TextView tvProxy;

    private Sensor lightSensor, accelSensor,
            gravitySensor, gyroscopeSensor,
            pressureSensor, proximitySensor;

    private float[] lightArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        declareSensors();

    }

    public void declareSensors(){
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void registerSensors() {
        sensorManager.registerListener( this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensors() {
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensors();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();


        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                lightArr = sensorEvent.values;
                float lux = (float) (Math.round(lightArr[0] * 100) / 100);
                tvLight.setText(lux + " lux");
                break;

            case Sensor.TYPE_PROXIMITY:
                float dist = sensorEvent.values[0];
                tvProxy.setText(dist + "distance value");
                break;

            case Sensor.TYPE_GRAVITY:
                float grav0 = sensorEvent.values[0];
                float grav1 = sensorEvent.values[1];
                float grav2 = sensorEvent.values[2];
                tvGravity.setText("0 : " + grav0 + " \n1 : " + grav1 + " \n2 : " + grav2);

            case Sensor.TYPE_ACCELEROMETER:
                float accx = sensorEvent.values[0];
                float accy = sensorEvent.values[1];
                float accz = sensorEvent.values[2];

                tvAccel.setText("x : " + accx + " \ny : " + accy + " \nz : " + accz);

            case Sensor.TYPE_GYROSCOPE:
                float gyrox = sensorEvent.values[0];
                float gyroy = sensorEvent.values[1];
                float gyroz = sensorEvent.values[2];

                tvGyro.setText("x : " + gyrox + " \ny : " + gyroy + " \nz : " + gyroz);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
