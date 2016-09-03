package com.wcom.proximitysensor;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

//The activity must implement the methods in the SensorEventListener interface
//to be able to access sensor data.
public class MainActivity extends Activity implements SensorEventListener {

	//The object that manages the sensors
	private SensorManager mSensorManager;
	//The sensor object
	private Sensor mProxSensor;
	//The value retrieved from the sensor
	private float mProxVal;
	//The text element in the GUI
	//It will display text depending on whether
	//an object is close or far away from the proximity sensor.
	private TextView mTvProx;

	//This method gets executed when the activity is created.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Load the GUI from the folder res/layout/activity_main.xml
		setContentView(R.layout.activity_main);

		//Get a sensor manager from the sensor service.
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		//Get the proximity sensor from the sensor manager.
		mProxSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		//Register our activity as a receiver of data from our proximity sensor
		//Also, indicate that the delay between sensor readings is only as fast
		//as is needed in order to display the readings in a GUI.
		mSensorManager.registerListener(this, mProxSensor, SensorManager.SENSOR_DELAY_UI);

		//Get a hold of a text element in the GUI. This element display will indicate whether there is an object
		//close or far away from the sensor.
		mTvProx = (TextView) findViewById(R.id.tvProx);
	}

	//This method gets executed when the activity is destroyed.
	@Override
	protected void onDestroy() {
		super.onDestroy();

		//Tell the sensor manager that our class will not need its sensor data updates anymore
		//since the activity is being destroyed.
		mSensorManager.unregisterListener(this);
	}

	//This method is part of the SensorEventListener interface and it will get executed
	//whenever an event takes place. The event is a change in the value read by the sensor.
	//The SensoEvent object will hold data pertaining to this event, such as value
	//read by the sensor, time, and the type of sensor. 
	public void onSensorChanged(SensorEvent event){
		//Fetch the value
		mProxVal = event.values[0];
		
		//TODO: Uncomment the rest of the lines in this method to initialize a string that will be displayed by a text element in the GUI, depending on the reading of the proximity sensor.
		String sProxNear;
		if(mProxVal<=5)
		{
			sProxNear=new String("near");
			//Change the color of the text to red when an object is close to the sensor.
			mTvProx.setTextColor(Color.RED);
		}	
		else
		{
			sProxNear=new String("far");
			//Change the color of the text to black when an object is close to the sensor.
			mTvProx.setTextColor(Color.BLACK);
		}
		//Display the string in the text element.
		mTvProx.setText(sProxNear);
	}  

	//This method gets called whenever the accuracy of the data provided by a sensor changes.
	//Accuracy can be low, medium, or high.
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}