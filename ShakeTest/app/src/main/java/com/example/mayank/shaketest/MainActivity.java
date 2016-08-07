package com.example.mayank.shaketest;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    Sensor accelerometer;
    public static final int THRESHOLD_SPEED=800;
    int shake_count=0;
    float x,y,z,last_x,last_y,last_z;
    long lasttime;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        textView=(TextView)findViewById(R.id.textcount);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
           long currenttime=System.currentTimeMillis();
           long d=currenttime-lasttime;
        if(currenttime-lasttime>200){
            x= sensorEvent.values[0];
            y= sensorEvent.values[1];
            z= sensorEvent.values[2];
           long diff=currenttime-lasttime;
            int speed= (int) (Math.abs(x+y+z-last_x-last_y-last_z)/(diff)*10000);
                if(speed>THRESHOLD_SPEED){
                    shake_count++;
                    textView.setText("Count= "+shake_count+"\n"+"Shake detected. Speed="+String.valueOf(speed));
                }
            last_x=x;
            last_y=y;
            last_z=z;
            lasttime=currenttime;
        }
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this,accelerometer);
        super.onPause();
    }
}
