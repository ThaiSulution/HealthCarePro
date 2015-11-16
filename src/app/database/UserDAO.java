package app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.dto.UserDTO;

public class UserDAO extends DbConnectionService {

	public static final String USER_TABLE = "USER";
	public static final String COLUMN_USER_ID = "UserId";
	public static final String COLUMN_USER_USERNAME = "Username";
	public static final String COLUMN_USER_TIMESTART = "TimeStart";
	public static final String COLUMN_USER_TARGET = "StepTarget";

	public UserDAO(Context context) {
		super(context);
	}

	public boolean insertUSER(UserDTO userDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_USER_ID, this.getNewUserId());
			contentValues.put(COLUMN_USER_USERNAME, userDTO.getUserName());
			contentValues.put(COLUMN_USER_TIMESTART, userDTO.getTimeStrat());
			contentValues.put(COLUMN_USER_TARGET, userDTO.getTarget());
			myDb.insert(USER_TABLE, null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean updateUSER(UserDTO userDTO) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(COLUMN_USER_USERNAME, userDTO.getUserName());
			contentValues.put(COLUMN_USER_TIMESTART, userDTO.getTimeStrat());
			contentValues.put(COLUMN_USER_TARGET, userDTO.getTarget());
			myDb.update(USER_TABLE, contentValues, COLUMN_USER_ID + " = ? ",
					new String[] { Integer.toString(1) });
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public Integer deleteUSER(Integer id) {
		return myDb.delete(USER_TABLE, COLUMN_USER_ID + " = ? ",
				new String[] { Integer.toString(id) });
	}

	public UserDTO getUser() {
		UserDTO user = new UserDTO();
		Cursor res = myDb.rawQuery("select * from " + USER_TABLE, null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			user.setUserId(Integer.parseInt(String.valueOf(res.getInt(res
					.getColumnIndex(COLUMN_USER_ID)))));
			user.setUserName(res.getString(res
					.getColumnIndex(COLUMN_USER_USERNAME)));
			user.setTimeStrat(res.getString(res
					.getColumnIndex(COLUMN_USER_TIMESTART)));
			user.setTarget(res.getInt(res.getColumnIndex(COLUMN_USER_TARGET)));
			res.moveToNext();
		}
		return user;
	}

	public Integer getNewUserId() {
		int id = 0;
		Cursor res = myDb.rawQuery("select " + COLUMN_USER_ID + " from "
				+ USER_TABLE, null);
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
