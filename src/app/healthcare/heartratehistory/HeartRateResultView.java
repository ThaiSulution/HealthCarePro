package app.healthcare.heartratehistory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import app.dto.HeartRateDTO;
import app.healthcare.R;

import com.facebook.share.model.ShareLinkContent;

public class HeartRateResultView extends Activity {
	HeartRateDTO data = new HeartRateDTO();
	ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(
			Uri.parse("https://developers.facebook.com")).build();
	ImageButton imgeShareFacebook;

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
		data = HistoryHeartRate.itemCurentSelect;
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
		imgeShareFacebook = (ImageButton) findViewById(R.id.image_share_facebook);

		imgeShareFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Bitmap bm = takeScreenshot();
				saveBitmap(takeScreenshot());
				File filePath = getFileStreamPath(Environment
						.getExternalStorageDirectory()
						+ "/"
						+ String.valueOf(data.getHeartRateId()) + ".png"); // optional
				// //internal
				// storage
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_TEXT, "Nhịp tim của tôi là: "
						+ String.valueOf(data.getHeartRate()));
				shareIntent.putExtra(Intent.EXTRA_STREAM,
						Uri.fromFile(filePath)); // optional//use this
													// when you want to
													// send an image
				shareIntent.setType("image/png");
				shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				startActivity(Intent.createChooser(shareIntent, "send"));
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

	public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}

	public void saveBitmap(Bitmap bitmap) {
		File imagePath = new File(Environment.getExternalStorageDirectory()
				+ "/" + String.valueOf(data.getHeartRateId()) + ".png");
		Log.e("imagePath", String.valueOf(data.getHeartRateId()));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("GREC", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("GREC", e.getMessage(), e);
		}
	}
}