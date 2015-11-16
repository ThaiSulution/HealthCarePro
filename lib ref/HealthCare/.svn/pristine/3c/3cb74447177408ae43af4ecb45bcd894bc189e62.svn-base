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
	public static final String COLUMN_STEPRUN_TOTAL_TIME = "Time";
	public static final String COLUMN_STEPRUN_TAGETS = "Tagets";
	public static final String COLUMN_STEPRUN_TOTAL_RUN = "TotalRun";

	public StepRunDAO(Context context) {
		super(context);
	}

	public boolean insertStepRun(StepRunDTO stepRunDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_STEPRUN_ID, this.getNewStepRunId());
			contentValues.put(COLUMN_STEPRUN_USER_ID, stepRunDTO.getUserId());
			contentValues.put(COLUMN_STEPRUN_TOTAL_TIME,
					stepRunDTO.getTime());
			contentValues.put(COLUMN_STEPRUN_TAGETS, stepRunDTO.getTagets());
			contentValues.put(COLUMN_STEPRUN_TOTAL_RUN,
					stepRunDTO.getTotalRun());
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
					.getColumnIndex(COLUMN_STEPRUN_TOTAL_TIME)));
			item.setTotalRun(res.getInt(res
					.getColumnIndex(COLUMN_STEPRUN_TOTAL_RUN)));
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