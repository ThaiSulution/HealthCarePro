package app.healthcare;

import java.util.List;
import zulu.app.healthcare.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import app.database.TimeTableTakeDAO;
import app.database.UserDAO;
import app.dto.TimeTableTakeDTO;

public class TimeTableTakeFragment extends Fragment {
	public TableLayout tableData;
	public CheckBox cbFinish;
	public CheckBox cbNotFinish;
	public EditText tbxSickName;
	public EditText tbxDateSick;
	public EditText tbxTimeSpacing;
	public EditText tbxStatus;
	public EditText tbxCountTime;
	public UserDAO userDao;
	public Button btnSave;
	public TimeTableTakeDAO timeTableTakeDao;
	SharedPreferences prefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_time_table_take,
				container, false);
		initView(rootView);
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initView(View rootView) {
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		tableData = (TableLayout) rootView
				.findViewById(R.id.tableDataTimeTable);
		tbxDateSick = (EditText) rootView.findViewById(R.id.tbxDateSick);
		tbxSickName = (EditText) rootView.findViewById(R.id.tbxSick);
		tbxTimeSpacing = (EditText) rootView.findViewById(R.id.tbxTimeSpacing);
		userDao = new UserDAO(getActivity());
		timeTableTakeDao = new TimeTableTakeDAO(getActivity());
		tbxStatus = (EditText) rootView.findViewById(R.id.tbxStatus);
		btnSave = (Button) rootView.findViewById(R.id.btnSave);
		tbxCountTime = (EditText)rootView.findViewById(R.id.tbxCountTime);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				if (tbxDateSick.getText().length() == 0) {
					Toast.makeText(getActivity(), "Bạn phải nhập đủ thông số",
							Toast.LENGTH_SHORT).show();
				} else if (tbxSickName.getText().length() == 0) {
					Toast.makeText(getActivity(), "Bạn phải nhập đủ thông số",
							Toast.LENGTH_SHORT).show();
				} else if (tbxStatus.getText().length() == 0) {
					Toast.makeText(getActivity(), "Bạn phải nhập đủ thông số",
							Toast.LENGTH_SHORT).show();
				} else if (tbxTimeSpacing.getText().length() == 0) {
					Toast.makeText(getActivity(), "Bạn phải nhập đủ thông số",
							Toast.LENGTH_SHORT).show();
				} else {
					if (save())
						getActivity().startService(
								new Intent(getActivity(),
										TimeTableTakeService.class));
				}
			}
		});
		buildTableData();
	}

	public void buildTableData() {
		List<TimeTableTakeDTO> listdata = timeTableTakeDao
				.getListTimeTableTake();
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
					tv.setText(listdata.get(i).getTime().toString());
				} else if (j == 1) {
					tv.setGravity(Gravity.CENTER);
					tv.setText(listdata.get(i).getSick().toString());
				} else if (j == 2) {
					tv.setGravity(Gravity.RIGHT);
					tv.setText(listdata.get(i).getStatus().toString());
				}
				final int id = listdata.get(i).getTimeTableTakeId();
				row.addView(tv);
				row.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getDetail(id);
					}
				});
			}
			tableData.addView(row);
		}
	}

	public void getDetail(int id) {
		TimeTableTakeDTO dto = timeTableTakeDao.getTimeTableTake(id);
		tbxSickName.setText(dto.getSick());
		tbxDateSick.setText(dto.getTime());
		tbxTimeSpacing.setText(dto.getTimeSpacing());
		tbxStatus.setText(dto.getStatus());
		tbxCountTime.setText(String.valueOf(dto.getCountTime()));
	}

	public boolean save() {
		TimeTableTakeDTO dto = new TimeTableTakeDTO();
		dto.setSick(tbxSickName.getText().toString());
		dto.setStatus(tbxStatus.getText().toString());
		dto.setTime(tbxDateSick.getText().toString());
		int temp = Integer.parseInt(tbxCountTime.getText().toString());
		dto.setCountTime(temp);
		dto.setTimeSpacing(tbxTimeSpacing.getText().toString());
		int id = userDao.getUser().getUserId();
		dto.setUserId(id);
		timeTableTakeDao.insertOrUpdateTimeTableTake(dto);
		Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_SHORT)
				.show();
		int sec = (Integer.parseInt(dto.getTimeSpacing())) * 3600*1000;
		setTime(sec, Constants.getInstance().TIME_COUNT);
		setCountTime(temp, "count_time");
		if (Integer.parseInt(tbxTimeSpacing.getText().toString()) == 0) {
			getActivity().stopService(
					new Intent(getActivity(), TimeTableTakeService.class));
			return false;
		}
		return true;
	}

	public void setTime(int score, String s) {
		SharedPreferences.Editor settingsEditor = prefs.edit();
		settingsEditor.putInt(s, score);
		settingsEditor.commit();
	}

	public void setCountTime(int countTime, String s) {
		SharedPreferences.Editor settingsEditor = prefs.edit();
		settingsEditor.putInt(s, countTime);
		settingsEditor.commit();
	}
	public int getTime(String s) {
		return prefs.getInt(s, 0);
	}
}