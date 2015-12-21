package app.healthcare.whr;

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
import app.healthcare.R;
import app.healthcare.dataobject.RatioWHRDTO;

public class WHRResultView extends Activity {
	RatioWHRDTO data = new RatioWHRDTO();
	ImageButton imgeShareFacebook;
	File imagePath;

	public RatioWHRDTO getData() {
		return data;
	}

	public void setData(RatioWHRDTO data) {
		this.data = data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whr_result_view);
		data = HistoryWHR.itemCurentSelect;
		init();

	}

	private void init() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		float width;
		float unit;
		float avg;
		width = displaymetrics.widthPixels - 32;
		avg = (width / 4) / 2;
		unit = (width / 4);
		View vProgess;
		vProgess = (View) findViewById(R.id.whr_indicator);
		TextView ratio = (TextView) findViewById(R.id.ratio);
		TextView status = (TextView) findViewById(R.id.whr_status_text);
		TextView timeText = (TextView) findViewById(R.id.time_text);
		TextView dateText = (TextView) findViewById(R.id.date_text);
		ImageView imageBodyStatus = (ImageView) findViewById(R.id.image_whr_status);
		imgeShareFacebook = (ImageButton) findViewById(R.id.image_share_facebook);

		imgeShareFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Bitmap bm = takeScreenshot();
				saveBitmap(takeScreenshot());
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_STREAM,
						Uri.fromFile(imagePath.getAbsoluteFile()));
				shareIntent.setType("image/png");
				shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
			}
		});
		ratio.setText(String.valueOf(data.getRatio()));
		if (data.getStatus().equals("Sức khỏe tốt")) {
			vProgess.setX(avg);
			imageBodyStatus.setImageResource(R.drawable.good);
		} else if (data.getStatus().equals("Ít nguy hiểm")) {
			vProgess.setX(unit + avg);
			imageBodyStatus.setImageResource(R.drawable.warning);
		} else if (data.getStatus().equals("Mức độ nguy hiểm trung bình")) {
			vProgess.setX(unit * 2 + avg);
			imageBodyStatus.setImageResource(R.drawable.warning);
		} else if (data.getStatus().equals("Rất nguy hiểm")) {
			vProgess.setX(unit * 3 + avg);
			imageBodyStatus.setImageResource(R.drawable.risk);
		}
		status.setText(data.getStatus());
		timeText.setText(data.getTime());
		dateText.setText(data.getDate());

	}

	public Bitmap takeScreenshot() {
		View rootView = findViewById(R.id.whr_content_take);// .getRootView();
		rootView.setDrawingCacheEnabled(true);
		return rootView.getDrawingCache();
	}

	public void saveBitmap(Bitmap bitmap) {
		imagePath = new File(Environment.getExternalStorageDirectory() + "/"
				+ String.valueOf(data.getRatio()) + ".png");
		Log.e("imagePath", imagePath.getAbsolutePath());
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