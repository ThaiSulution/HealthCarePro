package app.healthcare.bmi;

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
public class HistoryBMIAdapter extends BaseAdapter {

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

	public HistoryBMIAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.item_history_bmi, null);
		TextView typeDate = (TextView) vi
				.findViewById(R.id.history_child_item_date);
		TextView typeRate = (TextView) vi
				.findViewById(R.id.history_child_item_rate);
		TextView typeStatusText = (TextView) vi
				.findViewById(R.id.history_child_item_type_status);
		ImageView status = (ImageView) vi
				.findViewById(R.id.history_bmi_child_item_type_image);
		HashMap<String, String> listBMI = new HashMap<String, String>();
		listBMI = data.get(position);
		// Setting all values in listview
		typeDate.setText(listBMI.get(HistoryBMI.KEY_DATE) + " "
				+ listBMI.get(HistoryBMI.KEY_TIME));
		typeRate.setText(listBMI.get(HistoryBMI.KEY_RATE));
		typeStatusText.setText(listBMI.get(HistoryBMI.KEY_STATUS));
		if (listBMI.get(HistoryBMI.KEY_STATUS).equals(
				"Thể trạng gầy, thiếu năng lượng")) {
			imageLoader.DisplayImage(String.valueOf(R.drawable.gay), status);
		} else if (listBMI.get(HistoryBMI.KEY_STATUS).equals(
				"Bạn đang thừa cân")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.beophi1), status);
		} else if (listBMI.get(HistoryBMI.KEY_STATUS).equals(
				"Thân hình bình thường")) {
			imageLoader.DisplayImage(String.valueOf(R.drawable.binhthuong),
					status);
		} else if (listBMI.get(HistoryBMI.KEY_STATUS).equals(
				"Thừa cân - tiền béo phì")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.beophi1), status);
		} else if (listBMI.get(HistoryBMI.KEY_STATUS)
				.equals("Béo phì cấp độ 1")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.beophi1), status);
		} else if (listBMI.get(HistoryBMI.KEY_STATUS)
				.equals("Béo phì cấp độ 2")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.beophi2), status);
		} else if (listBMI.get(HistoryBMI.KEY_STATUS)
				.equals("Béo phì cấp độ 3")) {
			imageLoader
					.DisplayImage(String.valueOf(R.drawable.beophi3), status);
		}

		return vi;
	}
}
