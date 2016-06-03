package com.example.admin.lighttest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 光センサーで光を感知
 */
public class MainActivity extends Activity implements SensorEventListener {

    //メンバー変数
    private SensorManager mSensorManager;	//センサーマネージャー
    private Sensor mLight;					//光センサー
    private Sensor mAccelerometer;			//加速度センサー

    TextView mTextOutputLight;				//光センサーの値を入れるtextview
    TextView mTextOutputSpeedX;				//加速度センサーのX軸の値を入れるtextview
    TextView mTextOutputSpeedY;				//加速度センサーのY軸の値を入れるtextview
    TextView mTextOutputSpeedZ;				//加速度センサーのZ軸の値を入れるtextview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //結び付け
        mTextOutputLight=(TextView)findViewById(R.id.TextOutputLight);
        mTextOutputSpeedX=(TextView)findViewById(R.id.TextOutputSpeedX);
        mTextOutputSpeedY=(TextView)findViewById(R.id.TextOutputSpeedY);
        mTextOutputSpeedZ=(TextView)findViewById(R.id.TextOutputSpeedZ);

        //SensorManagerオブジェクトを取得
        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //光センサーである[Sensor.TYPE_LIGHT]を指定
        mLight=mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //加速度センサーである[TYPE_ACCELEROMETER]を取得
        mAccelerometer=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onResume() {
        super.onResume();
        //registerListener()でSensorEventListenerを登録
        //データ取得の間隔を指定
        //加速度センサーの場合 直接指定 500,000マイクロ秒
        mSensorManager.registerListener(this,mAccelerometer,500000);
        //光センサーの場合 SENSOR_DELAY_NORMALは[画面の向きの変更に最適な通常モード]
        mSensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregisterListener()で、SensorEventListenerの登録解除
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO 自動生成されたメソッド・スタブ
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType()==Sensor.TYPE_LIGHT){					//光センサーを取得した場合
            //SensorEventのvalues配列をセットする(光の場合は1つしかない)
            mTextOutputLight.setText(String.valueOf(event.values[0]));
        }else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){	//加速度センサーを取得した場合
            //SensorEventのvalues配列をセットする(加速度の場合は3つある)
            mTextOutputSpeedX.setText(String.valueOf(event.values[0]));
            mTextOutputSpeedY.setText(String.valueOf(event.values[1]));
            mTextOutputSpeedZ.setText(String.valueOf(event.values[2]));
        }else{
            return;
        }
    }
}