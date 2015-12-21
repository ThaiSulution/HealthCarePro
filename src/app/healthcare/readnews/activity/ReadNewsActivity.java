package app.healthcare.readnews.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import app.healthcare.R;
import app.healthcare.readnews.adapters.PaperAdapter;
import app.healthcare.readnews.models.Variables;

public class ReadNewsActivity extends ListActivity {

	private PaperAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		adapter = new PaperAdapter(this, R.layout.read_news_main_activity,
				Variables.PAPERS);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (Variables.PAPERS[position].equalsIgnoreCase("haivl")) {
			Intent intent = new Intent(this, NoRssActivity.class);
			intent.putExtra(Variables.paper, position);
			intent.putExtra(Variables.position, 0);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, CategotyActivity.class);
			intent.putExtra(Variables.paper, position);
			startActivity(intent);
		}

		// overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_enter);
		super.onListItemClick(l, v, position, id);
	}
}
