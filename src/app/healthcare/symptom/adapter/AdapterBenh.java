package app.healthcare.symptom.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import app.healthcare.R;
import app.healthcare.symptom.object.Benh;

public class AdapterBenh extends BaseAdapter {
	Context mContext;
	List<Benh> listBenh;

	public AdapterBenh(Context mContext) {
		this.mContext = mContext;
		this.listBenh = new ArrayList<Benh>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listBenh.size();
	}

	public void addBenh(Benh ma) {
		listBenh.add(ma);
	}

	public void clearAdapterBenh() {
		listBenh.clear();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.listBenh.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Benh lst = listBenh.get(position);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.singlefood, null);
		}
		TextView nameBenh = (TextView) convertView
				.findViewById(R.id.txtMonanSingle);
		nameBenh.setText(lst.getNameBenh());

		return convertView;
	}

}
