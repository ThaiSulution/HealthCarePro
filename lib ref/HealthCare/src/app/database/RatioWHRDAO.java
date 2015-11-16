package app.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.RatioWHRDTO;

public class RatioWHRDAO extends DbConnectionService {
	public static final String RATIOWHR_TABLE = "RATIOWHR";
	public static final String COLUMN_RATIOWHR_ID = "RatioWHRId";
	public static final String COLUMN_RATIOWHR_USER_ID = "UserId";
	public static final String COLUMN_RATIOWHR_TIME = "Time";
	public static final String COLUMN_RATIOWHR_RATIO = "Ratio";
	public static final String COLUMN_RATIOWHR_STATUS = "Status";

	public RatioWHRDAO(Context context) {
		super(context);
	}

	public boolean insertRatioWHR(RatioWHRDTO ratioWHRDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_RATIOWHR_ID, this.getNewRatioWHRId());
			contentValues.put(COLUMN_RATIOWHR_USER_ID, ratioWHRDTO.getUserId());
			contentValues.put(COLUMN_RATIOWHR_TIME, ratioWHRDTO.getTime());
			contentValues.put(COLUMN_RATIOWHR_RATIO, ratioWHRDTO.getRatio());
			contentValues.put(COLUMN_RATIOWHR_STATUS, ratioWHRDTO.getStatus());
			myDb.insert(RATIOWHR_TABLE, null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public Integer deleteRatioWHR(Integer id) {
		return myDb.delete(RATIOWHR_TABLE, COLUMN_RATIOWHR_ID + " = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList<RatioWHRDTO> getListRatioWHR(Integer userId) {
		ArrayList<RatioWHRDTO> arrayListRatioWHR = new ArrayList<RatioWHRDTO>();
		Cursor res = myDb.rawQuery("select * from " + RATIOWHR_TABLE + " where "
				+ COLUMN_RATIOWHR_USER_ID + " = ?",
				new String[] { Integer.toString(userId) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			RatioWHRDTO item = new RatioWHRDTO();
			item.setRatioWHRId(userId);
			item.setUserId(res.getInt(res
					.getColumnIndex(COLUMN_RATIOWHR_USER_ID)));
			item.setTime(res.getString(res.getColumnIndex(COLUMN_RATIOWHR_TIME)));
			item.setRatio(res.getString(res.getColumnIndex(COLUMN_RATIOWHR_RATIO)));
			item.setStatus(res.getString(res
					.getColumnIndex(COLUMN_RATIOWHR_STATUS)));
			arrayListRatioWHR.add(item);
			res.moveToNext();
		}
		return arrayListRatioWHR;
	}

	public Integer getNewRatioWHRId() {
		Cursor res = myDb.rawQuery("select " + COLUMN_RATIOWHR_ID + " from "
				+ RATIOWHR_TABLE, null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}
