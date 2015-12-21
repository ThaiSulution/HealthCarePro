package app.healthcare.bmi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import app.healthcare.Constants;
import app.healthcare.R;
import app.healthcare.dataobject.RatioBMIDTO;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.gc.materialdesign.widgets.Dialog;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class HistoryBMI extends Activity {
	BarGraph bg;

	static final String KEY_ID = "ID";
	static final String KEY_RATE = "ratio";
	static final String KEY_TIME = "time";
	static final String KEY_DATE = "date";
	static final String KEY_STATUS = "status";

	ListView list;
	HistoryBMIAdapter adapter;
	public static RatioBMIDTO itemCurentSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_bmi);
		itemCurentSelect = new RatioBMIDTO();
		initChart();
		initList();
	}

	private void initList() {
		ArrayList<HashMap<String, String>> ratiopBMIList = new ArrayList<HashMap<String, String>>();
		// looping through all song nodes &lt;song&gt;
		for (int i = 0; i < Constants.getInstance().listDataBMI.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key =&gt; value
			map.put(KEY_ID, String.valueOf(Constants.getInstance().listDataBMI
					.get(i).getRatioBMIId()));
			map.put(KEY_TIME, Constants.getInstance().listDataBMI.get(i)
					.getTime());
			map.put(KEY_DATE, Constants.getInstance().listDataBMI.get(i)
					.getDate());
			map.put(KEY_STATUS, Constants.getInstance().listDataBMI.get(i)
					.getStatus());
			map.put(KEY_RATE,
					String.valueOf(Constants.getInstance().listDataBMI.get(i)
							.getRatio()));
			// adding HashList to ArrayList
			ratiopBMIList.add(map);
		}
		list = (ListView) findViewById(R.id.list_bmi);
		// Getting adapter by passing xml data ArrayList
		adapter = new HistoryBMIAdapter(this, ratiopBMIList);
		list.setAdapter(adapter);
		final Intent i = new Intent(this, BMIResultView.class);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				itemCurentSelect = Constants.getInstance().listDataBMI.get(pos);
				startActivity(i);
			}

		});
		final Dialog dialogBMI = new Dialog(this, "Xóa chỉ số BMI",
				"Bạn có muốn xóa thông tin BMI này?", R.drawable.bmi_icon);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				final int index = pos;
				dialogBMI.show();
				dialogBMI.getButtonAccept().setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								ParseQuery<RatioBMIDTO> queryBMI = ParseQuery
										.getQuery("RatioBMIDTO");
								queryBMI.whereEqualTo("ratioBMIId", Constants
										.getInstance().listDataBMI.get(index)
										.getRatioBMIId());
								queryBMI.findInBackground((new FindCallback<RatioBMIDTO>() {
									@Override
									public void done(List<RatioBMIDTO> datas,
											ParseException e) {
										if (e == null) {
											for (int i = 0; i < datas.size(); i++) {
												for (int j = 0; j < Constants
														.getInstance().listDataBMI
														.size(); j++) {
													if (Constants.getInstance().listDataBMI
															.get(j)
															.getRatioBMIId() == datas
															.get(i)
															.getRatioBMIId()) {
														Constants.getInstance().listDataBMI
																.remove(j);
													}
												}
												datas.get(i)
														.deleteInBackground(
																new DeleteCallback() {

																	@Override
																	public void done(
																			ParseException arg0) {
																		initList();
																		initChart();
																	}
																});

											}
										}
									}

								}));

								dialogBMI.dismiss();
							}
						});
				return false;
			}
		});
	}

	private void initChart() {
		final Resources resources = getResources();
		ArrayList<Bar> aBars = new ArrayList<Bar>();
		Bar bar;

		for (int i = Constants.getInstance().listDataBMI.size() - 1; i >= 0; i--) {
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			String dateMonth = Constants.getInstance().listDataBMI.get(i)
					.getDate();
			String[] sub = dateMonth.split("/");
			bar.setName(sub[0] + "/" + sub[1]);
			bar.setValue(Float.parseFloat(String.valueOf(Constants
					.getInstance().listDataBMI.get(i).getRatio())));
			bar.setValueString(String.valueOf(Constants.getInstance().listDataBMI
					.get(i).getRatio()));
			aBars.add(bar);
			if ((Constants.getInstance().listDataBMI.size() - i) >= 8) {
				break;
			}
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_bmi);
		bg = barGraph;
		barGraph.setBars(aBars);
		final Intent i = new Intent(this, BMIResultView.class);
		barGraph.setOnBarClickedListener(new OnBarClickedListener() {
			@Override
			public void onClick(int index) {
				Log.e("bargrap click", String.valueOf(index));
				index += 1;
				HistoryBMI.itemCurentSelect = Constants.getInstance().listDataBMI
						.get(Constants.getInstance().listDataBMI.size() - index);
				startActivity(i);
			}
		});
	}
}