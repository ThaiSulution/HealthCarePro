package app.healthcare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "DB_HealthCare";
	public static final int DATABASE_VERSION = 1;

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table THUOC "
				+ "(MA_THUOC integer primary key, TEN_THUOC text, NHOM_DUOC_LY text, THANH_PHAN text, CHI_DINH text, CHONG_CHI_DINH text, TUONG_TAC_THUOC text, TAC_DUNG_PHU text, CHU_Y_DE_PHONG text, LIEU_LUONG text)");
		db.execSQL("create table BENH "
				+ "(MA_BENH integer primary key, TEN_BENH text, MO_TA text, DINH_NGHIA text, TRIEU_CHUNG text, GAP_BAC_SI text, NGUYEN_NHAN text, NGUY_CO text, BIEN_CHUNG text, XET_NGHIEM text, DIEU_TRI text, THUOC text, PHONG_CHONG text, HINH_ANH text)");
		db.execSQL("create table BENH_TRIEUCHUNG "
				+ "(MA integer primary key, TEN_BENH text, TRIEU_CHUNG text, VI_TRI text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS THUOC");
		db.execSQL("DROP TABLE IF EXISTS BENH");
		db.execSQL("DROP TABLE IF EXISTS BENH_TRIEUCHUNG");
		onCreate(db);
	}
}
