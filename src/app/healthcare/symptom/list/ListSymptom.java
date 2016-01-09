package app.healthcare.symptom.list;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import app.healthcare.R;
import app.healthcare.symptom.activity.Diasease;
import app.healthcare.symptom.adapter.AdapterBenh;
import app.healthcare.symptom.object.Benh;
import app.healthcare.symptom.object.SickDAO;
import app.healthcare.symptom.object.SickSymptomDAO;
import app.healthcare.symptom.object.SickSymptomDTO;

public class ListSymptom extends ListActivity {
	List<Benh> array;
	ListView lst;
	private Context mContext;
	AdapterBenh adapter;
	private AutoCompleteTextView autoSearch;
	// private Button btnSearch;
	// AdapterDB mDB = new AdapterDB(this);
	private Cursor mCursor;

	//
	TextView selection;
	ArrayAdapter<String> adapter1 = null;
	SickSymptomDAO sickSymptomDAO;
	SickDAO sickDao;
	List<String> arr = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo);
		sickSymptomDAO = new SickSymptomDAO(this);
		sickDao = new SickDAO(this);
		List<SickSymptomDTO> dtos = sickSymptomDAO.getListSymptom();
		for (int i = 0; i < dtos.size(); i++) {
			SickSymptomDTO temp = dtos.get(i);
			String s2 = String.valueOf(temp.getTrieuChung());
			arr.add(s2);
		}
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		setListAdapter(adapter1);
		//selection = (TextView) findViewById(R.id.selection);
		autoSearch = (AutoCompleteTextView) findViewById(R.id.autoSearch);
		autoSearch.setAdapter(adapter1);
		autoSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// text.setText(autotext.getText());
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Diasease.symptom = arr.get(position);
		Intent iBenh3 = new Intent(this, Diasease.class);
		startActivity(iBenh3);

	}
}
