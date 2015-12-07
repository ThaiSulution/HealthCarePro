package app.database;

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
		db.execSQL("create table USER "
				+ "(UserId integer primary key, Username text,TimeStart text, StepTarget integer)");
		db.execSQL("create table RATIOBMI "
				+ "(RatioBMIId integer primary key, UserId integer, DateRelease text, TimeRelease text, Ratio text, Status text)");
		db.execSQL("create table RATIOWHR "
				+ "(RatioWHRId integer primary key, UserId integer, DateRelease text, TimeRelease text, Ratio text, Status text)");
		db.execSQL("create table STEPRUN "
				+ "(StepRunId integer primary key, UserId integer, DateRelease text, TimeRelease text, Tagets integer, TotalRun integer, Calos integer, TotalMin integer, Distance Float)");
		db.execSQL("create table HEART_RATE "
				+ "(HEART_RATE_ID integer primary key, MOTION_STATUS text, DATE_RELEASE text, TIME_RELEASE text, RATE integer, BODY_CONDITION text, NOTE text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS USER");
		db.execSQL("DROP TABLE IF EXISTS RATIOBMI");
		db.execSQL("DROP TABLE IF EXISTS RATIOWHR");
		db.execSQL("DROP TABLE IF EXISTS STEPRUN");
		db.execSQL("DROP TABLE IF EXISTS HEART_RATE");
		onCreate(db);
	}
}
