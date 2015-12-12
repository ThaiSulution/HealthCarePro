package app.healthcare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

public class HistoryStep extends Activity {
	BarGraph bg;
	private ProgressDialog dialog;
	final Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_step);
		dialog = ProgressDialog.show(HistoryStep.this, "", "Vui lòng chờ...");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// if (!GoogleFitService.getDataDayFinish
				// || !GoogleFitService.getDataYearFinish) {
				if (!GoogleFitService.getDataYearFinish) {
					if (!dialog.isShowing()) {
						dialog = ProgressDialog.show(HistoryStep.this, "",
								"Vui lòng chờ...");
					}
					mHandler.postDelayed(this, 2000);
				} else {
					init();
					dialog.cancel();
					Thread.currentThread().interrupt();
				}
			}
		};
		// start handler
		mHandler.post(runnable);
	}

	private void init() {
		final Resources resources = getResources();
		ArrayList<Bar> aBars = new ArrayList<Bar>();
		Bar bar;
		int total = 0;
		if (GoogleFitService.listDataStep.size() >= 16) {
			total = 16;
		} else {
			total = GoogleFitService.listDataStep.size();
		}
		// for (int i = GoogleFitService.listDataStep.size()-1;i>=0;i--){
		for (int i = 0; i < total; i++) {
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			String dateMonth = GoogleFitService.listDataStep.get(i).getTime();
			bar.setName(dateMonth);
			bar.setValue((float) GoogleFitService.listDataStep.get(i).getStep());
			bar.setValueString(String.valueOf(GoogleFitService.listDataStep
					.get(i).getStep()));
			aBars.add(bar);
			// if ((GoogleFitService.listDataStep.size() - i )>15){
			// break;
			// }
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_step);
		bg = barGraph;
		barGraph.setBars(aBars);
	}
}
