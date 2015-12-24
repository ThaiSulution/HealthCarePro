package app.healthcare.symptom.activity;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import app.healthcare.GoogleFitService;
import app.healthcare.R;
import app.healthcare.call.ListDoctor;

public class GuideMenu extends Activity {
	String idnguoidung = "phivien";

	// LinearLayout thucdonLinearLayout;

	// AdapterDB mDB = new AdapterDB(this);

	public boolean isNetworkAvailable(Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle icookmenu) {
		super.onCreate(icookmenu);
		setContentView(R.layout.main_diagnious);

	}

	/*
	 * boolean kiemtrathucdon() { return
	 * AdapterDB.kiemtranguoidungphatsinh(idnguoidung); }
	 */
	public void trieuchung(View v) throws NumberFormatException, JSONException {
		Intent intent = new Intent(GuideMenu.this, BodyActivity.class);
		startActivity(intent);
	}

	public void giupdo(View v) throws NumberFormatException, JSONException {
		final Intent i = new Intent(this, ListDoctor.class);
		Button btnHelp = (Button) findViewById(R.id.button_laplich);
		btnHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(i);
			}
		});
	}

	public void phanhoi(View v) throws NumberFormatException, JSONException {

		Intent browserIntent = new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("https://docs.google.com/forms/d/1MJlpNthinWugsYscFfGNdp-LMWoDzsklrsKM-GQee6A/prefill"));
		startActivity(browserIntent);

	}

	public void benhvien(View v) throws NumberFormatException, JSONException {
		String locationX = String.valueOf(GoogleFitService.currentLatitude);
		String locationY = String.valueOf(GoogleFitService.currentLongitude);
		Uri gmmIntentUri = Uri.parse("geo:" + locationX + "," + locationY+ "?q=hospital");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);

	}

	// @Override
	// public void onBackPressed() {
	// super.onBackPressed();
	// Intent intent = new Intent(GuideMenu.this, LoginActivity.class);
	// startActivity(intent);
	// }

	/*
	 * public void thongtin(View v) throws NumberFormatException, JSONException
	 * { Intent intent = new Intent(GuideMenu.this, Info.class);
	 * intent.putExtra("idnguoidung", idnguoidung); startActivity(intent); }
	 */

	/*
	 * public void huongdan(View v) throws NumberFormatException, JSONException
	 * { Intent intent = new Intent(GuideMenu.this, Guide.class);
	 * intent.putExtra("idnguoidung", idnguoidung); startActivity(intent); }
	 */

	// public void taikhoan(View v) throws NumberFormatException {
	// Intent intent = new Intent(GuideMenu.this, LoginActivity.class);
	// startActivity(intent);
	// }

	public BitmapDrawable writeOnDrawable(int drawableId, String text) {

		Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
				.copy(Bitmap.Config.ARGB_8888, true);

		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20);

		Canvas canvas = new Canvas(bm);
		canvas.drawText(text, 0, bm.getHeight() / 2, paint);

		return new BitmapDrawable(bm);
	}

	@SuppressLint("SdCardPath")
	/*
	 * public void writetextonimage() { Bitmap src =
	 * BitmapFactory.decodeResource(getResources(),
	 * R.drawable.background_mainmemu); // the original file // yourimage.jpg i
	 * added in // resources Bitmap dest = Bitmap.createBitmap(src.getWidth(),
	 * src.getHeight(), Bitmap.Config.ARGB_8888);
	 * 
	 * String yourText = "Thá»±c Ä‘Æ¡n cá»§a tÃ´i";
	 * 
	 * Canvas cs = new Canvas(dest); Paint tPaint = new Paint();
	 * tPaint.setTextSize(35); tPaint.setColor(Color.BLUE);
	 * tPaint.setStyle(Style.FILL); cs.drawBitmap(src, 0f, 0f, null); float
	 * height = tPaint.measureText("yY"); float width =
	 * tPaint.measureText(yourText); float x_coord = (src.getWidth() - width) /
	 * 2; cs.drawText(yourText, x_coord, height + 15f, tPaint); // 15f is to put
	 * // space between // top edge and // the text, if // you want to // change
	 * it, // you can try { dest.compress(Bitmap.CompressFormat.JPEG, 100, new
	 * FileOutputStream(new File( "/mnt/sdcard/ImageAfterAddingText.jpg")));
	 * Toast.makeText(getApplicationContext(), "ok", 0).show(); } catch
	 * (FileNotFoundException e) { Toast.makeText(getApplicationContext(), "no",
	 * 0).show(); e.printStackTrace(); } }
	 */
	// private MenuItem fblogout = null;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Hướng dẫn");
		menu.add("Thông tin");
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) {
	 * 
	 * if (item.getTitle().toString().equals("HÆ°á»›ng dáº«n")) try { //
	 * huongdan(null); } catch (NumberFormatException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (JSONException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } if
	 * (item.getTitle().toString().equals("ThÃ´ng tin")) try { //
	 * thongtin(null); } catch (NumberFormatException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (JSONException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } return false;
	 * }
	 */

	int chiaseActivity = 0;
	int phatsinhActivity = 1;

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) {
	 * 
	 * if (requestCode == phatsinhActivity) { if (resultCode == RESULT_OK) { try
	 * { thucdonLinearLayout.setVisibility(View.VISIBLE); //thucdon(null); }
	 * catch (NumberFormatException e) { e.printStackTrace(); } catch
	 * (JSONException e) { e.printStackTrace(); } } }
	 * 
	 * }
	 */

	/*
	 * protected String checkUserCreateMenu(String idUser) { mDB.open(); String
	 * k = "0"; if (mDB.kiemtranguoidung(idUser)) { if
	 * (AdapterDB.kiemtranguoidungphatsinh(idUser)) k = "1"; } else {
	 * mDB.themnguoidung(idUser); k = "0"; }
	 * 
	 * mDB.close(); return k; }
	 */

}