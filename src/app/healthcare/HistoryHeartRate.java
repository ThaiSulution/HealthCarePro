package app.healthcare;

import java.util.ArrayList;
import java.util.List;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.gc.materialdesign.treeview.model.TreeNode;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import app.database.HeartRateDAO;
import app.dto.HeartRateDTO;

public class HistoryHeartRate extends Activity {
	BarGraph bg;
	HeartRateDAO dao;
	List<HeartRateDTO> listData;
	boolean[] isMonthHaveData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_heartrate);
		dao = new HeartRateDAO(this);
		listData = dao.getListHeartRate();
		isMonthHaveData = new boolean[12];
		initChart();
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
			bar.setName(dateMonth);
			bar.setValue((float) listData.get(i).getHeartRate());
			bar.setValueString(String.valueOf(listData.get(i).getHeartRate()));
			aBars.add(bar);
			if ((listData.size() - i) > 10) {
				break;
			}
		}

		final BarGraph barGraph = (BarGraph) findViewById(R.id.chart_history_step);
		bg = barGraph;
		barGraph.setBars(aBars);
	}

	private void initList() {
		final TreeNode root = TreeNode.root();
		TreeNode m1 = new TreeNode(
				new HistoryHeartRateItem.IconTreeItem(
						R.drawable.heart_red_small, "Thang 1"))
				.setViewHolder(new HistoryHeartRateItem(this));
	}
}
