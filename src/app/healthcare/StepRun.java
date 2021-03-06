package app.healthcare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;
import app.healthcare.dataobject.StepRunDTOParse;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.gc.materialdesign.views.Button;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class StepRun extends Activity {

	public final static String TAG = "GoogleFitService";
	private ConnectionResult mFitResultResolution;
	private static final String AUTH_PENDING = "auth_state_pending";
	private boolean authInProgress = false;
	private static final int REQUEST_OAUTH = 1431;
	private PieSlice sliceRed;
	public static String weight_weight;
	public static int target;
	EditText targetContent;
	Button btnHistory;
	Button btnSetup;
	final Handler mHandler = new Handler();
	private ProgressDialog dialog;
	File imagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step_run);
		NotificationManager notificationManager =
			    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			int icon = R.drawable.run_icon;
			CharSequence notiText = "Ban da di du so buoc";
			long meow = System.currentTimeMillis();
			Notification notification = new Notification(icon, notiText, meow);
			Context context = getApplicationContext();
			CharSequence contentTitle = "Your notification";
			CharSequence contentText = "Some data has arrived!";
			notification.setLatestEventInfo(context, contentTitle, contentText, MainActivity.contentIntent);
			int SERVER_DATA_RECEIVED = 1;
			notificationManager.notify(SERVER_DATA_RECEIVED, notification);
		init();
	}

	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void init() {

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
			getDataToYear();
		}

		dialog = ProgressDialog.show(StepRun.this, "", "Vui lòng chờ...");
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
						Constants.getInstance().setTarget(5000);
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
				setTargetToDB();
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
							// + String.valueOf((long) Constants.getInstance()
							// .getDistance())
							+ String.valueOf(Math
									.round(GoogleFitService.distance))
							+ "m\n"
							+ "Calo: "
							+ String.valueOf(Math.round(Constants.getInstance()
									.getCalos())));
					pg.getSlices()
							.get(0)
							.setGoalValue(
									Constants.getInstance().getStepRuns() + 1);
					if (Constants.getInstance().getTarget() > 0) {
						pg.getSlices()
								.get(1)
								.setGoalValue(
										Constants.getInstance().getTarget());
						targetContent.setText(String.valueOf(Constants.getInstance().getTarget()));
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
					setTargetToDB();
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
				setTargetToDB();
			}
		});

		Bitmap b = BitmapFactory.decodeResource(getResources(),
				R.drawable.run_icon_black);
		pg.setBackgroundBitmap(b);
		pg.setInnerCircleRatio(220);
	}

	private void setTargetToDB() {
		int id = 1;
		if (Constants.getInstance().listDataStepDTO.size() > 0) {
			id = Constants.getInstance().listDataStepDTO.size() + 1;
		}
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		long insertTime = cal.getTimeInMillis();
		StepRunDTOParse dto = new StepRunDTOParse();
		dto.setCalos((double) Math.round(Constants.getInstance().getCalos()));
		dto.setTime(insertTime);
		dto.setDistance(Double.parseDouble(String
				.valueOf(GoogleFitService.distance)));
		dto.setStepID(id);
		dto.setTarget(Integer.parseInt(targetContent.getText().toString()));
		dto.setStep((int) Constants.getInstance().getStepRuns());
		Constants.getInstance().listDataStepDTO.add(dto);
		dto.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException arg0) {
				if (arg0 != null) {
					Log.e("setTargetToDB", arg0.toString());
				} else {
					Log.e("setTargetToDB", "thanh cong");
				}
			}
		});
	}

	// [START GET DATA]
	private void getDataToDay() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_GET_DATA_TO_DAY);
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
					} else if (!SetupWeightAndHeight.isCancle){
						setWeightAndHieght();
						SetupWeightAndHeight.isFinishSetup = false;
						Thread.currentThread().interrupt();
					}else{
						Thread.currentThread().interrupt();
					}
				}
			};
			// start handler
			mHandler.post(runnable);
			return true;
		} else if (id == R.id.share) {
			saveBitmap(takeScreenshot());
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM,
					Uri.fromFile(imagePath.getAbsoluteFile()));
			shareIntent.setType("image/png");
			shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
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
				GoogleFitService.TYPE_SET_HEIGHT_AND_WEIGHT);
		startService(service);
	}

	public Bitmap takeScreenshot() {
		View rootView = findViewById(R.id.step_run_chart_progess);// .getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}

	public void saveBitmap(Bitmap bitmap) {
		imagePath = new File(Environment.getExternalStorageDirectory()
				+ "/"
				+ String.valueOf(Constants.getInstance().listDataStepDTO.get(
						Constants.getInstance().listDataStepDTO.size() - 1)
						.getStepID()) + ".png");
		Log.e("imagePath", imagePath.getAbsolutePath());
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("GREC", e.getMessage(), e);
		}
	}
}