package docbaoonline.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import docbaoonline.activity.R;
import docbaoonline.models.Variables;

public class PaperAdapter extends ArrayAdapter<String>{
	
	private Context context;
	
	public PaperAdapter(Context context, int textViewResourceId,
			String[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}





	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_main, null);
		
		ImageView ivIcon = (ImageView)rowView.findViewById(R.id.ivIcon);
		ivIcon.setImageResource(Variables.ICONS[position]);
		
		TextView tvPaper = (TextView)rowView.findViewById(R.id.tvPaper);
		tvPaper.setText(Variables.PAPERS[position]);
		
		return rowView;
	}

}
