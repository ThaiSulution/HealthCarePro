package app.healthcare.symptom.activity;


import java.util.ArrayList;
import java.util.List;

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
import app.healthcare.symptom.object.SickSymptomDAO;
import app.healthcare.symptom.object.SickSymptomDTO;

public class Symptom4Part extends ListActivity {
	
	private AutoCompleteTextView autoSearch;
	TextView selection;
	
	ArrayAdapter<String>adapter1=null;
	SickSymptomDAO sickSymptomDAO;
	List<String> arr = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo2);
		sickSymptomDAO = new SickSymptomDAO(this);
		List<SickSymptomDTO> dtos = sickSymptomDAO.searchSymptom(" Bụng");
		arr = new ArrayList<String>();
		for(SickSymptomDTO dto : dtos){
			arr.add(dto.getTrieuChung());
		}
		adapter1=new ArrayAdapter<String>
		(this,
		android.R.layout.simple_list_item_1,
		arr);
	setListAdapter(adapter1);
	
	//selection=(TextView) findViewById(R.id.selection);
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
		Diasease1.symptom = arr.get(position);
		Diasease1.viTri = " Bụng";
		// xử lý bệnh cho từng bộ phận
		Intent iBenh1 = new Intent(this, Diasease1.class );
		startActivity(iBenh1);
	}
}
