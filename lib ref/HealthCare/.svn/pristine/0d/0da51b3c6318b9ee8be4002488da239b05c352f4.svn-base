package app.healthcare;

import java.util.List;

import zulu.app.healthcare.R;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import app.database.StepRunDAO;
import app.database.UserDAO;
import app.dto.StepRunDTO;
import app.dto.UserDTO;

public class MeasureStepRunFragment extends Fragment {

	private TextView tvcountStep;
	EditText tvChiTieu;
	private Button btnStartStepRun;
	private Button btnStopStepRun;
	private Button btnResetStepRun;
	SharedPreferences prefs;
	UserDAO userdao;
	StepRunDAO dao;
	TableLayout tableData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_measure_step_run,
				container, false);
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		initView(rootView);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter(Constants.INTENT_GET_BEAT);
		getActivity().registerReceiver(mToturialReceiver, intentFilter);
	}

	@Override
	public void onPause() {
		// unregister listener
		super.onPause();
		getActivity().unregisterReceiver(mToturialReceiver);
	}

	/**
	 * 
	 */
	private BroadcastReceiver mToturialReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent
					.getStringExtra(Constants.ActionReceiver.ACTION_RECEIVER);
			if (action.equals(Constants.ActionReceiver.SHOW_TEXT)) {
				int step = intent.getIntExtra(
						Constants.ActionReceiver.ACTION_VALUE, 0);
				tvcountStep.setText(step + "");
			}
		}
	};

	private void initView(View view) {
		userdao = new UserDAO(getActivity());
		dao = new StepRunDAO(getActivity());
		tableData = (TableLayout) view.findViewById(R.id.tableDataStepRun);
		tvcountStep = (TextView) view.findViewById(R.id.tbxCountStepRun);
		tvcountStep.setText(getHighScore() + "");

		tvChiTieu = (EditText) view.findViewById(R.id.tbxChiTieu);
		tvChiTieu.setText(String.valueOf(Constants.getInstance().TARGETS));

		btnStartStepRun = (Button) view.findViewById(R.id.btnStartService);
		btnStartStepRun.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				getActivity().startService(
						new Intent(getActivity(), StepRunServices.class));
				btnStartStepRun.setVisibility(View.INVISIBLE);
				btnResetStepRun.setVisibility(View.VISIBLE);
				btnStopStepRun.setVisibility(View.VISIBLE);
			}
		});
		btnStopStepRun = (Button) view.findViewById(R.id.btnStopService);
		btnStopStepRun.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				getActivity().stopService(
						new Intent(getActivity(), StepRunServices.class));
				btnStartStepRun.setVisibility(View.VISIBLE);
				btnResetStepRun.setVisibility(View.VISIBLE);
				btnStopStepRun.setVisibility(View.INVISIBLE);				
			}
		});
		btnResetStepRun = (Button) view.findViewById(R.id.btnReset);
		btnResetStepRun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().stopService(
						new Intent(getActivity(), StepRunServices.class));
				setHighScore(-1);
				getActivity().startService(
						new Intent(getActivity(), StepRunServices.class));
				btnStartStepRun.setVisibility(View.VISIBLE);
				btnResetStepRun.setVisibility(View.VISIBLE);
				btnStopStepRun.setVisibility(View.VISIBLE);
				StepRunDTO dto = new StepRunDTO();
				int targets = Integer.parseInt(tvChiTieu.getText().toString());
				dto.setTagets(targets);
				Constants.getInstance().getTime().setToNow();
				dto.setTime(Constants.getInstance().getTime().monthDay + "/"
						+ Constants.getInstance().getTime().month + "/"
						+ Constants.getInstance().getTime().year + "");
				dto.setUserId(1);
				int totalRun = Integer.parseInt(tvcountStep.getText().toString());
				dto.setTotalRun(totalRun);
				dao.insertStepRun(dto);
			}
		});
		buildTableData();
	}

	public void setHighScore(int score) {
		SharedPreferences.Editor settingsEditor = prefs.edit();
		settingsEditor.putInt(Constants.KEY_HISCORE, score);
		settingsEditor.commit();
	}

	public int getHighScore() {
		return prefs.getInt(Constants.KEY_HISCORE, 0);
	}

	private void buildTableData() {
		UserDTO userdto = userdao.getUser();
		List<StepRunDTO> listdata = dao.getListStepRun(userdto.getUserId());
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
					tv.setText(listdata.get(i).getTagets()+ "");
				} else if (j == 2) {
					tv.setGravity(Gravity.RIGHT);
					tv.setText(listdata.get(i).getTotalRun() + "");
				}
				row.addView(tv);
			}
			tableData.addView(row);
		}
	}
}
