package app.healthcare.heartratehistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import app.dto.HeartRateDTO;
import app.healthcare.R;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class HistoryHeartRate extends Activity {
	BarGraph bg;
	public static HeartRateDTO itemCurentSelect;
	List<HeartRateDTO> listData = new ArrayList<HeartRateDTO>();
	TextView avgHeartRate;

	// All static variables
	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String KEY_ID = "ID";
	static final String KEY_RATE = "rate";
	static final String KEY_TIME = "time";
	static final String KEY_DATE = "date";
	static final String KEY_FEEL = "feel";
	static final String KEY_MOTION_STATUS = "motion_status";
	static final String KEY_NOTE = "note";

	ListView list;
	HistoryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_heartrate);
		avgHeartRate = (TextView) findViewById(R.id.avg_text);
		try {
			ParseQuery<HeartRateDTO> query = ParseQuery
					.getQuery("HeartRateDTO");
			listData = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		itemCurentSelect = new HeartRateDTO();
		initChart();
		initList();
	}

	private void initChart() {

		final Resources resources = getResources();
		ArrayList<Bar> aBars = new ArrayList<Bar>();
		Bar bar;

		for (int i = listData.size() - 1; i >= 0; i--) {
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			bar.setName(listData.get(i).getDate());
			bar.setValue((float) listData.get(i).getHeartRate());
			bar.setValueString(String.valueOf(listData.get(i).getHeartRate()));
			bar.setId(listData.get(i).getHeartRateId());
			aBars.add(bar);
			if ((listData.size() - i) >= 10) {
				break;
			}
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_heartrate);
		bg = barGraph;
		barGraph.setBars(aBars);
		final Intent i = new Intent(this, HeartRateResultView.class);
		barGraph.setOnBarClickedListener(new OnBarClickedListener() {
			@Override
			public void onClick(int index) {
				try {
					ParseQuery<HeartRateDTO> query = ParseQuery
							.getQuery("HeartRateDTO");
					query.whereEqualTo("heartRateId",
							barGraph.getBars().get(index).getId());
					List<HeartRateDTO> dataSelect = new ArrayList<HeartRateDTO>();
					dataSelect = query.find();
					if (dataSelect.size() > 0) {
						HistoryHeartRate.itemCurentSelect = dataSelect.get(0);
					} else {
						HistoryHeartRate.itemCurentSelect = new HeartRateDTO();
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
				startActivity(i);
			}
		});
	}

	private void initList() {
		int avg = 0;
		ArrayList<HashMap<String, String>> heasrtRateList = new ArrayList<HashMap<String, String>>();

		// looping through all song nodes &lt;song&gt;
		for (int i = 0; i < listData.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key =&gt; value
			map.put(KEY_RATE, String.valueOf(listData.get(i).getHeartRate()));
			map.put(KEY_TIME, listData.get(i).getTime());
			map.put(KEY_DATE, listData.get(i).getDate());
			switch (listData.get(i).getBodyCo()) {
			case 1:
				map.put(KEY_FEEL, "Rất tốt");
				break;
			case 2:
				map.put(KEY_FEEL, "Tốt");
				break;
			case 3:
				map.put(KEY_FEEL, "Bình thường");
				break;
			case 4:
				map.put(KEY_FEEL, "Không tốt");
				break;
			case 5:
				map.put(KEY_FEEL, "Tệ");
				break;
			default:
				break;
			}
			switch (listData.get(i).getStatusSport()) {
			case 1:
				map.put(KEY_MOTION_STATUS,
						String.valueOf(R.drawable.ic_hr_type_custom_add));
				break;
			case 2:
				map.put(KEY_MOTION_STATUS,
						String.valueOf(R.drawable.ic_hr_type_sleep_add));
				break;
			case 3:
				map.put(KEY_MOTION_STATUS,
						String.valueOf(R.drawable.ic_hr_type_before_sport_add));
				break;
			case 4:
				map.put(KEY_MOTION_STATUS,
						String.valueOf(R.drawable.ic_hr_type_after_sport_add));
				break;
			case 5:
				map.put(KEY_MOTION_STATUS,
						String.valueOf(R.drawable.ic_hr_type_max_add));
				break;
			default:
				break;
			}
			map.put(KEY_NOTE, listData.get(i).getNote());
			// adding HashList to ArrayList
			heasrtRateList.add(map);
			avg += listData.get(i).getHeartRate();
		}
		list = (ListView) findViewById(R.id.list);
		// Getting adapter by passing xml data ArrayList
		adapter = new HistoryAdapter(this, heasrtRateList);
		list.setAdapter(adapter);
		avg = avg / listData.size();
		avgHeartRate.setText(String.valueOf(avg));
		final Intent i = new Intent(this, HeartRateResultView.class);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				itemCurentSelect = listData.get(pos);
				startActivity(i);
			}

		});
	}
}
