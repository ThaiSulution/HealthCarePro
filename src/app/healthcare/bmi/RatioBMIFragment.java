package app.healthcare.bmi;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.dto.RatioBMIDTO;
import app.healthcare.Constants;
import app.healthcare.R;

import com.gc.materialdesign.widgets.Dialog;
import com.parse.ParseException;
import com.parse.SaveCallback;

@SuppressLint("RtlHardcoded")
public class RatioBMIFragment extends Fragment {
	CheckBox cbWHO;
	CheckBox cbIDIAndWPRO;
	EditText tbxHeight;
	EditText tbxWeight;
	TextView tbxImpact;
	TextView tbxResult;

	Button btnReinphut;
	Button btnCalculateBMI;

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

	public void calculateBMI() {
		Double height = Double.parseDouble(tbxHeight.getText().toString());
		Double weight = Double.parseDouble(tbxWeight.getText().toString());
		Double ratioBMI = weight / (height * height);
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
		ratioBMI = (double) (Math.round((double) ratioBMI * 10) / (double) 10);
		tbxResult.setText(String.valueOf(ratioBMI));
		tbxImpact.setText(result);
		dto.setRatio(ratioBMI);
		dto.setStatus(result);
		int id = 1;
		if (Constants.getInstance().listDataBMI.size() > 0) {
			id = Constants.getInstance().listDataBMI.size() + 1;
		}
		dto.setRatioBMIId(id);
		Constants.getInstance().getTime().setToNow();
		int date = Constants.getInstance().getTime().monthDay;
		int month = Constants.getInstance().getTime().month + 1;
		int year = Constants.getInstance().getTime().year;
		dto.setTime(String.valueOf(Constants.getInstance().getTime().hour)
				+ ":"
				+ String.valueOf(Constants.getInstance().getTime().minute)
				+ ":"
				+ String.valueOf(Constants.getInstance().getTime().second));
		dto.setDate(String.valueOf(date) + "/" + String.valueOf(month) + "/"
				+ String.valueOf(year) + "");
		Constants.getInstance().listDataBMI.add(dto);
		final Double ratioToView = ratioBMI;
		final String resultToView = result;
		final Intent historyBMI = new Intent(getActivity(), HistoryBMI.class);
		dto.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException ex) {
				if (ex == null) {
					final Dialog dialog = new Dialog(getActivity(),
							"Chỉ số BMI", "Chỉ số BMI của bạn là: "
									+ String.valueOf(ratioToView) + "\n"
									+ resultToView,
							app.healthcare.R.drawable.bmi_icon);
					dialog.show();
					dialog.getButtonAccept().setOnClickListener(
							new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									startActivity(historyBMI);
									dialog.dismiss();
								}
							});

				} else {
					Constants.getInstance().listDataBMI.remove(Constants
							.getInstance().listDataBMI.size() - 1);
					Dialog dialog = new Dialog(getActivity(), "Chỉ số BMI",
							"Có lỗi xảy ra!",
							app.healthcare.R.drawable.bmi_icon);
					dialog.show();
				}
			}
		});
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