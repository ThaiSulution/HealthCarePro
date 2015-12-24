package app.healthcare.symptom.activity;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import app.healthcare.R;

public class Diasease1 extends ListActivity {
	
	TextView selection;
	String arr[]={
			  " Đau dạ dày"};
	ArrayAdapter<String>adapter2=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo3);
		//ListActivity
				//Thiáº¿t láº­p Data Source cho Adapter
						adapter2=new ArrayAdapter<String>
							(this,
							android.R.layout.simple_list_item_1,
							arr);
							setListAdapter(adapter2);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent iDaDay = new Intent(this, Diabete1.class);
		startActivity(iDaDay);
		finish();
		
	}
}
