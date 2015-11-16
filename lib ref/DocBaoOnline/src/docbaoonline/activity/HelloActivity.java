package docbaoonline.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class HelloActivity extends Activity {
	private ImageView iv1;
	private int resources[] = { R.drawable.doctor, R.drawable.afamily,
			R.drawable.yhocsuckhoe};
	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_activity);
		iv1 = (ImageView) findViewById(R.id.iv1);
//		this.run();
		
		CountDownTimer cdTimer = new CountDownTimer(5000, 300) {

			@Override
			public void onFinish() {
				if (isNetworkAvaiable()) {
					Intent intent = new Intent(HelloActivity.this,
							MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.zoom_enter,
							R.anim.zoom_exit);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							HelloActivity.this);
					builder.setTitle("Network no avaiable");
					builder.setMessage("Please check the network connection!");
					builder.setPositiveButton("Finish",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();

								}
							});
					builder.show();
				}
			}

			@Override
			public void onTick(long millisUntilFinished) {
				iv1.setImageResource(resources[count]);
				if (count < 2) {
//					if(state){
//						iv1.setScaleX(3);
//					} else{
//						iv1.setScaleX(1);
//					}
//					state = !state;
					count++;
				} else {
					count = 0;
				}
			}
		};//
		cdTimer.start();
	}

	private boolean isNetworkAvaiable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		Log.d("Network", (info != null) + "  " + info);
		return (info != null);
	}

	@Override
	protected void onResume() {
		Log.d("OnResume", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

//	private boolean state = true;

//	@Override
//	public void run() {
//		if (state) {
//			ObjectAnimator oa = ObjectAnimator.ofFloat(iv1, ImageView.SCALE_X,
//					1, 3);
//			oa.setDuration(500);
//			oa.setInterpolator(new AccelerateDecelerateInterpolator());
//			oa.start();
//
//			iv1.postDelayed(this, 50);
//		} else {
//			ObjectAnimator oa = ObjectAnimator.ofFloat(iv1, ImageView.SCALE_X,
//					3, 1);
//			oa.setDuration(500);
//			oa.setInterpolator(new AccelerateDecelerateInterpolator());
//			oa.start();
//
//			iv1.postDelayed(this, 50);
//		}
//		
//		state = !state;
//	}
}
