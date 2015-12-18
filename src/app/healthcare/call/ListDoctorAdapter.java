package app.healthcare.call;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import app.healthcare.R;
import app.healthcare.heartratehistory.ImageLoader;

@SuppressLint("InflateParams")
public class ListDoctorAdapter extends BaseAdapter {

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

	public ListDoctorAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
			vi = inflater.inflate(R.layout.item_list_doctor, null);
		TextView typeName = (TextView) vi.findViewById(R.id.name_doctor);
		TextView typeNumberCall = (TextView) vi.findViewById(R.id.number_call);
		TextView typeSkypeNumber = (TextView) vi
				.findViewById(R.id.number_skype_call);
		HashMap<String, String> listDoctor = new HashMap<String, String>();
		listDoctor = data.get(position);
		// Setting all values in listview
		typeName.setText(listDoctor.get(ListDoctor.KEY_NAME));
		typeNumberCall.setText(listDoctor.get(ListDoctor.KEY_NUMBER_CALL));
		typeSkypeNumber.setText(listDoctor.get(ListDoctor.KEY_NUMBER_SKYPE));
		return vi;
	}
}
