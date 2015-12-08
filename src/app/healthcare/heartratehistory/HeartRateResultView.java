package app.healthcare.heartratehistory;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import app.database.HeartRateDAO;
import app.dto.HeartRateDTO;
import app.healthcare.R;

public class HeartRateResultView extends Activity {
	HeartRateDAO dao;
	HeartRateDTO data;

	public HeartRateDTO getData() {
		return data;
	}

	public void setData(HeartRateDTO data) {
		this.data = data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heartrate_result_view);
		dao = new HeartRateDAO(this);
		data = dao.getHeartRate(HistoryHeartRate.itemCurentSelect
				.getHeartRateId());
		init();

	}

	private void init() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		float width;
		width = displaymetrics.widthPixels;
		View vProgess;
		vProgess = (View) findViewById(R.id.measurement_bpm_indicator);
		vProgess.setX((width / 90) * (data.getHeartRate() - 30));
		TextView measurementBpm = (TextView) findViewById(R.id.measurement_bpm);
		TextView motionStatusText = (TextView) findViewById(R.id.motion_status_text);
		TextView bodyConditionText = (TextView) findViewById(R.id.body_condition_text);
		TextView timeText = (TextView) findViewById(R.id.time_text);
		TextView dateText = (TextView) findViewById(R.id.date_text);
		TextView measurementNote = (TextView) findViewById(R.id.measurement_note);
		ImageView imageBodyCondition = (ImageView) findViewById(R.id.image_body_condition);
		ImageView imageMotionStatus = (ImageView) findViewById(R.id.image_motion_status);
		ImageButton imgeShareFacebook = (ImageButton) findViewById(R.id.image_share_facebook);
		imgeShareFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		measurementBpm.setText(String.valueOf(data.getHeartRate()));
		switch (data.getStatusSport()) {
		case 1:
			imageMotionStatus.setImageResource(R.drawable.ic_hr_type_custom);
			motionStatusText.setText("Bình thường");
			break;
		case 2:
			imageMotionStatus.setImageResource(R.drawable.ic_hr_type_sleep);
			motionStatusText.setText("Nghỉ ngơi");
			break;
		case 3:
			imageMotionStatus
					.setImageResource(R.drawable.ic_hr_type_before_sport);
			motionStatusText.setText("Trước vận động");
			break;
		case 4:
			imageMotionStatus
					.setImageResource(R.drawable.ic_hr_type_after_sport);
			motionStatusText.setText("Sau vận động");
			break;
		case 5:
			imageMotionStatus.setImageResource(R.drawable.ic_hr_type_max);
			motionStatusText.setText("Nhịp tim tối đa");
			break;
		default:
			break;
		}

		switch (data.getBodyCo()) {
		case 1:
			imageBodyCondition
					.setImageResource(R.drawable.feeling_colored_awesome);
			bodyConditionText.setText("Rất tốt");
			break;
		case 2:
			imageBodyCondition
					.setImageResource(R.drawable.feeling_colored_good);
			bodyConditionText.setText("Tốt");
			break;
		case 3:
			imageBodyCondition
					.setImageResource(R.drawable.feeling_colored_soso);
			bodyConditionText.setText("Bình thường");
			break;
		case 4:
			imageBodyCondition
					.setImageResource(R.drawable.feeling_colored_sluggish);
			bodyConditionText.setText("Không tốt");
			break;
		case 5:
			imageBodyCondition
					.setImageResource(R.drawable.feeling_colored_injured);
			bodyConditionText.setText("Tệ");
			break;
		default:
			break;
		}
		timeText.setText(data.getTime());
		dateText.setText(data.getDate());
		measurementNote.setText(data.getNote());

	}
}