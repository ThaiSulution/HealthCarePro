package app.healthcare;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import app.dto.HeartRateDTO;
import app.dto.RatioBMIDTO;
import app.dto.RatioWHRDTO;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class StartAppScreen extends Fragment {
	ImageView imgNew;
	BarGraph bg;
	private PieSlice sliceStepRun;
	private PieSlice sliceHeartRate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.menu, container, false);
		init(rootView);
		return rootView;
	}

	private void init(View rootView) {
		final MainActivity a = (MainActivity) getActivity();
		buildChartBMI(rootView, a);
		buildChartWHR(rootView, a);
		buildChartHreatRate(rootView, a);
		buildChartStepRun(rootView, a);
	}

	private void buildChartBMI(View rootView, final MainActivity a) {
		try {
			List<RatioBMIDTO> listData = new ArrayList<RatioBMIDTO>();
			ParseQuery<RatioBMIDTO> query = new ParseQuery<RatioBMIDTO>(
					"RatioBMIDTO");
			try {
				listData = query.find();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int rows = listData.size();
			final Resources resources = getResources();
			ArrayList<Bar> aBars = new ArrayList<Bar>();
			Bar bar;
			for (int i = rows - 1; (i >= rows - 7) && (i >= 0); i--) {
				bar = new Bar();
				bar.setColor(resources.getColor(R.color.holo_ogrange_light));
				bar.setSelectedColor(resources
						.getColor(R.color.transparent_orange));
				String dateMonth = listData.get(i).getDate().split("/")[0]
						+ "/" + listData.get(i).getDate().split("/")[1];
				bar.setName(dateMonth);
				bar.setValue(Float.parseFloat(String.valueOf(listData.get(i)
						.getRatio())));
				bar.setValueString(String.valueOf(listData.get(i).getRatio()));
				aBars.add(bar);

			}

			final BarGraph barGraph = (BarGraph) rootView
					.findViewById(R.id.bmi_chart);
			bg = barGraph;
			barGraph.setBars(aBars);
			barGraph.setBackgroundResource(R.drawable.bmi_cover);
			barGraph.setOnBarClickedListener(new OnBarClickedListener() {

				@Override
				public void onClick(int index) {
					a.displayView(3);
				}
			});
		} catch (NullPointerException e) {
			Log.e("buildChartBMI", e.toString());
		}
	}

	private void buildChartWHR(View rootView, final MainActivity a) {
		try {
			List<RatioWHRDTO> listData = new ArrayList<RatioWHRDTO>();
			ParseQuery<RatioWHRDTO> query = new ParseQuery<RatioWHRDTO>(
					"RatioWHRDTO");
			try {
				listData = query.find();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int rows = listData.size();
			final Resources resources = getResources();
			ArrayList<Bar> aBars = new ArrayList<Bar>();
			Bar bar;
			for (int i = rows - 1; (i >= rows - 7) && (i >= 0); i--) {
				bar = new Bar();
				bar.setColor(resources.getColor(R.color.red));
				bar.setSelectedColor(resources
						.getColor(R.color.transparent_orange));
				String dateMonth = listData.get(i).getDate().split("/")[0]
						+ "/" + listData.get(i).getDate().split("/")[1];
				bar.setName(dateMonth);
				bar.setValue(Float.parseFloat(String.valueOf(listData.get(i)
						.getRatio())));
				bar.setValueString(String.valueOf(listData.get(i).getRatio()));
				aBars.add(bar);
			}
			final BarGraph barGraph = (BarGraph) rootView
					.findViewById(R.id.whr_chart);
			bg = barGraph;
			barGraph.setBars(aBars);
			barGraph.setBackgroundResource(R.drawable.whr_cover);
			barGraph.setOnBarClickedListener(new OnBarClickedListener() {
				@Override
				public void onClick(int index) {
					a.displayView(2);
				}
			});
		} catch (NullPointerException e) {
			Log.e("buildChartWHR", e.toString());
		}
	}

	private void buildChartStepRun(View rootView, final MainActivity a) {
		try {
			final Resources resources = getResources();
			final PieGraph pg = (PieGraph) rootView
					.findViewById(R.id.step_run_chart);
			sliceStepRun = new PieSlice();
			sliceStepRun.setColor(resources.getColor(R.color.red));
			sliceStepRun.setSelectedColor(resources
					.getColor(R.color.transparent_orange));
			sliceStepRun.setValue(1);
			sliceStepRun.setTitle("first");
			pg.addSlice(sliceStepRun);
			sliceStepRun = new PieSlice();
			sliceStepRun.setColor(resources.getColor(R.color.orange));
			sliceStepRun.setValue(1);
			pg.addSlice(sliceStepRun);
			pg.setTextSizeGr(20);
			pg.setOnSliceClickedListener(new OnSliceClickedListener() {

				@Override
				public void onClick(int index) {
					a.displayView(4);
				}

			});

			Bitmap b = BitmapFactory.decodeResource(getResources(),
					R.drawable.run_icon_black);
			pg.setBackgroundBitmap(b);
			// pg.setBackgroundText("Step in here");
			pg.setInnerCircleRatio(220);
			pg.setBackgroundText("SB: "
					+ String.valueOf(Constants.getInstance().getStepRuns())
					+ "\n"
					+ "KC: "
					+ String.valueOf((long) Constants.getInstance()
							.getDistance()) + "m\n" + "Calo: "
					+ String.valueOf(Constants.getInstance().getCalos()));
			if (Constants.getInstance().getTarget() > Constants.getInstance()
					.getStepRuns()) {
				pg.getSlices().get(0)
						.setGoalValue(Constants.getInstance().getStepRuns());
				pg.getSlices()
						.get(1)
						.setGoalValue(
								Constants.getInstance().getTarget()
										- Constants.getInstance().getStepRuns());
			} else {
				pg.getSlices().get(0).setGoalValue(1);
				pg.getSlices().get(1).setGoalValue(0);

			}
			pg.setDuration(1000);// default if unspecified is 300 ms
			pg.setInterpolator(new AccelerateDecelerateInterpolator());
			pg.setAnimationListener(getAnimationListener());
			pg.animateToGoalValues();
		} catch (NullPointerException e) {
			Log.e("buildChartStepRun", e.toString());
		}

	}

	private void buildChartHreatRate(View rootView, final MainActivity a) {
		try {
			final Resources resources = getResources();
			final PieGraph pg = (PieGraph) rootView
					.findViewById(R.id.heart_rate_chart);
			sliceHeartRate = new PieSlice();
			sliceHeartRate.setColor(resources.getColor(R.color.red));
			sliceHeartRate.setSelectedColor(resources
					.getColor(R.color.transparent_orange));
			sliceHeartRate.setValue(1);
			sliceHeartRate.setTitle("first");
			pg.addSlice(sliceHeartRate);
			sliceHeartRate = new PieSlice();
			sliceHeartRate.setColor(resources.getColor(R.color.orange));
			sliceHeartRate.setValue(0);
			pg.addSlice(sliceHeartRate);
			pg.setTextSizeGr(20);
			List<HeartRateDTO> listdata = new ArrayList<HeartRateDTO>();
			ParseQuery<HeartRateDTO> query = new ParseQuery<HeartRateDTO>(
					"HeartRateDTO");
			try {
				listdata = query.find();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int rows = listdata.size();
			int avg = 0;
			for (int i = 0; i < rows; i++) {
				avg += listdata.get(i).getHeartRate();
			}
			if (rows > 0) {
				avg = avg / rows;
				if ((avg - listdata.get(listdata.size() - 1).getHeartRate()) > 0) {
					pg.getSlices()
							.get(0)
							.setGoalValue(
									listdata.get(listdata.size() - 1)
											.getHeartRate());
					pg.getSlices()
							.get(1)
							.setGoalValue(
									avg
											- listdata.get(listdata.size() - 1)
													.getHeartRate());
				} else {
					pg.getSlices().get(0).setGoalValue(1);
					pg.getSlices().get(1).setGoalValue(0);

				}
				pg.setDuration(1000);// default if unspecified is 300 ms
				pg.setInterpolator(new AccelerateDecelerateInterpolator());
				pg.setAnimationListener(getAnimationListener());
				pg.animateToGoalValues();
				pg.setBackgroundText("TB: "
						+ String.valueOf(avg)
						+ "\n"
						+ "HT: "
						+ String.valueOf(listdata.get(listdata.size() - 1)
								.getHeartRate()));
			} else {
				pg.setBackgroundText("TB: " + String.valueOf(avg) + "\n"
						+ "HT: " + String.valueOf(0));
			}

			pg.setOnSliceClickedListener(new OnSliceClickedListener() {

				@Override
				public void onClick(int index) {
					a.displayView(1);
				}

			});

			Bitmap b = BitmapFactory.decodeResource(getResources(),
					R.drawable.heart_on);
			pg.setBackgroundBitmap(b);
			// pg.setBackgroundText("Step in here");
			pg.setInnerCircleRatio(220);
		} catch (NullPointerException e) {
			Log.e("buildChartHreatRate", e.toString());
		}
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
}