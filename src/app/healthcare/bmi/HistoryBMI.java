package app.healthcare.bmi;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import app.dto.RatioBMIDTO;
import app.healthcare.R;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.parse.ParseQuery;

public class HistoryBMI extends Activity {
	BarGraph bg;
	List<RatioBMIDTO> listData = new ArrayList<RatioBMIDTO>();

	static final String KEY_ID = "ID";
	static final String KEY_RATE = "ratio";
	static final String KEY_TIME = "time";
	static final String KEY_DATE = "date";
	static final String KEY_STATUS = "status";

	ListView list;
	HistoryBMIAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_bmi);
		try {
			ParseQuery<RatioBMIDTO> query = new ParseQuery<RatioBMIDTO>(
					"RatioBMIDTO");
			listData = query.find();
		} catch (Exception e) {
		}
		initChart();
		initList();
	}

	private void initList() {
		ArrayList<HashMap<String, String>> ratiopBMIList = new ArrayList<HashMap<String, String>>();
		// looping through all song nodes &lt;song&gt;
		for (int i = 0; i < listData.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key =&gt; value
			map.put(KEY_ID, String.valueOf(listData.get(i).getRatioBMIId()));
			map.put(KEY_TIME, listData.get(i).getTime());
			map.put(KEY_DATE, listData.get(i).getDate());
			map.put(KEY_STATUS, listData.get(i).getStatus());
			map.put(KEY_RATE, String.valueOf(listData.get(i).getRatio()));
			// adding HashList to ArrayList
			ratiopBMIList.add(map);
		}
		list = (ListView) findViewById(R.id.list_bmi);
		// Getting adapter by passing xml data ArrayList
		adapter = new HistoryBMIAdapter(this, ratiopBMIList);
		list.setAdapter(adapter);
	}

	private void initChart() {
=======
import java.util.List;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import app.database.HeartRateDAO;
import app.database.RatioBMIDAO;
import app.dto.HeartRateDTO;
import app.dto.RatioBMIDTO;
import app.healthcare.R;
import app.healthcare.heartratehistory.HeartRateResultView;
import app.healthcare.heartratehistory.HistoryAdapter;
import app.healthcare.heartratehistory.HistoryHeartRate;

public class HistoryBMI extends Activity {
	BarGraph bg;
	RatioBMIDAO dao;
	public static RatioBMIDTO itemCurentSelect;
	List<RatioBMIDTO> listData;
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
		dao = new RatioBMIDAO(this);
		listData = dao.getListRatioBMI(1);
		itemCurentSelect = new RatioBMIDTO();
		initChart();
		initList();
	}
	private void initList() {
>>>>>>> ddef683a6e1f695bedba55a20d69afead1f89c78
		final Resources resources = getResources();
		ArrayList<Bar> aBars = new ArrayList<Bar>();
		Bar bar;

		for (int i = listData.size() - 1; i >= 0; i--) {
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			String dateMonth = listData.get(i).getDate();
			String[] sub = dateMonth.split("/");
			bar.setName(sub[0] + "/" + sub[1]);
<<<<<<< HEAD
			bar.setValue(Float.parseFloat(String.valueOf(listData.get(i)
					.getRatio())));
			bar.setValueString(String.valueOf(listData.get(i).getRatio()));
=======
			bar.setValue(Float.parseFloat(listData.get(i).getRatio()));
			bar.setValueString(String.valueOf(listData.get(i).getHeartRate()));
			bar.setId(listData.get(i).getHeartRateId());
>>>>>>> ddef683a6e1f695bedba55a20d69afead1f89c78
			aBars.add(bar);
			if ((listData.size() - i) >= 10) {
				break;
			}
		}

<<<<<<< HEAD
		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_bmi);
		bg = barGraph;
		barGraph.setBars(aBars);
=======
		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_heartrate);
		bg = barGraph;
		barGraph.setBars(aBars);		
	}
	private void initChart() {
		// TODO Auto-generated method stub
		
>>>>>>> ddef683a6e1f695bedba55a20d69afead1f89c78
	}
}
