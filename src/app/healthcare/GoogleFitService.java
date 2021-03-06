package app.healthcare;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.fitness.result.DataTypeResult;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

@SuppressLint("SimpleDateFormat")
public class GoogleFitService extends IntentService implements
		ConnectionCallbacks, OnConnectionFailedListener, ConfigApi,
		LocationListener {
	public static GoogleApiClient mClient;
	private boolean mTryingToConnect = false;
	public static boolean isConnected = false;
	public static boolean getDataDayFinish = false;
	public static boolean getDataYearFinish = false;
	//public static boolean setWHDataYearFinish = false;
	private OnDataPointListener mListener;
	public static long totalStepsRecord = 0;
	public static long totalStepsGet = 0;
	public static long totalStepsGetInYear = 0;
	public static float totalCalosGet = 0;
	public static float totalCalosInYear = 0;
	public static float totalCalosRecord = 0;
	public static int dataSizeSteps = 0;
	public static int dataSizeCalories = 0;
	public static int dataSizeDistance = 0;
	public static Integer targetToset = 0;
	public static Integer workout = 0;
	public static float distance = 0;
	private LocationRequest mLocationRequest;
	public static double currentLatitude = 0;
	public static double currentLongitude = 0;

	public static final int TYPE_GET_DATA_TO_DAY = 1;
	public static final int TYPE_GET_DATA_TO_WEEK = 2;
	public static final int TYPE_GET_DATA_TO_MONTH = 3;
	public static final int TYPE_GET_DATA_TO_YEAR = 4;
	public static final int TYPE_REQUEST_CONNECTION = 5;
	public static final int TYPE_SET_HEIGHT_AND_WEIGHT = 6;
	public static final int TYPE_SET_DISTANCE = 7;
	public static final int TYPE_SET_ROUND_BUTT = 8;
	public static final int TYPE_SET_TARGET = 9;
	public static final int TYPE_GET_TARGET = 10;

	public static final String TAG = "GoogleFitService";
	public static final String TAG_SENSOR = "BasicSensorsApi";
	public static final String TAG_SESSION = "BasicSessions";
	public static final String TAG_RECORDING = "BasicRecordingApi";
	public static final String TAG_HISTORY = "BasicHistoryApi";
	public static final String SERVICE_REQUEST_TYPE = "requestType";
	public static final String HISTORY_INTENT = "fitHistory";
	public static final String HISTORY_EXTRA_STEPS_TODAY = "stepsToday";
	public static final String HISTORY_EXTRA_CALOS_TODAY = "calosToday";
	public static final String HISTORY_EXTRA_STEPS_TOWEEK = "stepsToweek";
	public static final String HISTORY_EXTRA_STEPS_TOMONTH = "stepsTomonth";
	public static final String FIT_NOTIFY_INTENT = "fitStatusUpdateIntent";
	public static final String FIT_EXTRA_CONNECTION_MESSAGE = "fitFirstConnection";
	public static final String FIT_EXTRA_NOTIFY_FAILED_STATUS_CODE = "fitExtraFailedStatusCode";
	public static final String FIT_EXTRA_NOTIFY_FAILED_INTENT = "fitExtraFailedIntent";
	public static final String SAMPLE_SESSION_NAME = "Afternoon run";

	private static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";

	@Override
	public void onDestroy() {
		Log.d(TAG, "GoogleFitService destroyed");
		if (mClient.isConnected()) {
			Log.d(TAG, "Disconecting Google Fit.");
			mClient.disconnect();
		}
		super.onDestroy();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		buildFitnessClient();
		Log.d(TAG, "GoogleFitService created");
	}

	public GoogleFitService() {
		super("GoogleFitService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int type = intent.getIntExtra(SERVICE_REQUEST_TYPE, -1);
		if (!mClient.isConnected()) {
			mTryingToConnect = true;
			mClient.connect();
			while (mTryingToConnect) {
				try {
					Thread.sleep(1000, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (mClient.isConnected()) {
			isConnected = true;
			Calendar cal;
			long endTime;
			long startTime;
			Date now = new Date();
			@SuppressWarnings("deprecation")
			int time = now.getHours();
			
			DataReadRequest readRequest;
			DataReadResult dataReadResult;
			switch (type) {
			case TYPE_GET_DATA_TO_DAY:
				getDataDayFinish = false;
				cal = Calendar.getInstance();
				cal.setTime(now);
				endTime = cal.getTimeInMillis();
				cal.add(Calendar.HOUR, -time);
				startTime = cal.getTimeInMillis();
				DailyTotalResult rs = Fitness.HistoryApi.readDailyTotal(
						mClient, DataType.TYPE_STEP_COUNT_DELTA).await(1,
						TimeUnit.MINUTES);
				totalStepsGet = dumpDataSetHistorySteps(rs.getTotal());
				Constants.getInstance().setStepRuns(totalStepsGet);
				DailyTotalResult rscl = Fitness.HistoryApi.readDailyTotal(
						mClient, DataType.TYPE_CALORIES_EXPENDED).await(1,
						TimeUnit.MINUTES);
				totalCalosGet = dumpDataSetHistoryCalos(rscl.getTotal());
				Constants.getInstance().setCalos(totalCalosGet);
				DailyTotalResult rsd = Fitness.HistoryApi.readDailyTotal(
						mClient, DataType.TYPE_DISTANCE_DELTA).await(1,
						TimeUnit.MINUTES);
				distance = dumpDataSetHistoryDistance(rsd.getTotal());
				Constants.getInstance().setDistance(distance);
				getDataDayFinish = true;
				break;
			case TYPE_SET_HEIGHT_AND_WEIGHT:
				String sWeight = StepRun.weight_weight.split("_")[1];
				String sHeight = StepRun.weight_weight.split("_")[0];
				float weight = Float.parseFloat(sWeight);
				float height = Float.parseFloat(sHeight);
				setUserHeight(height);
				setUserWeight(weight);
				break;
			case TYPE_GET_DATA_TO_YEAR:
				getDataYearFinish = false;
				cal = Calendar.getInstance();
				cal.setTime(now);
				endTime = cal.getTimeInMillis();
				int date = cal.get(Calendar.DAY_OF_YEAR);
				// reset lai so ngay co du lieu
				dataSizeSteps = 0;
				Constants.getInstance().listDataStep.retainAll(Constants
						.getInstance().listDataStep);
				// xac dinh so ngay co hoat dong de tinh so buoc di trung binh
				for (int i = 0; i < date; i++) {
					// xac dinh thoi gian bat dau lay data
					cal.add(Calendar.DATE, -1);
					startTime = cal.getTimeInMillis();
					// tien hanh lay data
					readRequest = queryFitnessDataStep(startTime, endTime);
					dataReadResult = Fitness.HistoryApi.readData(mClient,
							readRequest).await(1, TimeUnit.MINUTES);
					long tempSteps = printDataStep(dataReadResult);
					if (tempSteps != 0) {
						dataSizeSteps += 1;
						totalStepsGetInYear += tempSteps;
						HistoryStepObject o = new HistoryStepObject();
						o.setStep(tempSteps);
						String curTime = String.valueOf(cal
								.get(Calendar.DAY_OF_MONTH))
								+ "/"
								+ String.valueOf(cal.get(Calendar.MONTH) + 1);
						o.setTime(curTime);
						Constants.getInstance().listDataStep.add(o);
					}
					// tien hanh lay data so calo
					readRequest = queryFitnessDataCaloFree(startTime, endTime);
					dataReadResult = Fitness.HistoryApi.readData(mClient,
							readRequest).await(1, TimeUnit.MINUTES);
					float tempCalories = printDataCaloFree(dataReadResult);
					if (tempCalories != 0) {
						dataSizeCalories += 1;
						totalCalosInYear += tempCalories;
					}
					if (dataSizeSteps >= 50) {
						break;
					}
					endTime = startTime;
				}
				getDataYearFinish = true;
				break;
			default:
				break;
			}
		} else {
			Log.w(TAG, "Fit wasn't able to connect, so the request failed.");
		}
	}

	private void setUserHeight(float heightCentimiters) {
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.DATE, -1);
		long startTime = cal.getTimeInMillis();
		// Create a data source
		DataSource dataSourceHeight = new DataSource.Builder()
				.setAppPackageName(this).setDataType(DataType.TYPE_HEIGHT)
				.setName(TAG_HISTORY + " - TYPE_HEIGHT")
				.setType(DataSource.TYPE_RAW).build();
		DataSet dataSet = DataSet.create(dataSourceHeight);
		DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(
				startTime, endTime, TimeUnit.MILLISECONDS);
		dataPoint.getValue(Field.FIELD_HEIGHT).setFloat(heightCentimiters);
		dataSet.add(dataPoint);
		com.google.android.gms.common.api.Status weightInsertStatus =
		        Fitness.HistoryApi.insertData(mClient, dataSet )
		                .await(1, TimeUnit.MINUTES);
		// Before querying the data, check to see if the insertion succeeded.
		if (!weightInsertStatus.isSuccess()) {
		    Log.i(TAG, "There was a problem inserting the dataset.");
		}

		// At this point, the data has been inserted and can be read.
		Log.i(TAG, "Data insert was successful!");
	}

	private void setUserWeight(float weight) {
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.DATE, -1);
		long startTime = cal.getTimeInMillis();
		// Create a data source
		DataSource dataSourceHeight = new DataSource.Builder()
				.setAppPackageName(this).setDataType(DataType.TYPE_WEIGHT)
				.setName(TAG_HISTORY + " - TYPE_WEIGHT")
				.setType(DataSource.TYPE_RAW).build();
		// Create a data set
		DataSet dataSet = DataSet.create(dataSourceHeight);
		DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(
				startTime, endTime, TimeUnit.MILLISECONDS);
		dataPoint.getValue(Field.FIELD_WEIGHT).setFloat(weight);
		dataSet.add(dataPoint);
		com.google.android.gms.common.api.Status weightInsertStatus =
		        Fitness.HistoryApi.insertData(mClient, dataSet )
		                .await(1, TimeUnit.MINUTES);
		// Before querying the data, check to see if the insertion succeeded.
		if (!weightInsertStatus.isSuccess()) {
		    Log.i(TAG, "There was a problem inserting the dataset.");
		}

		// At this point, the data has been inserted and can be read.
		Log.i(TAG, "Data insert was successful!");
	}

	/*-----------SensorsApi------------------*/
	public void findFitnessDataSourcesSensors() {
		// [START find_data_sources]
		Fitness.SensorsApi.findDataSources(
				mClient,
				new DataSourcesRequest.Builder()
				// At least one datatype must be specified.
						.setDataTypes(DataType.TYPE_LOCATION_SAMPLE,
								DataType.TYPE_STEP_COUNT_DELTA,
								DataType.TYPE_DISTANCE_DELTA,
								DataType.TYPE_LOCATION_SAMPLE,
								DataType.TYPE_CALORIES_EXPENDED,
								DataType.TYPE_CALORIES_CONSUMED,
								DataType.TYPE_WORKOUT_EXERCISE)
						// Can specify whether data type is raw or derived.
						.setDataSourceTypes(DataSource.TYPE_RAW,
								DataSource.TYPE_DERIVED).build())
				.setResultCallback(new ResultCallback<DataSourcesResult>() {
					@Override
					public void onResult(DataSourcesResult dataSourcesResult) {
						for (DataSource dataSource : dataSourcesResult
								.getDataSources()) {
							if (dataSource.getDataType().equals(
									DataType.TYPE_STEP_COUNT_DELTA)
									&& mListener == null) {
								registerFitnessDataListener(dataSource,
										DataType.TYPE_STEP_COUNT_DELTA);
							}
						}
					}
				});
		// [END find_data_sources]
	}

	/**
	 * Register a listener with the Sensors API for the provided
	 * {@link DataSource} and {@link DataType} combo.
	 */
	public void registerFitnessDataListener(DataSource dataSource,
			DataType dataType) {
		// [START register_data_listener]
		mListener = new OnDataPointListener() {
			@Override
			public void onDataPoint(DataPoint dataPoint) {
				for (Field field : dataPoint.getDataType().getFields()) {
					Value val = dataPoint.getValue(field);
					if (field.getName().equals("steps")) {
						totalStepsRecord += val.asInt();
					}
					if (totalStepsGet >= 1){
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
					}

				}
			}
		};
		Fitness.SensorsApi.add(
				mClient,
				new SensorRequest.Builder().setDataSource(dataSource)
						.setDataType(dataType)
						.setSamplingRate(10, TimeUnit.SECONDS).build(),
				mListener).setResultCallback(new ResultCallback<Status>() {
			@Override
			public void onResult(Status status) {
				if (status.isSuccess()) {
					Log.i(TAG_SENSOR, "Listener registered!");
				} else {
					Log.i(TAG_SENSOR, "Listener not registered.");
				}
				//Log.i(TAG, "bugggggggggggggggggggggggggg" + String.valueOf(totalStepsGet));
				totalStepsGet = 5;
				if (totalStepsGet >= 1){
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
				}
			}
		});
		// [END register_data_listener]
	}

	/**
	 * Unregister the listener with the Sensors API.
	 */
	public void unregisterFitnessDataListener() {
		if (mListener == null) {
			return;
		}
		// [START unregister_data_listener]
		Fitness.SensorsApi.remove(mClient, mListener).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							Log.i(TAG_SENSOR, "Listener was removed!");
						} else {
							Log.i(TAG_SENSOR, "Listener was not removed.");
						}
					}
				});
		// [END unregister_data_listener]
	}

	/*-----------RecordingApi------------------*/
	public void subscribe() {
		Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_STEP_COUNT_DELTA)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_LOCATION_SAMPLE)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_LOCATION_TRACK)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_DISTANCE_DELTA)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		Fitness.RecordingApi
				.subscribe(mClient, DataType.TYPE_CALORIES_EXPENDED)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		Fitness.RecordingApi
				.subscribe(mClient, DataType.TYPE_CALORIES_CONSUMED)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_WORKOUT_EXERCISE)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
								Log.i(TAG_RECORDING,
										"Existing subscription for activity detected.");
							} else {
								Log.i(TAG_RECORDING, "Successfully subscribed!");
							}
						} else {
							Log.i(TAG_RECORDING,
									"There was a problem subscribing.");
						}
					}
				});
		// [END subscribe_to_datatype]
	}

	public long dumpSubscriptionsList() {
		long stepRecord = 0;
		// [START list_current_subscriptions]
		Fitness.RecordingApi.listSubscriptions(mClient,
				DataType.TYPE_ACTIVITY_SAMPLE).setResultCallback(
				new ResultCallback<ListSubscriptionsResult>() {
					@Override
					public void onResult(
							ListSubscriptionsResult listSubscriptionsResult) {
						for (Subscription sc : listSubscriptionsResult
								.getSubscriptions()) {
							DataType dt = sc.getDataType();
							Log.i(TAG_RECORDING,
									"Active subscription for data type: "
											+ dt.getName());
						}
					}
				});
		return stepRecord;
		// [END list_current_subscriptions]
	}

	public void cancelSubscription() {
		final String dataTypeStr = DataType.TYPE_ACTIVITY_SAMPLE.toString();
		Log.i(TAG_RECORDING, "Unsubscribing from data type: " + dataTypeStr);
		Fitness.RecordingApi
				.unsubscribe(mClient, DataType.TYPE_ACTIVITY_SAMPLE)
				.setResultCallback(new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							Log.i(TAG_RECORDING,
									"Successfully unsubscribed for data type: "
											+ dataTypeStr);
						} else {
							// Subscription not removed
							Log.i(TAG_RECORDING,
									"Failed to unsubscribe for data type: "
											+ dataTypeStr);
						}
					}
				});
		// [END unsubscribe_from_datatype]
	}

	/*-----------SessionsApi------------------*/
	public class InsertAndVerifySessionTask extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			// First, create a new session and an insertion request.
			SessionInsertRequest insertRequest = insertFitnessSession();
			// [START insert_session]
			Log.i(TAG_SESSION, "Inserting the session in the History API");
			com.google.android.gms.common.api.Status insertStatus = Fitness.SessionsApi
					.insertSession(mClient, insertRequest).await(1,
							TimeUnit.MINUTES);
			if (!insertStatus.isSuccess()) {
				Log.i(TAG_SESSION,
						"There was a problem inserting the session: "
								+ insertStatus.getStatusMessage());
				return null;
			}
			// At this point, the session has been inserted and can be read.
			Log.i(TAG_SESSION, "Session insert was successful!");
			// [END insert_session]
			// Begin by creating the query.
			SessionReadRequest readRequest = readFitnessSession();
			// [START read_session]
			// Invoke the Sessions API to fetch the session with the query and
			// wait for the result
			// of the read request.
			SessionReadResult sessionReadResult = Fitness.SessionsApi
					.readSession(mClient, readRequest).await(1,
							TimeUnit.MINUTES);
			// Get a list of the sessions that match the criteria to check the
			// result.
			Log.i(TAG_SESSION,
					"Session read was successful. Number of returned sessions is: "
							+ sessionReadResult.getSessions().size());
			for (Session session : sessionReadResult.getSessions()) {
				// Process the session
				dumpSession(session);
				// Process the data sets for this session
				List<DataSet> dataSets = sessionReadResult.getDataSet(session);
				for (DataSet dataSet : dataSets) {
					dumpDataSetSessions(dataSet);
				}
			}
			// [END read_session]

			return null;
		}
	}

	public SessionInsertRequest insertFitnessSession() {
		Log.i(TAG, "Creating a new session for an afternoon run");
		// Setting start and end times for our run.
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		// Set a range of the run, using a start time of 30 minutes before this
		// moment,
		// with a 10-minute walk in the middle.
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.MINUTE, -10);
		long endWalkTime = cal.getTimeInMillis();
		cal.add(Calendar.MINUTE, -10);
		long startWalkTime = cal.getTimeInMillis();
		cal.add(Calendar.MINUTE, -10);
		long startTime = cal.getTimeInMillis();
		// Create a data source
		DataSource stepDataSource = new DataSource.Builder()
				.setAppPackageName(this.getPackageName())
				.setDataType(DataType.TYPE_STEP_COUNT_DELTA)
				.setName(SAMPLE_SESSION_NAME + "- step")
				.setType(DataSource.TYPE_RAW).build();

		float runSpeedMps = 10;
		float walkSpeedMps = 3;
		// Create a data set of the run speeds to include in the session.
		DataSet stepDataSet = DataSet.create(stepDataSource);

		DataPoint firstRunSpeed = stepDataSet.createDataPoint()
				.setTimeInterval(startTime, startWalkTime,
						TimeUnit.MILLISECONDS);
		firstRunSpeed.getValue(Field.FIELD_SPEED).setFloat(runSpeedMps);
		stepDataSet.add(firstRunSpeed);

		DataPoint walkSpeed = stepDataSet.createDataPoint().setTimeInterval(
				startWalkTime, endWalkTime, TimeUnit.MILLISECONDS);
		walkSpeed.getValue(Field.FIELD_SPEED).setFloat(walkSpeedMps);
		stepDataSet.add(walkSpeed);

		DataPoint secondRunSpeed = stepDataSet.createDataPoint()
				.setTimeInterval(endWalkTime, endTime, TimeUnit.MILLISECONDS);
		secondRunSpeed.getValue(Field.FIELD_SPEED).setFloat(runSpeedMps);
		stepDataSet.add(secondRunSpeed);

		// [START build_insert_session_request_with_activity_segments]
		// Create a second DataSet of ActivitySegments to indicate the runner
		// took a 10-minute walk
		// in the middle of the run.
		DataSource activitySegmentDataSource = new DataSource.Builder()
				.setAppPackageName(this.getPackageName())
				.setDataType(DataType.TYPE_ACTIVITY_SEGMENT)
				.setName(SAMPLE_SESSION_NAME + "-activity segments")
				.setType(DataSource.TYPE_RAW).build();
		DataSet activitySegments = DataSet.create(activitySegmentDataSource);

		DataPoint firstRunningDp = activitySegments.createDataPoint()
				.setTimeInterval(startTime, startWalkTime,
						TimeUnit.MILLISECONDS);
		firstRunningDp.getValue(Field.FIELD_ACTIVITY).setActivity(
				FitnessActivities.RUNNING);
		activitySegments.add(firstRunningDp);

		DataPoint walkingDp = activitySegments.createDataPoint()
				.setTimeInterval(startWalkTime, endWalkTime,
						TimeUnit.MILLISECONDS);
		walkingDp.getValue(Field.FIELD_ACTIVITY).setActivity(
				FitnessActivities.WALKING);
		activitySegments.add(walkingDp);

		DataPoint secondRunningDp = activitySegments.createDataPoint()
				.setTimeInterval(endWalkTime, endTime, TimeUnit.MILLISECONDS);
		secondRunningDp.getValue(Field.FIELD_ACTIVITY).setActivity(
				FitnessActivities.RUNNING);
		activitySegments.add(secondRunningDp);

		// [START build_insert_session_request]
		// Create a session with metadata about the activity.
		Session session = new Session.Builder().setName(SAMPLE_SESSION_NAME)
				.setDescription("Long run around Shoreline Park")
				.setIdentifier("UniqueIdentifierHere")
				.setActivity(FitnessActivities.RUNNING)
				.setStartTime(startTime, TimeUnit.MILLISECONDS)
				.setEndTime(endTime, TimeUnit.MILLISECONDS).build();
		// Build a session insert request
		SessionInsertRequest insertRequest = new SessionInsertRequest.Builder()
				.setSession(session).addDataSet(stepDataSet)
				.addDataSet(activitySegments).build();
		// [END build_insert_session_request]
		// [END build_insert_session_request_with_activity_segments]
		return insertRequest;
	}

	/**
	 * Return a {@link SessionReadRequest} for all speed data in the past week.
	 */
	public SessionReadRequest readFitnessSession() {
		Log.i(TAG_SESSION, "Reading History API results for session: "
				+ SAMPLE_SESSION_NAME);
		// [START build_read_session_request]
		// Set a start and end time for our query, using a start time of 1 week
		// before this moment.
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		long startTime = cal.getTimeInMillis();

		// Build a session read request
		SessionReadRequest readRequest = new SessionReadRequest.Builder()
				.setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
				.read(DataType.TYPE_SPEED).setSessionName(SAMPLE_SESSION_NAME)
				.build();
		// [END build_read_session_request]
		return readRequest;
	}

	public void dumpDataSetSessions(DataSet dataSet) {
		for (DataPoint dp : dataSet.getDataPoints()) {
			for (Field field : dp.getDataType().getFields()) {
				Log.i(TAG_SESSION, "\tField: " + field.getName() + " Value: "
						+ dp.getValue(field));
			}
		}
	}

	public void dumpSession(Session session) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Log.i(TAG_SESSION,
				"Data returned for Session: "
						+ session.getName()
						+ "\n\tDescription: "
						+ session.getDescription()
						+ "\n\tStart: "
						+ dateFormat.format(session
								.getStartTime(TimeUnit.MILLISECONDS))
						+ "\n\tEnd: "
						+ dateFormat.format(session
								.getEndTime(TimeUnit.MILLISECONDS)));
	}

	public void deleteSession() {
		Log.i(TAG_SESSION, "Deleting today's session data for speed");
		// Set a start and end time for our data, using a start time of 1 day
		// before this moment.
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		long startTime = cal.getTimeInMillis();

		// Create a delete request object, providing a data type and a time
		// interval
		DataDeleteRequest request = new DataDeleteRequest.Builder()
				.setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
				.addDataType(DataType.TYPE_SPEED).deleteAllSessions() // Or
																		// specify
																		// a
																		// particular
																		// session
																		// here
				.build();

		// Invoke the History API with the Google API client object and the
		// delete request and
		// specify a callback that will check the result.
		Fitness.HistoryApi.deleteData(mClient, request).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							Log.i(TAG_SESSION,
									"Successfully deleted today's sessions");
						} else {
							// The deletion will fail if the requesting app
							// tries to delete data
							// that it did not insert.
							Log.i(TAG, "Failed to delete today's sessions");
						}
					}
				});
	}

	/*-----------HistoryApi------------------*/
	public class InsertAndVerifyDataTask extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			// First, create a new dataset and insertion request.
			DataSet dataSet = insertFitnessStepData();
			// [START insert_dataset]
			// Then, invoke the History API to insert the data and await the
			// result, which is
			// possible here because of the {@link AsyncTask}. Always include a
			// timeout when calling
			// await() to prevent hanging that can occur from the service being
			// shutdown because
			// of low memory or other conditions.
			Log.i(TAG_HISTORY, "Inserting the dataset in the History API");
			com.google.android.gms.common.api.Status insertStatus = Fitness.HistoryApi
					.insertData(mClient, dataSet).await(1, TimeUnit.MINUTES);
			// Before querying the data, check to see if the insertion
			// succeeded.
			if (!insertStatus.isSuccess()) {
				Log.i(TAG_HISTORY, "There was a problem inserting the dataset.");
				return null;
			}
			// At this point, the data has been inserted and can be read.
			Log.i(TAG_HISTORY, "Data insert was successful!");
			// [END insert_dataset]
			// Begin by creating the query.

			return null;
		}
	}

	/**
	 * Create and return a {@link DataSet} of step count data for the History
	 * API.
	 */
	public DataSet insertFitnessStepData() {
		Log.i(TAG_HISTORY, "Creating a new data insert request");
		// [START build_insert_data_request]
		// Set a start and end time for our data, using a start time of 1 hour
		// before this moment.
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		long startTime = cal.getTimeInMillis();

		// Create a data source
		DataSource dataSourceSteps = new DataSource.Builder()
				.setAppPackageName(this)
				.setDataType(DataType.TYPE_STEP_COUNT_DELTA)
				.setName(TAG_HISTORY + " - step count")
				.setType(DataSource.TYPE_RAW).build();

		// Create a data set
		int stepCountDelta = (int) totalStepsRecord;
		totalStepsRecord = 0;
		DataSet dataSet = DataSet.create(dataSourceSteps);
		// For each data point, specify a start time, end time, and the data
		// value -- in this case,
		// the number of new steps.
		DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(
				startTime, endTime, TimeUnit.MILLISECONDS);
		dataPoint.getValue(Field.FIELD_STEPS).setInt(stepCountDelta);
		dataSet.add(dataPoint);
		// [END build_insert_data_request]
		return dataSet;
	}

	/**
	 * Create and return a {@link DataSet} of step count data for the History
	 * API.
	 */
	public DataSet insertFitnessCaloData() {
		Log.i(TAG_HISTORY, "Creating a new data insert request");
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.HOUR_OF_DAY, -1);
		long startTime = cal.getTimeInMillis();
		// Create a data source
		DataSource dataSourceSteps = new DataSource.Builder()
				.setAppPackageName(this)
				.setDataType(DataType.TYPE_CALORIES_EXPENDED)
				.setName(TAG_HISTORY + " - step count")
				.setType(DataSource.TYPE_RAW).build();
		// Create a data set
		int stepCountDelta = (int) totalStepsRecord;
		totalStepsRecord = 0;
		DataSet dataSet = DataSet.create(dataSourceSteps);
		DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(
				startTime, endTime, TimeUnit.MILLISECONDS);
		dataPoint.getValue(Field.FIELD_STEPS).setInt(stepCountDelta);
		dataSet.add(dataPoint);
		return dataSet;
	}

	/**
	 * Return a {@link DataReadRequest} for all step count changes in the past
	 * week.
	 */
	public DataReadRequest queryFitnessDataStep(long startTime, long endTime) {
		DataReadRequest readRequest = new DataReadRequest.Builder()
				.aggregate(DataType.TYPE_STEP_COUNT_DELTA,
						DataType.AGGREGATE_STEP_COUNT_DELTA)
				.bucketByTime(1, TimeUnit.DAYS)
				.setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
				.build();
		return readRequest;
	}

	/**
	 * lay thong tin calo theo ngay hoac tuan hoac thang
	 * 
	 * @param startTime
	 *            thoi gian bat dau
	 * @param endTime
	 *            thoi gian ket thuc
	 * @return so calo tieu thu khi khong hoat dong
	 */
	public DataReadRequest queryFitnessDataCaloFree(long startTime, long endTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Log.i(TAG_HISTORY, "Range Start: " + dateFormat.format(startTime));
		Log.i(TAG_HISTORY, "Range End: " + dateFormat.format(endTime));

		DataReadRequest readRequest = new DataReadRequest.Builder()
				.aggregate(DataType.TYPE_CALORIES_EXPENDED,
						DataType.AGGREGATE_CALORIES_EXPENDED)
				.bucketByTime(1, TimeUnit.DAYS)
				.setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
				.build();
		return readRequest;
	}

	/**
	 * lay thong tin quang duong di duoc theo ngay hoac tuan hoac thang
	 * 
	 * @param startTime
	 *            thoi gian bat dau
	 * @param endTime
	 *            thoi gian ket thuc
	 * @return so calo tieu thu khi khong hoat dong
	 */
	public DataReadRequest queryFitnessDataDistance(long startTime, long endTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Log.i(TAG_HISTORY, "Range Start: " + dateFormat.format(startTime));
		Log.i(TAG_HISTORY, "Range End: " + dateFormat.format(endTime));

		DataReadRequest readRequest = new DataReadRequest.Builder()
				.aggregate(DataType.TYPE_DISTANCE_DELTA,
						DataType.AGGREGATE_DISTANCE_DELTA)
				.bucketByTime(1, TimeUnit.DAYS)
				.setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
				.build();
		return readRequest;
	}

	public long printDataStep(DataReadResult dataReadResult) {
		long steps = 0;
		if (dataReadResult.getBuckets().size() > 0) {
			Log.i(TAG_HISTORY, "Number of returned buckets of DataSets is: "
					+ dataReadResult.getBuckets().size());
			for (Bucket bucket : dataReadResult.getBuckets()) {
				List<DataSet> dataSets = bucket.getDataSets();
				for (DataSet dataSet : dataSets) {
					long tempSteps = dumpDataSetHistorySteps(dataSet);
					steps += tempSteps;
				}
			}
		} else if (dataReadResult.getDataSets().size() > 0) {
			Log.i(TAG_HISTORY, "Number of returned DataSets is: "
					+ dataReadResult.getDataSets().size());
			for (DataSet dataSet : dataReadResult.getDataSets()) {
				steps += dumpDataSetHistorySteps(dataSet);
			}
		}
		return steps;
		// [END parse_read_data_result]
	}

	/**
	 * print thong tin calo khi khong hoat dong
	 * 
	 * @param dataReadResult
	 *            ket qua tra ve sau khi truy van
	 * @return tong so calo da tieu thu
	 */
	public float printDataCaloFree(DataReadResult dataReadResult) {
		float calos = 0;
		if (dataReadResult.getBuckets().size() > 0) {
			for (Bucket bucket : dataReadResult.getBuckets()) {
				List<DataSet> dataSets = bucket.getDataSets();
				for (DataSet dataSet : dataSets) {
					calos += dumpDataSetHistoryCalos(dataSet);
				}
			}
		} else if (dataReadResult.getDataSets().size() > 0) {
			for (DataSet dataSet : dataReadResult.getDataSets()) {
				calos += dumpDataSetHistoryCalos(dataSet);
			}
		}
		return calos;
	}

	/**
	 * print thong tin quang duong
	 * 
	 * @param dataReadResult
	 *            ket qua tra ve sau khi truy van
	 * @return tong so quang duong di duoc
	 */
	public float printDataDistance(DataReadResult dataReadResult) {
		float distance = 0;
		if (dataReadResult.getBuckets().size() > 0) {
			Log.i(TAG_HISTORY, "Number of returned buckets of DataSets is: "
					+ dataReadResult.getBuckets().size());
			for (Bucket bucket : dataReadResult.getBuckets()) {
				List<DataSet> dataSets = bucket.getDataSets();
				for (DataSet dataSet : dataSets) {
					distance += dumpDataSetHistoryCalos(dataSet);
				}
			}
		} else if (dataReadResult.getDataSets().size() > 0) {
			Log.i(TAG_HISTORY, "Number of returned DataSets is: "
					+ dataReadResult.getDataSets().size());
			for (DataSet dataSet : dataReadResult.getDataSets()) {
				distance += dumpDataSetHistoryCalos(dataSet);
			}
		}
		return distance;
	}

	public long dumpDataSetHistorySteps(DataSet dataSet) {
		Log.i(TAG, "Data returned for Data type: "
				+ dataSet.getDataType().getName());
		long steps = 0;
		for (DataPoint dp : dataSet.getDataPoints()) {
			for (Field field : dp.getDataType().getFields()) {
				Log.i(TAG,
						"\tField: " + field.getName() + " Value: "
								+ dp.getValue(field));
				if (field.getName().equals("steps")) {
					steps += dp.getValue(field).asInt();
				}
			}
		}
		return steps;
	}

	public float dumpDataSetHistoryCalos(DataSet dataSet) {
		Log.i(TAG, "Data returned for Data type: "
				+ dataSet.getDataType().getName());
		float calos = 0;
		for (DataPoint dp : dataSet.getDataPoints()) {
			for (Field field : dp.getDataType().getFields()) {
				Log.i(TAG,
						"\tField: " + field.getName() + " Value: "
								+ dp.getValue(field));
				if (field.getName().equals("calories")) {
					calos += dp.getValue(field).asFloat();
				}
			}
		}
		return calos;
	}

	public float dumpDataSetHistoryDistance(DataSet dataSet) {
		Log.e(TAG, "Data returned for Data type: "
				+ dataSet.getDataType().getName());
		float distance = 0;
		for (DataPoint dp : dataSet.getDataPoints()) {
			for (Field field : dp.getDataType().getFields()) {
				Log.e("type",
						"\tField: " + field.getName() + " Value: "
								+ dp.getValue(field));
				if (field.getName().equals("distance")) {
					distance += dp.getValue(field).asFloat();
				}
			}
		}
		return distance;
	}

	// [END parse_dataset]

	/**
	 * Delete a {@link DataSet} from the History API. In this example, we delete
	 * all step count data for the past 24 hours.
	 */
	public void deleteData() {
		Log.i(TAG_HISTORY, "Deleting today's step count data");
		// [START delete_dataset]
		// Set a start and end time for our data, using a start time of 1 day
		// before this moment.
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		long startTime = cal.getTimeInMillis();
		// Create a delete request object, providing a data type and a time
		// interval
		DataDeleteRequest request = new DataDeleteRequest.Builder()
				.setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
				.addDataType(DataType.TYPE_STEP_COUNT_DELTA).build();
		// Invoke the History API with the Google API client object and delete
		// request, and then
		// specify a callback that will check the result.
		Fitness.HistoryApi.deleteData(mClient, request).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(Status status) {
						if (status.isSuccess()) {
							Log.i(TAG_HISTORY,
									"Successfully deleted today's step count data");
						} else {
							// The deletion will fail if the requesting app
							// tries to delete data
							// that it did not insert.
							Log.i(TAG_HISTORY,
									"Failed to delete today's step count data");
						}
					}
				});
		// [END delete_dataset]
	}

	public void publishTodaysStepData(long totalSteps, int type) {
		Intent intent = new Intent(HISTORY_INTENT);
		// You can also include some extra data.
		if (type == 1) {
			intent.putExtra(HISTORY_EXTRA_STEPS_TODAY, totalSteps);
		} else if (type == 2) {
			intent.putExtra(HISTORY_EXTRA_STEPS_TOWEEK, totalSteps);
		} else {
			intent.putExtra(HISTORY_EXTRA_STEPS_TOMONTH, totalSteps);
		}

		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	public void buildFitnessClient() {
		// Create the Google API Client
		mClient = new GoogleApiClient.Builder(this)
				.addOnConnectionFailedListener(this)
				.addApi(Fitness.HISTORY_API)
				.addApi(Fitness.RECORDING_API)
				.addApi(Fitness.SENSORS_API)
				.addApi(Fitness.CONFIG_API)
				.addApi(Fitness.SESSIONS_API)
				.addApi(Plus.API)
				.addApi(LocationServices.API)
				.addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
				.addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
				.addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
				.addScope(new Scope(Scopes.FITNESS_NUTRITION_READ_WRITE))
				.addScope(new Scope(Scopes.PROFILE))
				.addScope(new Scope(Scopes.PLUS_LOGIN))
				.addScope(new Scope(Scopes.PLUS_ME))
				.addScope(new Scope(Scopes.CLOUD_SAVE))
				.addConnectionCallbacks(
						new GoogleApiClient.ConnectionCallbacks() {

							@Override
							public void onConnected(Bundle bundle) {
								Log.i(TAG, "Google Fit connected.");
								mTryingToConnect = false;
								Log.d(TAG,
										"Notifying the UI that we're connected.");
								notifyUiFitConnected();
							}

							@Override
							public void onConnectionSuspended(int i) {
								// If your connection to the sensor gets lost at
								// some point,
								// you'll be able to determine the reason and
								// react to it here.
								mTryingToConnect = false;
								if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
									Log.i(TAG,
											"Google Fit Connection lost.  Cause: Network Lost.");
								} else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
									Log.i(TAG,
											"Google Fit Connection lost.  Reason: Service Disconnected");
								}
							}
						})
				.addOnConnectionFailedListener(
						new GoogleApiClient.OnConnectionFailedListener() {
							// Called whenever the API client fails to connect.
							@Override
							public void onConnectionFailed(
									ConnectionResult result) {
								mTryingToConnect = false;
								notifyUiFailedConnection(result);
							}
						}).build();
	}

	public void notifyUiFitConnected() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mClient) != null) {
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mClient);
				Constants.getInstance().name = currentPerson.getDisplayName();
				Constants.getInstance().email = Plus.AccountApi
						.getAccountName(mClient);
				Constants.getInstance().sex = currentPerson.getGender();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		findFitnessDataSourcesSensors();
		// new InsertAndVerifyDataTask().execute();
		subscribe();
		
		mLocationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(10 * 1000) // 10 seconds, in milliseconds
				.setFastestInterval(1 * 1000); // 1 second, in milliseconds
		Location location = LocationServices.FusedLocationApi
				.getLastLocation(mClient);

		if (location == null) {
			LocationServices.FusedLocationApi.requestLocationUpdates(mClient,
					mLocationRequest, this);

		} else {
			// If everything went fine lets get latitude and longitude
			currentLatitude = location.getLatitude();
			currentLongitude = location.getLongitude();
		}
		Log.e("notifyUiFitConnected", currentLatitude + " WORKS " + currentLongitude + "");
		Intent intent = new Intent(FIT_NOTIFY_INTENT);
		intent.putExtra(FIT_EXTRA_CONNECTION_MESSAGE,
				FIT_EXTRA_CONNECTION_MESSAGE);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	public void notifyUiFailedConnection(ConnectionResult result) {
		Intent intent = new Intent(FIT_NOTIFY_INTENT);
		intent.putExtra(FIT_EXTRA_NOTIFY_FAILED_STATUS_CODE,
				result.getErrorCode());
		intent.putExtra(FIT_EXTRA_NOTIFY_FAILED_INTENT, result.getResolution());
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.i(TAG, "onConnected");
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mClient.connect();

	}

	@Override
	public PendingResult<DataTypeResult> createCustomDataType(
			GoogleApiClient arg0, DataTypeCreateRequest arg1) {
		DataTypeCreateRequest request = new DataTypeCreateRequest.Builder()
				// The prefix of your data type name must match your app's
				// package name
				.setName("app.healthcare")
				.addField("HEART_RATE_ID", Field.FORMAT_INT32)
				.addField("RATE", Field.FORMAT_INT32)
				.addField("MOTION_STATUS", Field.FORMAT_STRING)
				.addField("TIME", Field.FORMAT_STRING)
				.addField("BODY_CONDITION", Field.FORMAT_STRING)
				.addField("NOTE", Field.FORMAT_STRING)
				.addField(Field.FIELD_ACTIVITY).build();
		PendingResult<DataTypeResult> pendingResult = Fitness.ConfigApi
				.createCustomDataType(mClient, request);

		pendingResult.setResultCallback(new ResultCallback<DataTypeResult>() {
			@Override
			public void onResult(DataTypeResult dataTypeResult) {
				// DataType customType = dataTypeResult.getDataType();

			}
		});
		return null;
	}

	@Override
	public PendingResult<Status> disableFit(GoogleApiClient arg0) {
		return null;
	}

	@Override
	public PendingResult<DataTypeResult> readDataType(GoogleApiClient arg0,
			String arg1) {
		PendingResult<DataTypeResult> pendingResult = Fitness.ConfigApi
				.readDataType(mClient, "app.healthcare");
		// 2. Check the result asynchronously
		// (The result may not be immediately available)
		pendingResult.setResultCallback(new ResultCallback<DataTypeResult>() {
			@Override
			public void onResult(DataTypeResult dataTypeResult) {
				// Retrieve the custom data type
				// DataType customType = dataTypeResult.getDataType();

			}
		});
		return null;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}
}
