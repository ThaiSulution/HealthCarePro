package app.healthcare.symptom.object;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.healthcare.DbConnectionService;

public class SickSymptomDAO extends DbConnectionService {
	public SickSymptomDAO(Context context) {
		super(context);
	}

	public boolean insert(SickSymptomDTO dto) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("MA", this.getNewId());
			contentValues.put("TEN_BENH", dto.getTenBenh());
			contentValues.put("TRIEU_CHUNG", dto.getTrieuChung());
			contentValues.put("VI_TRI", dto.getViTri());
			myDb.insert("BENH_TRIEUCHUNG", null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public ArrayList<SickSymptomDTO> getListSymptom() {
		ArrayList<SickSymptomDTO> arrayList = new ArrayList<SickSymptomDTO>();
		Cursor res = myDb.rawQuery(
				"select TRIEU_CHUNG from BENH_TRIEUCHUNG group by TRIEU_CHUNG",
				null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			SickSymptomDTO item = new SickSymptomDTO();
			item.setTrieuChung(res.getString(res.getColumnIndex("TRIEU_CHUNG")));
			arrayList.add(item);
			res.moveToNext();
		}
		return arrayList;
	}
	
	public ArrayList<SickSymptomDTO> searchSymptom(String viTri) {
		ArrayList<SickSymptomDTO> arrayList = new ArrayList<SickSymptomDTO>();
		Cursor res = myDb.rawQuery(
				"select TRIEU_CHUNG from BENH_TRIEUCHUNG where VI_TRI = ? group by TRIEU_CHUNG",
				new String[] { viTri });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			SickSymptomDTO item = new SickSymptomDTO();
			item.setTrieuChung(res.getString(res.getColumnIndex("TRIEU_CHUNG")));
			arrayList.add(item);
			res.moveToNext();
		}
		return arrayList;
	}

	public ArrayList<SickSymptomDTO> searchSick(String symptom, String viTri) {
		ArrayList<SickSymptomDTO> arrayListSick = new ArrayList<SickSymptomDTO>();
		Cursor res = myDb
				.rawQuery(
						"select TEN_BENH from BENH_TRIEUCHUNG where VI_TRI = ? and TRIEU_CHUNG = ? group by TEN_BENH",
						new String[] {viTri, symptom });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			SickSymptomDTO item = new SickSymptomDTO();
			item.setTenBenh(res.getString(res.getColumnIndex("TEN_BENH")));
			arrayListSick.add(item);
			res.moveToNext();
		}
		return arrayListSick;
	}
	
	public ArrayList<SickSymptomDTO> searchSick(String symptom) {
		ArrayList<SickSymptomDTO> arrayListSick = new ArrayList<SickSymptomDTO>();
		Cursor res = myDb
				.rawQuery(
						"select TEN_BENH from BENH_TRIEUCHUNG where TRIEU_CHUNG = ? group by TEN_BENH",
						new String[] {symptom  });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			SickSymptomDTO item = new SickSymptomDTO();
			item.setTenBenh(res.getString(res.getColumnIndex("TEN_BENH")));
			arrayListSick.add(item);
			res.moveToNext();
		}
		return arrayListSick;
	}

	public Integer getNewId() {
		Cursor res = myDb.rawQuery("select MA from BENH_TRIEUCHUNG ", null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}