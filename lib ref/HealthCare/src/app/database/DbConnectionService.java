package app.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbConnectionService {
	SQLiteDatabase myDb;
	Database dbHelper;
	Context context;

	public DbConnectionService(Context context) {
		this.context = context;
		dbHelper = new Database(context);
		// open database
		try {
			openDb();
		} catch (SQLException e) {
			System.out.println(e.toString());
			Log.e("opendata", "k mo data");
		}
	}

	public void openDb() {
		try {
			myDb = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			Log.e("loimydb", e.toString());
		}
	}

	public void close() {
		dbHelper.close();
	}
}
