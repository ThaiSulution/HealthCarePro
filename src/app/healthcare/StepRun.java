package app.healthcare;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.gc.materialdesign.views.Button;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.fitness.FitnessStatusCodes;

public class StepRun extends Activity implements LocationListener {
	// location
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected String latitude, longitude;
	protected boolean gps_enabled, network_enabled;
	double lastLocationX;
	double lastLocationY;
	double curentLocationX;
	double curentLocationY;
	// end location
	public final static String TAG = "GoogleFitService";
	private ConnectionResult mFitResultResolution;
	private static final String AUTH_PENDING = "auth_state_pending";
	private boolean authInProgress = false;
	private static final int REQUEST_OAUTH = 1431;
	/*
	 * private Spinner view_type; private Spinner view_unit; private long
	 * avgStep; private boolean monthTypeFlg = true; private boolean dayTypeFlg
	 * = false; private boolean weekTypeFlg = false;
	 */
	private PieSlice sliceRed;
	public static String weight_weight;
	public static int target;
	EditText targetContent;
	Button btnHistory;
	Button btnSetup;
	final Handler mHandler = new Handler();
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step_run);
		init();
	}

	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void init() {
		// loaction
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
		lastLocationX = 0;
		lastLocationY = 0;
		curentLocationX = 0;
		curentLocationY = 0;
		// location
		final Resources resources = getResources();
		final PieGraph pg = (PieGraph) findViewById(R.id.step_run_chart_progess);
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mFitStatusReceiver,
				new IntentFilter(GoogleFitService.FIT_NOTIFY_INTENT));
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mFitDataReceiver,
				new IntentFilter(GoogleFitService.HISTORY_INTENT));
		requestFitConnection();
		if (GoogleFitService.isConnected) {
			getDataToDay();
			getTargetToDay();
			getDataToYear();
		}

		dialog = ProgressDialog.show(StepRun.this, "", "Vui lòng chờ...");
		/*
		 * if (!AppStatus.getInstance(this).isOnline()) { // Dialog dialog = new
		 * Dialog(this, "Kết nối mạng", //
		 * "Thiết bị đang off line, hãy kiểm tra lại kết nối của bạn", //
		 * R.drawable.whr_icon, 0); // dialog.show(); Dialog a = new
		 * Dialog(this, "Loi", "Khong co ket noi", 0,
		 * app.healthcare.R.drawable.capture); a.show(); }
		 */

		// UserDAO dao = new UserDAO(this);
		// int target = dao.getUser().getTarget();
		btnHistory = (Button) findViewById(R.id.btnHistory);
		btnHistory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(StepRun.this, HistoryStep.class));
			}
		});
		sliceRed = new PieSlice();
		sliceRed.setColor(resources.getColor(R.color.red));
		sliceRed.setSelectedColor(resources
				.getColor(R.color.transparent_orange));
		sliceRed.setValue(Constants.getInstance().getStepRuns() + 1);
		sliceRed.setTitle("first");
		pg.addSlice(sliceRed);
		sliceRed = new PieSlice();
		sliceRed.setColor(resources.getColor(R.color.orange));
		sliceRed.setValue(1000);
		pg.addSlice(sliceRed);
		pg.setTextSizeGr(25);
		targetContent = (EditText) findViewById(R.id.target);
		targetContent.setText(String.valueOf(1000));
		btnSetup = (Button) findViewById(R.id.btnSetTarget);
		btnSetup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (targetContent.getText().toString().length() > 0) {
					try {
						Constants.getInstance().setTarget(
								Long.parseLong(targetContent.getText()
										.toString()));
					} catch (NumberFormatException e) {
						// default
						Constants.getInstance().setTarget(3000);
					}
				}
				pg.getSlices().get(0)
						.setGoalValue(Constants.getInstance().getStepRuns());
				if ((Constants.getInstance().getTarget() - Constants
						.getInstance().getStepRuns()) > 0) {
					pg.getSlices()
							.get(1)
							.setGoalValue(
									Constants.getInstance().getTarget()
											- Constants.getInstance()
													.getStepRuns());
				} else {
					pg.getSlices().get(1).setGoalValue(0);
				}

				pg.setDuration(1000);// default if unspecified is 300 ms
				pg.setInterpolator(new AccelerateDecelerateInterpolator());
				pg.setAnimationListener(getAnimationListener());
				pg.animateToGoalValues();
				target = Integer.parseInt(targetContent.getText().toString());
				setTargetToDay();
			}
		});
		// wait for get data finnish from service
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// if (!GoogleFitService.getDataDayFinish
				// || !GoogleFitService.getDataYearFinish) {
				if (!GoogleFitService.getDataDayFinish) {
					if (!dialog.isShowing()) {
						dialog = ProgressDialog.show(StepRun.this, "",
								"Vui lòng chờ...");
					}
					mHandler.postDelayed(this, 2000);
				} else {
					pg.setBackgroundText("Số bước: "
							+ String.valueOf(Constants.getInstance()
									.getStepRuns())
							+ "\n"
							+ "Khoảng cách: "
							+ String.valueOf((long) Constants.getInstance()
									.getDistance())
							+ "m\n"
							+ "Calo: "
							+ String.valueOf(Constants.getInstance().getCalos()));
					pg.getSlices()
							.get(0)
							.setGoalValue(
									Constants.getInstance().getStepRuns() + 1);
					if (Constants.getInstance().getTarget() > 0) {
						pg.getSlices()
								.get(1)
								.setGoalValue(
										Constants.getInstance().getTarget());
					} else {
						pg.getSlices()
								.get(1)
								.setGoalValue(
										Integer.parseInt(targetContent
												.getText().toString()));
						Constants.getInstance().setTarget(
								Integer.parseInt(targetContent.getText()
										.toString()));
					}
					pg.setDuration(1000);// default if unspecified is 300 ms
					pg.setAnimationListener(getAnimationListener());
					pg.animateToGoalValues();
					dialog.cancel();
					Thread.currentThread().interrupt();
				}
			}
		};
		// start handler
		mHandler.post(runnable);

		pg.setOnSliceClickedListener(new OnSliceClickedListener() {
			@Override
			public void onClick(int index) {
				getDataToDay();
				getTargetToDay();

				if (targetContent.getText().toString().length() > 0) {
					try {
						Constants.getInstance().setTarget(
								Long.parseLong(targetContent.getText()
										.toString()));
					} catch (NumberFormatException e) {
						// default
						Constants.getInstance().setTarget(3000);
					}
				} else if (GoogleFitService.targetToset > 0) {
					Constants.getInstance().setTarget(
							GoogleFitService.targetToset);
				}
				pg.getSlices()
						.get(0)
						.setGoalValue(Constants.getInstance().getStepRuns() + 1);
				if ((Constants.getInstance().getTarget() - Constants
						.getInstance().getStepRuns()) > 0) {
					pg.getSlices()
							.get(1)
							.setGoalValue(
									Constants.getInstance().getTarget()
											- Constants.getInstance()
													.getStepRuns());
				} else {
					pg.getSlices().get(1).setGoalValue(0);
				}

				pg.setDuration(1000);// default if unspecified is 300 ms
				pg.setInterpolator(new AccelerateDecelerateInterpolator());
				pg.setAnimationListener(getAnimationListener());
				pg.animateToGoalValues();
				// setTargetToDB();
			}
		});

		Bitmap b = BitmapFactory.decodeResource(getResources(),
				R.drawable.run_icon_black);
		pg.setBackgroundBitmap(b);
		pg.setInnerCircleRatio(220);
	}

	public void setTargetToDB() {
		
	}

	// [START GET DATA]
	private void getDataToDay() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_GET_DATA_TO_DAY);
		startService(service);
	}

	private void getTargetToDay() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_GET_TARGET);
		startService(service);
	}

	private void setTargetToDay() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_SET_TARGET);
		startService(service);
	}

	private void getDataToYear() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_GET_DATA_TO_YEAR);
		startService(service);
	}

	// [END GET DATA]

	private void requestFitConnection() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_REQUEST_CONNECTION);
		startService(service);
	}

	private BroadcastReceiver mFitStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			if (intent
					.hasExtra(GoogleFitService.FIT_EXTRA_NOTIFY_FAILED_STATUS_CODE)
					&& intent
							.hasExtra(GoogleFitService.FIT_EXTRA_NOTIFY_FAILED_STATUS_CODE)) {
				// Recreate the connection result
				int statusCode = intent
						.getIntExtra(
								GoogleFitService.FIT_EXTRA_NOTIFY_FAILED_STATUS_CODE,
								0);
				PendingIntent pendingIntent = intent
						.getParcelableExtra(GoogleFitService.FIT_EXTRA_NOTIFY_FAILED_INTENT);
				ConnectionResult result = new ConnectionResult(statusCode,
						pendingIntent);
				Log.d(TAG, "Fit connection failed - opening connect screen.");
				fitHandleFailedConnection(result);

			}
			if (intent.hasExtra(GoogleFitService.FIT_EXTRA_CONNECTION_MESSAGE)) {
				Log.d(TAG,
						"Fit connection successful - closing connect screen if it's open.");
			}
		}
	};

	// This would typically go in your fragment.
	private BroadcastReceiver mFitDataReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			if (intent.hasExtra(GoogleFitService.HISTORY_EXTRA_STEPS_TODAY)) {
				// long temp = GoogleFitService.totalStepsGet;
				long totalSteps = intent.getIntExtra(
						GoogleFitService.HISTORY_EXTRA_STEPS_TODAY, 0);
				Toast.makeText(StepRun.this, "Total Steps: " + totalSteps,
						Toast.LENGTH_SHORT).show();

			}

			if (intent.hasExtra(GoogleFitService.HISTORY_EXTRA_STEPS_TOWEEK)) {
				// long temp = GoogleFitService.totalStepsGet;
				long totalSteps = intent.getIntExtra(
						GoogleFitService.HISTORY_EXTRA_STEPS_TOWEEK, 0);
				Toast.makeText(StepRun.this, "Total Steps: " + totalSteps,
						Toast.LENGTH_SHORT).show();

			}

			if (intent.hasExtra(GoogleFitService.HISTORY_EXTRA_STEPS_TOMONTH)) {

				// long temp = GoogleFitService.totalStepsGet;
				long totalSteps = intent.getIntExtra(
						GoogleFitService.HISTORY_EXTRA_STEPS_TOMONTH, 0);
				Toast.makeText(StepRun.this, "Total Steps: " + totalSteps,
						Toast.LENGTH_SHORT).show();

			}

			if (intent.hasExtra(GoogleFitService.HISTORY_EXTRA_CALOS_TODAY)) {

				// long temp = GoogleFitService.totalCalosGet;
				long totalSteps = intent.getIntExtra(
						GoogleFitService.HISTORY_EXTRA_STEPS_TOMONTH, 0);
				Toast.makeText(StepRun.this, "Total Calos: " + totalSteps,
						Toast.LENGTH_SHORT).show();

			}
		}
	};

	private void fitHandleFailedConnection(ConnectionResult result) {
		Log.i(TAG, "Activity Thread Google Fit Connection failed. Cause: "
				+ result.toString());
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
					StepRun.this, 0).show();
			return;
		}
		if (!authInProgress) {
			if (result.getErrorCode() == FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS) {
				try {
					Log.d(TAG,
							"Google Fit connection failed with OAuth failure.  Trying to ask for consent (again)");
					result.startResolutionForResult(StepRun.this, REQUEST_OAUTH);
				} catch (IntentSender.SendIntentException e) {
					Log.e(TAG,
							"Activity Thread Google Fit Exception while starting resolution activity",
							e);
				}
			} else {
				Log.i(TAG,
						"Activity Thread Google Fit Attempting to resolve failed connection");
				mFitResultResolution = result;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.setup) {
			android.app.Dialog v = new SetupWeightAndHeight(this, "Cài đặt");
			v.show();
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					if (!SetupWeightAndHeight.isFinishSetup) {
						mHandler.postDelayed(this, 2000);
					} else {
						setWeightAndHieght();
						Thread.currentThread().interrupt();
					}
				}
			};
			// start handler
			mHandler.post(runnable);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		fitSaveInstanceState(outState);
	}

	private void fitSaveInstanceState(Bundle outState) {
		outState.putBoolean(AUTH_PENDING, authInProgress);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		fitActivityResult(requestCode, resultCode);
	}

	private void fitActivityResult(int requestCode, int resultCode) {
		if (requestCode == REQUEST_OAUTH) {
			authInProgress = false;
			if (resultCode == Activity.RESULT_OK) {
				// Ask the service to reconnect.
				Log.d(TAG, "Fit auth completed.  Asking for reconnect.");
				requestFitConnection();

			} else {
				try {
					authInProgress = true;
					mFitResultResolution.startResolutionForResult(StepRun.this,
							REQUEST_OAUTH);

				} catch (IntentSender.SendIntentException e) {
					Log.e(TAG,
							"Activity Thread Google Fit Exception while starting resolution activity",
							e);
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mFitStatusReceiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mFitDataReceiver);

		super.onDestroy();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public Animator.AnimatorListener getAnimationListener() {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)
			return new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {
					Log.d("piefrag", "anim end");
				}

				@Override
				public void onAnimationCancel(Animator animation) {
					Log.d("piefrag", "anim cancel");
				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}
			};
		else
			return null;
	}

	public void setWeightAndHieght() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_SET_HEIGHT_ANDWEIGHT);
		startService(service);
	}

	@Override
	public void onLocationChanged(Location location) {
		try {
			lastLocationX = curentLocationX;
			lastLocationY = curentLocationY;
			curentLocationX = location.getLatitude();
			curentLocationY = location.getLongitude();
			float[] dis = new float[1];
			dis[0] = 0;
			Location.distanceBetween(lastLocationX, lastLocationY,
					curentLocationX, curentLocationY, dis);
			Constants.getInstance().setDistance(
					Constants.getInstance().getDistance() + dis[0]);
		} catch (Exception e) {

		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
}