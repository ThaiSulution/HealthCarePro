package app.healthcare.drug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import app.healthcare.R;

public class ListDrug extends Activity implements TextWatcher {
	DrugDAO dao;
	ListView listDrug;
	int indexSelection = -1;
	ProgressDialog dialogProgess;
	Handler handler = new Handler() {  
	    @Override  
	    public void handleMessage(Message msg) {
	    	dialogProgess.dismiss(); 
	    }  
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listdrug);
		
		dao = new DrugDAO(this);
		listDrug = (ListView) findViewById(R.id.listBenh);
		final AutoCompleteTextView searchBox = (AutoCompleteTextView) findViewById(R.id.autoSearch);
		final List<DrugDTO> dtos = dao.getListDrug();
		final List<String> drugName = new ArrayList<String>();
		if (dtos.size() == 0) {
			dialogProgess = ProgressDialog.show(this, "",
     				"Vui lòng chờ");
			new Thread(new Runnable() {  
		         @Override  
		         public void run() {  
		        	 
		        	 String line;
		 			InputStream in = getResources().openRawResource(R.drawable.data);
		 			InputStreamReader inreader = new InputStreamReader(in);
		 			BufferedReader bufreader = new BufferedReader(inreader);
		 			if (in != null) {
		 				try {
		 					DrugDTO dto = new DrugDTO();
		 					while ((line = bufreader.readLine()) != null) {
		 						if (line.startsWith("Tên thuốc: ")) {
		 							dto.setTenThuoc(line.replace("Tên thuốc: ", ""));
		 						}
		 						if (line.startsWith("Nhóm Dược lý: ")) {
		 							dto.setNhomDuocLy(line
		 									.replace("Nhóm Dược lý: ", ""));
		 						}
		 						if (line.startsWith("Thành phần: ")) {
		 							dto.setThanhPhan(line.replace("Thành phần: ", ""));
		 						}
		 						if (line.startsWith("Chỉ định: ")) {
		 							dto.setChiDinh(line.replace("Chỉ định: ", ""));
		 						}
		 						if (line.startsWith("Chống chỉ định: ")) {
		 							dto.setChongChiDinh(line.replace(
		 									"Chống chỉ định: ", ""));
		 						}
		 						if (line.startsWith("Tương tác thuốc: ")) {
		 							dto.setTuongTacThuoc(line.replace(
		 									"Tương tác thuốc: ", ""));
		 						}
		 						if (line.startsWith("Chú ý đề phòng: ")) {
		 							dto.setChuYDePhong(line.replace("Chú ý đề phòng: ",
		 									""));
		 						}
		 						if (line.startsWith("Tác dụng phụ: ")) {
		 							dto.setTacDungPhu(line
		 									.replace("Tác dụng phụ: ", ""));
		 						}
		 						if (line.startsWith("Liều lượng: ")) {
		 							dto.setLieuLuong(line.replace("Liều lượng: ", ""));
		 						}
		 						if (line.startsWith("end")) {
		 							dao.insertRatioBMI(dto);
		 							dtos.add(dto);
		 							dto = new DrugDTO();
		 						}
		 					}
		 				} catch (IOException e) {
		 					e.printStackTrace();
		 					dialogProgess.dismiss();
		 				}
		 			}
		 			
		 			for (int i = 0; i < dtos.size(); i++) {
		 				DrugDTO temp = dtos.get(i);
		 				String s2 = String.valueOf(temp.getTenThuoc());
		 				drugName.add(s2);
		 			}
		             handler.sendEmptyMessage(0); 
		          }  
		      }).start(); 
			
			
		} else {
			for (int i = 0; i < dtos.size(); i++) {
 				DrugDTO temp = dtos.get(i);
 				String s2 = String.valueOf(temp.getTenThuoc());
 				drugName.add(s2);
 			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, drugName);
		listDrug.setAdapter(adapter);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, drugName);
		searchBox.setAdapter(adapter1);
		searchBox.addTextChangedListener(this);
		Button btnSearch = (Button) findViewById(R.id.btn_search_drug);
		final Intent detail = new Intent(this, DrugDetail.class);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DrugDTO result = dao.getDrug(searchBox.getText().toString());
				if (result != null) {
					DrugDetail.chiDinh = result.getChiDinh();
					DrugDetail.chongChiDinh = result.getChongChiDinh();
					DrugDetail.chuY = result.getChuYDePhong();
					DrugDetail.lieuLuong = result.getLieuLuong().replace("+",
							"");
					DrugDetail.nhomDuocLy = result.getNhomDuocLy();
					DrugDetail.tacDungPhu = result.getTacDungPhu();
					DrugDetail.tenThuoc = result.getTenThuoc();
					DrugDetail.thanhPhan = result.getThanhPhan();
					DrugDetail.tuongTacThuoc = result.getTuongTacThuoc();
					startActivity(detail);
				}

			}
		});
		listDrug.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				DrugDTO result = dtos.get(pos);
				if (result != null) {
					DrugDetail.chiDinh = result.getChiDinh();
					DrugDetail.chongChiDinh = result.getChongChiDinh();
					DrugDetail.chuY = result.getChuYDePhong();
					DrugDetail.lieuLuong = result.getLieuLuong().replace("+",
							"");
					DrugDetail.nhomDuocLy = result.getNhomDuocLy();
					DrugDetail.tacDungPhu = result.getTacDungPhu();
					DrugDetail.tenThuoc = result.getTenThuoc();
					DrugDetail.thanhPhan = result.getThanhPhan();
					DrugDetail.tuongTacThuoc = result.getTuongTacThuoc();
					startActivity(detail);
				}
			}

		});
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}
}
