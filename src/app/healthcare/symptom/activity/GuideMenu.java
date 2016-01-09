package app.healthcare.symptom.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import app.healthcare.GoogleFitService;
import app.healthcare.R;
import app.healthcare.call.ListDoctor;
import app.healthcare.symptom.object.SickDAO;
import app.healthcare.symptom.object.SickDTO;
import app.healthcare.symptom.object.SickSymptomDAO;
import app.healthcare.symptom.object.SickSymptomDTO;

@SuppressLint("HandlerLeak")
public class GuideMenu extends Activity {
	String idnguoidung = "phivien";

	// LinearLayout thucdonLinearLayout;

	// AdapterDB mDB = new AdapterDB(this);
	ProgressDialog dialogProgess;
	Handler handler = new Handler() {  
	    @Override  
	    public void handleMessage(Message msg) {
	    	dialogProgess.dismiss(); 
	    }  
	};

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

		final SickDAO sickDao = new SickDAO(this);
		final SickSymptomDAO sickSymptomDao = new SickSymptomDAO(this);
		List<SickDTO> dtos = sickDao.getListSick();
		if (dtos.size() == 0) {
			dialogProgess = ProgressDialog.show(this, "",
     				"Thiết lập dữ liệu lần đầu");
			new Thread(new Runnable() {  
		         @Override  
		         public void run() {  
		        	String lineSick;
		 			InputStream inSick = getResources().openRawResource(R.drawable.benh);
		 			InputStreamReader inreader = new InputStreamReader(inSick);
		 			BufferedReader bufreader = new BufferedReader(inreader);
		 			if (inSick != null) {
		 				try {
		 					SickDTO dto = new SickDTO();
		 					while ((lineSick = bufreader.readLine()) != null) {
		 						if (lineSick.startsWith("Tên bệnh: ")) {
		 							dto.setTen(lineSick.replace("Tên bệnh: ", ""));
		 						}
		 						if (lineSick.startsWith("Mô tả: ")) {
		 							dto.setMoTa(lineSick.replace("Mô tả: ", ""));
		 						}
		 						if (lineSick.startsWith("Định nghĩa: ")) {
		 							dto.setDinhNghia(lineSick.replace("Định nghĩa: ", ""));
		 						}
		 						if (lineSick.startsWith("Các triệu chứng: ")) {
		 							dto.setTrieuChung(lineSick.replace("Các triệu chứng: ", ""));
		 						}
		 						if (lineSick.startsWith("Đến gặp bác sĩ khi: ")) {
		 							dto.setGapBacSi(lineSick.replace("Đến gặp bác sĩ khi: ", ""));
		 						}
		 						if (lineSick.startsWith("Nguyên nhân: ")) {
		 							dto.setNguyenNhan(lineSick.replace("Nguyên nhân: ", ""));
		 						}
		 						if (lineSick.startsWith("Yếu tố nguy cơ: ")) {
		 							dto.setNguyCo(lineSick.replace("Yếu tố nguy cơ: ", ""));
		 						}
		 						if (lineSick.startsWith("Các biến chứng: ")) {
		 							dto.setBienChung(lineSick.replace("Các biến chứng: ", ""));
		 						}
		 						if (lineSick.startsWith("Các xét nghiệm và chẩn đoán: ")) {
		 							dto.setXetNghiem(lineSick.replace("Các xét nghiệm và chẩn đoán: ", ""));
		 						}
		 						if (lineSick.startsWith("Phương pháp điều trị: ")) {
		 							dto.setDieuTri(lineSick.replace("Phương pháp điều trị: ", ""));
		 						}
		 						if (lineSick.startsWith("Thuốc: ")) {
		 							dto.setThuoc(lineSick.replace("Thuốc: ", ""));
		 						}
		 						if (lineSick.startsWith("Phòng chống: ")) {
		 							dto.setPhongChong(lineSick.replace("Phòng chống: ", ""));
		 						}
		 						if (lineSick.startsWith("Hình ảnh: ")) {
		 							dto.setHinhAnh(lineSick.replace("Hình ảnh: ", ""));
		 						}
		 						if (lineSick.startsWith("end")) {
		 							sickDao.insertSick(dto);
		 							dto = new SickDTO();
		 						}
		 					}
		 				} catch (IOException e) {
		 					e.printStackTrace();
		 					dialogProgess.dismiss();
		 				}
		 			}
		 			
		 			String lineSymptom;
		 			InputStream inSymptom = getResources().openRawResource(R.drawable.benh_trieuchung);
		 			InputStreamReader inreaderSymptom = new InputStreamReader(inSymptom);
		 			BufferedReader bufreaderSymptom = new BufferedReader(inreaderSymptom);
		 			if (inSymptom != null) {
		 				try {
		 					SickSymptomDTO dto = new SickSymptomDTO();
		 					while ((lineSymptom = bufreaderSymptom.readLine()) != null) {
		 						dto.setTenBenh(lineSymptom.split(":")[0]);
		 						dto.setTrieuChung(lineSymptom.split(":")[1]);
		 						dto.setViTri(lineSymptom.split(":")[2]);
		 						sickSymptomDao.insert(dto);
		 						dto = new SickSymptomDTO();
		 					}
		 				} catch (IOException e) {
		 					e.printStackTrace();
		 					dialogProgess.dismiss();
		 				}
		 			}
		 			
		             handler.sendEmptyMessage(0); 
		          }  
		      }).start(); 
			
			
		}
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