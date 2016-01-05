package app.healthcare.bmi;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.healthcare.Constants;
import app.healthcare.R;
import app.healthcare.dataobject.RatioBMIDTO;

import com.gc.materialdesign.widgets.Dialog;
import com.parse.ParseException;
import com.parse.SaveCallback;

@SuppressLint("RtlHardcoded")
public class RatioBMIFragment extends Fragment {
	EditText tbxHeight;
	EditText tbxWeight;
	TextView tbxImpact;
	TextView tbxResult;

	Button btnReinphut;
	Button btnCalculateBMI;
	Button btnHistory;
	private ProgressDialog dialogProgess;

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
		tbxHeight = (EditText) rootView.findViewById(R.id.tbxHeight);
		tbxWeight = (EditText) rootView.findViewById(R.id.tbxWeight);
		final Intent historyBMI = new Intent(getActivity(), HistoryBMI.class);
		btnHistory = (Button) rootView.findViewById(R.id.btn_history);
		btnHistory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(historyBMI);
			}
		});
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
				dialogProgess = ProgressDialog.show(getActivity(), "",
						"Vui lòng chờ...");
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
		RatioBMIDTO dto = new RatioBMIDTO();
		ratioBMI = (double) (Math.floor((double) ratioBMI * 10) / (double) 10);
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
		final Intent BMIResult = new Intent(getActivity(), BMIResultView.class);
		dto.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException ex) {
				dialogProgess.dismiss();
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
									HistoryBMI.itemCurentSelect = Constants
											.getInstance().listDataBMI
											.get(Constants.getInstance().listDataBMI
													.size() - 1);
									startActivity(historyBMI);
									startActivity(BMIResult);
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
}
