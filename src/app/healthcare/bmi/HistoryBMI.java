package app.healthcare.bmi;

import java.util.ArrayList;
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
			bar.setValue(Float.parseFloat(listData.get(i).getRatio()));
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
	}
	private void initChart() {
		// TODO Auto-generated method stub
		
	}
}
