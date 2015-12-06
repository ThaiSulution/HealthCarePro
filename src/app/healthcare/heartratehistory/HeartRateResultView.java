package app.healthcare.heartratehistory;

import android.app.Activity;
import android.os.Bundle;
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
		data = dao.getHeartRate(HistoryHeartRate.itemCurentSelect.getHeartRateId());
		init();
		
	}

	private void init() {
		TextView measurementBpm = (TextView) findViewById(R.id.measurement_bpm);
		View measurementBpmIndicator = (View) findViewById(R.id.measurement_bpm_indicator);
		TextView motionStatusText = (TextView) findViewById(R.id.motion_status_text);
		TextView bodyConditionText = (TextView) findViewById(R.id.body_condition_text);
		TextView timeText = (TextView) findViewById(R.id.time_text);
		TextView measurementNote = (TextView) findViewById(R.id.measurement_note);
		ImageView imageBodyCondition = (ImageView) findViewById(R.id.image_body_condition);
		ImageView imageMotionStatus = (ImageView) findViewById(R.id.image_motion_status);
		ImageButton imgeShareFacebook = (ImageButton) findViewById(R.id.image_share_facebook);
		measurementBpm.setText(String.valueOf(data.getHeartRate()));
		switch (data.getStatusSport()) {
		case 1:
			motionStatusText.setText("Bình thường");
			break;
		case 2:
			motionStatusText.setText("Nghỉ ngơi");
			break;
		case 3:
			motionStatusText.setText("Trước vận động");
			break;
		case 4:
			motionStatusText.setText("Sau vận động");
			break;
		case 5:
			motionStatusText.setText("Nhịp tim tối đa");
			break;
		default:
			break;
		}
		
		switch (data.getBodyCo()) {
		case 1:
			bodyConditionText.setText("Rất tốt");
			break;
		case 2:
			bodyConditionText.setText("Tốt");
			break;
		case 3:
			bodyConditionText.setText("Bình thường");
			break;
		case 4:
			bodyConditionText.setText("Không tốt");
			break;
		case 5:
			bodyConditionText.setText("Tệ");
			break;
		default:
			break;
		}
		
		
		
	}
}