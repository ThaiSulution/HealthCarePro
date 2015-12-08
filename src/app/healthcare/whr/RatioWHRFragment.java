package app.healthcare.whr;

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
import app.database.RatioWHRDAO;
import app.database.UserDAO;
import app.dto.RatioWHRDTO;
import app.dto.UserDTO;
import app.healthcare.Constants;
import app.healthcare.GoogleFitService;
import app.healthcare.R;
import app.healthcare.R.drawable;
import app.healthcare.R.id;
import app.healthcare.R.layout;

import com.gc.materialdesign.widgets.Dialog;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

@SuppressLint("RtlHardcoded")
public class RatioWHRFragment extends Fragment {

	CheckBox cbMale;
	CheckBox cbFeMale;
	EditText tbxCe;
	EditText tbxCm;
	TextView tbxImpact;
	TextView tbxResult;
	Button btnReinphut;
	Button btnCalculateWHR;
	UserDAO userdao;
	RatioWHRDAO dao;
	TableLayout tableData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ratio_whr,
				container, false);
		initView(rootView);
		return rootView;
	}

	public void initView(View rootView) {
		userdao = new UserDAO(getActivity());
		dao = new RatioWHRDAO(getActivity());
		tableData = (TableLayout) rootView.findViewById(R.id.tableDataWHR);
		cbMale = (CheckBox) rootView.findViewById(R.id.cbMale);
		cbMale.setOnCheckedChangeListener(listener);
		cbFeMale = (CheckBox) rootView.findViewById(R.id.cbFeMale);
		cbFeMale.setChecked(true);
		cbFeMale.setOnCheckedChangeListener(listener);
		tbxCe = (EditText) rootView.findViewById(R.id.tbxCe);
		tbxCm = (EditText) rootView.findViewById(R.id.tbxCm);
		btnCalculateWHR = (Button) rootView.findViewById(R.id.btnCalculateWHR);
		btnCalculateWHR.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				if (tbxCe.getText().length() == 0) {
					Toast.makeText(getActivity(), "Nhập thông số vòng eo",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (tbxCm.getText().length() == 0) {
					Toast.makeText(getActivity(), "Nhập thông số vòng mông",
							Toast.LENGTH_SHORT).show();
					return;
				}
				calculateWHR();
			}
		});
		btnReinphut = (Button) rootView.findViewById(R.id.btnReinput);
		btnReinphut.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				reInput();
			}
		});
		tbxResult = (TextView) rootView.findViewById(R.id.tbxResultWHR);
		tbxImpact = (TextView) rootView.findViewById(R.id.tbxImpact);
		try {
			buildTableData();
		} catch (NullPointerException e) {
			Log.e("nulldata", "chua co du lieu");
		}
		try {
			if (GoogleFitService.mClient.equals(null)) {
				if (Plus.PeopleApi.getCurrentPerson(GoogleFitService.mClient) != null) {
					Person currentPerson = Plus.PeopleApi
							.getCurrentPerson(GoogleFitService.mClient);
					int gender = currentPerson.getGender();
					if (gender == 0) {
						cbMale.setChecked(true);
						cbFeMale.setChecked(false);
						cbMale.setEnabled(false);
						cbFeMale.setEnabled(false);
					} else if (gender == 1) {
						cbMale.setChecked(false);
						cbFeMale.setChecked(true);
						cbMale.setEnabled(false);
						cbFeMale.setEnabled(false);
					}
				}
			}

		} catch (NullPointerException e) {
			Log.e("RatioWhr", e.toString());
		}
	}

	@SuppressWarnings("deprecation")
	protected void calculateWHR() {
		float ce = Float.parseFloat(tbxCe.getText().toString());
		float cm = Float.parseFloat(tbxCm.getText().toString());
		String result;
		float ratioWHR = ce / cm;
		if (cbMale.isChecked()) {
			if (ratioWHR <= 0.9) {
				result = "Sức khỏe tốt";
			} else if (ratioWHR > 0.9 && ratioWHR <= 0.95) {
				result = "Ít nguy hiểm";
			} else if (ratioWHR > 0.95 && ratioWHR < 1) {
				result = "Mức độ nguy hiểm trung bình";
			} else {
				result = "Rất nguy hiểm";
			}
		} else {
			cbFeMale.setChecked(true);
			if (ratioWHR <= 0.7) {
				result = "Sức khỏe tốt";
			} else if (ratioWHR > 0.7 && ratioWHR <= 0.8) {
				result = "Ít nguy hiểm";
			} else if (ratioWHR > 0.8 && ratioWHR < 0.85) {
				result = "Nguy hiểm";
			} else {
				result = "Rất nguy hiểm";
			}
		}
		ratioWHR *= 10;
		int temp = (int) (ratioWHR);
		ratioWHR = temp;
		ratioWHR /= 10;
		tbxResult.setText(String.valueOf(ratioWHR));
		tbxImpact.setText(result);
		RatioWHRDTO dto = new RatioWHRDTO();
		dto.setRatio(String.valueOf(ratioWHR));
		dto.setStatus(result);

		Constants.getInstance().getTime().setToNow();
		int date = Constants.getInstance().getTime().monthDay;
		int month = Constants.getInstance().getTime().month + 1;
		int year = Constants.getInstance().getTime().year;
		dto.setDate(String.valueOf(date) + "/" + String.valueOf(month) + "/"
				+ String.valueOf(year));
		dto.setTime(String.valueOf(Constants.getInstance().getTime().hour)
				+ ":"
				+ String.valueOf(Constants.getInstance().getTime().minute)
				+ ":"
				+ String.valueOf(Constants.getInstance().getTime().second));
		dto.setUserId(1);
		dao.insertRatioWHR(dto);
		Dialog dialog = new Dialog(getActivity(), "Chỉ số WHR",
				"Chỉ số WHR của bạn là: " + String.valueOf(ratioWHR) + "\n"
						+ result, app.healthcare.R.drawable.whr_icon);
		dialog.show();
	}

	public void reInput() {
		tbxCe.setText("");
		tbxCm.setText("");
		tbxImpact.setText("Mức độ ảnh hưởng");
		tbxResult.setText("WHR");
	}

	private void buildTableData() {
		UserDTO userdto = userdao.getUser();
		List<RatioWHRDTO> listdata = dao.getListRatioWHR(userdto.getUserId());
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
					tv.setGravity(Gravity.RIGHT);
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

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if (isChecked) {
				switch (arg0.getId()) {
				case R.id.cbFeMale:
					cbFeMale.setChecked(true);
					cbMale.setChecked(false);
					break;
				case R.id.cbMale:
					cbMale.setChecked(true);
					cbFeMale.setChecked(false);
					break;
				}
			}
		}
	};
}
