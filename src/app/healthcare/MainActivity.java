package app.healthcare;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import zulu.app.libraries.ldrawer.ActionBarDrawerToggle;
import zulu.app.libraries.ldrawer.DrawerArrowDrawable;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import app.healthcare.bmi.RatioBMIFragment;
import app.healthcare.call.ListDoctor;
import app.healthcare.dataobject.DoctorDTO;
import app.healthcare.dataobject.HeartRateDTO;
import app.healthcare.dataobject.RatioBMIDTO;
import app.healthcare.dataobject.RatioWHRDTO;
import app.healthcare.dataobject.StepRunDTO;
import app.healthcare.heartrate.HeartRateFragment;
import app.healthcare.readnews.activity.ReadNewsActivity;
import app.healthcare.slidingmenu.adapter.NavDrawerListAdapter;
import app.healthcare.slidingmenu.model.NavDrawerItem;
import app.healthcare.symptom.activity.GuideMenu;
import app.healthcare.whr.RatioWHRFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends Activity {
	public final static String TAG = "GoogleFitService";
	private ConnectionResult mFitResultResolution;
	private String AUTH_PENDING = "auth_state_pending";
	private boolean authInProgress = false;
	private int REQUEST_OAUTH = 1431;
	private boolean doubleBackToExitPressedOnce = false;
	private boolean inHomePage = true;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	final Handler mHandler = new Handler();
	private ProgressDialog dialog;
	static boolean hasAcount = false;
	public static boolean hasLogIn = false;
	public static boolean getBMIFinish = false;
	public static boolean getWHRFinish = false;
	public static boolean getHRFinish = false;
	public static boolean getStepFinish = false;
	public static boolean getDoctorFinish = false;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	// SharedPreferences checkCreateDatabase;

	public static final int FRAG_HOME = 0;
	public static final int FRAG_HEART_RATE = 1;
	public static final int FRAG_WHR = 2;
	public static final int FRAG_BMI = 3;
	public static final int FRAG_STEPRUN = 4;
	public static final int FRAG_NEWS = 5;
	public static final int FRAG_INFO = 6;
	public static final int FRAG_LIST_DOCTOR = 7;

	// Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Constants.getInstance().isStart = true;
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
				.getResourceId(8, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons
				.getResourceId(9, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.app_name, R.string.app_name) {

			/**
			 * Called when a drawer has settled in a completely closed state.
			 */

			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
			}

			/** Called when a drawer has settled in a completely open state. */

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (!isNetworkAvaiable()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Không có kết nối");
			builder.setMessage("Vui lòng kiểm tra kết nối mạng!");
			builder.setPositiveButton("Finish",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// finish();

						}
					});
			builder.show();
		} else {

			if (savedInstanceState != null) {
				authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
			}
			LocalBroadcastManager.getInstance(this).registerReceiver(
					mFitStatusReceiver,
					new IntentFilter(GoogleFitService.FIT_NOTIFY_INTENT));
			LocalBroadcastManager.getInstance(this).registerReceiver(
					mFitDataReceiver,
					new IntentFilter(GoogleFitService.HISTORY_INTENT));
			requestFitConnection();
			if (GoogleFitService.isConnected) {
				getDataToDay();
				dialog = ProgressDialog.show(MainActivity.this, "",
						"Vui lòng chờ...");
				// getDataToDay();
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						if (!GoogleFitService.getDataDayFinish) {
							if (!dialog.isShowing()) {
								dialog = ProgressDialog.show(MainActivity.this,
										"", "Vui lòng chờ...");
							}
							mHandler.postDelayed(this, 1000);
						} else {
							// if (savedInstanceState == null) {
							// on first time display view for first nav item
							dialog.cancel();
							// displayView(FRAG_HOME);
							// }
							Thread.currentThread().interrupt();
						}
					}
				};
				// start handler
				mHandler.post(runnable);
			}

			try {
				Parse.enableLocalDatastore(this);
				ParseObject.registerSubclass(HeartRateDTO.class);
				ParseObject.registerSubclass(RatioBMIDTO.class);
				ParseObject.registerSubclass(RatioWHRDTO.class);
				ParseObject.registerSubclass(StepRunDTO.class);
				ParseObject.registerSubclass(DoctorDTO.class);
				Parse.initialize(MainActivity.this,
						"ZGXqZjd6vKlpdEnDDODoBTWBuzt25xbSUcdEBiVt",
						"NKG4pQrCIFXDsVKAsLSpNZaWxcR7vYbVUbbRLyZ5");
				ParseAnalytics.trackAppOpenedInBackground(getIntent());
				ParseACL defaultACL = new ParseACL();
				ParseACL.setDefaultACL(defaultACL, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			displayView(FRAG_HOME);
		}

	}

	private void getDataToDay() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_GET_DATA_TO_DAY);

		startService(service);
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
					mFitResultResolution.startResolutionForResult(
							MainActivity.this, REQUEST_OAUTH);

				} catch (IntentSender.SendIntentException e) {
					Log.e(TAG,
							"Activity Thread Google Fit Exception while starting resolution activity",
							e);
				}
			}
		}
	}

	public static void getData() {
		// lay thong tin bmi
		if (Constants.getInstance().listDataBMI.size() > 0) {
			Constants.getInstance().listDataBMI.retainAll(Constants
					.getInstance().listDataBMI);
		}
		ParseQuery<RatioBMIDTO> queryBMI = new ParseQuery<RatioBMIDTO>(
				"RatioBMIDTO");
		queryBMI.findInBackground((new FindCallback<RatioBMIDTO>() {

			@Override
			public void done(List<RatioBMIDTO> datas, ParseException arg1) {
				Constants.getInstance().listDataBMI = datas;
				getBMIFinish = true;
			}

		}));
		// lay thong tin whr
		if (Constants.getInstance().listDataWHR.size() > 0) {
			Constants.getInstance().listDataWHR.retainAll(Constants
					.getInstance().listDataWHR);
		}
		ParseQuery<RatioWHRDTO> queryWHR = new ParseQuery<RatioWHRDTO>(
				"RatioWHRDTO");
		queryWHR.findInBackground((new FindCallback<RatioWHRDTO>() {

			@Override
			public void done(List<RatioWHRDTO> datas, ParseException arg1) {
				Constants.getInstance().listDataWHR = datas;
				getWHRFinish = true;
			}

		}));
		// lay thong tin heartrate
		if (Constants.getInstance().listDataHR.size() > 0) {
			Constants.getInstance().listDataHR.retainAll(Constants
					.getInstance().listDataHR);
		}
		ParseQuery<HeartRateDTO> queryHR = new ParseQuery<HeartRateDTO>(
				"HeartRateDTO");
		queryHR.findInBackground((new FindCallback<HeartRateDTO>() {

			@Override
			public void done(List<HeartRateDTO> datas, ParseException arg1) {
				Constants.getInstance().listDataHR = datas;
				getHRFinish = true;
			}

		}));
		// LAY THONG TIN STEP
		ParseQuery<StepRunDTO> queryStep = new ParseQuery<StepRunDTO>(
				"StepRunDTO");
		queryStep.findInBackground((new FindCallback<StepRunDTO>() {

			@Override
			public void done(List<StepRunDTO> datas, ParseException arg1) {
				Constants.getInstance().listDataStepDTO = datas;
				getStepFinish = true;
			}

		}));
		// LAY THONG TIN BAC SI
		ParseQuery<DoctorDTO> queryDoctor = new ParseQuery<DoctorDTO>(
				"DoctorDTO");
		queryDoctor.findInBackground((new FindCallback<DoctorDTO>() {

			@Override
			public void done(List<DoctorDTO> datas, ParseException arg1) {
				Constants.getInstance().listDoctorDTO = datas;
				getDoctorFinish = true;
			}

		}));
	}

	public static void logIn(final Context c) {
		try {
			if (!hasAcount) {
				ParseUser user = new ParseUser();
				user.setUsername(Constants.getInstance().email);
				user.setPassword(Constants.getInstance().email);

				// Call the Parse signup method
				user.signUpInBackground(new SignUpCallback() {
					@Override
					public void done(ParseException e) {
						if (e != null) {
							ParseUser.logInInBackground(
									Constants.getInstance().email,
									Constants.getInstance().email,
									new LogInCallback() {
										@Override
										public void done(ParseUser user,
												ParseException e) {
											if (e != null) {
											} else {
												try {
													getData();
													hasLogIn = true;

												} catch (NullPointerException ex) {
													AlertDialog.Builder builder = new AlertDialog.Builder(
															c);
													builder.setTitle("Không có kết nối");
													builder.setMessage("Kết nối không ổn định!");
													builder.setPositiveButton(
															"Finish",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	((Activity) c)
																			.finish();
																}
															});
													builder.show();
												}

											}

										}
									});
						}
					}
				});
			}
		} catch (IllegalArgumentException ex) {
			Log.e("login parse", "loi tham so " + ex.toString());
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		fitSaveInstanceState(outState);
	}

	private void fitSaveInstanceState(Bundle outState) {
		outState.putBoolean(AUTH_PENDING, authInProgress);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	public void displayView(int position) {
		if (Constants.getInstance().email.length() == 0) {
			Pattern emailPattern = Patterns.EMAIL_ADDRESS;
			Account[] accounts = AccountManager.get(this).getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					Constants.getInstance().email = account.name;
					break;
				}
			}
		}
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case FRAG_HOME:
			logIn(MainActivity.this);
			fragment = new StartAppScreen();
			break;
		case FRAG_HEART_RATE:
			if (GoogleFitService.isConnected) {
				fragment = new HeartRateFragment();
			} else {
				handleConnect();
			}
			if (ParseUser.getCurrentUser() == null) {
				hasAcount = false;
				logIn(MainActivity.this);
			}
			break;
		case FRAG_WHR:
			if (GoogleFitService.isConnected) {
				fragment = new RatioWHRFragment();
			} else {
				handleConnect();
			}
			if (ParseUser.getCurrentUser() == null) {
				hasAcount = false;
				logIn(MainActivity.this);
			}
			break;
		case FRAG_BMI:
			if (GoogleFitService.isConnected) {
				fragment = new RatioBMIFragment();
			} else {
				handleConnect();
			}
			if (ParseUser.getCurrentUser() == null) {
				hasAcount = false;
				logIn(MainActivity.this);
			}
			break;
		case FRAG_STEPRUN:
			if (GoogleFitService.isConnected) {
				startActivity(new Intent(this, StepRun.class));
			} else {
				handleConnect();
			}
			if (ParseUser.getCurrentUser() == null) {
				hasAcount = false;
				logIn(MainActivity.this);
			}
			break;
		case FRAG_NEWS:
			startActivity(new Intent(this, ReadNewsActivity.class));
			break;
		case FRAG_INFO:
			startActivity(new Intent(this, About.class));
			break;
		case FRAG_LIST_DOCTOR:
			startActivity(new Intent(this, ListDoctor.class));
			break;
		case 8:
			startActivity(new Intent(this, GuideMenu.class));
			break;
		case 9:
			this.finish();
			break;
		default:
			break;
		}
		mDrawerLayout.closeDrawer(mDrawerList);
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			if (!fragment.equals(StartAppScreen.class)) {
				inHomePage = false;
			} else {
				inHomePage = true;
			}
		} else {
			mDrawerLayout.closeDrawer(mDrawerList);
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce && inHomePage) {
			super.onBackPressed();
			return;
		} else if (!doubleBackToExitPressedOnce && inHomePage) {
			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, "Please click BACK again to exit",
					Toast.LENGTH_SHORT).show();
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					doubleBackToExitPressedOnce = false;
				}
			}, 2000);
		} else if (!inHomePage) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, new StartAppScreen())
					.commit();
			mDrawerList.setItemChecked(0, true);
			mDrawerList.setSelection(0);
			setTitle(navMenuTitles[0]);
			mDrawerLayout.closeDrawer(mDrawerList);
			inHomePage = true;
		}
	}

	/**
	 * Connect to google fit server
	 * 
	 * @return true if connect success, false if failed
	 */
	private boolean handleConnect() {
		try {
			authInProgress = true;
			mFitResultResolution.startResolutionForResult(this, REQUEST_OAUTH);
			return true;
		} catch (IntentSender.SendIntentException e) {
			Log.e("GoogleFitService-main",
					"Activity Thread Google Fit Exception while starting resolution activity",
					e);
		} catch (NullPointerException ex) {
			Log.e("thai", ex.getMessage(), ex);
		}
		return false;
	}

	/**
	 * send request connect
	 */
	private void requestFitConnection() {
		Intent service = new Intent(this, GoogleFitService.class);
		service.putExtra(GoogleFitService.SERVICE_REQUEST_TYPE,
				GoogleFitService.TYPE_REQUEST_CONNECTION);
		startService(service);
	}

	private boolean isNetworkAvaiable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		Log.d("Network 3", (info != null) + "  " + info);
		return (info != null);
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

				final int totalSteps = intent.getIntExtra(
						GoogleFitService.HISTORY_EXTRA_STEPS_TODAY, 0);
				Toast.makeText(MainActivity.this, "Total Steps: " + totalSteps,
						Toast.LENGTH_SHORT).show();

			}
		}
	};

	private void fitHandleFailedConnection(ConnectionResult result) {
		Log.i(TAG, "Activity Thread Google Fit Connection failed. Cause: "
				+ result.toString());
		if (!result.hasResolution()) {
			// Show the localized error dialog
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
					MainActivity.this, 0).show();
			return;
		}

		// The failure has a resolution. Resolve it.
		// Called typically when the app is not yet authorized, and an
		// authorization dialog is displayed to the user.
		if (!authInProgress) {
			if (result.getErrorCode() == FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS) {
				try {
					Log.d(TAG,
							"Google Fit connection failed with OAuth failure.  Trying to ask for consent (again)");
					result.startResolutionForResult(MainActivity.this,
							REQUEST_OAUTH);
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

}
