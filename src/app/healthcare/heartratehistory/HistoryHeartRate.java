package app.healthcare.heartratehistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.gc.materialdesign.treeview.model.TreeNode;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import app.database.HeartRateDAO;
import app.dto.HeartRateDTO;
import app.healthcare.R;
import app.healthcare.R.color;
import app.healthcare.R.drawable;
import app.healthcare.R.id;
import app.healthcare.R.layout;

public class HistoryHeartRate extends Activity {
	BarGraph bg;
	HeartRateDAO dao;
	List<HeartRateDTO> listData;
	boolean[] isMonthHaveData;
	
	// All static variables
		static final String URL = "http://api.androidhive.info/music/music.xml";
		// XML node keys
		static final String KEY_SONG = "song"; // parent node
		static final String KEY_ID = "id";
		static final String KEY_TITLE = "title";
		static final String KEY_ARTIST = "artist";
		static final String KEY_DURATION = "duration";
		static final String KEY_THUMB_URL = "thumb_url";

		ListView list;
		HistoryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_heartrate);
		dao = new HeartRateDAO(this);
		listData = dao.getListHeartRate();
		isMonthHaveData = new boolean[12];
		initChart();
		initList() ;
	}

	private void initChart() {

		final Resources resources = getResources();
		ArrayList<Bar> aBars = new ArrayList<Bar>();
		Bar bar;

		for (int i = listData.size() - 1; i >= 0; i--) {
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			String dateMonth = listData.get(i).getTime();
			String[] sub = dateMonth.split("/");
			bar.setName(sub[0] + "/" + sub[1]);
			bar.setValue((float) listData.get(i).getHeartRate());
			bar.setValueString(String.valueOf(listData.get(i).getHeartRate()));
			aBars.add(bar);
			if ((listData.size() - i) > 10) {
				break;
			}
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_heartrate);
		bg = barGraph;
		barGraph.setBars(aBars);
	}

	private void initList() {
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		listData = dao.getListHeartRate();

		// looping through all song nodes &lt;song&gt;
		for (int i = 0; i < listData.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key =&gt; value
			map.put(KEY_ID, String.valueOf(listData.get(i).getHeartRate()));
			map.put(KEY_TITLE, listData.get(i).getTime());
			switch (listData.get(i).getBodyCo()) {
			case 1:
				map.put(KEY_ARTIST, "Rất tốt");
				break;
			case 2:
				map.put(KEY_ARTIST, "Tốt");
				break;
			case 3:
				map.put(KEY_ARTIST, "Bình thường");
				break;
			case 4:
				map.put(KEY_ARTIST, "Không tốt");
				break;
			case 5:
				map.put(KEY_ARTIST, "Tệ");
				break;
			default:
				break;
			}
			switch (listData.get(i).getStatusSport()) {
			case 1:
				map.put(KEY_DURATION, "Bình thường");
				break;
			case 2:
				map.put(KEY_DURATION, "Nghỉ ngơi");
				break;
			case 3:
				map.put(KEY_DURATION, "Trước vận động");
				break;
			case 4:
				map.put(KEY_DURATION, "Sau vận động");
				break;
			case 5:
				map.put(KEY_DURATION, "MAX");
				break;
			default:
				break;
			}
			map.put(KEY_THUMB_URL, listData.get(i).getNote());

			// adding HashList to ArrayList
			songsList.add(map);
		}

		list = (ListView) findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new HistoryAdapter(this, songsList);
		list.setAdapter(adapter);

		// Click event for single list row
		// list.setOnItemClickListener(new OnItemClickListener() {
		//
		//
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// });
		// }
	}
}
