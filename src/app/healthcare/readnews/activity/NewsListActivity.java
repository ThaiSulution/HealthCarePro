package app.healthcare.readnews.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import app.healthcare.R;
import app.healthcare.readnews.adapters.NewsListAdapter;
import app.healthcare.readnews.models.RssItem;
import app.healthcare.readnews.models.Variables;

public class NewsListActivity extends ListActivity {

	private NewsListAdapter adapter;
	private int key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		int paper = bundle.getInt(Variables.paper);
		int category = bundle.getInt(Variables.category);
		key = bundle.getInt(Variables.key);
		setTitle(Variables.PAPERS[paper] + " - "
				+ Variables.CATEGORIES[paper][category]);
		List<RssItem> items = Variables.MAP.get(key);
		adapter = new NewsListAdapter(this, R.layout.newslist, items);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (isNetworkAvaiable()) {
			Intent intent = new Intent(NewsListActivity.this, NewActivity.class);
			intent.putExtra(Variables.key, key);
			intent.putExtra(Variables.position, position);
			startActivity(intent);

			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		} else{
			AlertDialog.Builder builder = new AlertDialog.Builder(NewsListActivity.this);
			builder.setTitle("Network no avaiable");
			builder.setMessage("Please check the network connection!");
			builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					
				}
			});
			builder.show();
		}
	}

	private boolean isNetworkAvaiable() {
		// Phai add permission access network state
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		Log.d("Network 3", (info != null) + "  " + info);
		return (info != null);
	}
}
