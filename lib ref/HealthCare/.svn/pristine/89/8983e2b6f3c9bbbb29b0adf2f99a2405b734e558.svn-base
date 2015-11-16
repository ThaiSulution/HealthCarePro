package app.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.HeartRateDTO;

public class HeartRateDAO extends DbConnectionService {

	public static final String HEARTRATE_TABLE = "HEARTRATE";
	public static final String COLUMN_HEARTRATE_ID = "HeartRateId";
	public static final String COLUMN_HEARTRATE_USER_ID = "UserId";
	public static final String COLUMN_HEARTRATE_TIME = "Time";
	public static final String COLUMN_HEARTRATE_HEARTRATE = "HeartRate";

	public HeartRateDAO(Context context) {
		super(context);
	}

	public boolean insertHeartRate(HeartRateDTO heartRate) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_HEARTRATE_ID, this.getNewHeartRateaId());
			contentValues.put(COLUMN_HEARTRATE_USER_ID, heartRate.getUserId());
			contentValues.put(COLUMN_HEARTRATE_TIME, heartRate.getTime());
			contentValues.put(COLUMN_HEARTRATE_HEARTRATE,
					heartRate.getHeartRate());
			myDb.insert(COLUMN_HEARTRATE_ID, null, contentValues);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer deleteHeartRate(Integer id) {
		return myDb.delete(HEARTRATE_TABLE, COLUMN_HEARTRATE_ID + " = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList<HeartRateDTO> getListHeartRate(Integer userId) {
		ArrayList<HeartRateDTO> arrayListHeartRate = new ArrayList<HeartRateDTO>();
		Cursor res = myDb.rawQuery("select * from " + HEARTRATE_TABLE
				+ " where " + COLUMN_HEARTRATE_USER_ID + " = ",
				new String[] { Integer.toString(userId) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			HeartRateDTO item = new HeartRateDTO();
			item.setHeartRateId(res.getInt(res
					.getColumnIndex(COLUMN_HEARTRATE_ID)));
			item.setUserId(userId);
			item.setTime(res.getString(res
					.getColumnIndex(COLUMN_HEARTRATE_TIME)));
			item.setHeartRate(res.getInt(res
					.getColumnIndex(COLUMN_HEARTRATE_HEARTRATE)));
			arrayListHeartRate.add(item);
			res.moveToNext();
		}
		return arrayListHeartRate;
	}

	public Integer getNewHeartRateaId() {
		Cursor res = myDb.rawQuery("select " + COLUMN_HEARTRATE_ID + " from "
				+ HEARTRATE_TABLE, null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}