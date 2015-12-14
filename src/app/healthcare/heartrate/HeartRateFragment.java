package app.healthcare.heartrate;

import java.util.concurrent.atomic.AtomicBoolean;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import app.healthcare.DialogResultHeartRate;
import app.healthcare.R;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.gc.materialdesign.views.Button;

@SuppressWarnings("deprecation")
public class HeartRateFragment extends Fragment {

	private static final String TAG = "HeartRateMonitor";
	private static final AtomicBoolean processing = new AtomicBoolean(false);

	private static SurfaceView preview = null;
	private static SurfaceHolder previewHolder = null;
	private static Camera camera = null;
	private static Button btnStart = null;
	private static Button btnHelp = null;
	private static boolean checkHeartRate;
	private static WakeLock wakeLock = null;
	static DialogResultHeartRate alertDialog2;
	private static int averageIndex = 0;
	private static final int averageArraySize = 4;
	private static final int[] averageArray = new int[averageArraySize];

	private PieSlice sliceRed;
	static PieGraph pg;
	static Bitmap bmHeartOn;
	static Bitmap bmHeartOff;

	public static enum TYPE {
		GREEN, RED
	};

	private static TYPE currentType = TYPE.GREEN;

	public static TYPE getCurrent() {
		return currentType;
	}

	private static int beatsIndex = 0;
	private static final int beatsArraySize = 3;
	private static final int[] beatsArray = new int[beatsArraySize];
	private static double beats = 0;
	private static long startTime = 0;
	private static long startTimeUpdate = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_heart_rate,
				container, false);
		alertDialog2 = new DialogResultHeartRate(getActivity());
		initView(rootView);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();
		camera = Camera.open();
		startTime = System.currentTimeMillis();
	}

	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private void initView(View rootView) {
		checkHeartRate = false;
		preview = (SurfaceView) rootView.findViewById(R.id.preview);
		previewHolder = preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		btnHelp = (Button) rootView.findViewById(R.id.btnHelp);
		btnHelp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				alertDialog2.setContentView(R.layout.custom_dialog);
				alertDialog2.setTitle("Hướng dẫn");
				TextView text = (TextView) alertDialog2
						.findViewById(R.id.textDialog);
				text.setText("Để có thể có kết quả đo chuẩn xác nhất, "
						+ "bạn phải Đặt ngón tay vào sau camera, tùy vào độ sáng của đèn flash, "
						+ "nếu quá sáng, biểu đồ giao động hình ảnh là 1 đường thằng thì thiết bị của "
						+ "bạn sẽ không có kết quả chuẩn xác nhất.\n Khi thấy biểu đồ có sự thay đổi tuần tự, "
						+ "bạn bấm nút Start để bắt đầu đo và chờ kết quá. Trong quá trình đo đề nghị bạn giữ "
						+ "nguyên vị trí tay, tránh giao động làm sai số kết quả");
				Button declineButton = (Button) alertDialog2
						.findViewById(R.id.declineButton);
				declineButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alertDialog2.dismiss();
					}
				});
				alertDialog2.show();
			}
		});
		btnStart = (Button) rootView.findViewById(R.id.btnStart);
		btnStart.setText("START");
		btnStart.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (btnStart.getText() == "START") {
					checkHeartRate = true;
					btnStart.setText("STOP");
				} else if (btnStart.getText() == "STOP") {
					checkHeartRate = false;
					btnStart.setText("START");
				}
			}
		});
		PowerManager pm = (PowerManager) getActivity().getSystemService(
				Context.POWER_SERVICE);
		wakeLock = pm
				.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
		pg = (PieGraph) rootView.findViewById(R.id.heartbeat_chart);
		Resources resources = this.getActivity().getResources();
		sliceRed = new PieSlice();
		sliceRed.setColor(resources.getColor(R.color.red));
		sliceRed.setSelectedColor(resources
				.getColor(R.color.transparent_orange));
		sliceRed.setValue(0);
		sliceRed.setTitle("first");
		pg.addSlice(sliceRed);
		sliceRed = new PieSlice();
		sliceRed.setColor(resources.getColor(R.color.orange));
		sliceRed.setValue(30);
		pg.addSlice(sliceRed);
		bmHeartOff = BitmapFactory.decodeResource(getResources(),
				R.drawable.heart_off);
		bmHeartOn = BitmapFactory.decodeResource(getResources(),
				R.drawable.heart_on);
		pg.setBackgroundBitmap(bmHeartOff, (int) pg.getX() / 2,
				(int) pg.getY() / 2);
		pg.setInnerCircleRatio(220);
		pg.setBackgroundText("      000\n     BPM");
		pg.setDuration(1000);// default if unspecified is 300 ms
		pg.setInterpolator(new AccelerateDecelerateInterpolator());
		pg.setAnimationListener(getAnimationListener());
		pg.setTextSizeGr(35);

	}

	static int timeFinish = 0;
	static float grap = 0;
	static boolean heartOn = false;
	public static int heartBeat = 0;

	private static PreviewCallback previewCallback = new PreviewCallback() {

		@Override
		public void onPreviewFrame(byte[] data, Camera cam) {
			if (data == null)
				throw new NullPointerException();
			Camera.Size size = cam.getParameters().getPreviewSize();
			if (size == null)
				throw new NullPointerException();

			if (!processing.compareAndSet(false, true))
				return;
			int width = size.width;
			int height = size.height;

			int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(),
					height, width);
			if (imgAvg == 0 || imgAvg == 255) {
				processing.set(false);
				return;
			}
			if (checkHeartRate) {
				heartBeat = 0;
				int averageArrayAvg = 0;
				int averageArrayCnt = 0;
				for (int i = 0; i < averageArray.length; i++) {
					if (averageArray[i] > 0) {
						averageArrayAvg += averageArray[i];
						averageArrayCnt++;
					}
				}
				int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt)
						: 0;
				TYPE newType = currentType;
				if (imgAvg < rollingAverage) {
					newType = TYPE.RED;
					pg.setBackgroundBitmap(bmHeartOn);
					if (newType != currentType) {
						beats++;
					}
				} else if (imgAvg > rollingAverage) {
					newType = TYPE.GREEN;
					pg.setBackgroundBitmap(bmHeartOff);
				}
				if (averageIndex == averageArraySize)
					averageIndex = 0;
				averageArray[averageIndex] = imgAvg;
				averageIndex++;
				if (newType != currentType) {
					currentType = newType;
					if (heartOn) {
						heartOn = false;
					} else {
						heartOn = true;
					}
				}
				long endTime = System.currentTimeMillis();
				double totalTimeInSecs = (endTime - startTime) / 1000d;
				double totalTimeInSecsUpdate = (endTime - startTimeUpdate) / 1000d;
				if (totalTimeInSecsUpdate >= 0.1f) {
					grap += 0.1f;
					updateGraph(grap);
					Log.i(TAG, "updateGraph=" + String.valueOf(grap));
					startTimeUpdate = System.currentTimeMillis();
				}

				if (totalTimeInSecs >= 5) {
					timeFinish += 5;
					double bps = (beats / totalTimeInSecs);
					int dpm = (int) (bps * 60d);
					if (dpm < 30 || dpm > 180) {
						startTime = System.currentTimeMillis();
						beats = 0;
						processing.set(false);
						return;
					}
					if (beatsIndex == beatsArraySize)
						beatsIndex = 0;
					beatsArray[beatsIndex] = dpm;
					beatsIndex++;

					int beatsArrayAvg = 0;
					int beatsArrayCnt = 0;
					for (int i = 0; i < beatsArray.length; i++) {
						if (beatsArray[i] > 0) {
							beatsArrayAvg += beatsArray[i];
							beatsArrayCnt++;
						}
					}
					int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
					heartBeat = beatsAvg;
					pg.setBackgroundText("      " + String.valueOf(beatsAvg)
							+ "\n     BPM");
					startTime = System.currentTimeMillis();
					beats = 0;
					if (timeFinish >= 30) {
						updateGraph(30);
						checkHeartRate = false;
						alertDialog2.setContentView(R.layout.custom_dialog);
						alertDialog2.setTitle("Chỉ số");
						ImageView image = (ImageView) alertDialog2
								.findViewById(R.id.imageDialog);
						image.setImageResource(R.drawable.capture);
						TextView text = (TextView) alertDialog2
								.findViewById(R.id.textDialog);
						text.setText("Chỉ số nhip tim trên phút: "
								+ String.valueOf(beatsAvg));
						Button declineButton = (Button) alertDialog2
								.findViewById(R.id.declineButton);
						declineButton.setText("OK");
						declineButton
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										alertDialog2.dismiss();
										timeFinish = 0;
										grap = 0;
										pg.getSlices().get(0)
												.setGoalValue(0.01f);
										pg.getSlices().get(1).setGoalValue(30);
										pg.setDuration(1000);
										pg.setInterpolator(new AccelerateDecelerateInterpolator());
										pg.setAnimationListener(getAnimationListener());
										pg.animateToGoalValues();
										pg.setBackgroundText("      000\n     BPM");
										alertDialog2.startNewActivity();
									}
								});
						alertDialog2.show();
						btnStart.setText("START");
					}
				}
			}
			processing.set(false);
		}
	};

	private static void updateGraph(float f) {
		pg.getSlices().get(0).setGoalValue(f + 1);
		pg.getSlices().get(1).setGoalValue(30 - f);
		pg.setDuration(500);// default if unspecified is 300 ms
		pg.setInterpolator(new AccelerateDecelerateInterpolator());
		pg.setAnimationListener(getAnimationListener());
		pg.animateToGoalValues();
	}

	private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(previewHolder);
				camera.setPreviewCallback(previewCallback);
			} catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback",
						"Exception in setPreviewDisplay()", t);
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			if (!checkHeartRate) {
				Camera.Parameters parameters = camera.getParameters();
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				Camera.Size size = getSmallestPreviewSize(width, height,
						parameters);
				if (size != null) {
					parameters.setPreviewSize(size.width, size.height);
					Log.d(TAG, "Using width=" + size.width + " height="
							+ size.height);
				}
				camera.setParameters(parameters);
				camera.startPreview();
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// Ignore
		}
	};

	private static Camera.Size getSmallestPreviewSize(int width, int height,
			Camera.Parameters parameters) {
		Camera.Size result = null;
		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea < resultArea)
						result = size;
				}
			}
		}

		return result;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static Animator.AnimatorListener getAnimationListener() {
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
}