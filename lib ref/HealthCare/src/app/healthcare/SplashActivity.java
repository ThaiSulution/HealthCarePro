package app.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import zulu.app.healthcare.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}, 2000);
	}
	
}
