package app.healthcare;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class StepRunServices extends Service implements SensorEventListener{
	private SensorManager sensorManager;
	private boolean color = false;
	private long lastUpdate;
	public int step;
	SharedPreferences prefs;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate(){
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		step = getHighScore();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		lastUpdate = System.currentTimeMillis();
		return startId;
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		getAccelerometer(arg0);
		
	}
	
	public void setHighScore(int score) {
		SharedPreferences.Editor settingsEditor = prefs.edit();
		settingsEditor.putInt(Constants.KEY_HISCORE, score);
		settingsEditor.commit();
	}

	public int getHighScore() {
		return prefs.getInt(Constants.KEY_HISCORE, 0);
	}
	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;
		// Movement
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float accelationSquareRoot = (x * x + y * y + z * z)
				/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		long actualTime = System.currentTimeMillis();
		if (accelationSquareRoot >= 2) //
		{
			if (actualTime - lastUpdate < 200) {
				return;
			}
			lastUpdate = actualTime;
			
			if (color) {
				//tvText.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
				
			} else {
				//tvText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
				step+=1;
				setHighScore(step);
				//tvcountStep.setText(String.valueOf(step));
				Intent intent = new Intent(Constants.INTENT_GET_BEAT);
				intent.putExtra(Constants.ActionReceiver.ACTION_RECEIVER, Constants.ActionReceiver.SHOW_TEXT);
				intent.putExtra(Constants.ActionReceiver.ACTION_VALUE, getHighScore());
				getBaseContext().sendBroadcast(intent);
			}
			color = !color;
		}
	}
}
