package app.healthcare.whr;

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
import app.dto.RatioWHRDTO;
import app.healthcare.Constants;
import app.healthcare.R;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.gc.materialdesign.widgets.Dialog;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class HistoryWHR extends Activity {
	BarGraph bg;

	static final String KEY_ID = "ID";
	static final String KEY_RATE = "ratio";
	static final String KEY_TIME = "time";
	static final String KEY_DATE = "date";
	static final String KEY_STATUS = "status";

	ListView list;
	HistoryWHRAdapter adapter;
	public static RatioWHRDTO itemCurentSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_whr);
		itemCurentSelect = new RatioWHRDTO();
		initChart();
		initList();
	}

	private void initList() {
		ArrayList<HashMap<String, String>> ratiopWHRList = new ArrayList<HashMap<String, String>>();
		// looping through all song nodes &lt;song&gt;
		for (int i = 0; i < Constants.getInstance().listDataWHR.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key =&gt; value
			map.put(KEY_ID, String.valueOf(Constants.getInstance().listDataWHR
					.get(i).getRatioWHRId()));
			map.put(KEY_TIME, Constants.getInstance().listDataWHR.get(i)
					.getTime());
			map.put(KEY_DATE, Constants.getInstance().listDataWHR.get(i)
					.getDate());
			map.put(KEY_STATUS, Constants.getInstance().listDataWHR.get(i)
					.getStatus());
			map.put(KEY_RATE,
					String.valueOf(Constants.getInstance().listDataWHR.get(i)
							.getRatio()));
			// adding HashList to ArrayList
			ratiopWHRList.add(map);
		}
		list = (ListView) findViewById(R.id.list_whr);
		// Getting adapter by passing xml data ArrayList
		adapter = new HistoryWHRAdapter(this, ratiopWHRList);
		list.setAdapter(adapter);
		final Intent i = new Intent(this, WHRResultView.class);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				itemCurentSelect = Constants.getInstance().listDataWHR.get(pos);
				startActivity(i);
			}

		});
		final Dialog dialogWHR = new Dialog(this, "Xóa chỉ số WHR",
				"Bạn có muốn xóa thông tin WHR này?", R.drawable.whr_icon);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				final int index = pos;
				dialogWHR.show();
				dialogWHR.getButtonAccept().setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								ParseQuery<RatioWHRDTO> queryWHR = ParseQuery
										.getQuery("RatioWHRDTO");
								queryWHR.whereEqualTo("ratioWHRId", Constants
										.getInstance().listDataWHR.get(index)
										.getRatioWHRId());
								queryWHR.findInBackground((new FindCallback<RatioWHRDTO>() {
									@Override
									public void done(List<RatioWHRDTO> datas,
											ParseException e) {
										if (e == null) {
											for (int i = 0; i < datas.size(); i++) {
												for (int j = 0; j < Constants
														.getInstance().listDataWHR
														.size(); j++) {
													if (Constants.getInstance().listDataWHR
															.get(j)
															.getRatioWHRId() == datas
															.get(i)
															.getRatioWHRId()) {
														Constants.getInstance().listDataWHR
																.remove(j);
													}
												}
												datas.get(i)
														.deleteInBackground( new DeleteCallback() {
															
															@Override
															public void done(ParseException arg0) {
																initList();
																initChart();
															}
														});

											}
										}
									}

								}));

								dialogWHR.dismiss();
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

		for (int i = Constants.getInstance().listDataWHR.size() - 1; i >= 0; i--) {
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			String dateMonth = Constants.getInstance().listDataWHR.get(i)
					.getDate();
			String[] sub = dateMonth.split("/");
			bar.setName(sub[0] + "/" + sub[1]);
			bar.setValue(Float.parseFloat(String.valueOf(Constants
					.getInstance().listDataWHR.get(i).getRatio())));
			bar.setValueString(String.valueOf(Constants.getInstance().listDataWHR
					.get(i).getRatio()));
			aBars.add(bar);
			if ((Constants.getInstance().listDataWHR.size() - i) >= 8) {
				break;
			}
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_whr);
		bg = barGraph;
		barGraph.setBars(aBars);
		final Intent i = new Intent(this, WHRResultView.class);
		barGraph.setOnBarClickedListener(new OnBarClickedListener() {
			@Override
			public void onClick(int index) {
				Log.e("bargrap click", String.valueOf(index));
				index += 1;
				itemCurentSelect = Constants.getInstance().listDataWHR
						.get(Constants.getInstance().listDataWHR.size() - index);
				startActivity(i);
			}
		});
	}
}
