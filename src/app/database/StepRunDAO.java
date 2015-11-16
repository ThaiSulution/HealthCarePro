package app.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.StepRunDTO;

public class StepRunDAO extends DbConnectionService {
	public static final String STEPRUN_TABLE = "STEPRUN";
	public static final String COLUMN_STEPRUN_ID = "StepRunId";
	public static final String COLUMN_STEPRUN_USER_ID = "UserId";
	public static final String COLUMN_STEPRUN_TIME = "Time";
	public static final String COLUMN_STEPRUN_TAGETS = "Tagets";
	public static final String COLUMN_STEPRUN_TOTAL_RUN = "TotalRun";
	public static final String COLUMN_STEPRUN_CALOS = "Calos";
	public static final String COLUMN_STEPRUN_TOTALMIN = "TotalMin";
	public static final String COLUMN_STEPRUN_DISTANCE = "Distance";

	public StepRunDAO(Context context) {
		super(context);
	}

	public boolean insertStepRun(StepRunDTO stepRunDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_STEPRUN_ID, this.getNewStepRunId());
			contentValues.put(COLUMN_STEPRUN_USER_ID, stepRunDTO.getUserId());
			contentValues.put(COLUMN_STEPRUN_TIME,
					stepRunDTO.getTime());
			contentValues.put(COLUMN_STEPRUN_TAGETS, stepRunDTO.getTagets());
			contentValues.put(COLUMN_STEPRUN_TOTAL_RUN,
					stepRunDTO.getTotalRun());
			contentValues.put(COLUMN_STEPRUN_CALOS,
					stepRunDTO.getCalos());
			contentValues.put(COLUMN_STEPRUN_TOTALMIN,
					stepRunDTO.getTotalMin());
			contentValues.put(COLUMN_STEPRUN_DISTANCE,
					stepRunDTO.getDistance());
			myDb.insert(STEPRUN_TABLE, null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public Integer deleteStepRun(Integer id) {
		return myDb.delete(STEPRUN_TABLE, COLUMN_STEPRUN_ID + " = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList<StepRunDTO> getListStepRun(Integer userId) {
		ArrayList<StepRunDTO> arrayListStepRun = new ArrayList<StepRunDTO>();
		Cursor res = myDb.rawQuery("select * from " + STEPRUN_TABLE + " where "
				+ COLUMN_STEPRUN_USER_ID + " = ?",
				new String[] { Integer.toString(userId) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			StepRunDTO item = new StepRunDTO();
			item.setStepRunId(res.getInt(res.getColumnIndex(COLUMN_STEPRUN_ID)));
			item.setUserId(userId);
			item.setTagets(res.getInt(res.getColumnIndex(COLUMN_STEPRUN_TAGETS)));
			item.setTime(res.getString(res
					.getColumnIndex(COLUMN_STEPRUN_TIME)));
			item.setTotalRun(res.getInt(res
					.getColumnIndex(COLUMN_STEPRUN_TOTAL_RUN)));
			arrayListStepRun.add(item);
			item.setCalos(res.getInt(res
					.getColumnIndex(COLUMN_STEPRUN_CALOS)));
			arrayListStepRun.add(item);
			item.setTotalMin(res.getInt(res
					.getColumnIndex(COLUMN_STEPRUN_TOTALMIN)));
			arrayListStepRun.add(item);
			item.setDistance(res.getFloat(res
					.getColumnIndex(COLUMN_STEPRUN_DISTANCE)));
			arrayListStepRun.add(item);
			res.moveToNext();
		}
		return arrayListStepRun;
	}

	public Integer getNewStepRunId() {
		Cursor res = myDb.rawQuery("select " + COLUMN_STEPRUN_ID + " from "
				+ STEPRUN_TABLE, null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}