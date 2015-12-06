package app.healthcare.heartratehistory;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.healthcare.R;
public class HistoryAdapter extends BaseAdapter {
	 
	    private Activity activity;
	    private ArrayList<HashMap<String, String>> data;
	    public ArrayList<HashMap<String, String>> getData() {
			return data;
		}

		public void setData(ArrayList<HashMap<String, String>> data) {
			this.data = data;
		}

		private static LayoutInflater inflater=null;
	    public ImageLoader imageLoader; 
	 
	    public HistoryAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        imageLoader=new ImageLoader(activity.getApplicationContext());
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
	        View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.item_history_heartrate, null);
	 
	        TextView typeFeel = (TextView)vi.findViewById(R.id.history_child_item_type_feel); 
	        TextView typeDate = (TextView)vi.findViewById(R.id.history_child_item_date); 
	        TextView typeRate = (TextView)vi.findViewById(R.id.history_child_item_rate);
	        ImageView motionStatus=(ImageView)vi.findViewById(R.id.history_child_item_type_image); 
	 
	        HashMap<String, String> listHeartRate = new HashMap<String, String>();
	        listHeartRate = data.get(position);
	 
	        // Setting all values in listview
	        typeFeel.setText(listHeartRate.get( HistoryHeartRate.KEY_FEEL));
	        typeDate.setText(listHeartRate.get(HistoryHeartRate.KEY_TIME));
	        typeRate.setText(listHeartRate.get(HistoryHeartRate.KEY_RATE));
	        
	        imageLoader.DisplayImage(listHeartRate.get( HistoryHeartRate.KEY_MOTION_STATUS), motionStatus);
	        return vi;
	    }
}
