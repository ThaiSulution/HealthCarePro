package app.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.TimeTableTakeDTO;

public class TimeTableTakeDAO extends DbConnectionService {

	public static final String TIMETABLETAKE_TABLE = "TIMETABLETAKE";
	public static final String COLUMN_TIMETABLETAKE_ID = "TimeTableTakeId";
	public static final String COLUMN_TIMETABLETAKE_USER_ID = "UserId";
	public static final String COLUMN_TIMETABLETAKE_SICK = "Sick";
	public static final String COLUMN_TIMETABLETAKE_TIME = "Time";
	public static final String COLUMN_TIMETABLETAKE_COUNTTIME = "CountTime";
	public static final String COLUMN_TIMETABLETAKE_STATUS = "Status";
	public static final String COLUMN_TIMETABLETAKE_TIMESPACING = "TimeSpacing";

	public TimeTableTakeDAO(Context context) {
		super(context);
	}

	public boolean insertOrUpdateTimeTableTake(TimeTableTakeDTO timeTableTakeDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			if (this.getNewTimeTableTakeId() > 1) {
				timeTableTakeDTO.setTimeTableTakeId(1);
				updateTimeTableTake(timeTableTakeDTO);
				return true;
			} else {

				contentValues.put(COLUMN_TIMETABLETAKE_ID,
						this.getNewTimeTableTakeId());
				contentValues.put(COLUMN_TIMETABLETAKE_USER_ID,
						timeTableTakeDTO.getUserId());
				contentValues.put(COLUMN_TIMETABLETAKE_SICK,
						timeTableTakeDTO.getSick());
				contentValues.put(COLUMN_TIMETABLETAKE_TIME,
						timeTableTakeDTO.getTime());
				contentValues.put(COLUMN_TIMETABLETAKE_COUNTTIME,
						timeTableTakeDTO.getCountTime());
				contentValues.put(COLUMN_TIMETABLETAKE_STATUS,
						timeTableTakeDTO.getStatus());
				contentValues.put(COLUMN_TIMETABLETAKE_TIMESPACING,
						timeTableTakeDTO.getTimeSpacing());
				myDb.insert(TIMETABLETAKE_TABLE, null, contentValues);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public Integer deleteTimeTableTake(Integer id) {
		return myDb.delete(TIMETABLETAKE_TABLE, COLUMN_TIMETABLETAKE_ID
				+ " = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<TimeTableTakeDTO> getListTimeTableTake() {
		ArrayList<TimeTableTakeDTO> arrayListTimeTableTake = new ArrayList<TimeTableTakeDTO>();
		Cursor res = myDb
				.rawQuery("select * from " + TIMETABLETAKE_TABLE, null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			TimeTableTakeDTO item = new TimeTableTakeDTO();
			item.setTimeTableTakeId(res.getInt(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_ID)));
			item.setUserId(res.getInt(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_USER_ID)));
			item.setSick(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_SICK)));
			item.setTime(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_TIME)));
			int temp = 0;
			temp = Integer.parseInt(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_COUNTTIME)));
			item.setCountTime(temp);
			item.setStatus(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_STATUS)));
			item.setTimeSpacing(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_TIMESPACING)));
			arrayListTimeTableTake.add(item);
			res.moveToNext();
		}
		return arrayListTimeTableTake;
	}

	public boolean updateTimeTableTake(TimeTableTakeDTO timeTableTakeDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_TIMETABLETAKE_USER_ID,
					timeTableTakeDTO.getUserId());
			contentValues.put(COLUMN_TIMETABLETAKE_SICK,
					timeTableTakeDTO.getSick());
			contentValues.put(COLUMN_TIMETABLETAKE_TIME,
					timeTableTakeDTO.getTime());
			contentValues.put(COLUMN_TIMETABLETAKE_COUNTTIME,
					timeTableTakeDTO.getCountTime());
			contentValues.put(COLUMN_TIMETABLETAKE_STATUS,
					timeTableTakeDTO.getStatus());
			contentValues.put(COLUMN_TIMETABLETAKE_TIMESPACING,
					timeTableTakeDTO.getTimeSpacing());
			myDb.update(TIMETABLETAKE_TABLE, contentValues,
					COLUMN_TIMETABLETAKE_ID + " = ? ", new String[] { Integer
							.toString(timeTableTakeDTO.getTimeTableTakeId()) });
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public TimeTableTakeDTO getTimeTableTake(Integer id) {
		TimeTableTakeDTO timeTableTake = new TimeTableTakeDTO();
		Cursor res = myDb.rawQuery("select * from " + TIMETABLETAKE_TABLE
				+ " where " + COLUMN_TIMETABLETAKE_ID + " = ?",
				new String[] { Integer.toString(id) });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			timeTableTake.setTimeTableTakeId(res.getInt(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_ID)));
			timeTableTake.setUserId(id);
			timeTableTake.setSick(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_SICK)));
			timeTableTake.setTime(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_TIME)));
			int temp = 0;
			temp = Integer.parseInt(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_COUNTTIME)));
			timeTableTake.setCountTime(temp);
			timeTableTake.setStatus(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_STATUS)));
			timeTableTake.setTimeSpacing(res.getString(res
					.getColumnIndex(COLUMN_TIMETABLETAKE_TIMESPACING)));
			return timeTableTake;
		}
		return null;
	}

	public Integer getNewTimeTableTakeId() {
		Cursor res = myDb.rawQuery("select " + COLUMN_TIMETABLETAKE_ID
				+ " from " + TIMETABLETAKE_TABLE, null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}
