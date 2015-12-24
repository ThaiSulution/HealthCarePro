package app.healthcare.symptom.activity;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import app.healthcare.R;

public class Symptom4Part extends ListActivity {
	
	private AutoCompleteTextView autoSearch;
	TextView selection;
	String arr[]={
			 
			 "Đau bụng dưới", "Đau bụng trên", 
			 "Đau sườn", 
			 "Chán ăn", "Đầy hơi bụng", 
			 "Ngứa da",
			  "Nổi mề đây", "Nổi mẩn da",
			"Vàng da"
			 , ""};
	ArrayAdapter<String>adapter1=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo2);
		adapter1=new ArrayAdapter<String>
		(this,
		android.R.layout.simple_list_item_1,
		arr);
	//GĂ¡n Adapter vĂ o ListView
	//Nhá»› lĂ  pháº£i Ä‘áº·t id cho ListView theo Ä‘Ăºng quy táº¯c
	setListAdapter(adapter1);
	
	selection=(TextView) findViewById(R.id.selection);
	autoSearch = (AutoCompleteTextView) findViewById(R.id.autoSearch);
	autoSearch.setAdapter(adapter1);
	autoSearch.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			//text.setText(autotext.getText());
		}
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
	});
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		// xử lý bệnh cho từng bộ phận
		Intent iBenh1 = new Intent(this, Diasease1.class );
		//đau bao tử
				if(position==0 || position==1 || position==3|| position==4)
				{
					 startActivity(iBenh1);
				}
	}
}
