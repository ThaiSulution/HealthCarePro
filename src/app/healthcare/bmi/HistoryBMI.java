package app.healthcare.bmi;

import java.util.ArrayList;
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
			bar.setValue(Float.parseFloat(String.valueOf(listData.get(i)
					.getRatio())));
			bar.setValueString(String.valueOf(listData.get(i).getRatio()));
			aBars.add(bar);
			if ((listData.size() - i) >= 10) {
				break;
			}
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_bmi);
		bg = barGraph;
		barGraph.setBars(aBars);
	}
}
