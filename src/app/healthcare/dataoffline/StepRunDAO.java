package app.healthcare.dataoffline;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.healthcare.DbConnectionService;

public class StepRunDAO extends DbConnectionService {
	public StepRunDAO(Context context) {
		super(context);
	}

	public boolean insert(StepRunDTO dto) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("StepRunId", this.getNewId());
			contentValues.put("Taget", dto.getTaget());
			contentValues.put("Time", dto.getTime());
			contentValues.put("Step", dto.getStep());
			contentValues.put("Distance", dto.getDistance());
			contentValues.put("Calos", dto.getCalos());
			myDb.insert("STEPRUN ", null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public ArrayList<StepRunDTO> getListSymptom() {
		ArrayList<StepRunDTO> arrayList = new ArrayList<StepRunDTO>();
		Cursor res = myDb.rawQuery(
				"select TRIEU_CHUNG from BENH_TRIEUCHUNG group by TRIEU_CHUNG",
				null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			StepRunDTO item = new StepRunDTO();
			item.setTrieuChung(res.getString(res.getColumnIndex("TRIEU_CHUNG")));
			arrayList.add(item);
			res.moveToNext();
		}
		return arrayList;
	}
	
	public ArrayList<StepRunDTO> searchSymptom(String viTri) {
		ArrayList<StepRunDTO> arrayList = new ArrayList<StepRunDTO>();
		Cursor res = myDb.rawQuery(
				"select TRIEU_CHUNG from BENH_TRIEUCHUNG where VI_TRI = ? group by TRIEU_CHUNG",
				new String[] { viTri });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			StepRunDTO item = new StepRunDTO();
			item.setTrieuChung(res.getString(res.getColumnIndex("TRIEU_CHUNG")));
			arrayList.add(item);
			res.moveToNext();
		}
		return arrayList;
	}

	public ArrayList<StepRunDTO> searchSick(String symptom, String viTri) {
		ArrayList<StepRunDTO> arrayListSick = new ArrayList<StepRunDTO>();
		Cursor res = myDb
				.rawQuery(
						"select TEN_BENH from BENH_TRIEUCHUNG where VI_TRI = ? and TRIEU_CHUNG = ? group by TEN_BENH",
						new String[] {viTri, symptom });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			StepRunDTO item = new StepRunDTO();
			item.setTenBenh(res.getString(res.getColumnIndex("TEN_BENH")));
			arrayListSick.add(item);
			res.moveToNext();
		}
		return arrayListSick;
	}
	
	public ArrayList<StepRunDTO> searchSick(String symptom) {
		ArrayList<StepRunDTO> arrayListSick = new ArrayList<StepRunDTO>();
		Cursor res = myDb
				.rawQuery(
						"select TEN_BENH from BENH_TRIEUCHUNG where TRIEU_CHUNG = ? group by TEN_BENH",
						new String[] {symptom  });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			StepRunDTO item = new StepRunDTO();
			item.setTenBenh(res.getString(res.getColumnIndex("TEN_BENH")));
			arrayListSick.add(item);
			res.moveToNext();
		}
		return arrayListSick;
	}

	public Integer getNewId() {
		Cursor res = myDb.rawQuery("select StepRunId from STEPRUN ", null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}