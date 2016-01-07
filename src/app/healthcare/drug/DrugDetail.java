package app.healthcare.drug;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import app.healthcare.R;

public class DrugDetail extends Activity {
	public static String tenThuoc = "";
	public static String nhomDuocLy = "";
	public static String thanhPhan = "";
	public static String chiDinh = "";
	public static String chongChiDinh = "";
	public static String tuongTacThuoc = "";
	public static String tacDungPhu = "";
	public static String lieuLuong = "";
	public static String chuY = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drug_detail);
		TextView tenThuocView = (TextView) findViewById(R.id.ten_thuoc);
		TextView nhomDuocLyView = (TextView) findViewById(R.id.nhom_duoc_ly);
		TextView thanhPhanView = (TextView) findViewById(R.id.thanh_phan);
		TextView chiDinhView = (TextView) findViewById(R.id.chi_dinh);
		TextView chongChiDinhView = (TextView) findViewById(R.id.chong_chi_dinh);
		TextView tuongTacThuocView = (TextView) findViewById(R.id.tuong_tac_thuoc);
		TextView tacDungPhuView = (TextView) findViewById(R.id.tac_dung_phu);
		TextView lieuLuongView = (TextView) findViewById(R.id.lieu_luong);
		TextView chuYView = (TextView) findViewById(R.id.chu_y);
		tenThuocView.setText(tenThuoc);
		nhomDuocLyView.setText(nhomDuocLy);
		thanhPhanView.setText(thanhPhan);
		chiDinhView.setText(chiDinh);
		chongChiDinhView.setText(chongChiDinh);
		tuongTacThuocView.setText(tuongTacThuoc);
		tacDungPhuView.setText(tacDungPhu);
		lieuLuongView.setText(lieuLuong);
		chuYView.setText(chuY);

	}
}
