package app.healthcare.drug;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.healthcare.DbConnectionService;

public class DrugDAO extends DbConnectionService {
	public DrugDAO(Context context) {
		super(context);
	}

	public boolean insertDrug(DrugDTO drugDto) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("MA_THUOC", this.getNewDrugId());
			contentValues.put("TEN_THUOC", drugDto.getTenThuoc());
			contentValues.put("NHOM_DUOC_LY", drugDto.getNhomDuocLy());
			contentValues.put("THANH_PHAN", drugDto.getThanhPhan());
			contentValues.put("CHI_DINH", drugDto.getChiDinh());
			contentValues.put("CHONG_CHI_DINH", drugDto.getChongChiDinh());
			contentValues.put("TUONG_TAC_THUOC", drugDto.getTuongTacThuoc());
			contentValues.put("TAC_DUNG_PHU", drugDto.getTacDungPhu());
			contentValues.put("CHU_Y_DE_PHONG", drugDto.getChuYDePhong());
			contentValues.put("LIEU_LUONG", drugDto.getLieuLuong());
			myDb.insert("THUOC", null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public ArrayList<DrugDTO> getListDrug() {
		ArrayList<DrugDTO> arrayListDrug = new ArrayList<DrugDTO>();
		Cursor res = myDb.rawQuery("select * from THUOC", null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			DrugDTO item = new DrugDTO();
			item.setMaThuoc(res.getInt(res.getColumnIndex("MA_THUOC")));
			item.setTenThuoc(res.getString(res.getColumnIndex("TEN_THUOC")));
			item.setNhomDuocLy(res.getString(res.getColumnIndex("NHOM_DUOC_LY")));
			item.setChiDinh(res.getString(res.getColumnIndex("CHI_DINH")));
			item.setChongChiDinh(res.getString(res
					.getColumnIndex("CHONG_CHI_DINH")));
			item.setTacDungPhu(res.getString(res.getColumnIndex("TAC_DUNG_PHU")));
			item.setChuYDePhong(res.getString(res
					.getColumnIndex("CHU_Y_DE_PHONG")));
			item.setLieuLuong(res.getString(res.getColumnIndex("LIEU_LUONG")));
			arrayListDrug.add(item);
			res.moveToNext();
		}
		return arrayListDrug;
	}
	
	public DrugDTO getDrug(String name) {
		DrugDTO drug = new DrugDTO();
		Cursor res = myDb.rawQuery(
				"select * from THUOC where TEN_THUOC = ?",
				new String[] { name });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			drug.setMaThuoc(res.getInt(res.getColumnIndex("MA_THUOC")));
			drug.setTenThuoc(res.getString(res.getColumnIndex("TEN_THUOC")));
			drug.setNhomDuocLy(res.getString(res.getColumnIndex("NHOM_DUOC_LY")));
			drug.setChiDinh(res.getString(res.getColumnIndex("CHI_DINH")));
			drug.setChongChiDinh(res.getString(res
					.getColumnIndex("CHONG_CHI_DINH")));
			drug.setTacDungPhu(res.getString(res.getColumnIndex("TAC_DUNG_PHU")));
			drug.setChuYDePhong(res.getString(res
					.getColumnIndex("CHU_Y_DE_PHONG")));
			drug.setLieuLuong(res.getString(res.getColumnIndex("LIEU_LUONG")));
			return drug;
		}
		return drug;
	}

	public ArrayList<DrugDTO> searchDrug(String name) {
		ArrayList<DrugDTO> arrayListDrug = new ArrayList<DrugDTO>();
		Cursor res = myDb.rawQuery(
				"select * from THUOC where TEN_THUOC like ?",
				new String[] { name + "%"});
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			DrugDTO item = new DrugDTO();
			item.setMaThuoc(res.getInt(res.getColumnIndex("MA_THUOC")));
			item.setTenThuoc(res.getString(res.getColumnIndex("TEN_THUOC")));
			item.setNhomDuocLy(res.getString(res.getColumnIndex("NHOM_DUOC_LY")));
			item.setChiDinh(res.getString(res.getColumnIndex("CHI_DINH")));
			item.setChongChiDinh(res.getString(res
					.getColumnIndex("CHONG_CHI_DINH")));
			item.setTacDungPhu(res.getString(res.getColumnIndex("TAC_DUNG_PHU")));
			item.setChuYDePhong(res.getString(res
					.getColumnIndex("CHU_Y_DE_PHONG")));
			item.setLieuLuong(res.getString(res.getColumnIndex("LIEU_LUONG")));
			arrayListDrug.add(item);
			res.moveToNext();
		}
		return arrayListDrug;
	}

	public Integer getNewDrugId() {
		Cursor res = myDb.rawQuery("select MA_THUOC from THUOC ", null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}
