package app.healthcare;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import app.database.RatioBMIDAO;
import app.database.UserDAO;
import app.dto.RatioBMIDTO;
import app.dto.UserDTO;

import com.gc.materialdesign.widgets.Dialog;

@SuppressLint("RtlHardcoded") public class RatioBMIFragment extends Fragment {
	CheckBox cbWHO;
	CheckBox cbIDIAndWPRO;
	EditText tbxHeight;
	EditText tbxWeight;
	TextView tbxImpact;
	TextView tbxResult;

	Button btnReinphut;
	Button btnCalculateBMI;
	TableLayout tableData;
	UserDAO userdao;
	RatioBMIDAO dao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ratio_bmi,
				container, false);
		initView(rootView);
		return rootView;
	}

	/**
	 * khoi tao cac control
	 * 
	 * @param rootView
	 *            container chua cac control
	 */
	private void initView(View rootView) {
		userdao = new UserDAO(getActivity());
		dao = new RatioBMIDAO(getActivity());
		tableData = (TableLayout) rootView.findViewById(R.id.tableDataBMI);
		cbIDIAndWPRO = (CheckBox) rootView.findViewById(R.id.cbIDIAndWPRO);
		cbIDIAndWPRO.setOnCheckedChangeListener(listener);
		cbWHO = (CheckBox) rootView.findViewById(R.id.cbWHO);
		cbWHO.setChecked(true);
		cbWHO.setOnCheckedChangeListener(listener);
		tbxHeight = (EditText) rootView.findViewById(R.id.tbxHeight);
		tbxWeight = (EditText) rootView.findViewById(R.id.tbxWeight);
		btnCalculateBMI = (Button) rootView.findViewById(R.id.btnCalculateBMI);
		btnCalculateBMI.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				if (tbxHeight.getText().length() == 0) {
					Toast.makeText(getActivity(), "Nhập chiều cao của bạn",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (tbxWeight.getText().length() == 0) {
					Toast.makeText(getActivity(), "Nhập cân nặng của bạn",
							Toast.LENGTH_SHORT).show();
					return;
				}
				calculateBMI();
			}
		});
		btnReinphut = (Button) rootView.findViewById(R.id.btnReinput);
		btnReinphut.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				reInput();
			}
		});
		tbxResult = (TextView) rootView.findViewById(R.id.tbxResultBMI);
		tbxImpact = (TextView) rootView.findViewById(R.id.tbxImpact);

		try {
			buildTableData();

		} catch (NullPointerException e) {
			Log.e("nulldata", "chua co du lieu");
		}
	}

	private void buildTableData() {
		UserDTO userdto = userdao.getUser();
		List<RatioBMIDTO> listdata = dao.getListRatioBMI(userdto.getUserId());
		int rows = listdata.size();
		int cols = 3;
		for (int i = 0; i < rows; i++) {

			TableRow row = new TableRow(getActivity());
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			for (int j = 0; j < cols; j++) {
				TextView tv = new TextView(getActivity());
				tv.setTextColor(Color.YELLOW);
				if (j == 0) {
					tv.setGravity(Gravity.LEFT);
					tv.setText(listdata.get(i).getTime());
				} else if (j == 1) {
					tv.setGravity(Gravity.CENTER);
					tv.setText(listdata.get(i).getRatio());
				} else if (j == 2) {
					tv.setGravity(Gravity.RIGHT);
					tv.setText(listdata.get(i).getStatus());
				}
				row.addView(tv);
			}
			tableData.addView(row);
		}
	}

	/**
	 * nhap lai thong tin
	 */
	public void reInput() {
		tbxHeight.setText("");
		tbxWeight.setText("");
		tbxImpact.setText("Mức độ ảnh hưởng");
		tbxResult.setText("BMI");

	}

	@SuppressWarnings("deprecation")
	public void calculateBMI() {
		float height = Float.parseFloat(tbxHeight.getText().toString());
		float weight = Float.parseFloat(tbxWeight.getText().toString());
		float ratioBMI = weight / (height * height);
		String result;
		if (cbIDIAndWPRO.isChecked()) {
			if (ratioBMI < 18.5) {
				result = "Thể trạng gầy, thiếu năng lượng";
			} else if (ratioBMI == 23) {
				result = "Bạn đang thừa cân";
			} else if (ratioBMI >= 18.5 && ratioBMI < 23) {
				result = "Thân hình bình thường";
			} else if (ratioBMI > 23 && ratioBMI < 25) {
				result = "Thừa cân - tiền béo phì";
			} else if (ratioBMI >= 25 && ratioBMI < 30) {
				result = "Béo phì cấp độ 1";
			} else if (ratioBMI == 30) {
				result = "Béo phì cấp độ 2";
			} else {
				result = "Béo phì cấp độ 3";
			}
		} else {
			cbWHO.setChecked(true);
			if (ratioBMI < 18.5) {
				result = "Thể trạng gầy, thiếu năng lượng";
			} else if (ratioBMI == 25) {
				result = "Bạn đang thừa cân";
			} else if (ratioBMI >= 18.5 && ratioBMI < 25) {
				result = "Thân hình bình thường";
			} else if (ratioBMI > 25 && ratioBMI < 30) {
				result = "Thừa cân - tiền béo phì";
			} else if (ratioBMI >= 30 && ratioBMI < 35) {
				result = "Béo phì cấp độ 1";
			} else if (ratioBMI >= 35 && ratioBMI < 40) {
				result = "Béo phì cấp độ 2";
			} else {
				result = "Béo phì cấp độ 3";
			}
		}
		RatioBMIDTO dto = new RatioBMIDTO();
		ratioBMI *= 10;
		int temp = (int) (ratioBMI);
		ratioBMI = temp;
		ratioBMI /= 10;
		tbxResult.setText(String.valueOf(ratioBMI));
		tbxImpact.setText(result);
		dto.setRatio(String.valueOf(ratioBMI));
		dto.setStatus(result);

		Constants.getInstance().getTime().setToNow();
		int date = Constants.getInstance().getTime().monthDay;
		int month = Constants.getInstance().getTime().month + 1;
		int year = Constants.getInstance().getTime().year;
		dto.setTime(String.valueOf(date) + "/"
				+ String.valueOf(month) +"/"
				+ String.valueOf(year) + "");
		dto.setUserId(1);
		dao.insertRatioBMI(dto);
//		ImageView im = 
		Dialog dialog = new Dialog(getActivity(), "Chỉ số BMI",
				"Chỉ số BMI của bạn là: " + String.valueOf(ratioBMI) + "\n"
						+ result,0, app.healthcare.R.drawable.bmi_icon);
		dialog.show();
	}

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if (isChecked) {
				switch (arg0.getId()) {
				case R.id.cbIDIAndWPRO:
					cbIDIAndWPRO.setChecked(true);
					cbWHO.setChecked(false);
					break;
				case R.id.cbWHO:
					cbWHO.setChecked(true);
					cbIDIAndWPRO.setChecked(false);
					break;
				}
			}
		}
	};
}
