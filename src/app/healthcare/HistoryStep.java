package app.healthcare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

public class HistoryStep extends Activity{
	BarGraph bg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_step);
		init();
	}

	private void init() {
		final Resources resources = getResources();
		ArrayList<Bar> aBars = new ArrayList<Bar>();
		Bar bar;
        
	for (int i = GoogleFitService.listDataStep.size()-1;i>=0;i--){
			bar = new Bar();
			bar.setColor(resources.getColor(R.color.red));
			bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
			String dateMonth =GoogleFitService.listDataStep.get(i).getTime(); 
			bar.setName(dateMonth);
			bar.setValue((float)GoogleFitService.listDataStep.get(i).getStep());
			bar.setValueString(String.valueOf(GoogleFitService.listDataStep.get(i).getStep()));
			aBars.add(bar);
			if ((GoogleFitService.listDataStep.size() - i )>15){
				break;
			}
		}

		final BarGraph barGraph = (BarGraph)findViewById(R.id.chart_history_step);
		bg = barGraph;
		barGraph.setBars(aBars);
	}
}
