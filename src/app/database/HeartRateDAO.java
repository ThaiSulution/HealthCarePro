package app.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.HeartRateDTO;

public class HeartRateDAO extends DbConnectionService {

	public static final String HEARTRATE_TABLE = "HEART_RATE";
	public static final String HEART_RATE_ID = "HEART_RATE_ID";
	public static final String MOTION_STATUS = "MOTION_STATUS";
	public static final String DATE = "DATE_RELEASE";
	public static final String TIME = "TIME_RELEASE";
	public static final String RATE = "RATE";
	public static final String BODY_CONDITION = "BODY_CONDITION";
	public static final String NOTE = "NOTE";

	public HeartRateDAO(Context context) {
		super(context);
	}

	public boolean insertHeartRate(HeartRateDTO heartRate) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(HEART_RATE_ID, getNewHeartRateaId());
			contentValues.put(MOTION_STATUS, heartRate.getStatusSport());
			contentValues.put(DATE, heartRate.getDate());
			contentValues.put(TIME, heartRate.getTime());
			contentValues.put(RATE, heartRate.getHeartRate());
			contentValues.put(BODY_CONDITION, heartRate.getBodyCo());
			contentValues.put(NOTE, heartRate.getNote());
			myDb.insert(HEARTRATE_TABLE, null, contentValues);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer deleteHeartRate(Integer id) {
		return myDb.delete(HEARTRATE_TABLE, HEART_RATE_ID + " = ? ",
				new String[] { Integer.toString(id) });
	}

	public HeartRateDTO getHeartRate(int heartRateId) {
		HeartRateDTO heartRateData = new HeartRateDTO();
		String qr = "select * from " + HEARTRATE_TABLE + " where "
				+ HEART_RATE_ID + " = ? ";
		Cursor res = myDb.rawQuery(qr, new String[] { Integer.toString(heartRateId) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			HeartRateDTO item = new HeartRateDTO();
			item.setHeartRateId(res.getInt(res.getColumnIndex(HEART_RATE_ID)));
			item.setDate(res.getString(res.getColumnIndex(DATE)));
			item.setTime(res.getString(res.getColumnIndex(TIME)));
			item.setHeartRate(res.getInt(res.getColumnIndex(RATE)));
			item.setBodyCo(res.getInt(res.getColumnIndex(BODY_CONDITION)));
			item.setNote(res.getString(res.getColumnIndex(NOTE)));
			item.setStatusSport(res.getInt(res.getColumnIndex(MOTION_STATUS)));
			heartRateData = item;
			res.moveToNext();
		}
		return heartRateData;
	}

	public ArrayList<HeartRateDTO> getListHeartRate() {
		ArrayList<HeartRateDTO> arrayListHeartRate = new ArrayList<HeartRateDTO>();
		String qr = "select * from " + HEARTRATE_TABLE;
		Cursor res = myDb.rawQuery(qr, null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			HeartRateDTO item = new HeartRateDTO();
			item.setHeartRateId(res.getInt(res.getColumnIndex(HEART_RATE_ID)));
			item.setDate(res.getString(res.getColumnIndex(DATE)));
			item.setTime(res.getString(res.getColumnIndex(TIME)));
			item.setHeartRate(res.getInt(res.getColumnIndex(RATE)));
			item.setBodyCo(res.getInt(res.getColumnIndex(BODY_CONDITION)));
			item.setNote(res.getString(res.getColumnIndex(NOTE)));
			item.setStatusSport(res.getInt(res.getColumnIndex(MOTION_STATUS)));
			arrayListHeartRate.add(item);
			res.moveToNext();
		}
		return arrayListHeartRate;
	}

	public Integer getNewHeartRateaId() {
		int id = 0;
		Cursor res = myDb.rawQuery("select " + HEART_RATE_ID + " from "
				+ HEARTRATE_TABLE, null);
		try {
			id = res.getCount() + 1;
			res.close();
		} finally {
			if (res != null)
				res.close();
		}
		return id;
	}
}