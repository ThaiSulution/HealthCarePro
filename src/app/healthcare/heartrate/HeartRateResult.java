package app.healthcare.heartrate;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import app.healthcare.Constants;
import app.healthcare.R;
import app.healthcare.dataobject.HeartRateDTO;
import app.healthcare.heartratehistory.HeartRateResultView;
import app.healthcare.heartratehistory.HistoryHeartRate;

import com.gc.materialdesign.views.Button;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class HeartRateResult extends Activity {
	TextView resultView;
	CheckedTextView checkTextViewGenaral;
	CheckedTextView checkTextViewSleep;
	CheckedTextView checkTextViewBeforeSport;
	CheckedTextView checkTextViewAfterSport;
	CheckedTextView checkTextViewMax;
	CheckedTextView checkTextViewFeelingAwesome;
	CheckedTextView checkTextViewFeelingGood;
	CheckedTextView checkTextViewFeelingSoso;
	CheckedTextView checkTextViewFeelingSluggish;
	CheckedTextView checkTextViewFeelingInjured;
	EditText note;
	Button btnSave;
	public int motionStatus = 1;
	public int bodyCo = 1;
	public String noteString = "";
	View vProgess;
	float width;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heartrate_result);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		resultView = (TextView) findViewById(R.id.measurement_bpm);
		resultView.setText(String.valueOf(HeartRateFragment.heartBeat));
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				insertHeartRate();

			}
		});
		vProgess = (View) findViewById(R.id.measurement_bpm_indicator);
		checkTextViewGenaral = (CheckedTextView) findViewById(R.id.measurement_type_anytime);
		checkTextViewGenaral.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				motionStatus = 1;
				checkTextViewGenaral.setText("Bình thường");
				checkTextViewGenaral.setChecked(true);
				checkTextViewSleep.setChecked(false);
				checkTextViewBeforeSport.setChecked(false);
				checkTextViewAfterSport.setChecked(false);
				checkTextViewMax.setChecked(false);
			}
		});
		checkTextViewSleep = (CheckedTextView) findViewById(R.id.measurement_type_resting);
		checkTextViewSleep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				motionStatus = 2;
				checkTextViewSleep.setText("Nghỉ ngơi");
				checkTextViewGenaral.setChecked(false);
				checkTextViewSleep.setChecked(true);
				checkTextViewBeforeSport.setChecked(false);
				checkTextViewAfterSport.setChecked(false);
				checkTextViewMax.setChecked(false);
			}
		});
		checkTextViewBeforeSport = (CheckedTextView) findViewById(R.id.measurement_type_before_sport);
		checkTextViewBeforeSport.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				motionStatus = 3;
				checkTextViewBeforeSport.setText("Trước vận động");
				checkTextViewGenaral.setChecked(false);
				checkTextViewSleep.setChecked(false);
				checkTextViewBeforeSport.setChecked(true);
				checkTextViewAfterSport.setChecked(false);
				checkTextViewMax.setChecked(false);
			}
		});
		checkTextViewAfterSport = (CheckedTextView) findViewById(R.id.measurement_type_after_sport);
		checkTextViewAfterSport.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				motionStatus = 4;
				checkTextViewAfterSport.setText("Sau vận động");
				checkTextViewGenaral.setChecked(false);
				checkTextViewSleep.setChecked(false);
				checkTextViewBeforeSport.setChecked(false);
				checkTextViewAfterSport.setChecked(true);
				checkTextViewMax.setChecked(false);
			}
		});
		checkTextViewMax = (CheckedTextView) findViewById(R.id.measurement_type_max);
		checkTextViewMax.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				motionStatus = 5;
				checkTextViewMax.setText("Nhịp tim tối đa");
				checkTextViewGenaral.setChecked(false);
				checkTextViewSleep.setChecked(false);
				checkTextViewBeforeSport.setChecked(false);
				checkTextViewAfterSport.setChecked(false);
				checkTextViewMax.setChecked(true);
			}
		});
		checkTextViewFeelingAwesome = (CheckedTextView) findViewById(R.id.measurement_feeling_awesome);
		checkTextViewFeelingAwesome
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						bodyCo = 1;
						checkTextViewFeelingAwesome.setChecked(true);
						checkTextViewFeelingGood.setChecked(false);
						checkTextViewFeelingSoso.setChecked(false);
						checkTextViewFeelingSluggish.setChecked(false);
						checkTextViewFeelingInjured.setChecked(false);
					}
				});
		checkTextViewFeelingGood = (CheckedTextView) findViewById(R.id.measurement_feeling_good);
		checkTextViewFeelingGood.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bodyCo = 2;
				checkTextViewFeelingAwesome.setChecked(false);
				checkTextViewFeelingGood.setChecked(true);
				checkTextViewFeelingSoso.setChecked(false);
				checkTextViewFeelingSluggish.setChecked(false);
				checkTextViewFeelingInjured.setChecked(false);
			}
		});
		checkTextViewFeelingSoso = (CheckedTextView) findViewById(R.id.measurement_feeling_soso);
		checkTextViewFeelingSoso.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bodyCo = 3;
				checkTextViewFeelingAwesome.setChecked(false);
				checkTextViewFeelingGood.setChecked(false);
				checkTextViewFeelingSoso.setChecked(true);
				checkTextViewFeelingSluggish.setChecked(false);
				checkTextViewFeelingInjured.setChecked(false);
			}
		});
		checkTextViewFeelingSluggish = (CheckedTextView) findViewById(R.id.measurement_feeling_sluggish);
		checkTextViewFeelingSluggish
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						bodyCo = 4;
						checkTextViewFeelingAwesome.setChecked(false);
						checkTextViewFeelingGood.setChecked(false);
						checkTextViewFeelingSoso.setChecked(false);
						checkTextViewFeelingSluggish.setChecked(true);
						checkTextViewFeelingInjured.setChecked(false);
					}
				});
		checkTextViewFeelingInjured = (CheckedTextView) findViewById(R.id.measurement_feeling_injured);
		checkTextViewFeelingInjured
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						bodyCo = 5;
						checkTextViewFeelingAwesome.setChecked(false);
						checkTextViewFeelingGood.setChecked(false);
						checkTextViewFeelingSoso.setChecked(false);
						checkTextViewFeelingSluggish.setChecked(false);
						checkTextViewFeelingInjured.setChecked(true);
					}
				});
		note = (EditText) findViewById(R.id.measurement_note);
		vProgess.setX((width / 90) * (HeartRateFragment.heartBeat - 30));
	}

	private void insertHeartRate() {
		int id = 1;
		if (Constants.getInstance().listDataHR.size() > 0) {
			id = Constants.getInstance().listDataHR.size() + 1;
		}
		final Intent i = new Intent(this, HistoryHeartRate.class);
		final Intent resultView = new Intent(this, HeartRateResultView.class);
		noteString = note.getText().toString();
		HeartRateDTO dto = new HeartRateDTO();
		dto.setHeartRate(HeartRateFragment.heartBeat);
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
		dto.setNote(noteString);
		dto.setStatusSport(motionStatus);
		dto.setBodyCo(bodyCo);
		dto.setHeartRateId(id);
		Constants.getInstance().listDataHR.add(dto);
		dto.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				if (arg0 != null) {
					Constants.getInstance().listDataHR.remove(Constants
							.getInstance().listDataHR.size() - 1);
					Log.e("Save heart rate", arg0.getMessage());
				} else {
					HistoryHeartRate.itemCurentSelect = Constants.getInstance().listDataHR
							.get(Constants.getInstance().listDataHR.size() - 1);
					startActivity(i);
					startActivity(resultView);
					finish();
				}
			}
		});
	}
}
