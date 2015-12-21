package app.healthcare.docbaoonline.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import app.healthcare.R;
import app.healthcare.docbaoonline.adapters.CategoryAdapter;
import app.healthcare.docbaoonline.models.RssItem;
import app.healthcare.docbaoonline.models.RssParser;
import app.healthcare.docbaoonline.models.Variables;

public class CategotyActivity extends ListActivity {

	private CategoryAdapter adapter;
	private int paper;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		paper = intent.getExtras().getInt(Variables.paper);
		setTitle(Variables.PAPERS[paper]);
		adapter = new CategoryAdapter(this, Variables.ICONS[paper],
				Variables.CATEGORIES[paper]);
		setListAdapter(adapter);
	}

	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// Intent intent = new Intent(CategotyActivity.this,
	// NewsListActivity.class);
	// int key = 1000 * paper + position;
	// intent.putExtra(Variables.paper, paper);
	// intent.putExtra(Variables.category, position);
	// intent.putExtra(Variables.key, key);
	// Log.d("paper", paper+"");
	// Log.d("category", position+"");
	// Log.d("key", key+"");
	//
	// if (!Variables.MAP.containsKey(key)) {
	// RssParser rssParser = new RssParser();
	// List<RssItem> items = rssParser
	// .parser(Variables.LINKS[paper][position]);
	// Variables.MAP.put(key, items);
	// }
	// Log.d("debug", "debug");
	// startActivity(intent);
	//
	// overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_enter);
	//
	// super.onListItemClick(l, v, position, id);
	// }

	// Tao 1 thread load du lieu

	private boolean isNetworkAvaiable() {
		// Phai add permission access network state
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		Log.d("Network 2", (info != null) + "  " + info);
		return (info != null);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (isNetworkAvaiable()) {
			if (Variables.PAPERS[paper].equalsIgnoreCase("Kênh 14")) {
				Intent intent = new Intent(this, NoRssActivity.class);
				intent.putExtra(Variables.paper, paper);
				intent.putExtra(Variables.position, position);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_enter);
			} else {
				int key = 1000 * paper + position;
				if (Variables.MAP.containsKey(key)) {
					Intent intent = new Intent(this, NewsListActivity.class);
					intent.putExtra(Variables.paper, paper);
					intent.putExtra(Variables.category, position);
					intent.putExtra(Variables.key, key);
					startActivity(intent);
					overridePendingTransition(R.anim.zoom_enter,
							R.anim.zoom_enter);
				} else {
					dialog = ProgressDialog.show(this, "", "Loading...");
					new RssTask().execute(position);
				}
			}
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(CategotyActivity.this);
			builder.setTitle("Network no avaiable");
			builder.setMessage("Please check the network connection!");
			builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
					
				}
			});
			builder.show();
		}

		super.onListItemClick(l, v, position, id);
	}

	private class RssTask extends AsyncTask<Integer, Void, Void> {
		private int position;
		private int key;

		@Override
		protected Void doInBackground(Integer... params) {
			position = params[0];
			key = 1000 * paper + position;
			RssParser rssParser = new RssParser();
			List<RssItem> items = rssParser
					.parser(Variables.LINKS[paper][position]);
			Variables.MAP.put(key, items);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (dialog != null) {
				dialog.dismiss();
			}

			Intent intent = new Intent(CategotyActivity.this,
					NewsListActivity.class);
			intent.putExtra(Variables.paper, paper);
			intent.putExtra(Variables.category, position);
			intent.putExtra(Variables.key, key);
			startActivity(intent);
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_enter);
			super.onPostExecute(result);
		}
	}
}
