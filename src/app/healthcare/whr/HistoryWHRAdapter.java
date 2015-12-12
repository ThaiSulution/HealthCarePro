package app.healthcare.whr;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.healthcare.R;
import app.healthcare.heartratehistory.ImageLoader;

@SuppressLint("InflateParams")
public class HistoryWHRAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;

	public ArrayList<HashMap<String, String>> getData() {
		return data;
	}

	public void setData(ArrayList<HashMap<String, String>> data) {
		this.data = data;
	}

	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public HistoryWHRAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_history_whr, null);
		TextView typeDate = (TextView) vi
				.findViewById(R.id.history_child_item_date);
		TextView typeRate = (TextView) vi
				.findViewById(R.id.history_child_item_rate);
		TextView typeStatusText = (TextView) vi
				.findViewById(R.id.history_child_item_type_status);
		ImageView status = (ImageView) vi
				.findViewById(R.id.history_whr_child_item_type_image);
		HashMap<String, String> listWHR = new HashMap<String, String>();
		listWHR = data.get(position);
		// Setting all values in listview
		typeDate.setText(listWHR.get(HistoryWHR.KEY_DATE) + " "
				+ listWHR.get(HistoryWHR.KEY_TIME));
		typeRate.setText(listWHR.get(HistoryWHR.KEY_RATE));
		typeStatusText.setText(listWHR.get(HistoryWHR.KEY_STATUS));
		if (listWHR.get(HistoryWHR.KEY_STATUS).equals("Sức khỏe tốt")) {
			imageLoader.DisplayImage(String.valueOf(R.drawable.good), status);
		} else if (listWHR.get(HistoryWHR.KEY_STATUS).equals("Ít nguy hiểm")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.warning), status);
		} else if (listWHR.get(HistoryWHR.KEY_STATUS).equals(
				"Mức độ nguy hiểm trung bình")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.warning), status);
		} else if (listWHR.get(HistoryWHR.KEY_STATUS).equals(
				"Rất nguy hiểm")) {
			imageLoader.DisplayImage(String.valueOf(R.drawable.risk), status);
		}
		return vi;
	}
}
