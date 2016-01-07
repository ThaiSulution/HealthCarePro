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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS THUOC");
		onCreate(db);
	}
}
