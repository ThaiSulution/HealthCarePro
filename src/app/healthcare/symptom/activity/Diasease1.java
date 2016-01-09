package app.healthcare.symptom.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import app.healthcare.R;
import app.healthcare.symptom.object.SickSymptomDAO;
import app.healthcare.symptom.object.SickSymptomDTO;

public class Diasease1 extends ListActivity {

	TextView selection;
	public static String symptom = "";
	public static String viTri = "";
	ArrayAdapter<String> adapter2 = null;
	List<String> arr = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo3);
		SickSymptomDAO dao = new SickSymptomDAO(this);
		List<SickSymptomDTO> dtos = dao.searchSick(symptom, viTri);
		for (SickSymptomDTO dto : dtos) {
			arr.add(dto.getTenBenh());
		}
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		setListAdapter(adapter2);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		SickDetail.sickName = arr.get(position).trim();
		Intent iDaDay = new Intent(this, SickDetail.class);
		startActivity(iDaDay);
		finish();

	}
}
