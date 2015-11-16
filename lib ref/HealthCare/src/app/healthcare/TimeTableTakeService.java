package app.healthcare;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import zulu.app.healthcare.R;

public class TimeTableTakeService extends Service {

	private static final int FM_NOTIFICATION_ID = 1;
	SharedPreferences prefs;
	boolean check = true;
	final Handler mHandler = new Handler();

	@Override
	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		check = true;
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {
		Runnable runnable = new Runnable() {
			Intent newIntent;
			boolean first = true;
			boolean stop = false;

			@Override
			public void run() {
				if (!first) {
					if (!stop) {
						if (intent != null) {
							addNotification(intent);
							int temp = getCountTime("count_time");
							if (temp == 0) {
								stop = true;
							}
							setCountTime(temp -1, "count_time");
						}
						else {
							addNotification(new Intent(getApplicationContext(),
									TimeTableTakeService.class));
						}
					}

				} else {
					first = false;
				}
				
				mHandler.postDelayed(this, getTime(Constants.getInstance().TIME_COUNT));
			}

		};
		// start handler
		mHandler.post(runnable);
		return startId;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	private void addNotification(Intent intent) {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Thông báo uống thuốc")
				.setContentText(
						"Đã tới giờ uống thuốc rồi, đề nghị bạn uống thuốc đễ mau khỏi bệnh");

		Intent notificationIntent = intent;
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);

		// Add as notification
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(FM_NOTIFICATION_ID, builder.build());
	}

	// Remove notification
	private void removeNotification() {
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(FM_NOTIFICATION_ID);
	}

	public void setTime(int score, String s) {

		SharedPreferences.Editor settingsEditor = prefs.edit();
		settingsEditor.putInt(s, score);
		settingsEditor.commit();
	}

	public int getTime(String s) {
		return prefs.getInt(s, 0);
	}
	
	public int getCountTime(String s) {
		return prefs.getInt(s, 0);
	}
	
	public void setCountTime(int countTime, String s) {
		SharedPreferences.Editor settingsEditor = prefs.edit();
		settingsEditor.putInt(s, countTime);
		settingsEditor.commit();
	}
	
}
