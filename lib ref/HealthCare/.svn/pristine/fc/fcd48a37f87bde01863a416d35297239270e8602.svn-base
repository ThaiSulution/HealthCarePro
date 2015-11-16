package app.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.RatioBMIDTO;

public class RatioBMIDAO extends DbConnectionService {
	public static final String RATIOBMI_TABLE = "RATIOBMI";
	public static final String COLUMN_RATIOBMI_ID = "RatioBMIId";
	public static final String COLUMN_RATIOBMI_USER_ID = "UserId";
	public static final String COLUMN_RATIOBMI_TIME = "Time";
	public static final String COLUMN_RATIOBMI_RATIO = "Ratio";
	public static final String COLUMN_RATIOBMI_STATUS = "Status";

	public RatioBMIDAO(Context context) {
		super(context);
	}

	public boolean insertRatioBMI(RatioBMIDTO ratioBMIDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_RATIOBMI_ID, this.getNewRatioBMIId());
			contentValues.put(COLUMN_RATIOBMI_USER_ID, ratioBMIDTO.getUserId());
			contentValues.put(COLUMN_RATIOBMI_TIME, ratioBMIDTO.getTime());
			contentValues.put(COLUMN_RATIOBMI_RATIO, ratioBMIDTO.getRatio());
			contentValues.put(COLUMN_RATIOBMI_STATUS, ratioBMIDTO.getStatus());
			myDb.insert(RATIOBMI_TABLE, null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public Integer deleteRatioBMI(Integer id) {
		return myDb.delete(RATIOBMI_TABLE, COLUMN_RATIOBMI_ID + " = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList<RatioBMIDTO> getListRatioBMI(Integer userId) {
		ArrayList<RatioBMIDTO> arrayListRatioBMI = new ArrayList<RatioBMIDTO>();
		Cursor res = myDb.rawQuery("select * from " + RATIOBMI_TABLE + " where "
				+ COLUMN_RATIOBMI_USER_ID + " = ?",
				new String[] { Integer.toString(userId) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			RatioBMIDTO item = new RatioBMIDTO();
			item.setRatioBMIId(res.getInt(res
					.getColumnIndex(COLUMN_RATIOBMI_ID)));
			item.setUserId(userId);
			item.setTime(res.getString(res.getColumnIndex(COLUMN_RATIOBMI_TIME)));
			item.setRatio(res.getString(res.getColumnIndex(COLUMN_RATIOBMI_RATIO)));
			item.setStatus(res.getString(res.getColumnIndex(COLUMN_RATIOBMI_STATUS)));
			arrayListRatioBMI.add(item);
			res.moveToNext();
		}
		return arrayListRatioBMI;
	}

	public Integer getNewRatioBMIId() {
		Cursor res = myDb.rawQuery("select " + COLUMN_RATIOBMI_ID + " from "
				+ RATIOBMI_TABLE, null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}
