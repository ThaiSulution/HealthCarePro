package app.healthcare.symptom.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import app.healthcare.R;
import app.healthcare.symptom.object.SickDAO;
import app.healthcare.symptom.object.SickDTO;

import com.gc.materialdesign.widgets.Dialog;
import com.squareup.picasso.Picasso;

public class SickDetail extends Activity {
	public static String sickName = "";
	ImageView hinhAnh;
	Bitmap b;
	SickDTO sick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sick_detail);
		if (sickName.trim().length() == 0) {
			final Dialog dialog = new Dialog(this, "Chi tiết bệnh",
					"Không có kết quả phù hợp",
					app.healthcare.R.drawable.ic_about);
			dialog.show();
			dialog.getButtonAccept().setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							finish();
						}
					});
		}
		SickDAO dao = new SickDAO(this);
		sick = dao.getSick(sickName);
		TextView ten = (TextView) findViewById(R.id.ten_benh);
		TextView moTa = (TextView) findViewById(R.id.mo_ta);
		TextView dinhNghia = (TextView) findViewById(R.id.dinh_nghia);
		TextView trieuChung = (TextView) findViewById(R.id.trieu_chung);
		TextView gapBacSi = (TextView) findViewById(R.id.gap_bac_si);
		TextView nguyenNhan = (TextView) findViewById(R.id.nguyen_nhan);
		TextView nguyCo = (TextView) findViewById(R.id.nguy_co);
		TextView bienChung = (TextView) findViewById(R.id.bien_chung);
		TextView xetNghiem = (TextView) findViewById(R.id.xet_nghiem);
		TextView dieuTri = (TextView) findViewById(R.id.dieu_tri);
		TextView thuoc = (TextView) findViewById(R.id.thuoc);
		TextView phongChong = (TextView) findViewById(R.id.phong_chong);
		hinhAnh = (ImageView) findViewById(R.id.hinh_anh);
		ten.setText(sick.getTen());
		moTa.setText(sick.getMoTa());
		dinhNghia.setText(sick.getDinhNghia());
		trieuChung.setText(sick.getTrieuChung());
		gapBacSi.setText(sick.getGapBacSi());
		nguyenNhan.setText(sick.getNguyenNhan());
		nguyCo.setText(sick.getNguyCo());
		bienChung.setText(sick.getBienChung());
		xetNghiem.setText(sick.getXetNghiem());
		dieuTri.setText(sick.getDieuTri());
		thuoc.setText(sick.getThuoc());
		phongChong.setText(sick.getPhongChong());
		Picasso.with(this).load(sick.getHinhAnh()).noFade().into(hinhAnh);

	}

}
