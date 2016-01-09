package app.healthcare.symptom.object;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import app.healthcare.DbConnectionService;

public class SickDAO extends DbConnectionService {
	public SickDAO(Context context) {
		super(context);
	}

	public boolean insertSick(SickDTO dto) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("MA_BENH", this.getNewSickId());
			contentValues.put("TEN_BENH", dto.getTen());
			contentValues.put("MO_TA", dto.getMoTa());
			contentValues.put("DINH_NGHIA", dto.getDinhNghia());
			contentValues.put("TRIEU_CHUNG", dto.getTrieuChung());
			contentValues.put("GAP_BAC_SI", dto.getGapBacSi());
			contentValues.put("NGUYEN_NHAN", dto.getNguyenNhan());
			contentValues.put("NGUY_CO", dto.getNguyCo());
			contentValues.put("BIEN_CHUNG", dto.getBienChung());
			contentValues.put("XET_NGHIEM", dto.getXetNghiem());
			contentValues.put("DIEU_TRI", dto.getDieuTri());
			contentValues.put("THUOC", dto.getThuoc());
			contentValues.put("PHONG_CHONG", dto.getPhongChong());
			contentValues.put("HINH_ANH", dto.getHinhAnh());
			myDb.insert("BENH", null, contentValues);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public ArrayList<SickDTO> getListSick() {
		ArrayList<SickDTO> arrayListSick = new ArrayList<SickDTO>();
		Cursor res = myDb.rawQuery("select * from BENH", null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			SickDTO item = new SickDTO();
			item.setId(res.getInt(res.getColumnIndex("MA_BENH")));
			item.setTen(res.getString(res.getColumnIndex("TEN_BENH")));
			item.setMoTa(res.getString(res.getColumnIndex("MO_TA")));
			item.setDinhNghia(res.getString(res.getColumnIndex("DINH_NGHIA")));
			item.setTrieuChung(res.getString(res.getColumnIndex("TRIEU_CHUNG")));
			item.setGapBacSi(res.getString(res.getColumnIndex("GAP_BAC_SI")));
			item.setNguyenNhan(res.getString(res.getColumnIndex("NGUYEN_NHAN")));
			item.setNguyCo(res.getString(res.getColumnIndex("NGUY_CO")));
			item.setBienChung(res.getString(res.getColumnIndex("BIEN_CHUNG")));
			item.setXetNghiem(res.getString(res.getColumnIndex("XET_NGHIEM")));
			item.setDieuTri(res.getString(res.getColumnIndex("DIEU_TRI")));
			item.setThuoc(res.getString(res.getColumnIndex("THUOC")));
			item.setPhongChong(res.getString(res.getColumnIndex("PHONG_CHONG")));
			item.setHinhAnh(res.getString(res.getColumnIndex("HINH_ANH")));
			arrayListSick.add(item);
			res.moveToNext();
		}
		return arrayListSick;
	}

	public SickDTO getSick(String name) {
		SickDTO item = new SickDTO();
		Cursor res = myDb.rawQuery("select * from BENH where TEN_BENH = ?",
				new String[] { name });
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			item.setId(res.getInt(res.getColumnIndex("MA_BENH")));
			item.setTen(res.getString(res.getColumnIndex("TEN_BENH")));
			item.setMoTa(res.getString(res.getColumnIndex("MO_TA")));
			item.setDinhNghia(res.getString(res.getColumnIndex("DINH_NGHIA")));
			item.setTrieuChung(res.getString(res.getColumnIndex("TRIEU_CHUNG")));
			item.setGapBacSi(res.getString(res.getColumnIndex("GAP_BAC_SI")));
			item.setNguyenNhan(res.getString(res.getColumnIndex("NGUYEN_NHAN")));
			item.setNguyCo(res.getString(res.getColumnIndex("NGUY_CO")));
			item.setBienChung(res.getString(res.getColumnIndex("BIEN_CHUNG")));
			item.setXetNghiem(res.getString(res.getColumnIndex("XET_NGHIEM")));
			item.setDieuTri(res.getString(res.getColumnIndex("DIEU_TRI")));
			item.setThuoc(res.getString(res.getColumnIndex("THUOC")));
			item.setPhongChong(res.getString(res.getColumnIndex("PHONG_CHONG")));
			item.setHinhAnh(res.getString(res.getColumnIndex("HINH_ANH")));
			return item;
		}
		return item;
	}

//	public ArrayList<SickDTO> searchSick(String name) {
//		ArrayList<SickDTO> arrayListDrug = new ArrayList<SickDTO>();
//		Cursor res = myDb.rawQuery(
//				"select * from BENH where TEN_BENH like ?",
//				new String[] { name + "%" });
//		res.moveToFirst();
//		while (res.isAfterLast() == false) {
//			DrugDTO item = new DrugDTO();
//			item.setMaThuoc(res.getInt(res.getColumnIndex("MA_THUOC")));
//			item.setTenThuoc(res.getString(res.getColumnIndex("TEN_THUOC")));
//			item.setNhomDuocLy(res.getString(res.getColumnIndex("NHOM_DUOC_LY")));
//			item.setChiDinh(res.getString(res.getColumnIndex("CHI_DINH")));
//			item.setChongChiDinh(res.getString(res
//					.getColumnIndex("CHONG_CHI_DINH")));
//			item.setTacDungPhu(res.getString(res.getColumnIndex("TAC_DUNG_PHU")));
//			item.setChuYDePhong(res.getString(res
//					.getColumnIndex("CHU_Y_DE_PHONG")));
//			item.setLieuLuong(res.getString(res.getColumnIndex("LIEU_LUONG")));
//			arrayListDrug.add(item);
//			res.moveToNext();
//		}
//		return arrayListDrug;
//	}

	public Integer getNewSickId() {
		Cursor res = myDb.rawQuery("select MA_BENH from BENH ", null);
		if (res != null) {
			return res.getCount() + 1;
		} else
			return 1;
	}
}