package app.healthcare.docbaoonline.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.healthcare.R;

@SuppressLint({ "ViewHolder", "InflateParams" }) public class CategoryAdapter extends ArrayAdapter<String> {

	private Context context;
	private int icon;
	private String[] data;

	public CategoryAdapter(Context context, int icon, String[] data) {
		super(context, icon, data);
		this.context = context;
		this.icon = icon;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		@SuppressWarnings("static-access")
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.category, null);

		ImageView ivIcon = (ImageView) rowView.findViewById(R.id.ivIcon);
		ivIcon.setImageResource(icon);

		TextView tvPaper = (TextView) rowView.findViewById(R.id.tvCategory);
		tvPaper.setText(data[position]);

		return rowView;
	}

}
