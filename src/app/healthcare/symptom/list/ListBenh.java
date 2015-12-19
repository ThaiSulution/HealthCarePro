
package app.healthcare.symptom.list;

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

public class ListBenh extends ListActivity {
	List<Benh> array;
	ListView lst;
	private Context mContext;
	AdapterBenh adapter;
	private AutoCompleteTextView autoSearch;
//	private Button btnSearch;
	//AdapterDB mDB = new AdapterDB(this);
	private Cursor mCursor;
	
	//
	TextView selection;
	String arr[]={
			  "Cháº­m lĂ nh váº¿t loĂ©t hoáº·c nhiá»…m trĂ¹ng thÆ°á»�ng xuyĂªn", "ChÆ°á»›ng bá»¥ng",
			 "Ä�au sÆ°á»�n", "Ä�au ngá»±c", "Ä�au bá»¥ng dÆ°á»›i", "Ä�au bá»¥ng trĂªn", "Ä�Ă³i â€“ Äƒn nhiá»�u", "Ä�áº§y hÆ¡i bá»¥ng", "KhĂ´ miá»‡ng, ngá»©a da",
			 "Má»‡t má»�i","Má»�i máº¯t", "NĂ´n", "Ná»•i má»� Ä‘Ă¢y", "Ná»•i máº©n da", "NgĂ¡y", "LĂ£ng tai", 
			 "Uá»‘ng nhiá»�u quĂ¡ má»©c","Tiá»ƒu nhiá»�u"
			 ,"Thá»Ÿ khĂ² khĂ¨","Sá»¥t cĂ¢n","VĂ ng da"
			 , ""};
	ArrayAdapter<String>adapter1=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_demo);
		
		//ListActivity
		//Thiáº¿t láº­p Data Source cho Adapter
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

									
				
		//Activity
		
		/*mContext = this;
		adapter = new AdapterBenh(mContext);
		//autoSearch = (AutoCompleteTextView) findViewById(R.id.autoSearch);
		lst = (ListView) findViewById(R.id.listMonan);
		lst.setAdapter(adapter);
		String mTypeId = "";
		try {
			Bundle mTheLoai = getIntent().getExtras();
			mTypeId = mTheLoai.getString("mIdTheLoai");
		} catch (Exception e) {
		}

		mDB.open();
		if (mTypeId.equals(""))
		{
			mCursor = mDB.laybenh();
			startManagingCursor(mCursor);
		}
		else
		{
			mCursor = mDB.laybenhtheloai(mTypeId);
		    startManagingCursor(mCursor);
		}
		if (mCursor.moveToFirst()) {
			do {
				adapter.addBenh(new Benh(mCursor.getString(0), mCursor.getString(1)));
			} while (mCursor.moveToNext());
		}
		adapter.notifyDataSetChanged();
		mDB.close();
		
		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Benh item = (Benh) arg0.getItemAtPosition(arg2);
				String mIdBenh = item.getBenhId().toString();
				Intent mDetailBenh = new Intent(ListBenh.this,
						FoodsDetail.class);
				mDetailBenh.putExtra("idbenh", mIdBenh);
				startActivity(mDetailBenh);
			}
		});
		autoSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mDB.open();
				adapter.clearAdapterBenh();
				Cursor mCursor = mDB.getBenhTheoTen(autoSearch.getEditableText().toString());
				if (mCursor.moveToFirst()) {
					do {
						adapter.addBenh((new Benh(mCursor.getString(0), mCursor.getString(1))));
					   } while (mCursor.moveToNext());	
				}
				adapter.notifyDataSetChanged();
				mDB.close();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	protected void findBenh(String key) {
		mDB.open();
		mCursor = mDB.timkiembenh(key);
		array = new ArrayList<Benh>();
		if (array != null || array.size() > 0)
			array.clear();
		if (mCursor.moveToFirst() && mCursor.getCount() > 0) {
			do {
				adapter.addBenh(new Benh(mCursor.getString(0), mCursor.getString(1)));
			} while (mCursor.moveToNext());
		}
		adapter.notifyDataSetChanged();
		mDB.close();
*/	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		/*String txt ="arr[position]";
		if (txt.equals("arr[0]"))
			Toast.makeText(this, "Bá»‡nh tiá»ƒu Ä‘Æ°Ä�ng",  position);*/
		//Ä‘au dáº¡ dĂ y
		if(position==0)
		{
			Intent iBenh3 = new Intent(this, Diasease.class );
			startActivity(iBenh3);
		}
		// tiá»ƒu Ä‘Æ°á»�ng
		
		if(position==0 || position==6 || position==6 || position==8 || position==9 || position==10 ||
				position==15 || position==16 || position==17 || position==19 )
		{
			Intent iBenh3 = new Intent(this, Diasease.class );
			startActivity(iBenh3);
		}
		
		//Ä‘au tim
	}
}
