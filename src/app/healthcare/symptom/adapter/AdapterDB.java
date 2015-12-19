/*  Copyright [2015] [Nguyá»…n HoĂ ng VÅ© , Nguyá»…n Phi Viá»…n]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

package app.healthcare.symptom.adapter;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import app.symptom.list.Materials;
import app.symptom.object.MarketMaterial;
import app.symptom.object.MarketScheduleList;

public class AdapterDB {
	public static final String TAG = "DBAdapter";
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "TenBenh";

	private DatabaseHelper mDbHelper;
	public static SQLiteDatabase mDB;

	private static final String CREATE_LOAIBENH_TABLE = "CREATE TABLE loaibenh (idloaibenh text primary key, tenloaibenh text);";
	private static final String CREATE_BENH_TABLE = "CREATE TABLE benh (idbenh text primary key , tenbenh text, mota text, chitiet text, hinhanh text, idloaibenh text, videourl text);";
	//private static final String CREATE_LOAIMONAN_TABLE = "CREATE TABLE loaimonan (idloaimonan text primary key, tenloaimonan text);";
	//private static final String CREATE_MONAN_TABLE = "CREATE TABLE monan (idmonan text primary key, tenmonan text, khauphan text, congthuc text, hinhanh text, idloaimonan text, videourl text);";
	//private static final String CREATE_LOAINGUYENLIEU_TABLE = "CREATE TABLE loainguyenlieu (idloainguyenlieu text primary key, tenloai text);";
	private static final String CREATE_CHITIET_TABLE = "CREATE TABLE chitiet (idchitiet text primary key, motachitiet text, donvi text, idloainguyenlieu text);";
	private static final String CREATE_CHITIETBENH_TABLE = "CREATE TABLE chitietbenh (idbenh text , idchitiet text, soluong text, constraint pk_chitietmonan primary key (idbenh, idchitiet));";
	private static final String CREATE_QUYENHAN_TABLE = "CREATE TABLE quyenhan (idquyenhan text primary key, quyenhan text);";
	private static final String CREATE_NGUOIDUNG_TABLE = "CREATE TABLE nguoidung (idnguoidung text primary key,matkhau text, hoten text, ngaysinh date, email text, idquyenhan text);";
	//private static final String CREATE_THUCDONTRONGTUAN_TABLE = "CREATE TABLE thucdontrongtuan (stt integer auto increment primary key, idnguoidung text, thu integer, buoi integer, idmonan text);";
	//private static final String CREATE_PHATSINH_TABLE = "CREATE TABLE phatsinh (idnguoidung text primary key, songuoilon text, sotreem text, songaytrongtuan text, sobuaan text);";
	//private static final String CREATE_NGUYENLIEUDICHO_TABLE = "CREATE TABLE nguyenlieudicho (idnguoidung text, idnguyenlieu text, tongsoluong text, isbought integer, constraint pk_nguyenlieudicho primary key (idnguoidung, idnguyenlieu));";
	//private static final String CREATE_NHACNHO_TABLE = "CREATE TABLE NhacNho (Thu integer, Gio integer, Phut integer,Note text);";
	private static final String DATABASE_NAME = "Database_Benh";
   // private static final String DATABASE_TABLE = "MonAn";
	private static final int DATABASE_VERSION = 1;
	private final Context mContext;

	// loai mon an
	private static final String INSERT_LOAIMONAN1 = "INSERT INTO loaimonan VALUES ('MCA', 'MĂ³n canh');";
	private static final String INSERT_LOAIMONAN2 = "INSERT INTO loaimonan VALUES ('MCH', 'MĂ³n chiĂªn');";
	private static final String INSERT_LOAIMONAN3 = "INSERT INTO loaimonan VALUES ('MHA', 'MĂ³n háº¥p');";
	private static final String INSERT_LOAIMONAN4 = "INSERT INTO loaimonan VALUES ('MMA', 'MĂ³n máº·n');";
	private static final String INSERT_LOAIMONAN5 = "INSERT INTO loaimonan VALUES ('MNU', 'MĂ³n nÆ°á»›ng');";
	private static final String INSERT_LOAIMONAN6 = "INSERT INTO loaimonan VALUES ('MTR', 'MĂ³n trá»™n');";
	private static final String INSERT_LOAIMONAN7 = "INSERT INTO loaimonan VALUES ('MXA', 'MĂ³n xĂ o');";
	private static final String INSERT_LOAIMONAN8 = "INSERT INTO loaimonan VALUES ('NEW', 'MĂ³n Äƒn tá»± thĂªm');";
	
	// loai benh
		private static final String INSERT_LOAIBENH1 = "INSERT INTO loaibenh VALUES ('BTIM', 'Bá»‡nh Tim');";
		private static final String INSERT_LOAIBENH2 = "INSERT INTO loaibenh VALUES ('BPHOI', 'Bá»‡nh Phá»•i');";
		//private static final String INSERT_LOAIBENH3 = "INSERT INTO loaibenh VALUES ('BCA', 'Bá»‡nh Ung ThÆ°');";
		
		// benh - http://www.tribenhtim.com/
		private static final String INSERT_BENH1 = "INSERT INTO benh VALUES ( 1, 'Bá»‡nh Tim To', 2, 'BÆ°á»›c 1 : Æ¯á»›p thá»‹t vá»›i háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, kĂ¨m thĂªm má»™t chĂºt tiĂªu, Ä‘á»ƒ khoáº£ng 15 phĂºt cho ngáº¥m gia vá»‹. TĂ¡ch tá»«ng lĂ¡ báº¯p cáº£i, rá»­a sáº¡ch, láº¡ng bï¿½? pháº§n sá»‘ng lĂ¡. Tráº§n sÆ¡ báº¯p cáº£i, hĂ nh lĂ¡ báº±ng nÆ°á»›c sĂ´i rá»“i ngĂ¢m vĂ o nÆ°á»›c láº¡nh Ä‘á»ƒ rau giá»¯ nguyĂªn Ä‘Æ°á»£c mĂ u.@@@BÆ°á»›c 2 : Tráº£i tá»«ng lĂ¡ báº¯p cáº£i ra, cho thá»‹t vĂ o giá»¯a rá»“i cuá»‘n láº¡i. DĂ¹ng hĂ nh cháº§n cá»™t láº¡i.@@@BÆ°á»›c 3 : DĂ¹ng ná»“i nÆ°á»›c luá»™c báº¯p cáº£i vĂ  hĂ nh lĂ¡ lĂ m nÆ°á»›c dĂ¹ng, Ä‘un sĂ´i láº¡i, cho báº¯p cáº£i tráº¯ng cuá»™n thá»‹t vĂ o Ä‘un Ä‘áº¿n khi gáº§n chĂ­n thĂ¬ cho cĂ  chua vĂ o. NĂªm thĂªm háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, má»™t chĂºt muá»‘i cho vá»«a Äƒn. ï¿½?un sĂ´i vĂ  cho hĂ nh lĂ¡ vĂ o, táº¯t báº¿p.', 'drawable/benh1', 'BTT', 'https://www.youtube.com/watch?v=tuVfLT0gMyQ');";
		private static final String INSERT_BENH2 = "INSERT INTO benh VALUES (2, 'Bá»‡nh Tim To', 2, 'BÆ°á»›c 1 : Æ¯á»›p thá»‹t vá»›i háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, kĂ¨m thĂªm má»™t chĂºt tiĂªu, Ä‘á»ƒ khoáº£ng 15 phĂºt cho ngáº¥m gia vá»‹. TĂ¡ch tá»«ng lĂ¡ báº¯p cáº£i, rá»­a sáº¡ch, láº¡ng bï¿½? pháº§n sá»‘ng lĂ¡. Tráº§n sÆ¡ báº¯p cáº£i, hĂ nh lĂ¡ báº±ng nÆ°á»›c sĂ´i rá»“i ngĂ¢m vĂ o nÆ°á»›c láº¡nh Ä‘á»ƒ rau giá»¯ nguyĂªn Ä‘Æ°á»£c mĂ u.@@@BÆ°á»›c 2 : Tráº£i tá»«ng lĂ¡ báº¯p cáº£i ra, cho thá»‹t vĂ o giá»¯a rá»“i cuá»‘n láº¡i. DĂ¹ng hĂ nh cháº§n cá»™t láº¡i.@@@BÆ°á»›c 3 : DĂ¹ng ná»“i nÆ°á»›c luá»™c báº¯p cáº£i vĂ  hĂ nh lĂ¡ lĂ m nÆ°á»›c dĂ¹ng, Ä‘un sĂ´i láº¡i, cho báº¯p cáº£i tráº¯ng cuá»™n thá»‹t vĂ o Ä‘un Ä‘áº¿n khi gáº§n chĂ­n thĂ¬ cho cĂ  chua vĂ o. NĂªm thĂªm háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, má»™t chĂºt muá»‘i cho vá»«a Äƒn. ï¿½?un sĂ´i vĂ  cho hĂ nh lĂ¡ vĂ o, táº¯t báº¿p.', 'drawable/benh1', 'BTT', 'https://www.youtube.com/watch?v=tuVfLT0gMyQ');";
		
		// chi tiet benh
		//private static final String INSERT_CHITIETBENH1 = "INSERT INTO chitietbenh VALUES ('BTIM_BTT_1', 'Ci_Ba_1', 500);";
		//private static final String INSERT_CHITIETBENH2 = "INSERT INTO chitietbenh VALUES ('BTIM_BTT_2', 'Qu_Ca_1', 2);";

		//loai trieu chung
		private static final String INSERT_LOAITRIEUCHUNG1 = "INSERT INTO loaitrieuchung VALUES ('dd', 'ï¿½?au Ä‘á»›n');";
		private static final String INSERT_LOAITRIEUCHUNG2 = "INSERT INTO loaitrieuchung VALUES ('st', 'SÆ°ng Táº¥y');";
		
		//trieu chung
		private static final String INSERT_TRIEUCHUNG1 = "INSERT INTO trieuchung VALUES ('dd_tt_1', 'Ä‘au ngá»±c', 'xáº¥p', 'dd');";
		private static final String INSERT_TRIEUCHUNG2 = "INSERT INTO trieuchung VALUES ('Bt_Mo_1', 'náº·ng ngá»±c','xáº¥p', 'Bt');";
		private static final String INSERT_TRIEUCHUNG3 = "INSERT INTO trieuchung VALUES ('Bt_Mo_1', 'khĂ³ thá»Ÿ', 'xáº¥p', 'Bt');";
		private static final String INSERT_TRIEUCHUNG4 = "INSERT INTO trieuchung VALUES ('Bt_Mo_1', 'má»‡t mï¿½?i', 'xáº¥p', 'Bt');";
		private static final String INSERT_TRIEUCHUNG5 = "INSERT INTO trieuchung VALUES ('Bt_Mo_1', 'ngáº¥t', 'xáº¥p', 'Bt');";
		private static final String INSERT_TRIEUCHUNG6 = "INSERT INTO trieuchung VALUES ('Bt_Mo_1', 'hoa máº¯t', choĂ¡ng vĂ¡ng', 'xáº¥p', 'Bt');";
		private static final String INSERT_TRIEUCHUNG7 = "INSERT INTO trieuchung VALUES ('Bt_Mo_1', 'nhá»‹p tim nhanh, Ä‘Ă¡nh trá»‘ng ngá»±c', 'xáº¥p', 'Bt');";
		
	// mon an
	//private static final String INSERT_BENH1 = "INSERT INTO monan VALUES ('MCA_CBCCT_9', 'ABC', 2, 'BÆ°á»›c 1 : Æ¯á»›p thá»‹t vá»›i háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, kĂ¨m thĂªm má»™t chĂºt tiĂªu, Ä‘á»ƒ khoáº£ng 15 phĂºt cho ngáº¥m gia vá»‹. TĂ¡ch tá»«ng lĂ¡ báº¯p cáº£i, rá»­a sáº¡ch, láº¡ng bï¿½? pháº§n sá»‘ng lĂ¡. Tráº§n sÆ¡ báº¯p cáº£i, hĂ nh lĂ¡ báº±ng nÆ°á»›c sĂ´i rá»“i ngĂ¢m vĂ o nÆ°á»›c láº¡nh Ä‘á»ƒ rau giá»¯ nguyĂªn Ä‘Æ°á»£c mĂ u.@@@BÆ°á»›c 2 : Tráº£i tá»«ng lĂ¡ báº¯p cáº£i ra, cho thá»‹t vĂ o giá»¯a rá»“i cuá»‘n láº¡i. DĂ¹ng hĂ nh cháº§n cá»™t láº¡i.@@@BÆ°á»›c 3 : DĂ¹ng ná»“i nÆ°á»›c luá»™c báº¯p cáº£i vĂ  hĂ nh lĂ¡ lĂ m nÆ°á»›c dĂ¹ng, Ä‘un sĂ´i láº¡i, cho báº¯p cáº£i tráº¯ng cuá»™n thá»‹t vĂ o Ä‘un Ä‘áº¿n khi gáº§n chĂ­n thĂ¬ cho cĂ  chua vĂ o. NĂªm thĂªm háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, má»™t chĂºt muá»‘i cho vá»«a Äƒn. ï¿½?un sĂ´i vĂ  cho hĂ nh lĂ¡ vĂ o, táº¯t báº¿p.', 'drawable/monan9', 'MCA', 'http://www.youtube.com/watch?v=7nbIId-UGDM');";
	private static final String INSERT_MONAN2 = "INSERT INTO monan VALUES ('MCA_CBCGS_18', 'Canh báº¯p cáº£i giĂ² sá»‘ng', 2, 'BÆ°á»›c 1 : Trá»™n Ä‘ï¿½?u giĂ² sá»‘ng vá»›i hĂ nh tĂ­m vĂ  tiĂªu xay.@@@BÆ°á»›c 2 : ï¿½?un sĂ´i nÆ°á»›c láº¡nh, cho giĂ² sá»‘ng vĂ o náº¥u chĂ­n.@@@BÆ°á»›c 3 : Cho tiáº¿p báº¯p cáº£i vĂ o náº¥u chĂ­n. NĂªm háº¡t nĂªm cho vá»«a Äƒn.@@@BÆ°á»›c 4 : Ráº¯c tiĂªu xay vĂ  trang trĂ­ vá»›i hĂ nh lĂ¡, ngĂ² rĂ­. DĂ¹ng nĂ³ng.', 'drawable/monan18', 'MCA', 'not');";
	private static final String INSERT_MONAN3 = "INSERT INTO monan VALUES ('MCA_CBHSN_13', 'Canh báº¯p háº§m sÆ°ï¿½?n non', 2, 'BÆ°á»›c 1 : SÆ°ï¿½?n non rá»­a vá»›i má»™t chĂºt nÆ°á»›c muá»‘i cho sáº¡ch. Cháº·t khĂºc vá»«a Äƒn. Báº¯p rá»­a sáº¡ch cháº·t khĂºc. Cá»§ nÄƒng rá»­a sáº¡ch. Cá»§ nĂ o to thĂ¬ cáº¯t Ä‘Ă´i. CĂ  rá»‘t rá»­a sáº¡ch, tá»‰a hoa cho Ä‘áº¹p vĂ  cáº¯t khĂºc. TĂ¡o tĂ u ngĂ¢m má»™t chĂºt cho mï¿½?m rá»“i rá»­a sáº¡ch, Ä‘á»ƒ rĂ¡o.@@@BÆ°á»›c 2 : Cho sÆ°ï¿½?n non vĂ o ná»“i háº§m. NÆ°á»›c vá»«a sĂ´i vá»›t bï¿½?t vĂ  cho tiáº¿p báº¯p má»¹, cá»§ nÄƒng vĂ o háº§m chung vĂ  nĂªm chĂºt muá»‘i.@@@BÆ°á»›c 3 : Báº¯p vĂ  cá»§ nÄƒng hÆ¡i mï¿½?m thĂ¬ cho cĂ  rá»‘t vĂ  tĂ¡o tĂ u vĂ o háº§m. Náº¥u cho Ä‘áº¿n khi cĂ  rá»‘t mï¿½?m thĂ¬ nĂªm háº¡t nĂªm Knorr, muá»‘i, tiĂªu vá»«a Äƒn.@@@BÆ°á»›c 4 : Trang trĂ­ thĂªm báº±ng hĂ nh, ngĂ² cáº¯t khĂºc. DĂ¹ng nĂ³ng.', 'drawable/monan13', 'MCA', 'not');";
	private static final String INSERT_MONAN4 = "INSERT INTO monan VALUES ('MCA_CCRKQ_29', 'Canh cĂ¡ rĂ´ khá»• qua', 4, 'BÆ°á»›c 1 : Luá»™c cĂ¡ rĂ´ cho chĂ­n sau Ä‘Ă³ gá»¡ láº¥y thá»‹t. Æ¯á»›p thá»‹t cĂ¡ vá»›i Ă­t háº¡t nĂªm Knorr tá»« náº¥m & rong biá»ƒn, nÆ°á»›c máº¯m cháº¥m Knorr vĂ  tiĂªu xay.@@@BÆ°á»›c 2 : XĂ o sÆ¡ cĂ¡ vá»›i tï¿½?i vĂ  dáº§u Äƒn cho thÆ¡m, Ä‘á»ƒ riĂªng má»™t bĂªn.@@@BÆ°á»›c 3 : Khá»• qua cho vĂ o nÆ°á»›c cĂ¡, náº¥u chĂ­n. NĂªm vá»›i háº¡t nĂªm Knorr tá»« náº¥m & rong biá»ƒn, nÆ°á»›c máº¯m cháº¥m Knorr, dáº§u Äƒn cho vá»«a Äƒn.@@@BÆ°á»›c 4 : MĂºc canh vĂ o tĂ´, thĂªm thá»‹t cĂ¡, hĂ nh lĂ¡, ngĂ² rĂ­. DĂ¹ng nĂ³ng.@@@MĂ¡ch nhï¿½? : Chï¿½? nÆ°á»›c sĂ´i rá»“i má»›i tháº£ cĂ¡ vĂ o, vĂ¬ cĂ¡ rĂ´ ráº¥t mau chĂ­n mï¿½?m. Luá»™c cĂ¡ trong khoáº£ng 5-7 phĂºt lĂ  cĂ¡ Ä‘Ă£ mï¿½?m, khĂ´ng nĂªn luá»™c cĂ¡ quĂ¡ lĂ¢u lĂ m thá»‹t cĂ¡ bá»‹ rĂ£ ra, khi Ä‘Ă³ vá»›t cĂ¡ gá»¡ láº¥y thá»‹t sáº½ dá»… bá»‹ cĂ²n sĂ³t láº¡i xÆ°Æ¡ng nhï¿½?.', 'drawable/monan29', 'MCA', 'not');";
	private static final String INSERT_MONAN5 = "INSERT INTO monan VALUES ('MCA_CCRN_30', 'Canh cua rau nhĂºt', 3, 'BÆ°á»›c 1 : HoĂ  thá»‹t cua vá»›i nÆ°á»›c, dĂ¹ng tay bĂ³p Ä‘ï¿½?u. Gáº¡n láº¥y nÆ°á»›c, bï¿½? pháº§n xĂ¡c cua. HoĂ  vá»›i nÆ°á»›c cua 1 muá»—ng cĂ  phĂª háº¡t nĂªm Knorr, 1 muá»—ng canh nÆ°á»›c máº¯m cháº¥m Knorr.@@@BÆ°á»›c 2 : Báº¯c ná»“i lĂªn báº¿p, Ä‘un nhï¿½? lá»­a, dĂ¹ng Ä‘Å©a khuáº¥y Ä‘ï¿½?u Ä‘áº¿n khi thá»‹t cua ná»•i lĂªn máº·t.@@@BÆ°á»›c 3 : Vá»›t thá»‹t cua Ä‘á»ƒ riĂªng, cho rau nhĂºt vĂ o náº¥u chĂ­n, nĂªm láº¡i cho vá»«a Äƒn.@@@BÆ°á»›c 4 : MĂºc cua vĂ o bĂ¡t, trang trĂ­ vá»›i ngĂ² gai, á»›t. DĂ¹ng nĂ³ng.@@@MĂ¡ch nhï¿½? : ï¿½?á»ƒ riĂªu cua Ä‘Ă³ng thĂ nh bĂ¡nh, Ä‘áº§u tiĂªn khi má»›i báº¯c nÆ°á»›c cua lĂªn náº¥u, dĂ¹ng Ä‘Å©a khuáº¥y nháº¹ theo 1 chiï¿½?u trong vĂ i phĂºt cho cua khĂ´ng bá»‹ láº¯ng xuá»‘ng Ä‘Ă¡y, sau Ä‘Ă³ khĂ´ng khuáº¥y ná»¯a. ï¿½?un canh trĂªn lá»­a vá»«a khi canh báº¯t Ä‘áº§u sĂ´i, háº¡ lá»­a nhï¿½? cho thá»‹t cua ná»•i lĂªn Ä‘Ă³ng thĂ nh bĂ¡nh. Rau nhĂºt tuá»‘t bï¿½? pháº§n xá»‘p tráº¯ng, ngáº¯t khĂºc, bï¿½? máº¯t gĂºt, giá»¯ láº¡i nhĂ¡nh non, Ä‘oáº¡n nĂ o giĂ  xá»‘p vĂ  cĂ nh giĂ  thĂ¬ bï¿½?, ngĂ¢m rá»­a nhiï¿½?u láº§n vá»›i nÆ°á»›c cho sáº¡ch.', 'drawable/monan30', 'MCA', 'not');";
	private static final String INSERT_MONAN6 = "INSERT INTO monan VALUES ('MCA_CDCCBB_20', 'Canh dÆ°a cáº£i chua báº¯p bĂ²', 3, 'BÆ°á»›c 1 : Báº¯p bĂ² rá»­a sáº¡ch, bï¿½? mĂ ng nháº§y, luá»™c sÆ¡ trong nÆ°á»›c cĂ³ Ă­t gá»«ng Ä‘áº­p giáº­p, vá»›t ra, xáº¯t miáº¿ng mï¿½?ng. DÆ°a cáº£i chua rá»­a sáº¡ch, bĂ³p cho ra bá»›t nÆ°á»›c chua, xáº¯t nhï¿½?. CĂ  chua rá»­a sáº¡ch, cáº¯t mĂºi cau. HĂ nh tĂ­m bĂ³c vï¿½?, rá»­a sáº¡ch, xáº¯t lĂ¡t mï¿½?ng. HĂ nh ngĂ² rá»­a sáº¡ch, xáº¯t nhï¿½?.@@@BÆ°á»›c 2 : Báº¯c ná»“i nÆ°á»›c dĂ¹ng lĂªn báº¿p, khi nÆ°á»›c dĂ¹ng sĂ´i, cho báº¯p bĂ² vĂ o, Ä‘á»ƒ lá»­a riu riu, náº¥u Ä‘áº¿n khi báº¯p bĂ² hÆ¡i mï¿½?m.@@@BÆ°á»›c 3 : Cho cáº£i chua vĂ o, náº¥u Ä‘áº¿n khi báº¯p bĂ² tháº­t mï¿½?m, cho cĂ  chua vĂ o, nĂªm háº¡t nĂªm, Ä‘Æ°ï¿½?ng, bá»™t ngï¿½?t. Khi canh sĂ´i láº¡i thĂ¬ nháº¯c xuá»‘ng.@@@BÆ°á»›c 4 : MĂºc canh ra tĂ´, ráº¯c hĂ nh ngĂ² lĂªn máº·t.', 'drawable/monan20', 'MCA', 'not');";
	private static final String INSERT_MONAN7 = "INSERT INTO monan VALUES ('MCA_CGHNVT_17', 'Canh gĂ  háº§m náº¥m vĂ  trá»©ng', 4, 'BÆ°á»›c 1 : ï¿½?Ă¡nh trá»©ng nháº¹ cho tÆ¡i ra nhÆ°ng khĂ´ng Ä‘Ă¡nh quĂ¡ ká»¹. NĂªm vĂ o trá»©ng má»™t chĂºt muá»‘i, háº¡t nĂªm, Ä‘Æ°ï¿½?ng vĂ  tiĂªu.@@@BÆ°á»›c 2 : DĂ¹ng phá»…u rĂ³t trá»©ng vĂ o ruá»™t non vĂ  cá»™t cháº·t hai Ä‘áº§u. Cho lÆ°á»£ng trá»©ng vá»«a Ä‘á»§ Ä‘á»ƒ khi luá»™c ruá»™t khĂ´ng bá»‹ ná»Ÿ bung.@@@BÆ°á»›c 3 : NÆ°á»›c vá»«a nĂ³ng thĂ¬ cho ruá»™t vĂ o luá»™c cho Ä‘áº¿n khi vá»«a chĂ­n trá»©ng. Vá»›t ra vĂ  cho vĂ o thau nÆ°á»›c láº¡nh cho nguá»™i.@@@BÆ°á»›c 4 : Lá»™t bï¿½? lá»›p vï¿½? mï¿½?ng bĂªn ngoĂ i Ä‘á»ƒ ruá»™t khi Äƒn khĂ´ng bá»‹ dai rá»“i cáº¯t khĂºc nhï¿½?. ï¿½?á»ƒ trá»©ng ná»Ÿ Ä‘ï¿½?u vĂ  Ä‘áº¹p, khĂ´ng nĂªn cáº¯t xĂ©o ruá»™t.@@@BÆ°á»›c 5 : Cho náº¥m Ä‘Ă´ng cĂ´, náº¥m mĂ¨o vĂ  gĂ  vĂ o ná»“i nÆ°á»›c háº§m, nĂªm chĂºt muá»‘i, háº¡t nĂªm.@@@BÆ°á»›c 6 : Khi gĂ  vá»«a chĂ­n mï¿½?m thĂ¬ cho trá»©ng cáº¯t khĂºc vĂ o háº§m tiáº¿p. Váº·n lá»­a nhï¿½? láº¡i, khi trá»©ng vá»«a ná»Ÿ thĂ¬ táº¯t báº¿p. NĂªm náº¿m láº¡i cho vá»«a Äƒn.@@@BÆ°á»›c 7 : Trang trĂ­ thĂªm má»™t chĂºt hĂ nh, ngĂ² vĂ  dĂ¹ng nĂ³ng.', 'drawable/monan17', 'MCA', 'not');";
	private static final String INSERT_MONAN8 = "INSERT INTO monan VALUES ('MCA_CGNX_39', 'Canh gĂ  náº¥u sáº£', 2, 'BÆ°á»›c 1 : Luá»™c sÆ¡ thá»‹t gĂ  vá»›i Ă­t muá»‘i.@@@BÆ°á»›c 2 : ï¿½?un sĂ´i nÆ°á»›c lï¿½?c, cho thá»‹t, sáº£ cĂ¢y vĂ o náº¥u khoáº£ng 10 phĂºt cho chĂ­n. NĂªm háº¡t nĂªm Knorr vĂ  thĂªm náº¥m bĂ o ngÆ°, cĂ  rá»‘t vĂ o ná»“i, náº¥u tiáº¿p 5 phĂºt ná»¯a lĂ  Ä‘Æ°á»£c.@@@BÆ°á»›c 3 : MĂºc canh gĂ  náº¥u sáº£ vĂ o tĂ´, thĂªm tiĂªu xay vĂ  trang trĂ­ vá»›i hĂ nh tĂ¢y, ngĂ² rĂ­. DĂ¹ng nĂ³ng vá»›i nÆ°á»›c máº¯m vĂ  á»›t.', 'drawable/monan39', 'MCA', 'not');";
	private static final String INSERT_MONAN9 = "INSERT INTO monan VALUES ('MCA_CMTNTV_38', 'Canh mÄƒng tÆ°Æ¡i náº¥u tĂ´m viĂªn', 3, 'BÆ°á»›c 1 : MÄƒng và€ng rÆ°̀ƒa sà£ch cÄƒï¿½?t khoanh vÆ°̀€a Äƒn. TĂ´m boï¿½?c vò‰, chè‰ lÆ°ng bò‰ chì‰ Ä‘en, chà€ muĂ´ï¿½?i, rÆ°̀‰a sà£ch, Ä‘Ăª̀‰ thĂ¢̀£t raï¿½?o, xay nhuyĂª̀ƒn, quĂªï¿½?t dè‰o vÆ¡ï¿½?i Ä‘Ă¢̀€u hà€nh trÄƒï¿½?ng, 1 muá»—ng cĂ  phĂª hà£t nĂªm,1/3 cĂ  phĂª tiĂªu, viĂªn thà€nh viĂªn trò€n. Hà€nh laï¿½? tÆ°Æ¡ï¿½?c sÆ¡̀£i.@@@BÆ°á»›c 2 : ï¿½?un noï¿½?ng 1 muá»—ng dĂ¢̀€u Äƒn, cho hà€nh tiï¿½?m và€o phi thÆ¡m, cho mÄƒng và€o xà€o, nĂªm 1 muá»—ng cĂ  phĂª hà£t nĂªm vĂ o xà€o cho thĂ¢ï¿½?m, cho nÆ°Æ¡ï¿½?c dù€ng và€o Ä‘un sĂ´i. Sau Ä‘Ă³ cho tĂ´m viĂªn và€o nĂ¢ï¿½?u chiï¿½?n cho Ä‘áº¿n khi viĂªn tĂ´m nĂ´̀‰i lĂªn mÄƒ̀£t, mÄƒng chiï¿½?n, nĂªm là£i vÆ¡ï¿½?i 1 muá»—ng cĂ  phĂªÂ hà£t nĂªm, nĂªï¿½?m vÆ°̀€a Äƒn, tÄƒï¿½?t lá»­a.@@@BÆ°á»›c 3 : Muï¿½?c canh ra tĂ´, cho hà€nh tÆ°Æ¡ï¿½?c sÆ¡̀£i lĂªn, rÄƒï¿½?c tiĂªu. Dù€ng noï¿½?ng vÆ¡ï¿½?i cÆ¡m trÄƒï¿½?ng.@@@MĂ¡ch nhï¿½? : ï¿½?á»ƒ giáº£m bá»›t mĂ¹i hÄƒng vĂ  vá»‹ nháº«n cá»§a mÄƒng thĂ¬ nĂªn luá»™c mÄƒng qua nÆ°á»›c muá»‘i 2 - 3 láº§n.', 'drawable/monan38', 'MCA', 'http://www.youtube.com/watch?v=yFZxinXQp28');";
	private static final String INSERT_MONAN10 = "INSERT INTO monan VALUES ('MCA_CSNDN_24', 'Canh sÆ°ï¿½?n non Ä‘áº­u ngá»±', 3, 'BÆ°á»›c 1 : SÆ°ï¿½?n non rá»­a sáº¡ch, cháº·t miáº¿ng vá»«a Äƒn, cho vĂ o ná»“i nÆ°á»›c sĂ´i luá»™c sÆ¡ vĂ  rá»­a láº¡i báº±ng nÆ°á»›c láº¡nh cho sáº¡ch. ï¿½?áº­u ngá»± tÆ°Æ¡i rá»­a sáº¡ch. CĂ  rá»‘t gï¿½?t vï¿½?, xáº¯t miáº¿ng vá»«a Äƒn. HĂ nh lĂ¡, ngĂ² rĂ­ rá»­a sáº¡ch, cáº¯t nhuyá»…n.@@@BÆ°á»›c 2 : ï¿½?un sĂ´i nÆ°á»›c, cho sÆ°ï¿½?n non, hĂ nh tĂ­m Ä‘áº­p giáº­p, háº¡t nĂªm Knorr tá»« Thá»‹t thÄƒn vĂ  XÆ°Æ¡ng á»‘ng vĂ o, náº¥u 30 phĂºt cho mï¿½?m. Sau Ä‘Ă³, cho Ä‘áº­u ngá»± vĂ  cĂ  rá»‘t vĂ o, tiáº¿p tá»¥c náº¥u lá»­a nhï¿½? thĂªm khoáº£ng 30 phĂºt ná»¯a lĂ  Ä‘Æ°á»£c, nĂªm thĂªm nÆ°á»›c máº¯m cháº¥m Knorr cho vá»«a Äƒn.@@@BÆ°á»›c 3 : MĂºc canh vĂ o tĂ´, ráº¯c tiĂªu xay, hĂ nh lĂ¡, ngĂ² rĂ­ vĂ o, dĂ¹ng nĂ³ng trong bá»¯a cÆ¡m. CĂ³ thá»ƒ cháº¥m kĂ¨m vá»›i nÆ°á»›c máº¯m cháº¥m Knorr vĂ  á»›t.', 'drawable/monan24', 'MCA', 'http://www.youtube.com/watch?v=2Of1G7H6uzs');";
	private static final String INSERT_MONAN11 = "INSERT INTO monan VALUES ('MCA_KQH_7', 'Khá»• qua háº§m', 4, 'BÆ°á»›c 1 : Khá»• qua ráº¡ch bï¿½? ruá»™t, rá»­a sáº¡ch ngĂ¢m trong nÆ°á»›c muá»‘i pha loĂ£ng. Thá»‹t náº¡c, cĂ¡ thu (thĂ¡c lĂ¡c) bÄƒm nhuyá»…n. CĂ  rá»‘t gï¿½?t vï¿½?, rá»­a sáº¡ch, cáº¯t háº¡t lá»±u. BĂºn tĂ u, náº¥m mĂ¨o ngĂ¢m vĂ o nÆ°á»›c lĂ£ cho mï¿½?m sau Ä‘Ă³ thĂ¡i nhï¿½?. HĂ nh tĂ­m vĂ  tï¿½?i bÄƒm nhuyá»…n, hĂ nh lĂ¡ cáº¯t mï¿½?ng.@@@BÆ°á»›c 2 : Thá»‹t náº¡c, cĂ¡ trá»™n vá»›i cĂ  rá»‘t, náº¥m mĂ¨o, bĂºn tĂ u, trá»©ng gĂ , nÆ°á»›c máº¯m, tiĂªu, Ä‘Æ°ï¿½?ng, bá»™t ngï¿½?t, háº¡t nĂªm Knorr tá»« Thá»‹t thÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, muá»‘i (má»—i thá»© 1 muá»—ng cĂ  phĂª).@@@BÆ°á»›c 3 : Khá»• qua vá»›t ra Ä‘á»ƒ rĂ¡o nhá»“i nhĂ¢n vĂ o vá»«a pháº£i rá»“i xáº¿p vĂ o há»™p Ä‘á»ƒ vĂ o tá»§ láº¡nh. ï¿½?áº¿n bá»¯a Äƒn láº¥y ra 1 Ă­t Ä‘á»§ Äƒn, trá»¥ng sÆ¡ khá»• qua, náº¥u nÆ°á»›c dĂ¹ng vá»›i tĂ´m khĂ´ rá»“i bï¿½? khá»• qua vĂ o háº§m khoáº£ng 20 Ä‘áº¿n 30 phĂºt cho mï¿½?m. NĂªm náº¿m láº¡i cho vá»«a Äƒn rá»“i mĂºc ra tĂ´ trang trĂ­ vá»›i hĂ nh lĂ¡, ngĂ² lĂ  cĂ³ thá»ƒ dĂ¹ng Ä‘Æ°á»£c ngay. Vá»‹ Ä‘áº¯ng cá»§a khá»• qua hoĂ  quyá»‡n vá»›i thá»‹t vĂ  bĂºn tĂ u táº¡o cáº£m giĂ¡c dá»… chá»‹u, thanh thanh, khĂ´ng ngĂ¡n sau nhá»¯ng bá»¯a tiá»‡c ngĂ y táº¿t. ChĂºc cĂ¡c báº¡n cĂ³ nhá»¯ng ngĂ y táº¿t tháº­t áº¥m cĂºng vĂ  ngon miá»‡ng bĂªn gia Ä‘Ă¬nh.', 'drawable/monan7', 'MCA', 'http://www.youtube.com/watch?v=nKqqEgW2Ua4');";
	private static final String INSERT_MONAN12 = "INSERT INTO monan VALUES ('MCH_BRCM_5', 'Ba rï¿½?i chiĂªn mĂ¨', 2, 'BÆ°á»›c 1 : Thá»‹t ba rï¿½?i mua vï¿½? rá»­a sáº¡ch rá»“i Æ°á»›p thá»‹t ba rï¿½?i vá»›i háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn XÆ°Æ¡ng ï¿½?ng vĂ  Tá»§y vĂ  gia vá»‹ gá»“m tiĂªu xay, nÆ°á»›c máº¯m cháº¥m Knorr, nÆ°á»›c tÆ°Æ¡ng. ï¿½?á»ƒ khoáº£ng 30 phĂºt cho thá»‹t tháº¥m Ä‘ï¿½?u.@@@BÆ°á»›c 2 : Báº¯t cháº£o lĂªn rang vĂ ng mĂ¨ tráº¯ng.@@@BÆ°á»›c 3 :Â ï¿½?un dáº§u vá»«a nĂ³ng, cho thá»‹t ba rï¿½?i vĂ o, chiĂªn lá»­a nhï¿½? Ä‘áº¿n khi thá»‹t vá»«a chĂ­n vĂ ng Ä‘áº¹p lĂ  Ä‘Æ°á»£c.@@@BÆ°á»›c 4 : Cáº¯t mï¿½?ng thá»‹t ba rï¿½?i, ráº¯c mĂ¨ lĂªn trĂªn.@@@BÆ°á»›c 5 : Khi Äƒn, dï¿½?n kĂ¨m rau sá»‘ng, bĂ¡nh trĂ¡ng vĂ  nuá»›c máº¯m tï¿½?i á»›t.', 'drawable/monan5', 'MCH', 'not');";
	private static final String INSERT_MONAN13 = "INSERT INTO monan VALUES ('MCH_CGHS_6', 'Cháº£ giĂ² háº£i sáº£n', 2, 'BÆ°á»›c 1 : TĂ´m rá»­a sáº¡ch, bï¿½? Ä‘áº§u, bĂ³c vï¿½? pháº§n Ä‘uĂ´i. Má»±c rá»­a sáº¡ch, báº±m nhuyá»…n chung vá»›i tĂ´m hoáº·c xáº¯t sá»£i. Cua háº¥p chĂ­n láº¥y thá»‹t náº¡c. HĂ nh tĂ­m báº±m nhuyá»…n, cá»§ sáº¯n, náº¥m mĂ¨o, cĂ  rá»‘t xáº¯t sá»£i, táº¥t cáº£ trá»™n Ä‘ï¿½?u sau Ä‘Ă³ trá»™n chung vá»›i tĂ´m, cua, má»±c rá»“i cho má»™t chĂºt muá»‘i, Ä‘Æ°ï¿½?ng, háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y cho vá»«a Äƒn. Trá»™n há»—n há»£p nhĂ¢n cho Ä‘ï¿½?u tay Ä‘á»ƒ Ä‘Æ°á»£c má»™t há»—n há»£p nhĂ¢n dáº»o.@@@BÆ°á»›c 2 : Tráº£i bĂ¡nh trĂ¡ng ra mĂ¢m, thoa lĂªn má»™t Ă­t nÆ°á»›c cho bĂ¡nh dáº»o, cuá»‘n khĂ´ng bá»‹ rĂ¡ch. MĂºc nhĂ¢n háº£i sáº£n vá»«a lĂ m Ä‘á»ƒ lĂªn bĂ¡nh trĂ¡ng, gáº¥p 2 mĂ©p rá»“i cuá»‘n láº¡i.@@@BÆ°á»›c 3 : Báº¯c cháº£o dáº§u lĂªn báº¿p. Dáº§u sĂ´i, tháº£ tá»« tá»« tá»«ng chiáº¿c cháº£ giĂ² vĂ o. Khi cháº£ giĂ² chĂ­n vĂ ng, vá»›t ra khay, Ä‘á»ƒ rĂ¡o dáº§u.@@@BÆ°á»›c 4 : Cho rau xĂ  lĂ¡ch lĂªn dÄ©a, rá»“i cho cháº£ giĂ² lĂªn, Äƒn kĂ¨m vá»›i nÆ°á»›c máº¯m pha hoáº·c tÆ°Æ¡ng xĂ­ muá»™i.', 'drawable/monan6', 'MCH', 'not');";
	private static final String INSERT_MONAN14 = "INSERT INTO monan VALUES ('MCH_CMTL_31', 'Cháº£ má»±c thĂ¬ lĂ ', 2, 'BÆ°á»›c 1 : Cho má»±c, thĂ¬ lĂ , hĂ nh tĂ­m, tï¿½?i, háº¡t nĂªm Knorr vĂ  tiĂªu xay vĂ o mĂ¡y xay, xay nhuyá»…n.@@@BÆ°á»›c 2 : Quáº¿t Ä‘ï¿½?u bĂ¡nh trĂ¡ng vá»›i nÆ°á»›c cho mï¿½?m vĂ  cho nhĂ¢n má»±c vĂ o gï¿½?i cháº·t tay. Cáº¯t Ä‘Ă´i sau Ä‘Ă³ nhĂºn vĂ o bá»™t báº¯p.@@@BÆ°á»›c 3 : ï¿½?un dáº§u Äƒn vá»«a nĂ³ng, cho cháº£ má»±c vĂ o chiĂªn vĂ ng.@@@BÆ°á»›c 4 : Vá»›t cháº£ má»±c ra khï¿½?i cháº£o, Ä‘á»ƒ rĂ¡o dáº§u. DĂ¹ng nĂ³ng vá»›i xĂ  lĂ¡ch, rau sá»‘ng vĂ  nÆ°á»›c máº¯m chua ngï¿½?t.@@@Máº¹o váº·t : Má»±c nang dĂ y thá»‹t, máº­p trĂ²n, da cá»©ng, thĂ­ch há»£p lĂ m cĂ¡c mĂ³n nhÆ° phi lĂª Äƒn sá»‘ng, trá»™n gï¿½?i, náº¥u láº©u... Má»±c á»‘ng trĂ²n, dĂ i vĂ  da mï¿½?ng, thÆ°ï¿½?ng dĂ¹ng lĂ m khĂ´ má»±c vĂ  nhá»“i thá»‹t lĂ  ngon nháº¥t. Trong khi má»±c lĂ¡ á»Ÿ giá»¯a hai loáº¡i nĂ y, ngáº¯n hÆ¡n má»±c á»‘ng vĂ  dĂ i hÆ¡n má»±c nang, lĂ m cĂ¡c mĂ³n má»±c 1 náº¯ng, nÆ°á»›ng, xĂ o, háº¥p chiĂªn nhÆ° mĂ³n cháº£ má»±c thĂ¬ lĂ  nĂ y... lĂ  ráº¥t thĂ­ch há»£p.', 'drawable/monan31', 'MCH', 'not');";
	private static final String INSERT_MONAN15 = "INSERT INTO monan VALUES ('MCH_CTDT_36', 'CĂ  tĂ­m dá»“n thá»‹t', 4, 'BÆ°á»›c 1 : Æ¯á»›p thá»‹t 15 phĂºt vá»›i háº¡t nĂªm Knorr, tiĂªu xay vĂ  hĂ nh tĂ­m. CĂ  tĂ­m chiĂªn sÆ¡ Ä‘á»ƒ bĂ³c vï¿½?.@@@BÆ°á»›c 2 : DĂ¹ng tay nháº¥n dáº¹p cĂ  tĂ­m, sau Ä‘Ă³ ráº¯c bá»™t vĂ  phá»§ Ä‘ï¿½?u vá»›i thá»‹t heo.@@@BÆ°á»›c 3 : ChiĂªn chĂ­n cĂ  tĂ­m dá»“n thá»‹t trong dáº§u nĂ³ng. LĂ m nÆ°á»›c máº¯m : khuáº¥y tan Ä‘ï¿½?u táº¥t cáº£ nguyĂªn liá»‡u vá»›i nhau.@@@BÆ°á»›c 4 : ï¿½?á»ƒ cĂ  tĂ­m dá»“n thá»‹t vĂ o dÄ©a, trang trĂ­ vá»›i á»›t, hĂ nh vĂ  ngĂ² rĂ­. Ä‚n kĂ¨m nÆ°á»›c máº¯m tï¿½?i á»›t.', 'drawable/monan36', 'MCH', 'not');";
	private static final String INSERT_MONAN16 = "INSERT INTO monan VALUES ('MCH_DHDTXC_4', 'ï¿½?áº­u há»§ dá»“n thá»‹t xá»‘t cĂ ', 2, 'BÆ°á»›c 1 : ï¿½?áº­u hÅ© chiĂªn sÆ¡Â cáº¯t lĂ m Ä‘Ă´i, láº¥y muá»—ng mĂºcÂ pháº§nÂ ruá»™tÂ Ä‘áº­uÂ bĂªn trong (khĂ©o lĂ©o Ä‘á»«ng Ä‘á»ƒ vï¿½? Ä‘áº­u hÅ© bá»‹ rĂ¡ch). TĂ¡n nhuyá»…n lĂµi cá»§a Ä‘áº­u hÅ©, trá»™n chung vá»›i thá»‹t ba chá»‰ xay, Æ°á»›p thĂªm má»™t Ă­t háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y, thĂªm Ă­t tiĂªu, hĂ nh lĂ¡. Dá»“n nhĂ¢n vĂ o miáº¿ng Ä‘áº­u hÅ© Ä‘Ă£ láº¥y lĂµi.@@@BÆ°á»›c 2 : Cho dáº§u Äƒn vĂ o cháº£o vĂ  chiĂªn sÆ¡ miáº¿ng Ä‘áº­u hÅ© Ä‘Ă£ dá»“n thá»‹t. Cho dáº§u vĂ o cháº£o, phi tï¿½?i cho thÆ¡m, cho cĂ  chua xáº¯t mĂºi cau vĂ o xĂ o cho mï¿½?m. Sau Ä‘Ă³ cho Ä‘áº­u hÅ© vĂ o. NĂªm thĂªm háº¡t nĂªm Knorr, má»™t chĂºt Ä‘Æ°ï¿½?ng, nÆ°á»›c náº¯m cháº¥m Knorr cho vá»«a Äƒn. Cho thĂªm má»™t Ă­t nÆ°á»›c sĂ´i vĂ o, Ä‘un nhï¿½? lá»­a cho Ä‘áº¿n khi nÆ°á»›c sá»‘t cĂ  chua sï¿½?n sá»‡t thĂ¬ cho hĂ nh lĂ¡ vĂ o. Táº¯t báº¿p.', 'drawable/monan4', 'MCH', 'not');";
	private static final String INSERT_MONAN17 = "INSERT INTO monan VALUES ('MCH_GCLC_32', 'GĂ  cuá»™n lĂ¡ chanh', 4, 'BÆ°á»›c 1 : á»¨c gĂ  phi lĂª, láº¡ng mï¿½?ng. Æ¯á»›p á»©c gĂ  vá»›i háº¡t nĂªm tá»« thá»‹t thÄƒn vĂ  xÆ°Æ¡ng á»‘ng vĂ  tiĂªu xay cho vá»«a Äƒn. Cho lĂ¡ chanh, hĂ nh tĂ­m vĂ o á»©c gĂ , cuá»‘n cháº·t tay. DĂ¹ng miáº¿ng mĂ ng nhá»±a thá»±c pháº©m, gĂ³i cháº·t.@@@BÆ°á»›c 2 : Cho vĂ o nÆ°á»›c luá»™c 15 phĂºt cho chĂ­n. Vá»›t ra khï¿½?i ná»“i, gá»¡ bï¿½? mĂ ng nhá»±a thá»±c pháº©m.@@@BÆ°á»›c 3 : Cho vĂ o dáº§u chiĂªn chĂ­n. Cáº¯t miáº¿ng mï¿½?ng vĂ  Äƒn kĂ¨m vá»›i rau sá»‘ng vĂ  muá»‘i tiĂªu chanh.', 'drawable/monan32', 'MCH', 'not');";
	private static final String INSERT_MONAN18 = "INSERT INTO monan VALUES ('MCH_OHCG_26', 'Ă“c heo chiĂªn giĂ²n', 4, 'BÆ°á»›c 1 : Gá»«ng, gï¿½?t vï¿½?, cáº¯t miáº¿ng mï¿½?ng. HĂ nh lĂ¡ cáº¯t khĂºc. Ă“c heo láº¥y gĂ¢n mĂ¡u, rá»­a sáº¡ch, háº¥p hoáº·c luá»™c chĂ­n Ă³c heo vá»›i hĂ nh, gá»«ng, Ă­t muá»‘i. Khuáº¥y Ä‘ï¿½?u bá»™t chiĂªn giĂ²n, trá»©ng gĂ , nÆ°á»›c lï¿½?c, háº¡t nĂªm Knorr tá»« Thá»‹t thÄƒn vĂ  XÆ°Æ¡ng á»‘ng, tiĂªu xay cho cĂ³ Ä‘á»™ sá»‡t vá»«a pháº£i.@@@BÆ°á»›c 2 : NhĂºng Ă³c heo vĂ o bá»™t chiĂªn giĂ²n, cho vĂ o cháº£o chiĂªn chĂ­n, vá»›t ra Ä‘á»ƒ rĂ¡o dáº§u.@@@BÆ°á»›c 3 : Dï¿½?n ra Ä‘Ä©a, dĂ¹ng nĂ³ng vá»›i xá»‘t xĂ­ muá»™i vĂ  xĂ  lĂ¡ch, cĂ  chua.@@@Máº¹o váº·t : Ă“c heo sau khi mua vï¿½? tháº£ vĂ o nÆ°á»›c muá»‘i pha loĂ£ng ngĂ¢m vĂ i phĂºt lĂ  cĂ³ thá»ƒ bĂ³c Ä‘Æ°á»£c lá»›p mĂ ng vĂ  gĂ¢n mĂ¡u bĂªn ngoĂ i.@@@CĂ¡ch bĂ³c mĂ ng gĂ¢n mĂ¡u : Bá»™ Ă³c heo Ä‘áº·t trong lĂ²ng bĂ n tay, tay cĂ²n láº¡i dĂ¹ng má»™t chiáº¿c tÄƒm Ä‘áº§u nhï¿½?n, lĂ¡ch Ä‘áº§u tÄƒm vĂ o mĂ ng mĂ¡u, cuá»‘n theo má»™t chiï¿½?u Ä‘á»ƒ bĂ³c ra. Cá»© lĂ m tháº¿ cho Ä‘áº¿n háº¿t. Khi náº¥u cĂ³ thá»ƒ káº¿t há»£p vá»›i nhá»¯ng gia vá»‹ máº¡nh nhÆ° cá»§ gá»«ng, lĂ¡ gá»«ng, rau rÄƒm, hĂ nh Ä‘á»ƒ khá»­ háº§u háº¿t mĂ¹i tanh cá»§a Ă³c heo.', 'drawable/monan26', 'MCH', 'http://www.youtube.com/watch?v=oPDCS_b-Gyg');";
	private static final String INSERT_MONAN19 = "INSERT INTO monan VALUES ('MHA_MODTHXM_28', 'Má»±c á»‘ng dá»“n thá»‹t háº¥p xá»‘p me', 4, 'BÆ°á»›c 1 : Cho thá»‹t xay, hĂ nh tĂ­m bÄƒm, á»›t bÄƒm vĂ  khĂ´ng thá»ƒ thiáº¿u 2 muá»—ng háº¡t nĂªm Knorr tá»« thá»‹t thÄƒn vĂ  xÆ°Æ¡ng á»‘ng cho nhĂ¢n thĂªm thÆ¡m vĂ  Ä‘áº­m Ä‘Ă  vĂ o tĂ´ lá»›n. Trá»™n Ä‘ï¿½?u vĂ  chï¿½? trong 5 phĂºt cho thá»‹t tháº¥m gia vá»‹.@@@BÆ°á»›c 2 : Cho thá»‹t vĂ o dá»“n tá»« tá»« cho cháº¯c nhĂ¢n. Sau Ä‘Ă³ mang Ä‘i háº¥p cĂ¡ch thá»§y trong vĂ²ng khoáº£ng 20 phĂºt.@@@BÆ°á»›c 3 : Cho má»™t chĂºt dáº§u vĂ o cháº£o, dáº§u nĂ³ng phi tï¿½?i cho thÆ¡m. Sau Ä‘Ă³, cho nÆ°á»›c me vĂ o ná»“i Ä‘un sĂ´i, nĂªm náº¿m vá»«a Äƒn vá»›i chĂºt nÆ°á»›c máº¯m ngon vĂ  háº¡t nĂªm tá»« thá»‹t thÄƒn vĂ  xÆ°Æ¡ng á»‘ng.@@@BÆ°á»›c 4 : Cáº§n ï¿½?Ă  Láº¡t Ä‘Ă£ xáº¿p sáºµn bĂ y trang trĂ­ trĂªn dÄ©a. Xáº¿p khoanh má»±c lĂªn trĂªn cho Ä‘áº¹p. RÆ°á»›i nÆ°á»›c xá»‘t . MĂ³n nĂ y dĂ¹ng nĂ³ng vá»›i cÆ¡m.@@@Máº¹o váº·t : NĂªn chï¿½?n con má»±c á»‘ng thá»‹t mĂ u hÆ¡i há»“ng, Ä‘áº§u váº«n dĂ­nh cháº·t vĂ o thĂ¢n, tĂºi má»±c chÆ°a bá»ƒ. Má»±c khĂ´ng tÆ°Æ¡i cháº¯c cháº¯n cĂ³ mĂ¹i hĂ´i, thá»‹t mĂ u xanh ngĂ  ngĂ , thá»‹t bá»‹ mï¿½?m nhÅ©n vĂ  Ä‘áº§u khĂ´ng dĂ­nh cháº·t vĂ o thĂ¢n. DĂ¹ng tÄƒm xiĂªn miá»‡ng á»‘ng má»±c Ä‘á»ƒ trĂ¡nh thá»‹t trá»“i ra ngĂ²ai.', 'drawable/monan28', 'MHA','http://www.youtube.com/watch?v=-nXzqTcP2bI');";
	private static final String INSERT_MONAN20 = "INSERT INTO monan VALUES ('MHA_NHNRB_2', 'Náº¡c heo háº¥p rong biá»ƒn', 2, 'BÆ°á»›c 1 : Thá»‹t heo rá»­a sáº¡ch, xáº¯t sá»£i. LĂ¡ rong biá»ƒn ngĂ¢m nÆ°á»›c, rá»­a sáº¡ch, cáº¯t sá»£i. Náº¥m cáº¯t gá»‘c, ngĂ¢m muá»‘i, rá»­a sáº¡ch. CĂ  rá»‘t gï¿½?t vï¿½?, rá»­a sáº¡ch, thĂ¡i chá»‰ mï¿½?ng.@@@BÆ°á»›c 2 : Cho thá»‹t heo, rong biá»ƒn, náº¥m vĂ  cĂ  rá»‘t vĂ o xá»­ng háº¥p, Ä‘em háº¥p chĂ­n. TrĂºt ra Ä‘Ä©a, cho nÆ°á»›c máº¯m vĂ o, cho thĂªm dáº§u Äƒn dinh dÆ°á»¡ng Kiddy vĂ o trá»™n Ä‘ï¿½?u. Cho bĂ© dĂ¹ng nĂ³ng vá»›i cÆ¡m.', 'drawable/monan2', 'MHA', 'not');";
	private static final String INSERT_MONAN21 = "INSERT INTO monan VALUES ('MMA_BKRM_14', 'BĂ² kho rá»«ng mï¿½?m', 2, 'BÆ°á»›c 1 : Thá»‹t bĂ² rá»­a sáº¡ch, thĂ¡i quĂ¢n cï¿½?, Æ°á»›p vá»›i 1 thĂ¬a cĂ  phĂª muá»‘i trong khoáº£ng 1 tiáº¿ng cho ngáº¥m. Náº¿u khĂ´ng cĂ³ thï¿½?i gian báº¡n cĂ³ thá»ƒ Æ°á»›p khoáº£ng 30 phĂºt cÅ©ng Ä‘Æ°á»£c. Gá»«ng cáº¡o vï¿½?, thĂ¡i sá»£i. HĂ nh tĂ­m thĂ¡i mï¿½?ng.@@@BÆ°á»›c 2 : LĂ m nĂ³ng 2 thĂ¬a cĂ  phĂª dáº§u Äƒn trong ná»“i, phi thÆ¡m hĂ nh tĂ­m rá»“i Ä‘á»• tiáº¿p gá»«ng vĂ o xĂ o Ä‘ï¿½?u cho gá»«ng mï¿½?m vĂ  thÆ¡m.@@@BÆ°á»›c 3 : Nhanh tay Ä‘á»• thá»‹t bĂ² vĂ o xĂ o vá»›i gá»«ng cho thá»‹t Ä‘Æ°á»£c tháº¥m.@@@BÆ°á»›c 4 : Thá»‹t bĂ² chĂ­n tĂ¡i, báº¡n chĂ¢m thĂªm nÆ°á»›c láº¡nh ngáº­p máº·t thá»‹t bĂ², náº¥u sĂ´i rá»“i nĂªm thĂªm vá»›i bá»™t nĂªm, nÆ°á»›c máº¯m. Náº¿u thĂ­ch thá»‹t bĂ² cĂ³ mĂ u Ä‘áº­m Ä‘áº¹p, báº¡n thĂªm vĂ o 1 thĂ¬a cĂ  phĂª nÆ°á»›c Ä‘Æ°ï¿½?ng mĂ u cho Ä‘áº¹p, náº¿u khĂ´ng thĂ­ch cĂ³ thá»ƒ bï¿½? qua.@@@BÆ°á»›c 5 : ï¿½?un hÆ¡n 1 giï¿½? Ä‘á»“ng há»“, gá»«ng dáº»o quoĂ¡nh láº¡i, thá»­ miáº¿ng thá»‹t mï¿½?m lĂ  Ä‘Æ°á»£c.@@@BÆ°á»›c 6 : MĂºc ra dÄ©a ráº¯c háº¡t tiĂªu lĂªn bï¿½? máº·t thá»‹t. Náº¿u khĂ´ng cĂ³ thï¿½?i gian Ä‘un lĂ¢u, báº¡n cĂ³ thá»ƒ háº§m thá»‹t bĂ² báº±ng ná»“i Ă¡p suáº¥t, vá»«a nhanh láº¡i tiá»‡n lá»£i.', 'drawable/monan14', 'MMA', 'not');";
	private static final String INSERT_MONAN22 = "INSERT INTO monan VALUES ('MMA_CKDH_40', 'CĂ¡ kho dÆ°a hÆ°ï¿½?ng', 4, 'BÆ°á»›c 1 : Æ¯á»›p cĂ¡ 15 phĂºt vá»›i gia vá»‹ vĂ  hĂ nh, tï¿½?i, á»›t.@@@BÆ°á»›c 2 : ï¿½?un nĂ³ng dáº§u Äƒn cho cĂ¡ rĂ´ vĂ o ná»“i xĂ o sÆ¡. ThĂªm nÆ°á»›c dá»«a vĂ o ná»“i, Ä‘un sĂ´i lĂªn vĂ  kho khoáº£ng 15 phĂºt.@@@BÆ°á»›c 3 : Cho tiáº¿p dÆ°a hÆ°ï¿½?ng vĂ o ná»“i, kho thĂªm 10 phĂºt ná»¯a cho táº¥t cáº£ nguyĂªn liá»‡u chĂ­n Ä‘ï¿½?u vĂ  tháº¥m gia vá»‹ lĂ  Ä‘Æ°á»£c.@@@BÆ°á»›c 4 : MĂºc cĂ¡ kho dÆ°a hÆ°ï¿½?ng vĂ o tĂ´, ráº¯c tiĂªu xay vĂ  dĂ¹ng nĂ³ng vá»›i cÆ¡m.@@@Máº¹o váº·t : Chï¿½?n cĂ¡ rĂ´ mĂ¬nh trĂ²n, bá»¥ng cÄƒng lĂ  cĂ¡ rĂ´ cĂ¡i Ä‘ang cĂ³ trá»©ng Ä‘á»ƒ kho ngon. Cho cĂ¡ vĂ o ná»“i cĂ¹ng vá»›i 1 Ă­t muá»‘i há»™t, Ä‘áº­y náº¯p láº¡i xá»‘c nhiï¿½?u láº§n cho cĂ¡ cháº¿t vĂ  sáº¡ch nhá»›t trÆ°á»›c khi lĂ m cĂ¡.', 'drawable/monan40', 'MMA', 'not');";
	private static final String INSERT_MONAN23 = "INSERT INTO monan VALUES ('MMA_DHTKS_25', 'ï¿½?áº­u há»§ tráº¯ng kho sÆ°ï¿½?n', 4, 'BÆ°á»›c 1 : SÆ°ï¿½?n non rá»­a sáº¡ch, cháº·t khĂºc tá»« 3-4cm, Æ°á»›p vá»›i muá»‘i, háº¡t nĂªm Knorr tá»« Thá»‹t thÄƒn vĂ  XÆ°Æ¡ng á»‘ng, nÆ°á»›c máº¯m cháº¥m Knorr, Ä‘Æ°ï¿½?ng, tiĂªu, Ä‘á»ƒ khoáº£ng 10 phĂºt cho tháº¥m gia vá»‹. ï¿½?áº­u phá»¥ tráº¯ng rá»­a sáº¡ch, xáº¯t khá»‘i vá»«a Äƒn.@@@BÆ°á»›c 2 : Phi thÆ¡m hĂ nh tĂ­m trong thá»‘, cho sÆ°ï¿½?n vĂ o xĂ o cho sÄƒn, cháº¿ vĂ o 2 chĂ©n nÆ°á»›c Ä‘un sĂ´i, náº¥u 10 phĂºt, sau Ä‘Ă³ cho Ä‘áº­u phá»¥ vĂ o náº¥u trĂªn lá»­a vá»«a, náº¥u khoáº£ng 30 phĂºt cho Ä‘áº­u phá»¥ tháº¥m gia vá»‹ vĂ  sÆ°ï¿½?n ra vá»‹ ngï¿½?t. NĂªm láº¡i cho vá»«a Äƒn vá»›i háº¡t nĂªm Knorr tá»« Thá»‹t thÄƒn vĂ  XÆ°Æ¡ng á»‘ng.@@@BÆ°á»›c 3 : Tiáº¿p tá»¥c Ä‘un 15 phĂºt, Ä‘áº­u hÅ© sáº½ tháº¥m ngon, sÆ°ï¿½?n mï¿½?m thĂ¬ ráº¯c hĂ nh lĂ¡ cáº¯t nhï¿½? vĂ o, táº¯t báº¿p.@@@BÆ°á»›c 4 : MĂºc ra Ä‘Ä©a, dĂ¹ng kĂ¨m vá»›i cÆ¡m nĂ³ng.', 'drawable/monan25', 'MMA', 'http://www.youtube.com/watch?v=nasqNxDZxnY');";
	private static final String INSERT_MONAN24 = "INSERT INTO monan VALUES ('MMA_GOXD_1', 'GĂ  om xĂ¬ dáº§u', 4, 'BÆ°á»›c 1 : Cho dáº§u Äƒn vĂ  Ä‘Æ°ï¿½?ng vĂ o cháº£o nĂ³ng, Ä‘áº£o Ä‘ï¿½?u. Khi há»—n há»£p dáº§u Äƒn vĂ  Ä‘Æ°ï¿½?ng chuyá»ƒn thĂ nh ca-ra-men mĂ u cĂ¡nh giĂ¡n thĂ¬ báº¡nÂ cho gĂ  vĂ o, Ä‘áº£o Ä‘ï¿½?u cho gĂ  ngáº¥m Ä‘ï¿½?u sá»‘t ca-ra-men.@@@BÆ°á»›c 2 : Riï¿½?ng rá»­a sáº¡ch, Ä‘áº­p dáº­p.@@@BÆ°á»›c 3 : Khi sá»‘t bĂ¡m Ä‘ï¿½?u lĂªn hai máº·t miáº¿ng thá»‹t gĂ ,Â báº¡n thĂªmÂ ná»­a cá»‘c nÆ°á»›c vĂ o cháº£o. Rá»“i tiáº¿p tá»¥c vá»›iÂ xĂ¬ dáº§u,Â  sauÂ Ä‘Ă³Â lĂ  riï¿½?ng, cuá»‘i cĂ¹ng lĂ  cho ngÅ© vá»‹ hÆ°Æ¡ng vĂ o.@@@BÆ°á»›c 4 : ï¿½?un sĂ´i gĂ  vá»›i há»—n há»£p gia vá»‹ nĂ y, sau Ä‘Ă³ Ä‘áº­p náº¯p ná»“i vĂ  háº¡ nhï¿½? lá»­a Ä‘un liu riu chá»«ng 30 phĂºt. Khi nÆ°á»›c sá»‘t Ä‘Ă£ gáº§n cáº¡n, báº¡n táº¯t báº¿p vĂ  Ä‘á»ƒ gĂ  Æ°á»›p trong sá»‘t thĂªm chá»«ng 10 phĂºt ná»¯a.@@@BÆ°á»›c 5 : Láº¥y gĂ  ra Ä‘Ä©a Ä‘á»ƒ vĂ i phĂºt cho bá»›t nĂ³ng. NÆ°á»›c sá»‘t cĂ²n láº¡i báº¡n nĂªm náº¿m láº¡i, náº¿u máº·n thĂ¬ thĂªm chĂºt nÆ°á»›c sĂ´i cho vá»«a miá»‡ng. Cho thĂªm chĂºt dáº§u vá»«ng vĂ o nÆ°á»›c sá»‘t. ThĂ¡i lĂ¡t gĂ , bĂ y cĂ¹ngÂ nÆ°á»›c sá»‘t,Â giĂ¡ Ä‘á»— cháº§n vĂ  hĂ nh lĂ¡ náº¿u thĂ­ch. Vá»‹ bĂ©o mï¿½?m cá»§a thá»‹t gĂ  káº¿t há»£p vá»›i vá»‹ ngï¿½?t giĂ²n cá»§a giĂ¡Â Ä‘á»—Â ráº¥t thĂ­ch há»£p. Tuy nhiĂªn tĂ¹y theo sá»Ÿ thĂ­ch báº¡n cĂ³ thá»ƒ káº¿t há»£p mĂ³n gĂ  sá»‘t xĂ¬ dáº§u nĂ y vá»›i má»™t loáº¡i rau khĂ¡c. MĂ³n gĂ  om xĂ¬ dáº§u nĂ y mĂ¬nh hï¿½?c Ä‘Æ°á»£c tá»« má»™t quyá»ƒn sĂ¡ch hÆ°á»›ng dáº«n cĂ¡c mĂ³n Äƒn Singapore, nÆ¡i há»™i tá»¥ cá»§a cĂ¡c nï¿½?n áº©m thá»±c lá»›n cá»§a chĂ¢u ï¿½? nhÆ° Trung Quá»‘c, Malaysia, ThĂ¡i Lan vĂ  áº¤n ï¿½?á»™. MĂ³n Äƒn nĂ y cĂ³ Ä‘áº·c Ä‘iá»ƒm lĂ  ráº¥t thÆ¡m ngon vĂ  nháº¹ nhĂ ng. Miáº¿ng thá»‹t gĂ  bĂ©o mï¿½?m, hÆ¡i ngï¿½?t mĂ  láº¡i Ä‘áº­m Ä‘Ă , thÆ¡m thoang thoáº£ng mĂ¹i ngÅ© vá»‹ hÆ°Æ¡ng vĂ  riï¿½?ng, tháº­t háº¥p dáº«n. Náº¿u báº¡n Ä‘Ă£ tá»«ng thá»­ mĂ³n gĂ  om xĂ¬ dáº§u thĂ¬ sáº½ ráº¥t dá»… nghiï¿½?n mĂ³n Äƒn Ä‘áº·c biá»‡t nĂ y Ä‘áº¥y!', 'drawable/monan1', 'MMA', 'not');";
	private static final String INSERT_MONAN25 = "INSERT INTO monan VALUES ('MMA_THKT_21', 'Thá»‹t heo kho tá»™', 2, 'BÆ°á»›c 1 : ï¿½?un nĂ³ng ná»“i Ä‘áº¥t, Ä‘á»• dáº§u vĂ o ná»“i, chï¿½? cho Ä‘áº¿n khi dáº§u nĂ³ng nĂ³ng.@@@BÆ°á»›c 2 : ThĂªm tï¿½?i, á»›t Ä‘Ă£ xáº¯t nhuyá»…n vĂ o ná»“i, khuáº¥y Ä‘ï¿½?u cho Ä‘áº¿n khi tï¿½?i trá»Ÿ thĂ nh mĂ u nĂ¢u.@@@BÆ°á»›c 3 : Sau Ä‘Ă³, cho thá»‹t heo, nÆ°á»›c Ä‘Æ°ï¿½?ng tháº¯ng (nÆ°á»›c mĂ u) vĂ o ná»“i vĂ  khuáº¥y Ä‘ï¿½?u Ä‘á»ƒ nÆ°á»›c Ä‘Æ°ï¿½?ng tháº¯ng ngáº¥m Ä‘ï¿½?u vĂ´ thá»‹t heo.@@@BÆ°á»›c 4 : ï¿½?á»• nÆ°á»›c máº¯m, Ä‘Æ°ï¿½?ng, háº¡t tiĂªu vĂ o ná»“i thá»‹t, sau Ä‘Ă³ Ä‘á»• nÆ°á»›c vĂ o vĂ  trá»™n Ä‘ï¿½?u. ï¿½?á»ƒ mĂ³n Äƒn sĂ´i trĂªn lá»­a nhï¿½? khoáº£ng 5 phĂºt cho Ä‘áº¿n khi táº¥t cáº£ gia vá»‹ tháº¥m Ä‘ï¿½?u vĂ o thá»‹t.@@@BÆ°á»›c 5 : BĂ y ra Ä‘Ä©a, trang trĂ­ vá»›i hĂ nh tĂ¢y.', 'drawable/monan21', 'MMA', 'not');";
	private static final String INSERT_MONAN26 = "INSERT INTO monan VALUES ('MMA_TKO_19', 'TĂ´m kho á»›t', 3, 'BÆ°á»›c 1 : Æ¯á»›p tĂ´m sĂº vá»›i háº¡t nĂªm, Ä‘Æ°ï¿½?ng, nÆ°á»›c máº¯m vĂ  nÆ°á»›c tÆ°Æ¡ng.@@@BÆ°á»›c 2 : XĂ o hĂ nh tĂ­m vá»›i dáº§u Äƒn cho thÆ¡m.@@@BÆ°á»›c 3 : Cho tĂ´m, á»›t vĂ o xĂ o cho tĂ´m sÄƒn láº¡i. ThĂªm nÆ°á»›c dá»«a, tÆ°Æ¡ng á»›tÂ vĂ o kho lá»­a nhï¿½? Ä‘áº¿n khi nÆ°á»›c xá»‘t sá»‡t láº¡i lĂ  Ä‘Æ°á»£c.@@@BÆ°á»›c 4 : MĂºc tĂ´m kho á»›t vĂ o dÄ©a, ráº¯c tiĂªu xay, trang trĂ­ vá»›i hĂ nh lĂ¡,ngĂ² rĂ­. DĂ¹ng nĂ³ng.', 'drawable/monan19', 'MMA', 'not');";
	private static final String INSERT_MONAN27 = "INSERT INTO monan VALUES ('MNU_BCCN_12', 'BĂ² cuá»‘n cáº£i nÆ°á»›ng', 1, 'BÆ°á»›c 1 : BĂ² thĂ¡i miáº¿ng mï¿½?ng, to báº£n ngang thá»› thá»‹t. Náº¿u khĂ´ng thĂ¡i Ä‘Æ°á»£c mï¿½?ng cĂ¡c báº¡n cĂ³ thá»ƒ thĂ¡i dĂ y má»™t chĂºt rá»“i dĂ¹ng bĂºa dáº§n mï¿½?ng thá»‹t. ThĂ¡i thá»‹t thĂ nh miáº¿ng dĂ i khoáº£ng 3cm x 7cm. Gá»«ng Ä‘áº­p dáº­p, váº¯t láº¥y nÆ°á»›c cá»‘t.@@@BÆ°á»›c 2 : Æ¯á»›p thá»‹t bĂ² vá»›i nÆ°á»›c gá»«ng, gia vá»‹ vĂ  1 thĂ¬a dáº§u Äƒn trong vĂ²ng 30 phĂºt cho ngáº¥m. Cáº£i mĂ¨o rá»­a sáº¡ch, váº©y rĂ¡o nÆ°á»›c cáº¯t khĂºc Ä‘ï¿½?u nhau dĂ i khoáº£ng 5cm.@@@BÆ°á»›c 3 : Cuá»™n cáº£i mĂ¨o thĂ nh tá»«ng bĂ³ nhï¿½? to cá»¡ ngĂ³n tay cĂ¡i, dĂ¹ng thá»‹t bĂ² cuá»‘n xung quanh cáº£i mĂ¨o. XiĂªn cáº£i Ä‘Ă£ Ä‘Æ°á»£c cuá»‘n thá»‹t vĂ o que xiĂªn, á»Ÿ cĂ´ng Ä‘oáº¡n nĂ y náº¿u miáº¿ng thá»‹t bá»‹ lï¿½?ng báº¡n cĂ³ thá»ƒ Ä‘á»ƒ 2 miáº¿ng Ä‘Ă£ Ä‘Æ°á»£c cuá»‘n sĂ¡t vĂ o nhau Ä‘á»ƒ Ä‘áº£m báº£o khi chĂ­n thá»‹t cÅ©ng khĂ´ng bá»‹ rï¿½?i ra. TrÆ°ï¿½?ng há»£p nĂ y chá»‰ dĂ¹ng Ä‘á»ƒ Ä‘ï¿½? phĂ²ng nhá»¯ng miáº¿ng thá»‹t vá»¥n, cĂ²n báº£n thĂ¢n miáº¿ng thá»‹t khi sá»‘ng Ä‘Ă£ tá»± dĂ­nh vĂ o nhau mĂ  báº¡n khĂ´ng cáº§n pháº£i ghim giá»¯.@@@BÆ°á»›c 4 : LĂ m nĂ³ng lĂ² á»Ÿ nhiá»‡t Ä‘á»™ 200ÂºC trong vĂ²ng 10 phĂºt. DĂ¹ng dáº§u Äƒn pháº¿t qua cĂ¡c xiĂªn cáº£i cuá»‘n thá»‹t. Cho thá»‹t vĂ o lĂ² nÆ°á»›ng trong vĂ²ng 10 â€“ 15 phĂºt tĂ¹y nhiá»‡t Ä‘á»™ tá»«ng lĂ² Ä‘áº¿n khi thá»‹t bĂ² chĂ­n.@@@BÆ°á»›c 5 : Khi thá»‹t chĂ­n báº¡n láº¥y ra Ä‘Ä©a, dĂ¹ng nĂ³ng cháº¥m vá»›i muá»‘i chanh á»›t lĂ  ngon nháº¥t.', 'drawable/monan12', 'MNU', 'not');";
	private static final String INSERT_MONAN28 = "INSERT INTO monan VALUES ('MNU_SNT_8', 'SÆ°ï¿½?n nÆ°á»›ng tĂ¡o', 3, 'BÆ°á»›c 1 : SÆ°ï¿½?n rá»­a sáº¡ch, cháº·t miáº¿ng (khoáº£ng 3 - 4 cm). Xay nhuyá»…n tĂ¡o, hĂ nh tĂ¢y, tï¿½?i vĂ  gá»«ng báº±ng mĂ¡y xay.@@@BÆ°á»›c 2 : Cho táº¥t cáº£ cĂ¡c nguyĂªn liá»‡u cĂ²n láº¡i gá»“m cĂ³ Ä‘Æ°ï¿½?ng, nÆ°á»›c lï¿½?c, dáº¥m gáº¡o, dáº§u vá»«ng vĂ  háº¡t tiĂªu, háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng á»‘ng vĂ  Tá»§y vĂ o trá»™n Ä‘ï¿½?u cĂ¹ng há»—n há»£p tĂ¡o - hĂ nh tĂ¢y báº¡n Ä‘Ă£ xay Ä‘á»ƒ Ä‘Æ°á»£c nÆ°á»›c Æ°á»›p sÆ°ï¿½?n.@@@BÆ°á»›c 3 : Æ¯á»›p sÆ°ï¿½?n vá»›i nÆ°á»›c Æ°á»›p trong khoáº£ng 8 tiáº¿ng hoáº·c qua Ä‘Ăªm cho sÆ°ï¿½?n tháº¥m gia vá»‹.@@@BÆ°á»›c 4 : NÆ°á»›ng sÆ°ï¿½?n trong lĂ² nÆ°á»›ng vá»›i nhiá»‡t Ä‘á»™ khoáº£ng 170 - 180 Ä‘á»™ C trong 35 - 45 phĂºt (chĂº Ă½ lĂ  nĂªn báº­t lĂ² trÆ°á»›c 15 phĂºt Ä‘á»ƒ lĂ² nĂ³ng sáºµn) hoáº·c báº¡n cÅ©ng cĂ³ thá»ƒ nÆ°á»›ng trĂªn vá»‰ nÆ°á»›ng than, sÆ°ï¿½?n cÅ©ng ráº¥t ngon.@@@BÆ°á»›c 5 : Láº¥y ra, trĂ¬nh bĂ y ra Ä‘Ä©a cho Ä‘áº¹p máº¯t.', 'drawable/monan8', 'MNU', 'not');";
	private static final String INSERT_MONAN29 = "INSERT INTO monan VALUES ('MNU_TNMO_27', 'TĂ´m nÆ°á»›ng muá»‘i á»›t', 2, 'BÆ°á»›c 1 : GiĂ£ nĂ¡t á»›t vĂ  tï¿½?i, cho nÆ°á»›c chanh, háº¡t nĂªm tá»« thá»‹t thÄƒn vĂ  xÆ°Æ¡ng á»‘ng, muá»‘i há»™t, Ä‘Æ°ï¿½?ng, tiĂªu xay, tÆ°Æ¡ng á»›t, dáº§u Äƒn vĂ o trá»™n Ä‘ï¿½?u. DĂ¹ng que xiĂªn, xiĂªn tá»« Ä‘áº§u Ä‘áº¿n Ä‘uĂ´i tĂ´m.@@@BÆ°á»›c 2 : Æ¯á»›p tĂ´m sĂº 30 phĂºt vá»›i há»—n há»£p muá»‘i á»›t.@@@BÆ°á»›c 3 : NÆ°á»›ng chĂ­n tĂ´m trĂªn lá»­a than, khi nÆ°á»›ng pháº¿t Ä‘ï¿½?u vá»›i nÆ°á»›c Æ°á»›p.@@@BÆ°á»›c 4 : ï¿½?á»ƒ tĂ´m nÆ°á»›ng muá»‘i á»›t vĂ o dÄ©a, trang trĂ­ vá»›i xĂ  lĂ¡ch, cĂ  chua, dÆ°a leo. DĂ¹ng nĂ³ng vá»›i muá»‘i á»›t, chanh.', 'drawable/monan27', 'MNU', 'not');";
	private static final String INSERT_MONAN30 = "INSERT INTO monan VALUES ('MTR_BCXTDH_37', 'BĂ´ng cáº£i xanh trá»™n dáº§u hĂ o', 2, 'BÆ°á»›c 1 : TĂ´m sĂº háº¥p chĂ­n, bĂ³c vï¿½?, chá»«a Ä‘uĂ´i. Cháº§n bĂ´ng cáº£i xanh trong nÆ°á»›c sĂ´i cĂ³ cho tĂ­ muá»‘i Ä‘á»ƒ giá»¯ mĂ u xanh. Cháº§n vĂ  vá»›t ra cho vĂ o thau nÆ°á»›c Ä‘Ă¡ Ä‘á»ƒ bĂ´ng cáº£i giĂ²n vĂ  tÆ°Æ¡i xanh. Sau Ä‘Ă³, cho ra rá»• Ä‘á»ƒ rĂ¡o.@@@BÆ°á»›c 2 : HĂ²a há»—n há»£p dáº§u hĂ u, chĂºt xĂ­u Ä‘Æ°ï¿½?ng, nÆ°á»›c vĂ  1 muá»—ng cĂ  phĂª háº¡t nĂªm Knorr. Cho há»—n há»£p vĂ o cháº£o náº¥u sĂ´i. Cho tiáº¿p bá»™t báº¯p pha loĂ£ng vĂ o khuáº¥y Ä‘ï¿½?u Ä‘áº¿n khi xá»‘t hÆ¡i sĂ¡nh, báº¯c xuá»‘ng.@@@BÆ°á»›c 3 : Xáº¿p bĂ´ng cáº£i ra dÄ©a, xáº¿p tĂ´m lĂªn trĂªn, cho nhá»¯ng khoanh hĂ nh tĂ¢y lĂªn, rÆ°á»›i nÆ°á»›c xá»‘t vĂ  cuá»‘i cĂ¹ng lĂ  ráº¯c gá»«ng bÄƒm lĂªn trĂªn. MĂ³n Äƒn nĂ y dĂ¹ng nĂ³ng.@@@MĂ¡ch nhï¿½? : Chï¿½?n bĂ´ng cĂ³ mĂ u xanh mÆ°á»›t, cáº§m tháº¥y cháº¯c tay. TĂ¡ch nhĂ¡nh bĂ´ng cáº£i, cáº¯t miáº¿ng vá»«a Äƒn, ngĂ¢m trong nÆ°á»›c muá»‘i pha loĂ£ng, khoáº£ng 30 phĂºt. Dáº§u hĂ u Ä‘Æ°á»£c chiáº¿t xuáº¥t tá»« con hĂ u vĂ  cĂ¡c gia vá»‹ khĂ¡c: muá»‘i vĂ  Ä‘Æ°ï¿½?ng..., vĂ¬ váº­y khi nĂªm náº¿m cĂ¡c báº¡n cáº§n lÆ°u Ă½ Ä‘á»ƒ trĂ¡nh mĂ³n Äƒn bá»‹ ngï¿½?t hoáº·c máº·n quĂ¡.', 'drawable/monan37', 'MTR', 'http://www.youtube.com/watch?v=6Qo85ui3Gqo');";
	private static final String INSERT_MONAN31 = "INSERT INTO monan VALUES ('MTR_BTTDK_33', 'Bao tá»­ trá»™n dÆ°a kiá»‡u', 2, 'BÆ°á»›c 1 : Lá»™n trĂ¡i bao tá»­, láº¥y háº¿tÂ mĂ ng má»¡, rá»“i cho má»™t náº¯m bá»™t mĂ¬ trá»™nÂ chĂºt muá»‘i vĂ o bĂ³p ká»¹, rá»­a láº¡i báº±ng nÆ°á»›c. DĂ¹ng chanh chĂ  xĂ¡t Ä‘á»ƒ lĂ m cho tháº­t sáº¡ch nhá»›t vĂ  rá»­a sáº¡ch láº¡i.@@@BÆ°á»›c 2 : Luá»™c bao tá»­ trong nÆ°á»›c tháº­t sĂ´i cho chĂ­n, vá»›t ra ngĂ¢m nÆ°á»›c láº¡nh pha chanh, vá»›t ra Ä‘á»ƒ rĂ¡o, xáº¯t miáº¿ng vá»«a Äƒn. HĂ²a nÆ°á»›c máº¯m cháº¥m ngon vá»›i Ä‘Æ°ï¿½?ng vĂ  tÆ°Æ¡ng á»›t cho Ä‘ï¿½?u.@@@BÆ°á»›c 3 : Cho bao tá»­, cá»§ kiá»‡u, cá»§ hĂ nh, má»™t 1/2 pháº§n rau rÄƒm, hung lá»§i, á»›t sá»«ng vĂ o tĂ´ lá»›n. RÆ°á»›i nÆ°á»›c trá»™n lĂªn, trá»™n Ä‘ï¿½?u.@@@BÆ°á»›c 4 : Cho ra Ä‘Ä©a, ráº¯c rau rÄƒm, hung lá»§i, Ä‘áº­u phá»™ng.@@@MĂ¡ch nhï¿½? cĂ¡ch lĂ m sáº¡ch bao tá»­ nhanh : lá»™n trĂ¡i bao tá»­, láº¥y háº¿t mĂ ng má»¡, rá»“i cho má»™t náº¯m bá»™t mĂ¬ trá»™n chĂºt muá»‘i vĂ o bĂ³p ká»¹, rá»­a láº¡i báº±ng nÆ°á»›c. Sau Ä‘Ă³, chĂ  chanh Ä‘á»ƒ lĂ m cho tháº­t sáº¡ch nhá»›t vĂ  rá»­a sáº¡ch láº¡i. Khi luá»™c bao tá»­, pháº£i Ä‘á»£i nÆ°á»›c tháº­t sĂ´i má»›i cho vĂ o luá»™c vĂ  bao tá»­ pháº£i ngáº­p trong nÆ°á»›c. Khi bao tá»­ chĂ­n mï¿½?m, vá»›t ra cho vĂ o thau nÆ°á»›c láº¡nh váº¯t vĂ o 1 miáº¿ng chanh, Ä‘á»£i nguá»™i, vá»›t ra, Ä‘á»ƒ rĂ¡o.', 'drawable/monan33', 'MTR', 'not');";
	private static final String INSERT_MONAN32 = "INSERT INTO monan VALUES ('MXA_BHXHQ_15', 'BĂ´ng háº¹ xĂ o heo quay', 2, 'BÆ°á»›c 1 : LĂ m nĂ³ng cháº£o vĂ  cho má»™t Ă­t dáº§u Äƒn. VĂ¬ heo quay sáº½ cĂ²n ra má»¡ nĂªn cho Ă­t dáº§u thĂ´i nhĂ©. Khi dáº§u nĂ³ng, cho cá»§ hĂ nh tĂ¢y vĂ o xĂ o thÆ¡m. Tiáº¿p tá»¥c cho heo quay vĂ o xĂ o cho sÄƒn thá»‹t rá»“i cho náº¥m Ä‘Ă´ng cĂ´.@@@BÆ°á»›c 2 : Cuá»‘i cĂ¹ng cho bĂ´ng háº¹ vĂ  cĂ  rá»‘t vĂ o xĂ o chung.@@@BÆ°á»›c 3 : Rau sáº¯p chĂ­n thĂ¬ quáº­y bá»™t nÄƒng vĂ  Ä‘á»• vĂ o. Vá»«a cho bá»™t vá»«a khuáº¥y Ä‘á»ƒ táº¡o thĂ nh nÆ°á»›c xá»‘t sá»‡t sá»‡t. NĂªm háº¡t nĂªm Knorr, muá»‘i, tiĂªu vá»«a Äƒn vĂ  táº¯t báº¿p.', 'drawable/monan15', 'MXA', 'not');";
	private static final String INSERT_MONAN33 = "INSERT INTO monan VALUES ('MXA_BXT_16', 'BĂ² xĂ o tï¿½?i', 1, 'BÆ°á»›c 1 : Æ¯á»›p thá»‹t bĂ² vá»›i 1/2 muá»—ng cĂ  phĂª háº¡t nĂªm, nÆ°á»›c tÆ°Æ¡ng, tiĂªu xay vĂ  hĂ nh tĂ­m. XĂ o sÆ¡ cho thÆ¡m, Ä‘á»ƒ qua má»™t bĂªn. Tï¿½?i chiĂªn vĂ ng, Ä‘á»ƒ qua má»™t bĂªn. XĂ o tï¿½?i, hĂ nh tĂ¢y vĂ  á»›t xanh cho thÆ¡m. ThĂªm nÆ°á»›c láº¡nh vĂ  nĂªm vá»›i 1 muá»—ng cĂ  phĂª háº¡t nĂªm cĂ²n láº¡i cho vá»«a Äƒn.@@@BÆ°á»›c 2 : Cho tiáº¿p thá»‹t bĂ² vĂ o xĂ o chĂ­n. LĂ m sá»‡t nÆ°á»›c xá»‘t vá»›i bá»™t báº¯p.@@@BÆ°á»›c 3 :  MĂºc bĂ² xĂ o tï¿½?i vĂ o dÄ©a, ráº¯c tiĂªu xay, trang trĂ­ vá»›i ngĂ² rĂ­. DĂ¹ng nĂ³ng.', 'drawable/monan16', 'MXA', 'http://www.youtube.com/watch?v=51m8r4LXOOc');";
	private static final String INSERT_MONAN34 = "INSERT INTO monan VALUES ('MXA_DTXCC_11', 'Dá»“i trÆ°ï¿½?ng xĂ o cáº£i chua', 2, 'BÆ°á»›c 1 : Cáº£i chua rá»­a sáº¡ch, cáº¯t khĂºc, vá»«a Äƒn Ä‘á»ƒ rĂ¡o nÆ°á»›c. HĂ nh tĂ­m cháº» Ä‘Ă´i,Â cáº¯t mĂºi cam. Dá»“i trÆ°ï¿½?ng xĂ¡t muá»‘i, rá»­a láº¡i nhiï¿½?u láº§n vá»›i nÆ°á»›c vĂ  rÆ°á»£u tráº¯ng. Cho vĂ o ná»“i nÆ°á»›c, luá»™c chĂ­n, thĂ¡i miáº¿ng vá»«a Äƒn.@@@BÆ°á»›c 2 : ï¿½?un nĂ³ng 1 thĂ¬a cĂ -phĂª dáº§u Äƒn, cho hĂ nh vĂ o phi thÆ¡m. Cho dá»“i trÆ°ï¿½?ng vĂ o xĂ o sÆ¡. Sau Ä‘Ă³ cho cáº£i chua vĂ o, xĂ o khoáº£ng 5 phĂºt rá»“i nĂªm náº¿m gia vá»‹ gá»“m 1 thĂ¬a sĂºp Ä‘Æ°ï¿½?ng, 1/2 thĂ¬a sĂºp nÆ°á»›c máº¯m, 1/2 thĂ¬a cĂ  phĂª háº¡t nĂªm Knorr knorr tá»« xÆ°Æ¡ng á»‘ng vĂ  tá»§y.@@@BÆ°á»›c 3 : Táº¯t báº¿p, cho cáº§n tĂ¢y vĂ o Ä‘áº£o sÆ¡. Cho ra dÄ©a, ráº¯c tiĂªu lĂªn trĂªn.', 'drawable/monan11', 'MXA', 'not');";
	private static final String INSERT_MONAN35 = "INSERT INTO monan VALUES ('MXA_EXTT_35', 'áº¾ch xĂ o tĂ­a tĂ´', 2, 'BÆ°á»›c 1 : ï¿½?Ă¹i áº¿ch, chiĂªn sÆ¡, vá»›t ra Ä‘á»ƒ rĂ¡o. Cho dáº§u vĂ o cháº£o xĂ o gá»«ng, hĂ nh tĂ¢y cho thÆ¡m.@@@BÆ°á»›c 2 : Cho Ä‘Ă¹i áº¿ch vĂ o xĂ o nhanh, nĂªm gia vá»‹ : Ä‘Æ°ï¿½?ng vĂ  nÆ°á»›c máº¯m ngon. Cho lĂ¡ tĂ­a tĂ´ vĂ o.@@@BÆ°á»›c 3 : Dï¿½?n mĂ³n Äƒn cho ra dÄ©a. Dï¿½?n kĂ¨m nÆ°á»›c tÆ°Æ¡ng. DĂ¹ng nĂ³ng.@@@MĂ¡ch nhï¿½? : Thá»‹t áº¿ch ráº¥t há»£p vá»‹ vá»›i rau rÄƒm, sáº£, á»›t, ngĂ² om (rau ngá»•) vĂ  tĂ­a tĂ´. Rau tĂ­a tĂ´ cĂ³ vá»‹ cay, tĂ­nh áº¥m, cĂ³ tĂ¡c dá»¥ng chá»¯a háº¯t hÆ¡i sá»• mÅ©i, cáº£m máº¡o, ho, ra má»“ hĂ´i, tá»‘t cho tiĂªu hĂ³a.', 'drawable/monan35', 'MXA', 'not');";
	private static final String INSERT_MONAN36 = "INSERT INTO monan VALUES ('MXA_MGXADTV_22', 'MĂ¬ gĂ³i xĂ o sĂ² Ä‘iá»‡p tĂ´m viĂªn', 3, 'BÆ°á»›c 1 : Trá»¥ng mĂ¬ chĂ­n vá»›i nÆ°á»›c sĂ´i, vá»›t ra, Ä‘á»ƒ rĂ¡o. Cá»“i sĂ² Ä‘iá»‡p rá»­a sáº¡ch, trá»¥ng qua nÆ°á»›c sĂ´i. CĂ  rá»‘t tá»‰a hoa, xáº¯t lĂ¡t mï¿½?ng. Náº¥m Ä‘Ă´ng cĂ´ ngĂ¢m nÆ°á»›c rá»“i bĂ³p rá»­a sáº¡ch, xáº¯t lĂ¡t mï¿½?ng. Cáº£i ngï¿½?t rá»­a sáº¡ch, cáº¯t khĂºc. HĂ nh tĂ¢y cáº¯t mĂºi mï¿½?ng. HĂ nh lĂ¡ cáº¯t khĂºc.@@@BÆ°á»›c 2 : Tï¿½?i Ä‘áº­p giáº­p, phi thÆ¡m rá»“i cho cá»“i sĂ² Ä‘iá»‡p vĂ o xĂ o sÄƒn, trĂºt sĂ² Ä‘iá»‡p ra, cho náº¥m Ä‘Ă´ng cĂ´ vĂ o xĂ o. Cho tiáº¿p cĂ  rá»‘t, cáº£i ngï¿½?t, hĂ nh tĂ¢y, hĂ nh lĂ¡ vĂ o, nĂªm náº¿m vá»›i chĂºt xĂ­u Ä‘Æ°ï¿½?ng cĂ¹ng Háº¡t NĂªm tá»« Náº¥m & Rong Biá»ƒn. Cuá»‘i cĂ¹ng cho sĂ² Ä‘iá»‡p vĂ o Ä‘áº£o qua rá»“i cho mĂ¬ vĂ o trá»™n Ä‘ï¿½?u.@@@BÆ°á»›c 3 : BĂ y ra Ä‘Ä©a, xáº¿p ngĂ² rĂ­ lĂªn trang trĂ­, ráº¯c tiĂªu cho thÆ¡m, dĂ¹ng nĂ³ng.', 'drawable/monan22', 'MXA', 'not');";
	private static final String INSERT_MONAN37 = "INSERT INTO monan VALUES ('MXA_MXC_10', 'Miáº¿n xĂ o cua', 2, 'BÆ°á»›c 1 : Miáº¿n trong ngĂ¢m nÆ°á»›c áº¥m hoáº·c luá»™c sÆ¡ trong nÆ°á»›c sĂ´i cho mï¿½?m, láº¥y ra tháº­t nhanh tay. Xáº£ láº¡i miáº¿nÂ trong nÆ°á»›c láº¡nh Ä‘á»ƒ tá»«ng sá»£i miáº¿n Ä‘Æ°á»£c tĂ¡chÂ ra, dĂ¹ng kĂ©o cáº¯t ngáº¯n vá»«a Äƒn, xá»‘c chĂºt dáº§u phi tï¿½?i cho miáº¿n Ä‘Æ°á»£c rĂ¡o vĂ  rï¿½?i. Thá»‹t cua rá»‰a bï¿½? xÆ°Æ¡ng. CĂ  rá»‘t gï¿½?t vï¿½?, thĂ¡i dĂ i. á»�t sá»«ng tá»‰a hoa báº±ng cĂ¡ch cháº» tá»« trĂªn xuá»‘ng. HĂ nh tĂ¢y bĂ³c vï¿½?, cáº¯t dï¿½?c.@@@BÆ°á»›c 2 : Cháº£o nĂ³ng, cho hai muá»—ng xĂºp dáº§u, tï¿½?i báº±m phi vĂ ng. Cho tĂ´m, cua, hĂ nh tĂ¢y, cĂ  rá»‘t, rau cáº£i vĂ o xĂ o, nĂªm gia vá»‹ vá»«a Äƒn. Khi táº¥t cáº£ vá»«a chĂ­n, nháº¯c xuá»‘ng.@@@BÆ°á»›c 3 : Cháº£o nĂ³ng, cho 4 muá»—ng xĂºp dáº§u, tï¿½?i phi vĂ ng, cho miáº¿n vĂ o xĂ o, thĂªm gia vá»‹ vá»«a Äƒn. XĂ o Ä‘ï¿½?u vĂ  rĂ¡o, trá»™n má»™t ná»­a nhĂ¢n vĂ o. Nháº¯c xuá»‘ng, cho thĂªm tiĂªu.@@@BÆ°á»›c 4 : BĂ y ra Ä‘Ä©a, trang trĂ­ vá»›i á»›t vĂ  rau mĂ¹i, vĂ  pháº§n nhĂ¢n cĂ²n láº¡i cĂ¹ng vá»›i cĂ ng cua. DĂ¹ng chung xĂ¬ dáº§u, á»›t thĂ¡i khoang hoáº·c nÆ°á»›c máº¯m chanh.', 'drawable/monan10', 'MXA', 'not');";
	private static final String INSERT_MONAN38 = "INSERT INTO monan VALUES ('MXA_MXDC_23', 'Má»±c xĂ o dÆ°a cáº£i', 2, 'BÆ°á»›c 1 : Má»±c lĂ m rá»­a sáº¡ch, khá»©a váº©y rá»“ng, cáº¯t miáº¿ng vá»«a Äƒn, Æ°á»›p háº¡t nĂªm Knorr tá»« Náº¥m vĂ  Rong biá»ƒn, tiĂªu, Ä‘á»ƒ khoáº£ng 10 phĂºt cho tháº¥m gia vá»‹. DÆ°a chua rá»­a sáº¡ch, cáº¯t khĂºc, Ä‘á»ƒ rĂ¡o. CĂ  chua rá»­a sáº¡ch, cháº» mĂºi cau.@@@BÆ°á»›c 2 : ï¿½?un nĂ³ng 1 thĂ¬a sĂºp dáº§u Äƒn, phi thÆ¡m vá»›i tï¿½?i bÄƒm, cho má»±c vĂ o xĂ o cho sÄƒn trĂªn lá»­a lá»›n, mĂºc ra chĂ©n Ä‘á»ƒ riĂªng.@@@BÆ°á»›c 3 : Tiáº¿p tá»¥c Ä‘un nĂ³ng 1 thĂ¬a sĂºp dáº§u Äƒn, phi thÆ¡m vá»›i tï¿½?i bÄƒm, cho cĂ  chua vĂ  cáº£i chua vĂ o xĂ o vá»›i lá»­a lá»›n, nĂªm nÆ°á»›c máº¯m cháº¥m Knorr, háº¡t nĂªm Knorr tá»« Náº¥m vĂ  Rong biá»ƒn, Ä‘Æ°ï¿½?ng cho vá»«a Äƒn, trĂºt má»±c vĂ o, cho hĂ nh lĂ¡, ngĂ² rĂ­ cáº¯t khĂºc vĂ o Ä‘áº£o Ä‘ï¿½?u, táº¯t báº¿p.@@@BÆ°á»›c 4 : MĂºc mĂ³n Äƒn ra Ä‘Ä©a, dĂ¹ng vá»›i cÆ¡m nĂ³ng ráº¥t ngon.', 'drawable/monan23', 'MXA','http://www.youtube.com/watch?v=WFuQjLC9JTc');";
	private static final String INSERT_MONAN39 = "INSERT INTO monan VALUES ('MXA_SSXT_3', 'Su su xĂ o trá»©ng', 1, 'BÆ°á»›c 1 : Su su gï¿½?t vï¿½?, rá»­a sáº¡ch rá»“i cáº¯t dáº¡ng sá»£i vá»«a Äƒn. Trá»©ng Ä‘áº­p vĂ o chĂ©n rá»“i Ä‘Ă¡nh Ä‘ï¿½?u, nĂªm vĂ o má»™t chĂºt muá»‘i, tiĂªu vĂ  háº¡t nĂªm cho Ä‘áº­m Ä‘Ă .@@@BÆ°á»›c 2 : Cháº£o nĂ³ng, cho dáº§u Äƒn vĂ o trĂ¡ng Ä‘ï¿½?u rá»“i cho su su vĂ o xĂ o. Cho thĂªm má»™t Ă­t nÆ°á»›c nguá»™i Ä‘á»ƒ mĂ³n xĂ o cĂ³ Ä‘á»™ sá»‡t sá»‡t.@@@BÆ°á»›c 3 : Khi su su vá»«a chĂ­n thĂ¬ cho trá»©ng vĂ o xĂ o chung. Náº¿u muá»‘n trá»©ng khĂ´ng bá»‹ tÆ¡i nhï¿½? thĂ¬ Ä‘á»• trá»©ng vĂ o pháº§n giá»¯a cháº£o, Ä‘á»ƒ trá»©ng hÆ¡i Ä‘Ă´ng láº¡i rá»“i má»›i trá»™n chung vá»›i su su. NĂªm náº¿m vá»«a Äƒn vá»›i háº¡t nĂªm Knorr tá»« Thá»‹t ThÄƒn, XÆ°Æ¡ng ï¿½?ng vĂ  Tá»§y, má»™t chĂºt muá»‘i. Táº¯t báº¿p, cho hĂ nh ngĂ² cáº¯t nhuyá»…n vĂ o Ä‘áº£o Ä‘ï¿½?u cho chĂ­n. Khi Äƒn ráº¯c thĂªm má»™t chĂºt tiĂªu xay cho thÆ¡m.', 'drawable/monan3', 'MXA', 'not');";
	private static final String INSERT_MONAN40 = "INSERT INTO monan VALUES ('MXA_TXDT_34', 'TĂ´m xĂ o Ä‘áº­u tÆ°Æ¡ng', 4, 'BÆ°á»›c 1 : TĂ´m lĂ m sáº¡ch, Ä‘á»ƒ rĂ¡o, Æ°á»›p vá»›i 1 muá»—ng cĂ  phĂª háº¡t nĂªm, Ä‘á»ƒ tháº¥m gia vá»‹.@@@BÆ°á»›c 2 : ChiĂªn tĂ´m vá»›i dáº§u nĂ³ng cho sÄƒn Ä‘ï¿½?u, vá»›t ra Ä‘á»ƒ rĂ¡o dáº§u. NhÆ° váº­y tĂ´m sáº½ khĂ´ng bá»‹ teo vĂ  giá»¯ nguyĂªn hÆ°Æ¡ng vá»‹.@@@BÆ°á»›c 3 : Dáº§u nĂ³ng, cho hĂ nh tĂ­m vĂ o phi thÆ¡m, cho Ä‘áº­u tÆ°Æ¡ng vĂ  1 chĂ©n nÆ°á»›c dĂ¹ng vĂ o. NĂªm gia vá»‹ vá»«a Äƒn, rá»“i cho tiáº¿p hĂ nh tĂ¢y, cáº§n tĂ¢y, cĂ  chua tĂ´m, náº¥m mĂ¨o, thĂªm bá»™t nÄƒng vĂ o Ä‘á»ƒ táº¡o Ä‘á»™ sĂ¡nh.@@@BÆ°á»›c 4 : Cho ra Ä‘Ä©a, trang trĂ­, ráº¯c tiĂªu, dáº§u mĂ¨.', 'drawable/monan34', 'MXA', 'http://www.youtube.com/watch?v=qAD3QPlCHAc');";
	private static final String INSERT_MONAN41 = "INSERT INTO monan VALUES ('MCH_CGJV_41', 'CĂ¡nh gĂ  java', 2, 'BÆ°á»›c 1 : Æ¯á»›p cĂ¡nh gĂ  vá»›i nÆ°á»›c hĂ nh vĂ  1 muá»—ng cĂ  phĂª háº¡t nĂªm tá»« thá»‹t thÄƒn vĂ  xÆ°Æ¡ng á»‘ng, trá»™n Ä‘ï¿½?u cho tháº¥m gia vá»‹.@@@BÆ°á»›c 2 : Dáº§u nĂ³ng, tháº£ cĂ¡nh gĂ  vĂ o chiĂªn cho vĂ ng, rá»“i vá»›t ra cho vĂ o giáº¥y tháº¥m dáº§u.@@@BÆ°á»›c 3 : Cho há»—n há»£p tÆ°Æ¡ng cĂ , tÆ°Æ¡ng á»›t, nÆ°á»›c tÆ°Æ¡ng vĂ  máº­t ong vĂ o cháº£o náº¥u sĂ´i. NĂªm 2 muá»—ng cĂ  phĂª háº¡t nĂªm tá»« thá»‹t thÄƒn vĂ  xÆ°Æ¡ng á»‘ng.@@@BÆ°á»›c 4 : ThĂªm vĂ o má»™t Ă­t bá»™t nÄƒng pha loĂ£ng Ä‘á»ƒ táº¡o Ä‘á»™ sĂ¡nh cho nÆ°á»›c sá»‘t, rá»“i cho cá»§ hĂ nh tĂ­m Ä‘Ă£ chiĂªn, náº¥m rÆ¡m vĂ  á»›t xáº¯t háº¡t lá»±u vĂ o sá»‘t, táº¯t báº¿p. DĂ¹ng nĂ³ng. Ä‚n kĂ¨m vá»›i cÆ¡m chiĂªn sá»‘t cĂ  hoáº·c nui sá»‘t cĂ .', 'drawable/monan41', 'MCH', 'not');";
	private static final String INSERT_MONAN42 = "INSERT INTO monan VALUES ('MTR_SLTXX_42', 'Salad trá»™n xĂºc xĂ­ch', 1, 'BÆ°á»›c 1 : XĂºc xĂ­ch nÆ°á»›ng hoáº·c chiĂªn chĂ­n, cáº¯t miáº¿ng vá»«a Äƒn. Rau cáº§n cáº¯t khĂºc vá»«a Äƒn, hĂ nh tĂ¢y cáº¯t miáº¿ng mï¿½?ng, cĂ  chua cáº¯t mĂºi cau, á»›t sá»«ng cáº¯t sá»£i. LĂ m xá»‘t : giĂ£ nĂ¡t tï¿½?i vĂ  á»›t hiá»ƒm, cho nÆ°á»›c chanh, nÆ°á»›c máº¯m vĂ  Ä‘Æ°ï¿½?ng vĂ o tĂ´, khuáº¥y Ä‘ï¿½?u.@@@BÆ°á»›c 2 : Cho láº§n lÆ°á»£t xĂºc xĂ­ch vĂ  há»—n há»£p hĂ nh tĂ¢y vĂ o nÆ°á»›c xá»‘t, trá»™n Ä‘ï¿½?u cho tháº¥m.@@@BÆ°á»›c 3 : ï¿½?á»ƒ xĂ  lĂ¡ch xĂºc xĂ­ch vĂ o dÄ©a. DĂ¹ng ngay.', 'drawable/monan42', 'MTR', 'not');";
	private static final String INSERT_MONAN43 = "INSERT INTO monan VALUES ('MCA_CCNTL_43', 'Canh cĂ¡ náº¥u thĂ¬ lĂ ', 4, 'BÆ°á»›c 1 : CĂ  chua cáº¯t mĂºi cau. Rau thĂ¬ lĂ  rá»­a sáº¡ch, xáº¯t khĂºc ngáº¯n cá»¡ 3-4cm. LĂ m nĂ³ng cháº£o vá»›i chĂºt dáº§u Äƒn, phi thÆ¡m tï¿½?i rá»“i cho 1 trĂ¡i cĂ  chua vĂ o xĂ o nĂ¡t Ä‘á»ƒ láº¥y mĂ u, nĂªm thĂªm chĂºt muá»‘i.@@@BÆ°á»›c 2 : Sau khi cĂ  chua mï¿½?m báº¡n cho nÆ°á»›c vĂ o ná»“i Ä‘un sĂ´i vá»›i lá»­a vá»«a. Báº¡n nhá»› canh lÆ°á»£ng nÆ°á»›c vá»«a Ä‘á»§ dĂ¹ng cho gia Ä‘Ă¬nh mĂ¬nh nhĂ©! ï¿½?á»£i nÆ°á»›c sĂ´i, tháº£ cĂ¡ há»“i Ä‘Ă£ Ä‘Ă¡nh váº£y, cáº¯t khĂºc vĂ o ná»“i. Náº¥u cho cĂ¡ chĂ­n thĂ¬ vá»›t bï¿½?t, Ä‘á»£i ná»“i nÆ°á»›c sĂ´i láº¡i thĂ¬ báº¡n cho pháº§n cĂ  chua cĂ²n láº¡i vĂ o ná»“i.@@@BÆ°á»›c 3 : Cuá»‘i cĂ¹ng báº¡n tháº£ lĂ¡ thĂ¬ lĂ  vĂ o ná»“i, nĂªm náº¿m láº¡i vá»›i Ă­t muá»‘i vĂ  háº¡t nĂªm cho vá»«a Äƒn rá»“i táº¯t báº¿p. MĂ¬nh thÆ°ï¿½?ng thĂªm má»™t chĂºt xĂ­u Ä‘Æ°ï¿½?ng thay cho bá»™t ngï¿½?t Ä‘á»ƒ mĂ³n ÄƒnÂ cĂ³ vá»‹ ngï¿½?t Ä‘áº­m Ä‘Ă  hÆ¡n.@@@BÆ°á»›c 4 : Khi Äƒn báº¡n cáº¯t thĂªm vĂ i lĂ¡t chanh dï¿½?n kĂ¨m vá»›i chĂ©n nÆ°á»›c máº¯m máº·n cĂ¹ng vĂ i lĂ¡t á»›t cay cay. MĂ¬nh váº«n náº¥u theo cĂ¡ch Ngoáº¡i hay náº¥u cho mĂ¬nh Äƒn Ä‘Ă³ lĂ  khi dï¿½?n lĂªn bĂ n Äƒn thĂ¬ cho thĂªm vĂ i miáº¿ng chanh Ä‘á»ƒ riĂªng cho ai thĂ­ch Äƒn chua thĂ¬ váº¯t chanh vĂ o chĂ©n cá»§a mĂ¬nh theo lÆ°á»£ng thĂ­ch há»£p tĂ¹y kháº©u vá»‹ má»—i ngÆ°ï¿½?i. MĂ³n canh cĂ¡ náº¥u kiá»ƒu nĂ y á»Ÿ miï¿½?n Nam thÆ°ï¿½?ng Ä‘Æ°á»£c gï¿½?i lĂ  canh cĂ¡ náº¥u ngĂ³t. CĂ³ má»™t tĂ¡c giáº£ Ä‘Ă£ viáº¿t vï¿½? canh ngĂ³t lĂ  mĂ³n canh dĂ¢n dĂ£ náº¥u ráº¥t Ä‘Æ¡n giáº£n phá»• biáº¿n trong nhá»¯ng ngĂ y thĂ¡ng Ä‘i kháº©n hoang. Khi báº¯t Ä‘Æ°á»£c má»™t con cĂ¡ tÆ°Æ¡i chá»‰ cáº§n thĂªm chĂºt rau cáº§n, hĂ nh, cĂ  chua Ä‘em náº¥u lĂ  sáº½ cĂ³ ngay má»™t bá»¯a cÆ¡m ngon. CĂ¡ há»“i vá»«a thÆ¡m vá»«a bĂ©o, cháº¥m vá»›i nÆ°á»›c máº¯m cay má»›i Ä‘Ăºng â€œÄ‘iá»‡uï¿½?. Vá»«a Äƒn nĂ³ng vá»«a hĂ­t hĂ  váº­y mĂ  quay qua quay láº¡i ná»“i cÆ¡m Ä‘Ă£ vÆ¡i quĂ¡ ná»­a! ChĂºc cĂ¡c báº¡n bá»¯a tá»‘i vui vá»›i mĂ³n canh cĂ¡ tháº­t ngon nhĂ©!', 'drawable/monan43', 'MCA', 'not');";

	// loai nguyen lieu
	private static final String INSERT_LOAINGUYENLIEU1 = "INSERT INTO loainguyenlieu VALUES ('Bt', 'CĂ¡c loáº¡i bĂ¡nh trĂ¡ng');";
	private static final String INSERT_LOAINGUYENLIEU2 = "INSERT INTO loainguyenlieu VALUES ('Ca', 'CĂ¡c loáº¡i cĂ¡');";
	private static final String INSERT_LOAINGUYENLIEU3 = "INSERT INTO loainguyenlieu VALUES ('Ci', 'CĂ¡c loáº¡i cáº£i');";
	private static final String INSERT_LOAINGUYENLIEU4 = "INSERT INTO loainguyenlieu VALUES ('Cu', 'CĂ¡c loáº¡i cá»§');";
	private static final String INSERT_LOAINGUYENLIEU5 = "INSERT INTO loainguyenlieu VALUES ('Da', 'CĂ¡c loáº¡i Ä‘áº­u');";
	private static final String INSERT_LOAINGUYENLIEU6 = "INSERT INTO loainguyenlieu VALUES ('Hs', 'CĂ¡c loáº¡i háº£i sáº£n');";
	private static final String INSERT_LOAINGUYENLIEU7 = "INSERT INTO loainguyenlieu VALUES ('Na', 'CĂ¡c loáº¡i háº¥m');";
	private static final String INSERT_LOAINGUYENLIEU8 = "INSERT INTO loainguyenlieu VALUES ('Nt', 'CĂ¡c loáº¡i ná»™i táº¡ng');";
	private static final String INSERT_LOAINGUYENLIEU9 = "INSERT INTO loainguyenlieu VALUES ('Nu', 'CĂ¡c loáº¡i nÆ°á»›c cá»‘t');";
	private static final String INSERT_LOAINGUYENLIEU10 = "INSERT INTO loainguyenlieu VALUES ('Qu', 'CĂ¡c loáº¡i quáº£');";
	private static final String INSERT_LOAINGUYENLIEU11 = "INSERT INTO loainguyenlieu VALUES ('Ra', 'CĂ¡c loáº¡i rau');";
	private static final String INSERT_LOAINGUYENLIEU12 = "INSERT INTO loainguyenlieu VALUES ('Th', 'CĂ¡c loáº¡i thá»‹t');";
	private static final String INSERT_LOAINGUYENLIEU13 = "INSERT INTO loainguyenlieu VALUES ('Tp', 'CĂ¡c thá»±c pháº©m tá»« bá»™t');";
	private static final String INSERT_LOAINGUYENLIEU14 = "INSERT INTO loainguyenlieu VALUES ('Tr', 'CĂ¡c loáº¡i trá»©ng');";
	private static final String INSERT_LOAINGUYENLIEU15 = "INSERT INTO loainguyenlieu VALUES ('Xu', 'CĂ¡c loáº¡i xÆ°Æ¡ng');";
	private static final String INSERT_LOAINGUYENLIEU16 = "INSERT INTO loainguyenlieu VALUES ('Kh', 'CĂ¡c loáº¡i khĂ¡c');";

	// nguyen lieu
	private static final String INSERT_NGUYENLIEU1 = "INSERT INTO nguyenlieu VALUES ('Bt_Cu_1', 'BĂ¡nh trĂ¡ng cuá»‘n', 'xáº¥p', 'Bt');";
	private static final String INSERT_NGUYENLIEU2 = "INSERT INTO nguyenlieu VALUES ('Bt_Mo_1', 'BĂ¡nh trĂ¡ng mï¿½?ng', 'xáº¥p', 'Bt');";
	private static final String INSERT_NGUYENLIEU3 = "INSERT INTO nguyenlieu VALUES ('Ca_Ho_1', 'CĂ¡ há»“i', 'gr', 'Ca');";
	private static final String INSERT_NGUYENLIEU4 = "INSERT INTO nguyenlieu VALUES ('Ca_Ro_1', 'CĂ¡ rĂ´', 'con', 'Ca');";
	private static final String INSERT_NGUYENLIEU5 = "INSERT INTO nguyenlieu VALUES ('Ca_Th_1', 'CĂ¡ thu', 'gr', 'Ca');";
	private static final String INSERT_NGUYENLIEU6 = "INSERT INTO nguyenlieu VALUES ('Ci_Ba_1', 'Báº¯p cáº£i', 'gr', 'Ci');";
	private static final String INSERT_NGUYENLIEU7 = "INSERT INTO nguyenlieu VALUES ('Ci_Bo_1', 'BĂ´ng cáº£i xanh', 'gr', 'Ci');";
	private static final String INSERT_NGUYENLIEU8 = "INSERT INTO nguyenlieu VALUES ('Ci_Ch_1', 'Cáº£i chua', 'gr', 'Ci');";
	private static final String INSERT_NGUYENLIEU9 = "INSERT INTO nguyenlieu VALUES ('Ci_Ma_1', 'MÄƒng vĂ ng', 'gr', 'Ci');";
	private static final String INSERT_NGUYENLIEU10 = "INSERT INTO nguyenlieu VALUES ('Ci_Me_1', 'Cáº£i mĂ¨o', 'cĂ¢y', 'Ci');";
	private static final String INSERT_NGUYENLIEU11 = "INSERT INTO nguyenlieu VALUES ('Cu_Ca_1', 'CĂ  rá»‘t', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU12 = "INSERT INTO nguyenlieu VALUES ('Cu_Gu_1', 'Gá»«ng', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU13 = "INSERT INTO nguyenlieu VALUES ('Cu_Ha_1', 'HĂ nh tĂ­m', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU14 = "INSERT INTO nguyenlieu VALUES ('Cu_Ha_2', 'HĂ nh tĂ¢y', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU15 = "INSERT INTO nguyenlieu VALUES ('Cu_Ki_1', 'Cá»§ kiá»‡u', 'gr', 'Cu');";
	private static final String INSERT_NGUYENLIEU16 = "INSERT INTO nguyenlieu VALUES ('Cu_Na_1', 'Cá»§ nÄƒng', 'gr', 'Cu');";
	private static final String INSERT_NGUYENLIEU17 = "INSERT INTO nguyenlieu VALUES ('Cu_Ri_1', 'Cá»§ riï¿½?ng', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU18 = "INSERT INTO nguyenlieu VALUES ('Cu_Sa_1', 'Cá»§ sáº¯n', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU19 = "INSERT INTO nguyenlieu VALUES ('Cu_To_1', 'Tï¿½?i', 'cá»§', 'Cu');";
	private static final String INSERT_NGUYENLIEU20 = "INSERT INTO nguyenlieu VALUES ('Da_Hu_1', 'ï¿½?áº­u hÅ© vĂ ng', 'miáº¿ng', 'Da');";
	private static final String INSERT_NGUYENLIEU21 = "INSERT INTO nguyenlieu VALUES ('Da_Hu_2', 'ï¿½?áº­u hÅ© tráº¯ng', 'miáº¿ng', 'Da');";
	private static final String INSERT_NGUYENLIEU22 = "INSERT INTO nguyenlieu VALUES ('Da_Ng_1', 'ï¿½?áº­u ngá»±', 'gr', 'Da');";
	private static final String INSERT_NGUYENLIEU23 = "INSERT INTO nguyenlieu VALUES ('Da_Ph_1', 'ï¿½?áº­u phá»™ng', 'gr', 'Da');";
	private static final String INSERT_NGUYENLIEU24 = "INSERT INTO nguyenlieu VALUES ('Da_Tu_1', 'ï¿½?áº­u tÆ°Æ¡ng', 'gr', 'Da');";
	private static final String INSERT_NGUYENLIEU25 = "INSERT INTO nguyenlieu VALUES ('Hs_Cu_1', 'Cua biá»ƒn', 'con', 'Hs');";
	private static final String INSERT_NGUYENLIEU26 = "INSERT INTO nguyenlieu VALUES ('Hs_Cu_2', 'CĂ ng cua', 'cĂ¡i', 'Hs');";
	private static final String INSERT_NGUYENLIEU27 = "INSERT INTO nguyenlieu VALUES ('Hs_Cu_3', 'Cua Ä‘á»“ng', 'gr', 'Hs');";
	private static final String INSERT_NGUYENLIEU28 = "INSERT INTO nguyenlieu VALUES ('Hs_Mu_1', 'Má»±c lĂ¡', 'gr', 'Hs');";
	private static final String INSERT_NGUYENLIEU29 = "INSERT INTO nguyenlieu VALUES ('Hs_Mu_2', 'Má»±c á»‘ng', 'gr', 'Hs');";
	private static final String INSERT_NGUYENLIEU30 = "INSERT INTO nguyenlieu VALUES ('Hs_So_1', 'SĂ² Ä‘iá»‡p', 'gr', 'Hs');";
	private static final String INSERT_NGUYENLIEU31 = "INSERT INTO nguyenlieu VALUES ('Hs_To_1', 'TĂ´m', 'gr', 'Hs');";
	private static final String INSERT_NGUYENLIEU32 = "INSERT INTO nguyenlieu VALUES ('Na_Ba_1', 'Náº¥m bĂ o ngÆ°', 'gr', 'Na');";
	private static final String INSERT_NGUYENLIEU33 = "INSERT INTO nguyenlieu VALUES ('Na_Do_1', 'Náº¥m Ä‘Ă´ng cĂ´', 'gr', 'Na');";
	private static final String INSERT_NGUYENLIEU34 = "INSERT INTO nguyenlieu VALUES ('Na_Ro_1', 'Náº¥m rÆ¡m', 'gr', 'Na');";
	private static final String INSERT_NGUYENLIEU35 = "INSERT INTO nguyenlieu VALUES ('Na_Ta_1', 'Náº¥m tai mĂ¨o', 'gr', 'Na');";
	private static final String INSERT_NGUYENLIEU36 = "INSERT INTO nguyenlieu VALUES ('Nt_Dt_1', 'Dá»“i trÆ°ï¿½?ng', 'gr', 'Nt');";
	private static final String INSERT_NGUYENLIEU37 = "INSERT INTO nguyenlieu VALUES ('Nt_Rn_1', 'Ruá»™t non heo', 'gr', 'Nt');";
	private static final String INSERT_NGUYENLIEU38 = "INSERT INTO nguyenlieu VALUES ('Nu_Du_1', 'NÆ°á»›c dá»«a', 'trĂ¡i', 'Nu');";
	private static final String INSERT_NGUYENLIEU39 = "INSERT INTO nguyenlieu VALUES ('Qu_Ba_1', 'Báº¯p mÄ©', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU40 = "INSERT INTO nguyenlieu VALUES ('Qu_Ca_1', 'CĂ  chua', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU41 = "INSERT INTO nguyenlieu VALUES ('Qu_Ca_2', 'CĂ  tĂ­m', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU42 = "INSERT INTO nguyenlieu VALUES ('Qu_Ch_1', 'Chanh', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU43 = "INSERT INTO nguyenlieu VALUES ('Qu_Du_1', 'DÆ°a leo', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU44 = "INSERT INTO nguyenlieu VALUES ('Qu_Du_2', 'DÆ°a hÆ°ï¿½?ng', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU45 = "INSERT INTO nguyenlieu VALUES ('Qu_Kh_1', 'Khá»• qua', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU46 = "INSERT INTO nguyenlieu VALUES ('Qu_Me_1', 'Me', 'gr', 'Qu');";
	private static final String INSERT_NGUYENLIEU47 = "INSERT INTO nguyenlieu VALUES ('Qu_Ot_1', 'á»�t sá»«ng', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU48 = "INSERT INTO nguyenlieu VALUES ('Qu_Ot_2', 'á»�t xanh', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU49 = "INSERT INTO nguyenlieu VALUES ('Qu_Ot_3', 'á»�t hiá»ƒm', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU50 = "INSERT INTO nguyenlieu VALUES ('Qu_Su_1', 'Su Su', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU51 = "INSERT INTO nguyenlieu VALUES ('Qu_Ta_1', 'TĂ¡o', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU52 = "INSERT INTO nguyenlieu VALUES ('Qu_Ta_2', 'TĂ¡o tĂ u', 'quáº£', 'Qu');";
	private static final String INSERT_NGUYENLIEU53 = "INSERT INTO nguyenlieu VALUES ('Ra_Ca_1', 'Cáº£i ngï¿½?t', 'gr', 'Ra');";
	private static final String INSERT_NGUYENLIEU54 = "INSERT INTO nguyenlieu VALUES ('Ra_Ca_2', 'Cáº§n tĂ¢y', 'cï¿½?ng', 'Ra');";
	private static final String INSERT_NGUYENLIEU55 = "INSERT INTO nguyenlieu VALUES ('Ra_Ha_1', 'HĂ nh lĂ¡', 'cï¿½?ng', 'Ra');";
	private static final String INSERT_NGUYENLIEU56 = "INSERT INTO nguyenlieu VALUES ('Ra_He_1', 'BĂ´ng háº¹', 'gr', 'Ra');";
	private static final String INSERT_NGUYENLIEU57 = "INSERT INTO nguyenlieu VALUES ('Ra_Hu_1', 'Rau hĂºng lá»§i', 'gr', 'Ra');";
	private static final String INSERT_NGUYENLIEU58 = "INSERT INTO nguyenlieu VALUES ('Ra_La_1', 'LĂ¡ chanh', 'lĂ¡', 'Ra');";
	private static final String INSERT_NGUYENLIEU59 = "INSERT INTO nguyenlieu VALUES ('Ra_Nh_1', 'Rau nhĂºt', 'gr', 'Ra');";
	private static final String INSERT_NGUYENLIEU60 = "INSERT INTO nguyenlieu VALUES ('Ra_Ro_1', 'Rong biá»ƒn', 'lĂ¡', 'Ra');";
	private static final String INSERT_NGUYENLIEU61 = "INSERT INTO nguyenlieu VALUES ('Ra_Th_1', 'ThĂ¬ lĂ ', 'nhĂ¡nh', 'Ra');";
	private static final String INSERT_NGUYENLIEU62 = "INSERT INTO nguyenlieu VALUES ('Ra_Ti_1', 'TĂ­a tĂ´', 'cï¿½?ng', 'Ra');";
	private static final String INSERT_NGUYENLIEU63 = "INSERT INTO nguyenlieu VALUES ('Ra_Xa_1', 'XĂ  lĂ¡ch, rau sá»‘ng', 'gr', 'Ra');";
	private static final String INSERT_NGUYENLIEU64 = "INSERT INTO nguyenlieu VALUES ('Ra_Xa_2', 'Sáº£', 'cĂ¢y', 'Ra');";
	private static final String INSERT_NGUYENLIEU65 = "INSERT INTO nguyenlieu VALUES ('Th_Bo_1', 'Thá»‹t bĂ² thÄƒn', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU66 = "INSERT INTO nguyenlieu VALUES ('Th_Bo_2', 'Thá»‹t báº¯p bĂ²', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU67 = "INSERT INTO nguyenlieu VALUES ('Th_Ec_1', 'ï¿½?Ă¹i áº¿ch', 'cáº·p', 'Th');";
	private static final String INSERT_NGUYENLIEU68 = "INSERT INTO nguyenlieu VALUES ('Th_Ga_1', 'Thá»‹t gĂ ', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU69 = "INSERT INTO nguyenlieu VALUES ('Th_Ga_2', 'GĂ  nguyĂªn con', 'con', 'Th');";
	private static final String INSERT_NGUYENLIEU70 = "INSERT INTO nguyenlieu VALUES ('Th_Ga_3', 'á»¨c gĂ ', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU71 = "INSERT INTO nguyenlieu VALUES ('Th_Ga_4', 'ï¿½?Ă¹i gĂ ', 'cĂ¡i', 'Th');";
	private static final String INSERT_NGUYENLIEU72 = "INSERT INTO nguyenlieu VALUES ('Th_Ga_5', 'CĂ¡nh gĂ ', 'cĂ¡i', 'Th');";
	private static final String INSERT_NGUYENLIEU73 = "INSERT INTO nguyenlieu VALUES ('Th_He_1', 'Thá»‹t náº¡c', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU74 = "INSERT INTO nguyenlieu VALUES ('Th_He_2', 'Thá»‹t ba chá»‰', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU75 = "INSERT INTO nguyenlieu VALUES ('Th_He_3', 'Thá»‹t ba rï¿½?i', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU76 = "INSERT INTO nguyenlieu VALUES ('Th_He_4', 'Heo quay', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU77 = "INSERT INTO nguyenlieu VALUES ('Th_He_5', 'GiĂ² sá»‘ng', 'gr', 'Th');";
	private static final String INSERT_NGUYENLIEU78 = "INSERT INTO nguyenlieu VALUES ('Th_Xu_1', 'XĂºc xĂ­ch', 'cĂ¢y', 'Th');";
	private static final String INSERT_NGUYENLIEU79 = "INSERT INTO nguyenlieu VALUES ('Tp_Bo_1', 'Bá»™t chiĂªn giĂ²n', 'bá»‹ch', 'Tp');";
	private static final String INSERT_NGUYENLIEU80 = "INSERT INTO nguyenlieu VALUES ('Tp_Bo_2', 'Bá»™t báº¯p', 'chĂ©n', 'Tp');";
	private static final String INSERT_NGUYENLIEU81 = "INSERT INTO nguyenlieu VALUES ('Tp_Bu_1', 'BĂºn tĂ u', 'gr', 'Tp');";
	private static final String INSERT_NGUYENLIEU82 = "INSERT INTO nguyenlieu VALUES ('Tp_Mi_1', 'Miáº¿n', 'lï¿½?n', 'Tp');";
	private static final String INSERT_NGUYENLIEU83 = "INSERT INTO nguyenlieu VALUES ('Tp_Mi_2', 'MĂ¬ gĂ³i', 'gĂ³i', 'Tp');";
	private static final String INSERT_NGUYENLIEU84 = "INSERT INTO nguyenlieu VALUES ('Tr_Ga_1', 'Trá»©ng gĂ ', 'quáº£', 'Tr');";
	private static final String INSERT_NGUYENLIEU85 = "INSERT INTO nguyenlieu VALUES ('Tr_Vi_1', 'Trá»©ng vá»‹t', 'quáº£', 'Tr');";
	private static final String INSERT_NGUYENLIEU86 = "INSERT INTO nguyenlieu VALUES ('Xu_Oc_1', 'Ă“c heo', 'bá»™', 'Xu');";
	private static final String INSERT_NGUYENLIEU87 = "INSERT INTO nguyenlieu VALUES ('Xu_Su_1', 'SÆ°ï¿½?n non', 'gr', 'Xu');";

	// chi tiet mon an
	private static final String INSERT_CHITIETMONAN1 = "INSERT INTO chitietmonan VALUES ('MCA_CBCCT_9', 'Ci_Ba_1', 500);";
	private static final String INSERT_CHITIETMONAN2 = "INSERT INTO chitietmonan VALUES ('MCA_CBCCT_9', 'Qu_Ca_1', 2);";
	private static final String INSERT_CHITIETMONAN3 = "INSERT INTO chitietmonan VALUES ('MCA_CBCCT_9', 'Th_He_1', 300);";
	private static final String INSERT_CHITIETMONAN4 = "INSERT INTO chitietmonan VALUES ('MCA_CBCGS_18', 'Ci_Ba_1', 300);";
	private static final String INSERT_CHITIETMONAN5 = "INSERT INTO chitietmonan VALUES ('MCA_CBCGS_18', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN6 = "INSERT INTO chitietmonan VALUES ('MCA_CBCGS_18', 'Th_He_5', 100);";
	private static final String INSERT_CHITIETMONAN7 = "INSERT INTO chitietmonan VALUES ('MCA_CBHSN_13', 'Cu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN8 = "INSERT INTO chitietmonan VALUES ('MCA_CBHSN_13', 'Cu_Na_1', 200);";
	private static final String INSERT_CHITIETMONAN9 = "INSERT INTO chitietmonan VALUES ('MCA_CBHSN_13', 'Qu_Ba_1', 2);";
	private static final String INSERT_CHITIETMONAN10 = "INSERT INTO chitietmonan VALUES ('MCA_CBHSN_13', 'Qu_Ta_2', 4);";
	private static final String INSERT_CHITIETMONAN11 = "INSERT INTO chitietmonan VALUES ('MCA_CBHSN_13', 'Xu_Su_1', 200);";
	private static final String INSERT_CHITIETMONAN12 = "INSERT INTO chitietmonan VALUES ('MCA_CCNTL_43', 'Ca_Ho_1', 500);";
	private static final String INSERT_CHITIETMONAN13 = "INSERT INTO chitietmonan VALUES ('MCA_CCNTL_43', 'Cu_To_1', 0.35);";
	private static final String INSERT_CHITIETMONAN14 = "INSERT INTO chitietmonan VALUES ('MCA_CCNTL_43', 'Qu_Ca_1', 2);";
	private static final String INSERT_CHITIETMONAN15 = "INSERT INTO chitietmonan VALUES ('MCA_CCNTL_43', 'Ra_Th_1', 8);";
	private static final String INSERT_CHITIETMONAN16 = "INSERT INTO chitietmonan VALUES ('MCA_CCRKQ_29', 'Ca_Ro_1', 2);";
	private static final String INSERT_CHITIETMONAN17 = "INSERT INTO chitietmonan VALUES ('MCA_CCRKQ_29', 'Cu_To_1', 0.25);";
	private static final String INSERT_CHITIETMONAN18 = "INSERT INTO chitietmonan VALUES ('MCA_CCRKQ_29', 'Qu_Kh_1', 2);";
	private static final String INSERT_CHITIETMONAN19 = "INSERT INTO chitietmonan VALUES ('MCA_CCRKQ_29', 'Ra_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN20 = "INSERT INTO chitietmonan VALUES ('MCA_CCRN_30', 'Hs_Cu_3', 300);";
	private static final String INSERT_CHITIETMONAN21 = "INSERT INTO chitietmonan VALUES ('MCA_CCRN_30', 'Qu_Ot_1', 1);";
	private static final String INSERT_CHITIETMONAN22 = "INSERT INTO chitietmonan VALUES ('MCA_CCRN_30', 'Ra_Nh_1', 300);";
	private static final String INSERT_CHITIETMONAN23 = "INSERT INTO chitietmonan VALUES ('MCA_CDCCBB_20', 'Ci_Ch_1', 200);";
	private static final String INSERT_CHITIETMONAN24 = "INSERT INTO chitietmonan VALUES ('MCA_CDCCBB_20', 'Cu_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN25 = "INSERT INTO chitietmonan VALUES ('MCA_CDCCBB_20', 'Qu_Ca_1', 2);";
	private static final String INSERT_CHITIETMONAN26 = "INSERT INTO chitietmonan VALUES ('MCA_CDCCBB_20', 'Th_Bo_2', 200);";
	private static final String INSERT_CHITIETMONAN27 = "INSERT INTO chitietmonan VALUES ('MCA_CGHNVT_17', 'Na_Do_1', 100);";
	private static final String INSERT_CHITIETMONAN28 = "INSERT INTO chitietmonan VALUES ('MCA_CGHNVT_17', 'Na_Ta_1', 50);";
	private static final String INSERT_CHITIETMONAN29 = "INSERT INTO chitietmonan VALUES ('MCA_CGHNVT_17', 'Nt_Rn_1', 100);";
	private static final String INSERT_CHITIETMONAN30 = "INSERT INTO chitietmonan VALUES ('MCA_CGHNVT_17', 'Th_Ga_2', 1);";
	private static final String INSERT_CHITIETMONAN31 = "INSERT INTO chitietmonan VALUES ('MCA_CGHNVT_17', 'Tr_Ga_1', 8);";
	private static final String INSERT_CHITIETMONAN32 = "INSERT INTO chitietmonan VALUES ('MCA_CGNX_39', 'Cu_Ca_1', 0.25);";
	private static final String INSERT_CHITIETMONAN33 = "INSERT INTO chitietmonan VALUES ('MCA_CGNX_39', 'Na_Ba_1', 100);";
	private static final String INSERT_CHITIETMONAN34 = "INSERT INTO chitietmonan VALUES ('MCA_CGNX_39', 'Ra_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN35 = "INSERT INTO chitietmonan VALUES ('MCA_CGNX_39', 'Ra_Xa_2', 2);";
	private static final String INSERT_CHITIETMONAN36 = "INSERT INTO chitietmonan VALUES ('MCA_CGNX_39', 'Th_Ga_4', 1);";
	private static final String INSERT_CHITIETMONAN37 = "INSERT INTO chitietmonan VALUES ('MCA_CMTNTV_38', 'Ci_Ma_1', 300);";
	private static final String INSERT_CHITIETMONAN38 = "INSERT INTO chitietmonan VALUES ('MCA_CMTNTV_38', 'Cu_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN39 = "INSERT INTO chitietmonan VALUES ('MCA_CMTNTV_38', 'Hs_To_1', 200);";
	private static final String INSERT_CHITIETMONAN40 = "INSERT INTO chitietmonan VALUES ('MCA_CMTNTV_38', 'Ra_Ha_1', 3);";
	private static final String INSERT_CHITIETMONAN41 = "INSERT INTO chitietmonan VALUES ('MCA_CSNDN_24', 'Cu_Ca_1', 0.5);";
	private static final String INSERT_CHITIETMONAN42 = "INSERT INTO chitietmonan VALUES ('MCA_CSNDN_24', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN43 = "INSERT INTO chitietmonan VALUES ('MCA_CSNDN_24', 'Da_Ng_1', 200);";
	private static final String INSERT_CHITIETMONAN44 = "INSERT INTO chitietmonan VALUES ('MCA_CSNDN_24', 'Ra_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN45 = "INSERT INTO chitietmonan VALUES ('MCA_CSNDN_24', 'Xu_Su_1', 300);";
	private static final String INSERT_CHITIETMONAN46 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Ca_Th_1', 500);";
	private static final String INSERT_CHITIETMONAN47 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Cu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN48 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Na_Ta_1', 50);";
	private static final String INSERT_CHITIETMONAN49 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Qu_Kh_1', 4);";
	private static final String INSERT_CHITIETMONAN50 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Th_He_1', 300);";
	private static final String INSERT_CHITIETMONAN51 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Tp_Bu_1', 200);";
	private static final String INSERT_CHITIETMONAN52 = "INSERT INTO chitietmonan VALUES ('MCA_KQH_7', 'Tr_Ga_1', 1);";
	private static final String INSERT_CHITIETMONAN53 = "INSERT INTO chitietmonan VALUES ('MCH_BRCM_5', 'Bt_Cu_1', 2);";
	private static final String INSERT_CHITIETMONAN54 = "INSERT INTO chitietmonan VALUES ('MCH_BRCM_5', 'Ra_Xa_1', 300);";
	private static final String INSERT_CHITIETMONAN55 = "INSERT INTO chitietmonan VALUES ('MCH_BRCM_5', 'Th_He_3', 400);";
	private static final String INSERT_CHITIETMONAN56 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Bt_Mo_1', 1);";
	private static final String INSERT_CHITIETMONAN57 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Cu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN58 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Cu_Ha_1', 3);";
	private static final String INSERT_CHITIETMONAN59 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Cu_Sa_1', 1);";
	private static final String INSERT_CHITIETMONAN60 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Hs_Cu_1', 1);";
	private static final String INSERT_CHITIETMONAN61 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Hs_Mu_1', 200);";
	private static final String INSERT_CHITIETMONAN62 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Hs_To_1', 200);";
	private static final String INSERT_CHITIETMONAN63 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Na_Ta_1', 50);";
	private static final String INSERT_CHITIETMONAN64 = "INSERT INTO chitietmonan VALUES ('MCH_CGHS_6', 'Ra_Xa_1', 300);";
	private static final String INSERT_CHITIETMONAN65 = "INSERT INTO chitietmonan VALUES ('MCH_CGJV_41', 'Cu_Ha_1', 10);";
	private static final String INSERT_CHITIETMONAN66 = "INSERT INTO chitietmonan VALUES ('MCH_CGJV_41', 'Na_Ro_1', 50);";
	private static final String INSERT_CHITIETMONAN67 = "INSERT INTO chitietmonan VALUES ('MCH_CGJV_41', 'Qu_Ot_1', 2);";
	private static final String INSERT_CHITIETMONAN68 = "INSERT INTO chitietmonan VALUES ('MCH_CGJV_41', 'Th_Ga_5', 4);";
	private static final String INSERT_CHITIETMONAN69 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Bt_Cu_1', 1);";
	private static final String INSERT_CHITIETMONAN70 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN71 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Cu_To_1', 0.25);";
	private static final String INSERT_CHITIETMONAN72 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Hs_Mu_1', 300);";
	private static final String INSERT_CHITIETMONAN73 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Ra_Th_1', 2);";
	private static final String INSERT_CHITIETMONAN74 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Ra_Xa_1', 150);";
	private static final String INSERT_CHITIETMONAN75 = "INSERT INTO chitietmonan VALUES ('MCH_CMTL_31', 'Tp_Bo_2', 0.5);";
	private static final String INSERT_CHITIETMONAN76 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN77 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Cu_To_1', 0.25);";
	private static final String INSERT_CHITIETMONAN78 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Qu_Ca_2', 4);";
	private static final String INSERT_CHITIETMONAN79 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Qu_Ot_1', 0.5);";
	private static final String INSERT_CHITIETMONAN80 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Qu_Ot_3', 2);";
	private static final String INSERT_CHITIETMONAN81 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Ra_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN82 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Th_He_1', 200);";
	private static final String INSERT_CHITIETMONAN83 = "INSERT INTO chitietmonan VALUES ('MCH_CTDT_36', 'Tp_Bo_2', 0.5);";
	private static final String INSERT_CHITIETMONAN84 = "INSERT INTO chitietmonan VALUES ('MCH_DHDTXC_4', 'Da_Hu_1', 3);";
	private static final String INSERT_CHITIETMONAN85 = "INSERT INTO chitietmonan VALUES ('MCH_DHDTXC_4', 'Qu_Ca_1', 2);";
	private static final String INSERT_CHITIETMONAN86 = "INSERT INTO chitietmonan VALUES ('MCH_DHDTXC_4', 'Th_He_2', 200);";
	private static final String INSERT_CHITIETMONAN87 = "INSERT INTO chitietmonan VALUES ('MCH_GCLC_32', 'Cu_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN88 = "INSERT INTO chitietmonan VALUES ('MCH_GCLC_32', 'Ra_La_1', 4);";
	private static final String INSERT_CHITIETMONAN89 = "INSERT INTO chitietmonan VALUES ('MCH_GCLC_32', 'Ra_Xa_1', 150);";
	private static final String INSERT_CHITIETMONAN90 = "INSERT INTO chitietmonan VALUES ('MCH_GCLC_32', 'Th_Ga_3', 500);";
	private static final String INSERT_CHITIETMONAN91 = "INSERT INTO chitietmonan VALUES ('MCH_OHCG_26', 'Cu_Gu_1', 1);";
	private static final String INSERT_CHITIETMONAN92 = "INSERT INTO chitietmonan VALUES ('MCH_OHCG_26', 'Ra_Ha_1', 3);";
	private static final String INSERT_CHITIETMONAN93 = "INSERT INTO chitietmonan VALUES ('MCH_OHCG_26', 'Tp_Bo_1', 0.5);";
	private static final String INSERT_CHITIETMONAN94 = "INSERT INTO chitietmonan VALUES ('MCH_OHCG_26', 'Tr_Ga_1', 1);";
	private static final String INSERT_CHITIETMONAN95 = "INSERT INTO chitietmonan VALUES ('MCH_OHCG_26', 'Xu_Oc_1', 4);";
	private static final String INSERT_CHITIETMONAN96 = "INSERT INTO chitietmonan VALUES ('MHA_MODTHXM_28', 'Hs_Mu_2', 500);";
	private static final String INSERT_CHITIETMONAN97 = "INSERT INTO chitietmonan VALUES ('MHA_MODTHXM_28', 'Qu_Me_1', 150);";
	private static final String INSERT_CHITIETMONAN98 = "INSERT INTO chitietmonan VALUES ('MHA_MODTHXM_28', 'Th_He_1', 350);";
	private static final String INSERT_CHITIETMONAN99 = "INSERT INTO chitietmonan VALUES ('MHA_NHNRB_2', 'Cu_Ca_1', 0.5);";
	private static final String INSERT_CHITIETMONAN100 = "INSERT INTO chitietmonan VALUES ('MHA_NHNRB_2', 'Na_Ba_1', 60);";
	private static final String INSERT_CHITIETMONAN101 = "INSERT INTO chitietmonan VALUES ('MHA_NHNRB_2', 'Ra_Ro_1', 1);";
	private static final String INSERT_CHITIETMONAN102 = "INSERT INTO chitietmonan VALUES ('MHA_NHNRB_2', 'Th_He_1', 100);";
	private static final String INSERT_CHITIETMONAN103 = "INSERT INTO chitietmonan VALUES ('MMA_BKRM_14', 'Cu_Gu_1', 0.35);";
	private static final String INSERT_CHITIETMONAN104 = "INSERT INTO chitietmonan VALUES ('MMA_BKRM_14', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN105 = "INSERT INTO chitietmonan VALUES ('MMA_BKRM_14', 'Th_Bo_2', 400);";
	private static final String INSERT_CHITIETMONAN106 = "INSERT INTO chitietmonan VALUES ('MMA_CKDH_40', 'Ca_Ro_1', 4);";
	private static final String INSERT_CHITIETMONAN107 = "INSERT INTO chitietmonan VALUES ('MMA_CKDH_40', 'Cu_To_1', 0.35);";
	private static final String INSERT_CHITIETMONAN108 = "INSERT INTO chitietmonan VALUES ('MMA_CKDH_40', 'Nu_Du_1', 1);";
	private static final String INSERT_CHITIETMONAN109 = "INSERT INTO chitietmonan VALUES ('MMA_CKDH_40', 'Qu_Du_2', 2);";
	private static final String INSERT_CHITIETMONAN110 = "INSERT INTO chitietmonan VALUES ('MMA_CKDH_40', 'Qu_Ot_3', 3);";
	private static final String INSERT_CHITIETMONAN111 = "INSERT INTO chitietmonan VALUES ('MMA_CKDH_40', 'Ra_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN112 = "INSERT INTO chitietmonan VALUES ('MMA_DHTKS_25', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN113 = "INSERT INTO chitietmonan VALUES ('MMA_DHTKS_25', 'Da_Hu_2', 5);";
	private static final String INSERT_CHITIETMONAN114 = "INSERT INTO chitietmonan VALUES ('MMA_DHTKS_25', 'Ra_Ha_1', 3);";
	private static final String INSERT_CHITIETMONAN115 = "INSERT INTO chitietmonan VALUES ('MMA_DHTKS_25', 'Xu_Su_1', 400);";
	private static final String INSERT_CHITIETMONAN116 = "INSERT INTO chitietmonan VALUES ('MMA_GOXD_1', 'Cu_Ri_1', 1);";
	private static final String INSERT_CHITIETMONAN117 = "INSERT INTO chitietmonan VALUES ('MMA_GOXD_1', 'Th_Ga_1', 300);";
	private static final String INSERT_CHITIETMONAN118 = "INSERT INTO chitietmonan VALUES ('MMA_THKT_21', 'Cu_Ha_2', 0.35);";
	private static final String INSERT_CHITIETMONAN119 = "INSERT INTO chitietmonan VALUES ('MMA_THKT_21', 'Th_He_3', 200);";
	private static final String INSERT_CHITIETMONAN120 = "INSERT INTO chitietmonan VALUES ('MMA_TKO_19', 'Cu_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN121 = "INSERT INTO chitietmonan VALUES ('MMA_TKO_19', 'Hs_To_1', 300);";
	private static final String INSERT_CHITIETMONAN122 = "INSERT INTO chitietmonan VALUES ('MMA_TKO_19', 'Nu_Du_1', 0.35);";
	private static final String INSERT_CHITIETMONAN123 = "INSERT INTO chitietmonan VALUES ('MMA_TKO_19', 'Qu_Ot_3', 5);";
	private static final String INSERT_CHITIETMONAN124 = "INSERT INTO chitietmonan VALUES ('MNU_BCCN_12', 'Ci_Me_1', 1);";
	private static final String INSERT_CHITIETMONAN125 = "INSERT INTO chitietmonan VALUES ('MNU_BCCN_12', 'Cu_Gu_1', 0.35);";
	private static final String INSERT_CHITIETMONAN126 = "INSERT INTO chitietmonan VALUES ('MNU_BCCN_12', 'Th_Bo_1', 200);";
	private static final String INSERT_CHITIETMONAN127 = "INSERT INTO chitietmonan VALUES ('MNU_SNT_8', 'Cu_Gu_1', 0.35);";
	private static final String INSERT_CHITIETMONAN128 = "INSERT INTO chitietmonan VALUES ('MNU_SNT_8', 'Cu_Ha_2', 1);";
	private static final String INSERT_CHITIETMONAN129 = "INSERT INTO chitietmonan VALUES ('MNU_SNT_8', 'Cu_To_1', 0.35);";
	private static final String INSERT_CHITIETMONAN130 = "INSERT INTO chitietmonan VALUES ('MNU_SNT_8', 'Qu_Ta_1', 1);";
	private static final String INSERT_CHITIETMONAN131 = "INSERT INTO chitietmonan VALUES ('MNU_SNT_8', 'Xu_Su_1', 1000);";
	private static final String INSERT_CHITIETMONAN132 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Cu_To_1', 0.25);";
	private static final String INSERT_CHITIETMONAN133 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Hs_To_1', 400);";
	private static final String INSERT_CHITIETMONAN134 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Qu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN135 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Qu_Ch_1', 0.25);";
	private static final String INSERT_CHITIETMONAN136 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Qu_Du_1', 0.5);";
	private static final String INSERT_CHITIETMONAN137 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Qu_Ot_1', 1);";
	private static final String INSERT_CHITIETMONAN138 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Qu_Ot_3', 3);";
	private static final String INSERT_CHITIETMONAN139 = "INSERT INTO chitietmonan VALUES ('MNU_TNMO_27', 'Ra_Xa_1', 200);";
	private static final String INSERT_CHITIETMONAN140 = "INSERT INTO chitietmonan VALUES ('MTR_BCXTDH_37', 'Ci_Bo_1', 300);";
	private static final String INSERT_CHITIETMONAN141 = "INSERT INTO chitietmonan VALUES ('MTR_BCXTDH_37', 'Cu_Gu_1', 0.35);";
	private static final String INSERT_CHITIETMONAN142 = "INSERT INTO chitietmonan VALUES ('MTR_BCXTDH_37', 'Cu_Ha_2', 1);";
	private static final String INSERT_CHITIETMONAN143 = "INSERT INTO chitietmonan VALUES ('MTR_BCXTDH_37', 'Hs_To_1', 200);";
	private static final String INSERT_CHITIETMONAN144 = "INSERT INTO chitietmonan VALUES ('MTR_BCXTDH_37', 'Tp_Bo_2', 0.35);";
	private static final String INSERT_CHITIETMONAN145 = "INSERT INTO chitietmonan VALUES ('MTR_BTTDK_33', 'Cu_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN146 = "INSERT INTO chitietmonan VALUES ('MTR_BTTDK_33', 'Cu_Ki_1', 100);";
	private static final String INSERT_CHITIETMONAN147 = "INSERT INTO chitietmonan VALUES ('MTR_BTTDK_33', 'Da_Ph_1', 50);";
	private static final String INSERT_CHITIETMONAN148 = "INSERT INTO chitietmonan VALUES ('MTR_BTTDK_33', 'Qu_Ot_1', 1);";
	private static final String INSERT_CHITIETMONAN149 = "INSERT INTO chitietmonan VALUES ('MTR_BTTDK_33', 'Ra_Hu_1', 50);";
	private static final String INSERT_CHITIETMONAN150 = "INSERT INTO chitietmonan VALUES ('MTR_SLTXX_42', 'Cu_Ha_2', 1);";
	private static final String INSERT_CHITIETMONAN151 = "INSERT INTO chitietmonan VALUES ('MTR_SLTXX_42', 'Qu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN152 = "INSERT INTO chitietmonan VALUES ('MTR_SLTXX_42', 'Qu_Ot_3', 2);";
	private static final String INSERT_CHITIETMONAN153 = "INSERT INTO chitietmonan VALUES ('MTR_SLTXX_42', 'Ra_Ca_2', 2);";
	private static final String INSERT_CHITIETMONAN154 = "INSERT INTO chitietmonan VALUES ('MTR_SLTXX_42', 'Th_Xu_1', 2);";
	private static final String INSERT_CHITIETMONAN155 = "INSERT INTO chitietmonan VALUES ('MXA_BHXHQ_15', 'Cu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN156 = "INSERT INTO chitietmonan VALUES ('MXA_BHXHQ_15', 'Cu_Ha_2', 0.5);";
	private static final String INSERT_CHITIETMONAN157 = "INSERT INTO chitietmonan VALUES ('MXA_BHXHQ_15', 'Na_Do_1', 50);";
	private static final String INSERT_CHITIETMONAN158 = "INSERT INTO chitietmonan VALUES ('MXA_BHXHQ_15', 'Ra_He_1', 200);";
	private static final String INSERT_CHITIETMONAN159 = "INSERT INTO chitietmonan VALUES ('MXA_BHXHQ_15', 'Th_He_4', 150);";
	private static final String INSERT_CHITIETMONAN160 = "INSERT INTO chitietmonan VALUES ('MXA_BXT_16', 'Cu_Ha_1', 1);";
	private static final String INSERT_CHITIETMONAN161 = "INSERT INTO chitietmonan VALUES ('MXA_BXT_16', 'Cu_Ha_2', 0.35);";
	private static final String INSERT_CHITIETMONAN162 = "INSERT INTO chitietmonan VALUES ('MXA_BXT_16', 'Cu_To_1', 1);";
	private static final String INSERT_CHITIETMONAN163 = "INSERT INTO chitietmonan VALUES ('MXA_BXT_16', 'Qu_Ot_2', 0.25);";
	private static final String INSERT_CHITIETMONAN164 = "INSERT INTO chitietmonan VALUES ('MXA_BXT_16', 'Th_Bo_1', 150);";
	private static final String INSERT_CHITIETMONAN165 = "INSERT INTO chitietmonan VALUES ('MXA_DTXCC_11', 'Ci_Ch_1', 300);";
	private static final String INSERT_CHITIETMONAN166 = "INSERT INTO chitietmonan VALUES ('MXA_DTXCC_11', 'Cu_Ha_1', 5);";
	private static final String INSERT_CHITIETMONAN167 = "INSERT INTO chitietmonan VALUES ('MXA_DTXCC_11', 'Nt_Dt_1', 200);";
	private static final String INSERT_CHITIETMONAN168 = "INSERT INTO chitietmonan VALUES ('MXA_EXTT_35', 'Cu_Gu_1', 1);";
	private static final String INSERT_CHITIETMONAN169 = "INSERT INTO chitietmonan VALUES ('MXA_EXTT_35', 'Cu_Ha_2', 1);";
	private static final String INSERT_CHITIETMONAN170 = "INSERT INTO chitietmonan VALUES ('MXA_EXTT_35', 'Ra_Ti_1', 2);";
	private static final String INSERT_CHITIETMONAN171 = "INSERT INTO chitietmonan VALUES ('MXA_EXTT_35', 'Th_Ec_1', 4);";
	private static final String INSERT_CHITIETMONAN172 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Cu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN173 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Cu_Ha_2', 0.5);";
	private static final String INSERT_CHITIETMONAN174 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Cu_To_1', 0.35);";
	private static final String INSERT_CHITIETMONAN175 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Hs_So_1', 200);";
	private static final String INSERT_CHITIETMONAN176 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Na_Do_1', 200);";
	private static final String INSERT_CHITIETMONAN177 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Ra_Ca_1', 150);";
	private static final String INSERT_CHITIETMONAN178 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Ra_Ha_1', 3);";
	private static final String INSERT_CHITIETMONAN179 = "INSERT INTO chitietmonan VALUES ('MXA_MGXADTV_22', 'Tp_Mi_2', 4);";
	private static final String INSERT_CHITIETMONAN180 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Cu_Ca_1', 0.5);";
	private static final String INSERT_CHITIETMONAN181 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Cu_Gu_1', 0.35);";
	private static final String INSERT_CHITIETMONAN182 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Cu_Ha_2', 1);";
	private static final String INSERT_CHITIETMONAN183 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Hs_Cu_1', 1);";
	private static final String INSERT_CHITIETMONAN184 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Hs_Cu_2', 2);";
	private static final String INSERT_CHITIETMONAN185 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Qu_Ot_1', 1);";
	private static final String INSERT_CHITIETMONAN186 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Ra_Ca_1', 200);";
	private static final String INSERT_CHITIETMONAN187 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Ra_Ha_1', 6);";
	private static final String INSERT_CHITIETMONAN188 = "INSERT INTO chitietmonan VALUES ('MXA_MXC_10', 'Tp_Mi_1', 2);";
	private static final String INSERT_CHITIETMONAN189 = "INSERT INTO chitietmonan VALUES ('MXA_MXDC_23', 'Ci_Ch_1', 250);";
	private static final String INSERT_CHITIETMONAN190 = "INSERT INTO chitietmonan VALUES ('MXA_MXDC_23', 'Hs_Mu_1', 300);";
	private static final String INSERT_CHITIETMONAN191 = "INSERT INTO chitietmonan VALUES ('MXA_MXDC_23', 'Qu_Ca_1', 1);";
	private static final String INSERT_CHITIETMONAN192 = "INSERT INTO chitietmonan VALUES ('MXA_MXDC_23', 'Ra_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN193 = "INSERT INTO chitietmonan VALUES ('MXA_SSXT_3', 'Qu_Su_1', 1);";
	private static final String INSERT_CHITIETMONAN194 = "INSERT INTO chitietmonan VALUES ('MXA_SSXT_3', 'Tr_Vi_1', 1);";
	private static final String INSERT_CHITIETMONAN195 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Cu_Ha_1', 2);";
	private static final String INSERT_CHITIETMONAN196 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Cu_Ha_2', 0.5);";
	private static final String INSERT_CHITIETMONAN197 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Da_Tu_1', 250);";
	private static final String INSERT_CHITIETMONAN198 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Hs_To_1', 500);";
	private static final String INSERT_CHITIETMONAN199 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Na_Ta_1', 100);";
	private static final String INSERT_CHITIETMONAN200 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Qu_Ca_1', 3);";
	private static final String INSERT_CHITIETMONAN201 = "INSERT INTO chitietmonan VALUES ('MXA_TXDT_34', 'Ra_Ca_2', 4);";

	// quyen han
	private static final String INSERT_QUYENHAN1 = "INSERT INTO quyenhan VALUES ('NDCC', 'NgÆ°ï¿½?i dĂ¹ng cáº¥p cao');";
	private static final String INSERT_QUYENHAN2 = "INSERT INTO quyenhan VALUES ('NDTT', 'NgÆ°ï¿½?i dĂ¹ng thĂ´ng thÆ°ï¿½?ng');";
	private static final String INSERT_QUYENHAN3 = "INSERT INTO quyenhan VALUES ('QTCC', 'Quáº£n trá»‹ cáº¥p cao');";
	private static final String INSERT_QUYENHAN4 = "INSERT INTO quyenhan VALUES ('QTTT', 'Quáº£n trá»‹ thĂ´ng thÆ°ï¿½?ng');";
	// Nhac Nho test database
	private static final String INSERT_NHACNHO1 = "INSERT INTO NhacNho VALUES ('2',8,0,'ï¿½?i chá»£');";
	private static final String INSERT_NHACNHO2 = "INSERT INTO NhacNho VALUES ('2',10,0,'Náº¥u Äƒn');";
	private static final String INSERT_NHACNHO3 = "INSERT INTO NhacNho VALUES ('3',8,0,'Rá»­a chĂ©n');";
	private static final String INSERT_NHACNHO4 = "INSERT INTO NhacNho VALUES ('3',10,0,'Giáº·t Ä‘á»“');";
	// nguoi dung
	private static final String INSERT_NGUOIDUNG1 = "INSERT INTO nguoidung VALUES ('user', 'user', '', '', '', 'NDCC');";
	private static final String INSERT_NGUOIDUNG2 = "INSERT INTO nguoidung VALUES ('users', 'users', '', '', '', 'QTCC');";
	private static final String INSERT_NGUOIDUNG3 = "INSERT INTO nguoidung VALUES ('admin', 'admin', '','', '', 'QTCC');";

	public static class DatabaseHelper extends SQLiteOpenHelper 
	{

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) 
		{
			
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			
			db.execSQL(CREATE_LOAIBENH_TABLE);
			db.execSQL(CREATE_BENH_TABLE);
			//db.execSQL(CREATE_LOAICHITIET_TABLE);
			db.execSQL(CREATE_CHITIET_TABLE);
			db.execSQL(CREATE_CHITIETBENH_TABLE);
			db.execSQL(CREATE_QUYENHAN_TABLE);
			db.execSQL(CREATE_NGUOIDUNG_TABLE);
			//db.execSQL(CREATE_THUCDONTRONGTUAN_TABLE);
			//db.execSQL(CREATE_PHATSINH_TABLE);
			//db.execSQL(CREATE_NGUYENLIEUDICHO_TABLE);
			//db.execSQL(CREATE_NHACNHO_TABLE);

			// insert loai BENH
			db.execSQL(INSERT_LOAIBENH1);
			db.execSQL(INSERT_LOAIBENH2);
			//...
			
			// insert BENH
			db.execSQL(INSERT_BENH1);
			// insert loai mon an
			db.execSQL(INSERT_LOAIMONAN1);
			db.execSQL(INSERT_LOAIMONAN2);
			db.execSQL(INSERT_LOAIMONAN3);
			db.execSQL(INSERT_LOAIMONAN4);
			db.execSQL(INSERT_LOAIMONAN5);
			db.execSQL(INSERT_LOAIMONAN6);
			db.execSQL(INSERT_LOAIMONAN7);
			db.execSQL(INSERT_LOAIMONAN8);

			// insert mon an
			db.execSQL(INSERT_BENH1);
			db.execSQL(INSERT_MONAN2);
			db.execSQL(INSERT_MONAN3);
			db.execSQL(INSERT_MONAN4);
			db.execSQL(INSERT_MONAN5);
			db.execSQL(INSERT_MONAN6);
			db.execSQL(INSERT_MONAN7);
			db.execSQL(INSERT_MONAN8);
			db.execSQL(INSERT_MONAN9);
			db.execSQL(INSERT_MONAN10);
			db.execSQL(INSERT_MONAN11);
			db.execSQL(INSERT_MONAN12);
			db.execSQL(INSERT_MONAN13);
			db.execSQL(INSERT_MONAN14);
			db.execSQL(INSERT_MONAN15);
			db.execSQL(INSERT_MONAN16);
			db.execSQL(INSERT_MONAN17);
			db.execSQL(INSERT_MONAN18);
			db.execSQL(INSERT_MONAN19);
			db.execSQL(INSERT_MONAN20);
			db.execSQL(INSERT_MONAN21);
			db.execSQL(INSERT_MONAN22);
			db.execSQL(INSERT_MONAN23);
			db.execSQL(INSERT_MONAN24);
			db.execSQL(INSERT_MONAN25);
			db.execSQL(INSERT_MONAN26);
			db.execSQL(INSERT_MONAN27);
			db.execSQL(INSERT_MONAN28);
			db.execSQL(INSERT_MONAN29);
			db.execSQL(INSERT_MONAN30);
			db.execSQL(INSERT_MONAN31);
			db.execSQL(INSERT_MONAN32);
			db.execSQL(INSERT_MONAN33);
			db.execSQL(INSERT_MONAN34);
			db.execSQL(INSERT_MONAN35);
			db.execSQL(INSERT_MONAN36);
			db.execSQL(INSERT_MONAN37);
			db.execSQL(INSERT_MONAN38);
			db.execSQL(INSERT_MONAN39);
			db.execSQL(INSERT_MONAN40);
			db.execSQL(INSERT_MONAN41);
			db.execSQL(INSERT_MONAN42);
			db.execSQL(INSERT_MONAN43);

			// insert loai nguyen lieu
			db.execSQL(INSERT_LOAINGUYENLIEU1);
			db.execSQL(INSERT_LOAINGUYENLIEU2);
			db.execSQL(INSERT_LOAINGUYENLIEU3);
			db.execSQL(INSERT_LOAINGUYENLIEU4);
			db.execSQL(INSERT_LOAINGUYENLIEU5);
			db.execSQL(INSERT_LOAINGUYENLIEU6);
			db.execSQL(INSERT_LOAINGUYENLIEU7);
			db.execSQL(INSERT_LOAINGUYENLIEU8);
			db.execSQL(INSERT_LOAINGUYENLIEU9);
			db.execSQL(INSERT_LOAINGUYENLIEU10);
			db.execSQL(INSERT_LOAINGUYENLIEU11);
			db.execSQL(INSERT_LOAINGUYENLIEU12);
			db.execSQL(INSERT_LOAINGUYENLIEU13);
			db.execSQL(INSERT_LOAINGUYENLIEU14);
			db.execSQL(INSERT_LOAINGUYENLIEU15);
			db.execSQL(INSERT_LOAINGUYENLIEU16);

			// insert nguyen lieu
			db.execSQL(INSERT_NGUYENLIEU1);
			db.execSQL(INSERT_NGUYENLIEU2);
			db.execSQL(INSERT_NGUYENLIEU3);
			db.execSQL(INSERT_NGUYENLIEU4);
			db.execSQL(INSERT_NGUYENLIEU5);
			db.execSQL(INSERT_NGUYENLIEU6);
			db.execSQL(INSERT_NGUYENLIEU7);
			db.execSQL(INSERT_NGUYENLIEU8);
			db.execSQL(INSERT_NGUYENLIEU9);
			db.execSQL(INSERT_NGUYENLIEU10);
			db.execSQL(INSERT_NGUYENLIEU11);
			db.execSQL(INSERT_NGUYENLIEU12);
			db.execSQL(INSERT_NGUYENLIEU13);
			db.execSQL(INSERT_NGUYENLIEU14);
			db.execSQL(INSERT_NGUYENLIEU15);
			db.execSQL(INSERT_NGUYENLIEU16);
			db.execSQL(INSERT_NGUYENLIEU17);
			db.execSQL(INSERT_NGUYENLIEU18);
			db.execSQL(INSERT_NGUYENLIEU19);
			db.execSQL(INSERT_NGUYENLIEU20);
			db.execSQL(INSERT_NGUYENLIEU21);
			db.execSQL(INSERT_NGUYENLIEU22);
			db.execSQL(INSERT_NGUYENLIEU23);
			db.execSQL(INSERT_NGUYENLIEU24);
			db.execSQL(INSERT_NGUYENLIEU25);
			db.execSQL(INSERT_NGUYENLIEU26);
			db.execSQL(INSERT_NGUYENLIEU27);
			db.execSQL(INSERT_NGUYENLIEU28);
			db.execSQL(INSERT_NGUYENLIEU29);
			db.execSQL(INSERT_NGUYENLIEU30);
			db.execSQL(INSERT_NGUYENLIEU31);
			db.execSQL(INSERT_NGUYENLIEU32);
			db.execSQL(INSERT_NGUYENLIEU33);
			db.execSQL(INSERT_NGUYENLIEU34);
			db.execSQL(INSERT_NGUYENLIEU35);
			db.execSQL(INSERT_NGUYENLIEU36);
			db.execSQL(INSERT_NGUYENLIEU37);
			db.execSQL(INSERT_NGUYENLIEU38);
			db.execSQL(INSERT_NGUYENLIEU39);
			db.execSQL(INSERT_NGUYENLIEU40);
			db.execSQL(INSERT_NGUYENLIEU41);
			db.execSQL(INSERT_NGUYENLIEU42);
			db.execSQL(INSERT_NGUYENLIEU43);
			db.execSQL(INSERT_NGUYENLIEU44);
			db.execSQL(INSERT_NGUYENLIEU45);
			db.execSQL(INSERT_NGUYENLIEU46);
			db.execSQL(INSERT_NGUYENLIEU47);
			db.execSQL(INSERT_NGUYENLIEU48);
			db.execSQL(INSERT_NGUYENLIEU49);
			db.execSQL(INSERT_NGUYENLIEU50);
			db.execSQL(INSERT_NGUYENLIEU51);
			db.execSQL(INSERT_NGUYENLIEU52);
			db.execSQL(INSERT_NGUYENLIEU53);
			db.execSQL(INSERT_NGUYENLIEU54);
			db.execSQL(INSERT_NGUYENLIEU55);
			db.execSQL(INSERT_NGUYENLIEU56);
			db.execSQL(INSERT_NGUYENLIEU57);
			db.execSQL(INSERT_NGUYENLIEU58);
			db.execSQL(INSERT_NGUYENLIEU59);
			db.execSQL(INSERT_NGUYENLIEU60);
			db.execSQL(INSERT_NGUYENLIEU61);
			db.execSQL(INSERT_NGUYENLIEU62);
			db.execSQL(INSERT_NGUYENLIEU63);
			db.execSQL(INSERT_NGUYENLIEU64);
			db.execSQL(INSERT_NGUYENLIEU65);
			db.execSQL(INSERT_NGUYENLIEU66);
			db.execSQL(INSERT_NGUYENLIEU67);
			db.execSQL(INSERT_NGUYENLIEU68);
			db.execSQL(INSERT_NGUYENLIEU69);
			db.execSQL(INSERT_NGUYENLIEU70);
			db.execSQL(INSERT_NGUYENLIEU71);
			db.execSQL(INSERT_NGUYENLIEU72);
			db.execSQL(INSERT_NGUYENLIEU73);
			db.execSQL(INSERT_NGUYENLIEU74);
			db.execSQL(INSERT_NGUYENLIEU75);
			db.execSQL(INSERT_NGUYENLIEU76);
			db.execSQL(INSERT_NGUYENLIEU77);
			db.execSQL(INSERT_NGUYENLIEU78);
			db.execSQL(INSERT_NGUYENLIEU79);
			db.execSQL(INSERT_NGUYENLIEU80);
			db.execSQL(INSERT_NGUYENLIEU81);
			db.execSQL(INSERT_NGUYENLIEU82);
			db.execSQL(INSERT_NGUYENLIEU83);
			db.execSQL(INSERT_NGUYENLIEU84);
			db.execSQL(INSERT_NGUYENLIEU85);
			db.execSQL(INSERT_NGUYENLIEU86);
			db.execSQL(INSERT_NGUYENLIEU87);

			// insert chi tiet mon an
			db.execSQL(INSERT_CHITIETMONAN1);
			db.execSQL(INSERT_CHITIETMONAN2);
			db.execSQL(INSERT_CHITIETMONAN3);
			db.execSQL(INSERT_CHITIETMONAN4);
			db.execSQL(INSERT_CHITIETMONAN5);
			db.execSQL(INSERT_CHITIETMONAN6);
			db.execSQL(INSERT_CHITIETMONAN7);
			db.execSQL(INSERT_CHITIETMONAN8);
			db.execSQL(INSERT_CHITIETMONAN9);
			db.execSQL(INSERT_CHITIETMONAN10);
			db.execSQL(INSERT_CHITIETMONAN11);
			db.execSQL(INSERT_CHITIETMONAN12);
			db.execSQL(INSERT_CHITIETMONAN13);
			db.execSQL(INSERT_CHITIETMONAN14);
			db.execSQL(INSERT_CHITIETMONAN15);
			db.execSQL(INSERT_CHITIETMONAN16);
			db.execSQL(INSERT_CHITIETMONAN17);
			db.execSQL(INSERT_CHITIETMONAN18);
			db.execSQL(INSERT_CHITIETMONAN19);
			db.execSQL(INSERT_CHITIETMONAN20);
			db.execSQL(INSERT_CHITIETMONAN21);
			db.execSQL(INSERT_CHITIETMONAN22);
			db.execSQL(INSERT_CHITIETMONAN23);
			db.execSQL(INSERT_CHITIETMONAN24);
			db.execSQL(INSERT_CHITIETMONAN25);
			db.execSQL(INSERT_CHITIETMONAN26);
			db.execSQL(INSERT_CHITIETMONAN27);
			db.execSQL(INSERT_CHITIETMONAN28);
			db.execSQL(INSERT_CHITIETMONAN29);
			db.execSQL(INSERT_CHITIETMONAN30);
			db.execSQL(INSERT_CHITIETMONAN31);
			db.execSQL(INSERT_CHITIETMONAN32);
			db.execSQL(INSERT_CHITIETMONAN33);
			db.execSQL(INSERT_CHITIETMONAN34);
			db.execSQL(INSERT_CHITIETMONAN35);
			db.execSQL(INSERT_CHITIETMONAN36);
			db.execSQL(INSERT_CHITIETMONAN37);
			db.execSQL(INSERT_CHITIETMONAN38);
			db.execSQL(INSERT_CHITIETMONAN39);
			db.execSQL(INSERT_CHITIETMONAN40);
			db.execSQL(INSERT_CHITIETMONAN41);
			db.execSQL(INSERT_CHITIETMONAN42);
			db.execSQL(INSERT_CHITIETMONAN43);
			db.execSQL(INSERT_CHITIETMONAN44);
			db.execSQL(INSERT_CHITIETMONAN45);
			db.execSQL(INSERT_CHITIETMONAN46);
			db.execSQL(INSERT_CHITIETMONAN47);
			db.execSQL(INSERT_CHITIETMONAN48);
			db.execSQL(INSERT_CHITIETMONAN49);
			db.execSQL(INSERT_CHITIETMONAN50);
			db.execSQL(INSERT_CHITIETMONAN51);
			db.execSQL(INSERT_CHITIETMONAN52);
			db.execSQL(INSERT_CHITIETMONAN53);
			db.execSQL(INSERT_CHITIETMONAN54);
			db.execSQL(INSERT_CHITIETMONAN55);
			db.execSQL(INSERT_CHITIETMONAN56);
			db.execSQL(INSERT_CHITIETMONAN57);
			db.execSQL(INSERT_CHITIETMONAN58);
			db.execSQL(INSERT_CHITIETMONAN59);
			db.execSQL(INSERT_CHITIETMONAN60);
			db.execSQL(INSERT_CHITIETMONAN61);
			db.execSQL(INSERT_CHITIETMONAN62);
			db.execSQL(INSERT_CHITIETMONAN63);
			db.execSQL(INSERT_CHITIETMONAN64);
			db.execSQL(INSERT_CHITIETMONAN65);
			db.execSQL(INSERT_CHITIETMONAN66);
			db.execSQL(INSERT_CHITIETMONAN67);
			db.execSQL(INSERT_CHITIETMONAN68);
			db.execSQL(INSERT_CHITIETMONAN69);
			db.execSQL(INSERT_CHITIETMONAN70);
			db.execSQL(INSERT_CHITIETMONAN71);
			db.execSQL(INSERT_CHITIETMONAN72);
			db.execSQL(INSERT_CHITIETMONAN73);
			db.execSQL(INSERT_CHITIETMONAN74);
			db.execSQL(INSERT_CHITIETMONAN75);
			db.execSQL(INSERT_CHITIETMONAN76);
			db.execSQL(INSERT_CHITIETMONAN77);
			db.execSQL(INSERT_CHITIETMONAN78);
			db.execSQL(INSERT_CHITIETMONAN79);
			db.execSQL(INSERT_CHITIETMONAN80);
			db.execSQL(INSERT_CHITIETMONAN81);
			db.execSQL(INSERT_CHITIETMONAN82);
			db.execSQL(INSERT_CHITIETMONAN83);
			db.execSQL(INSERT_CHITIETMONAN84);
			db.execSQL(INSERT_CHITIETMONAN85);
			db.execSQL(INSERT_CHITIETMONAN86);
			db.execSQL(INSERT_CHITIETMONAN87);
			db.execSQL(INSERT_CHITIETMONAN88);
			db.execSQL(INSERT_CHITIETMONAN89);
			db.execSQL(INSERT_CHITIETMONAN90);
			db.execSQL(INSERT_CHITIETMONAN91);
			db.execSQL(INSERT_CHITIETMONAN92);
			db.execSQL(INSERT_CHITIETMONAN93);
			db.execSQL(INSERT_CHITIETMONAN94);
			db.execSQL(INSERT_CHITIETMONAN95);
			db.execSQL(INSERT_CHITIETMONAN96);
			db.execSQL(INSERT_CHITIETMONAN97);
			db.execSQL(INSERT_CHITIETMONAN98);
			db.execSQL(INSERT_CHITIETMONAN99);
			db.execSQL(INSERT_CHITIETMONAN100);
			db.execSQL(INSERT_CHITIETMONAN101);
			db.execSQL(INSERT_CHITIETMONAN102);
			db.execSQL(INSERT_CHITIETMONAN103);
			db.execSQL(INSERT_CHITIETMONAN104);
			db.execSQL(INSERT_CHITIETMONAN105);
			db.execSQL(INSERT_CHITIETMONAN106);
			db.execSQL(INSERT_CHITIETMONAN107);
			db.execSQL(INSERT_CHITIETMONAN108);
			db.execSQL(INSERT_CHITIETMONAN109);
			db.execSQL(INSERT_CHITIETMONAN110);
			db.execSQL(INSERT_CHITIETMONAN111);
			db.execSQL(INSERT_CHITIETMONAN112);
			db.execSQL(INSERT_CHITIETMONAN113);
			db.execSQL(INSERT_CHITIETMONAN114);
			db.execSQL(INSERT_CHITIETMONAN115);
			db.execSQL(INSERT_CHITIETMONAN116);
			db.execSQL(INSERT_CHITIETMONAN117);
			db.execSQL(INSERT_CHITIETMONAN118);
			db.execSQL(INSERT_CHITIETMONAN119);
			db.execSQL(INSERT_CHITIETMONAN120);
			db.execSQL(INSERT_CHITIETMONAN121);
			db.execSQL(INSERT_CHITIETMONAN122);
			db.execSQL(INSERT_CHITIETMONAN123);
			db.execSQL(INSERT_CHITIETMONAN124);
			db.execSQL(INSERT_CHITIETMONAN125);
			db.execSQL(INSERT_CHITIETMONAN126);
			db.execSQL(INSERT_CHITIETMONAN127);
			db.execSQL(INSERT_CHITIETMONAN128);
			db.execSQL(INSERT_CHITIETMONAN129);
			db.execSQL(INSERT_CHITIETMONAN130);
			db.execSQL(INSERT_CHITIETMONAN131);
			db.execSQL(INSERT_CHITIETMONAN132);
			db.execSQL(INSERT_CHITIETMONAN133);
			db.execSQL(INSERT_CHITIETMONAN134);
			db.execSQL(INSERT_CHITIETMONAN135);
			db.execSQL(INSERT_CHITIETMONAN136);
			db.execSQL(INSERT_CHITIETMONAN137);
			db.execSQL(INSERT_CHITIETMONAN138);
			db.execSQL(INSERT_CHITIETMONAN139);
			db.execSQL(INSERT_CHITIETMONAN140);
			db.execSQL(INSERT_CHITIETMONAN141);
			db.execSQL(INSERT_CHITIETMONAN142);
			db.execSQL(INSERT_CHITIETMONAN143);
			db.execSQL(INSERT_CHITIETMONAN144);
			db.execSQL(INSERT_CHITIETMONAN145);
			db.execSQL(INSERT_CHITIETMONAN146);
			db.execSQL(INSERT_CHITIETMONAN147);
			db.execSQL(INSERT_CHITIETMONAN148);
			db.execSQL(INSERT_CHITIETMONAN149);
			db.execSQL(INSERT_CHITIETMONAN150);
			db.execSQL(INSERT_CHITIETMONAN151);
			db.execSQL(INSERT_CHITIETMONAN152);
			db.execSQL(INSERT_CHITIETMONAN153);
			db.execSQL(INSERT_CHITIETMONAN154);
			db.execSQL(INSERT_CHITIETMONAN155);
			db.execSQL(INSERT_CHITIETMONAN156);
			db.execSQL(INSERT_CHITIETMONAN157);
			db.execSQL(INSERT_CHITIETMONAN158);
			db.execSQL(INSERT_CHITIETMONAN159);
			db.execSQL(INSERT_CHITIETMONAN160);
			db.execSQL(INSERT_CHITIETMONAN161);
			db.execSQL(INSERT_CHITIETMONAN162);
			db.execSQL(INSERT_CHITIETMONAN163);
			db.execSQL(INSERT_CHITIETMONAN164);
			db.execSQL(INSERT_CHITIETMONAN165);
			db.execSQL(INSERT_CHITIETMONAN166);
			db.execSQL(INSERT_CHITIETMONAN167);
			db.execSQL(INSERT_CHITIETMONAN168);
			db.execSQL(INSERT_CHITIETMONAN169);
			db.execSQL(INSERT_CHITIETMONAN170);
			db.execSQL(INSERT_CHITIETMONAN171);
			db.execSQL(INSERT_CHITIETMONAN172);
			db.execSQL(INSERT_CHITIETMONAN173);
			db.execSQL(INSERT_CHITIETMONAN174);
			db.execSQL(INSERT_CHITIETMONAN175);
			db.execSQL(INSERT_CHITIETMONAN176);
			db.execSQL(INSERT_CHITIETMONAN177);
			db.execSQL(INSERT_CHITIETMONAN178);
			db.execSQL(INSERT_CHITIETMONAN179);
			db.execSQL(INSERT_CHITIETMONAN180);
			db.execSQL(INSERT_CHITIETMONAN181);
			db.execSQL(INSERT_CHITIETMONAN182);
			db.execSQL(INSERT_CHITIETMONAN183);
			db.execSQL(INSERT_CHITIETMONAN184);
			db.execSQL(INSERT_CHITIETMONAN185);
			db.execSQL(INSERT_CHITIETMONAN186);
			db.execSQL(INSERT_CHITIETMONAN187);
			db.execSQL(INSERT_CHITIETMONAN188);
			db.execSQL(INSERT_CHITIETMONAN189);
			db.execSQL(INSERT_CHITIETMONAN190);
			db.execSQL(INSERT_CHITIETMONAN191);
			db.execSQL(INSERT_CHITIETMONAN192);
			db.execSQL(INSERT_CHITIETMONAN193);
			db.execSQL(INSERT_CHITIETMONAN194);
			db.execSQL(INSERT_CHITIETMONAN195);
			db.execSQL(INSERT_CHITIETMONAN196);
			db.execSQL(INSERT_CHITIETMONAN197);
			db.execSQL(INSERT_CHITIETMONAN198);
			db.execSQL(INSERT_CHITIETMONAN199);
			db.execSQL(INSERT_CHITIETMONAN200);
			db.execSQL(INSERT_CHITIETMONAN201);

			// insert quyenhan
			db.execSQL(INSERT_QUYENHAN1);
			db.execSQL(INSERT_QUYENHAN2);
			db.execSQL(INSERT_QUYENHAN3);
			db.execSQL(INSERT_QUYENHAN4);

			// insert nguoidung
			db.execSQL(INSERT_NGUOIDUNG1);
			db.execSQL(INSERT_NGUOIDUNG2);
			db.execSQL(INSERT_NGUOIDUNG3);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.i(TAG, "Upgrading DB");
			db.execSQL("DROP TABLE IF EXISTS users");
			onCreate(db);
		}
	}

	public void createDB() {
		SQLiteDatabase db = mDB;
		db.execSQL(CREATE_LOAIBENH_TABLE);
		db.execSQL(CREATE_BENH_TABLE);
		//db.execSQL(CREATE_LOAINGUYENLIEU_TABLE);
		db.execSQL(CREATE_CHITIET_TABLE);
		db.execSQL(CREATE_CHITIETBENH_TABLE);
		db.execSQL(CREATE_QUYENHAN_TABLE);
		db.execSQL(CREATE_NGUOIDUNG_TABLE);
		//db.execSQL(CREATE_THUCDONTRONGTUAN_TABLE);
		//db.execSQL(CREATE_PHATSINH_TABLE);
		//db.execSQL(CREATE_NGUYENLIEUDICHO_TABLE);
		//db.execSQL(CREATE_NHACNHO_TABLE);

		// insert loai mon an
		db.execSQL(INSERT_LOAIMONAN1);
		db.execSQL(INSERT_LOAIMONAN2);
		db.execSQL(INSERT_LOAIMONAN3);
		db.execSQL(INSERT_LOAIMONAN4);
		db.execSQL(INSERT_LOAIMONAN5);
		db.execSQL(INSERT_LOAIMONAN6);
		db.execSQL(INSERT_LOAIMONAN7);

		// insert mon an
		db.execSQL(INSERT_BENH1);
		db.execSQL(INSERT_MONAN2);
		db.execSQL(INSERT_MONAN3);
		db.execSQL(INSERT_MONAN4);
		db.execSQL(INSERT_MONAN5);
		db.execSQL(INSERT_MONAN6);
		db.execSQL(INSERT_MONAN7);
		db.execSQL(INSERT_MONAN8);
		db.execSQL(INSERT_MONAN9);
		db.execSQL(INSERT_MONAN10);
		db.execSQL(INSERT_MONAN11);
		db.execSQL(INSERT_MONAN12);
		db.execSQL(INSERT_MONAN13);
		db.execSQL(INSERT_MONAN14);
		db.execSQL(INSERT_MONAN15);
		db.execSQL(INSERT_MONAN16);
		db.execSQL(INSERT_MONAN17);
		db.execSQL(INSERT_MONAN18);
		db.execSQL(INSERT_MONAN19);
		db.execSQL(INSERT_MONAN20);
		db.execSQL(INSERT_MONAN21);
		db.execSQL(INSERT_MONAN22);
		db.execSQL(INSERT_MONAN23);
		db.execSQL(INSERT_MONAN24);
		db.execSQL(INSERT_MONAN25);
		db.execSQL(INSERT_MONAN26);
		db.execSQL(INSERT_MONAN27);
		db.execSQL(INSERT_MONAN28);
		db.execSQL(INSERT_MONAN29);
		db.execSQL(INSERT_MONAN30);
		db.execSQL(INSERT_MONAN31);
		db.execSQL(INSERT_MONAN32);
		db.execSQL(INSERT_MONAN33);
		db.execSQL(INSERT_MONAN34);
		db.execSQL(INSERT_MONAN35);
		db.execSQL(INSERT_MONAN36);
		db.execSQL(INSERT_MONAN37);
		db.execSQL(INSERT_MONAN38);
		db.execSQL(INSERT_MONAN39);
		db.execSQL(INSERT_MONAN40);
		db.execSQL(INSERT_MONAN41);
		db.execSQL(INSERT_MONAN42);
		db.execSQL(INSERT_MONAN43);

		// insert loai nguyen lieu
		db.execSQL(INSERT_LOAINGUYENLIEU1);
		db.execSQL(INSERT_LOAINGUYENLIEU2);
		db.execSQL(INSERT_LOAINGUYENLIEU3);
		db.execSQL(INSERT_LOAINGUYENLIEU4);
		db.execSQL(INSERT_LOAINGUYENLIEU5);
		db.execSQL(INSERT_LOAINGUYENLIEU6);
		db.execSQL(INSERT_LOAINGUYENLIEU7);
		db.execSQL(INSERT_LOAINGUYENLIEU8);
		db.execSQL(INSERT_LOAINGUYENLIEU9);
		db.execSQL(INSERT_LOAINGUYENLIEU10);
		db.execSQL(INSERT_LOAINGUYENLIEU11);
		db.execSQL(INSERT_LOAINGUYENLIEU12);
		db.execSQL(INSERT_LOAINGUYENLIEU13);
		db.execSQL(INSERT_LOAINGUYENLIEU14);
		db.execSQL(INSERT_LOAINGUYENLIEU15);

		// insert nguyen lieu
		db.execSQL(INSERT_NGUYENLIEU1);
		db.execSQL(INSERT_NGUYENLIEU2);
		db.execSQL(INSERT_NGUYENLIEU3);
		db.execSQL(INSERT_NGUYENLIEU4);
		db.execSQL(INSERT_NGUYENLIEU5);
		db.execSQL(INSERT_NGUYENLIEU6);
		db.execSQL(INSERT_NGUYENLIEU7);
		db.execSQL(INSERT_NGUYENLIEU8);
		db.execSQL(INSERT_NGUYENLIEU9);
		db.execSQL(INSERT_NGUYENLIEU10);
		db.execSQL(INSERT_NGUYENLIEU11);
		db.execSQL(INSERT_NGUYENLIEU12);
		db.execSQL(INSERT_NGUYENLIEU13);
		db.execSQL(INSERT_NGUYENLIEU14);
		db.execSQL(INSERT_NGUYENLIEU15);
		db.execSQL(INSERT_NGUYENLIEU16);
		db.execSQL(INSERT_NGUYENLIEU17);
		db.execSQL(INSERT_NGUYENLIEU18);
		db.execSQL(INSERT_NGUYENLIEU19);
		db.execSQL(INSERT_NGUYENLIEU20);
		db.execSQL(INSERT_NGUYENLIEU21);
		db.execSQL(INSERT_NGUYENLIEU22);
		db.execSQL(INSERT_NGUYENLIEU23);
		db.execSQL(INSERT_NGUYENLIEU24);
		db.execSQL(INSERT_NGUYENLIEU25);
		db.execSQL(INSERT_NGUYENLIEU26);
		db.execSQL(INSERT_NGUYENLIEU27);
		db.execSQL(INSERT_NGUYENLIEU28);
		db.execSQL(INSERT_NGUYENLIEU29);
		db.execSQL(INSERT_NGUYENLIEU30);
		db.execSQL(INSERT_NGUYENLIEU31);
		db.execSQL(INSERT_NGUYENLIEU32);
		db.execSQL(INSERT_NGUYENLIEU33);
		db.execSQL(INSERT_NGUYENLIEU34);
		db.execSQL(INSERT_NGUYENLIEU35);
		db.execSQL(INSERT_NGUYENLIEU36);
		db.execSQL(INSERT_NGUYENLIEU37);
		db.execSQL(INSERT_NGUYENLIEU38);
		db.execSQL(INSERT_NGUYENLIEU39);
		db.execSQL(INSERT_NGUYENLIEU40);
		db.execSQL(INSERT_NGUYENLIEU41);
		db.execSQL(INSERT_NGUYENLIEU42);
		db.execSQL(INSERT_NGUYENLIEU43);
		db.execSQL(INSERT_NGUYENLIEU44);
		db.execSQL(INSERT_NGUYENLIEU45);
		db.execSQL(INSERT_NGUYENLIEU46);
		db.execSQL(INSERT_NGUYENLIEU47);
		db.execSQL(INSERT_NGUYENLIEU48);
		db.execSQL(INSERT_NGUYENLIEU49);
		db.execSQL(INSERT_NGUYENLIEU50);
		db.execSQL(INSERT_NGUYENLIEU51);
		db.execSQL(INSERT_NGUYENLIEU52);
		db.execSQL(INSERT_NGUYENLIEU53);
		db.execSQL(INSERT_NGUYENLIEU54);
		db.execSQL(INSERT_NGUYENLIEU55);
		db.execSQL(INSERT_NGUYENLIEU56);
		db.execSQL(INSERT_NGUYENLIEU57);
		db.execSQL(INSERT_NGUYENLIEU58);
		db.execSQL(INSERT_NGUYENLIEU59);
		db.execSQL(INSERT_NGUYENLIEU60);
		db.execSQL(INSERT_NGUYENLIEU61);
		db.execSQL(INSERT_NGUYENLIEU62);
		db.execSQL(INSERT_NGUYENLIEU63);
		db.execSQL(INSERT_NGUYENLIEU64);
		db.execSQL(INSERT_NGUYENLIEU65);
		db.execSQL(INSERT_NGUYENLIEU66);
		db.execSQL(INSERT_NGUYENLIEU67);
		db.execSQL(INSERT_NGUYENLIEU68);
		db.execSQL(INSERT_NGUYENLIEU69);
		db.execSQL(INSERT_NGUYENLIEU70);
		db.execSQL(INSERT_NGUYENLIEU71);
		db.execSQL(INSERT_NGUYENLIEU72);
		db.execSQL(INSERT_NGUYENLIEU73);
		db.execSQL(INSERT_NGUYENLIEU74);
		db.execSQL(INSERT_NGUYENLIEU75);
		db.execSQL(INSERT_NGUYENLIEU76);
		db.execSQL(INSERT_NGUYENLIEU77);
		db.execSQL(INSERT_NGUYENLIEU78);
		db.execSQL(INSERT_NGUYENLIEU79);
		db.execSQL(INSERT_NGUYENLIEU80);
		db.execSQL(INSERT_NGUYENLIEU81);
		db.execSQL(INSERT_NGUYENLIEU82);
		db.execSQL(INSERT_NGUYENLIEU83);
		db.execSQL(INSERT_NGUYENLIEU84);
		db.execSQL(INSERT_NGUYENLIEU85);
		db.execSQL(INSERT_NGUYENLIEU86);
		db.execSQL(INSERT_NGUYENLIEU87);

		// insert chi tiet mon an
		db.execSQL(INSERT_CHITIETMONAN1);
		db.execSQL(INSERT_CHITIETMONAN2);
		db.execSQL(INSERT_CHITIETMONAN3);
		db.execSQL(INSERT_CHITIETMONAN4);
		db.execSQL(INSERT_CHITIETMONAN5);
		db.execSQL(INSERT_CHITIETMONAN6);
		db.execSQL(INSERT_CHITIETMONAN7);
		db.execSQL(INSERT_CHITIETMONAN8);
		db.execSQL(INSERT_CHITIETMONAN9);
		db.execSQL(INSERT_CHITIETMONAN10);
		db.execSQL(INSERT_CHITIETMONAN11);
		db.execSQL(INSERT_CHITIETMONAN12);
		db.execSQL(INSERT_CHITIETMONAN13);
		db.execSQL(INSERT_CHITIETMONAN14);
		db.execSQL(INSERT_CHITIETMONAN15);
		db.execSQL(INSERT_CHITIETMONAN16);
		db.execSQL(INSERT_CHITIETMONAN17);
		db.execSQL(INSERT_CHITIETMONAN18);
		db.execSQL(INSERT_CHITIETMONAN19);
		db.execSQL(INSERT_CHITIETMONAN20);
		db.execSQL(INSERT_CHITIETMONAN21);
		db.execSQL(INSERT_CHITIETMONAN22);
		db.execSQL(INSERT_CHITIETMONAN23);
		db.execSQL(INSERT_CHITIETMONAN24);
		db.execSQL(INSERT_CHITIETMONAN25);
		db.execSQL(INSERT_CHITIETMONAN26);
		db.execSQL(INSERT_CHITIETMONAN27);
		db.execSQL(INSERT_CHITIETMONAN28);
		db.execSQL(INSERT_CHITIETMONAN29);
		db.execSQL(INSERT_CHITIETMONAN30);
		db.execSQL(INSERT_CHITIETMONAN31);
		db.execSQL(INSERT_CHITIETMONAN32);
		db.execSQL(INSERT_CHITIETMONAN33);
		db.execSQL(INSERT_CHITIETMONAN34);
		db.execSQL(INSERT_CHITIETMONAN35);
		db.execSQL(INSERT_CHITIETMONAN36);
		db.execSQL(INSERT_CHITIETMONAN37);
		db.execSQL(INSERT_CHITIETMONAN38);
		db.execSQL(INSERT_CHITIETMONAN39);
		db.execSQL(INSERT_CHITIETMONAN40);
		db.execSQL(INSERT_CHITIETMONAN41);
		db.execSQL(INSERT_CHITIETMONAN42);
		db.execSQL(INSERT_CHITIETMONAN43);
		db.execSQL(INSERT_CHITIETMONAN44);
		db.execSQL(INSERT_CHITIETMONAN45);
		db.execSQL(INSERT_CHITIETMONAN46);
		db.execSQL(INSERT_CHITIETMONAN47);
		db.execSQL(INSERT_CHITIETMONAN48);
		db.execSQL(INSERT_CHITIETMONAN49);
		db.execSQL(INSERT_CHITIETMONAN50);
		db.execSQL(INSERT_CHITIETMONAN51);
		db.execSQL(INSERT_CHITIETMONAN52);
		db.execSQL(INSERT_CHITIETMONAN53);
		db.execSQL(INSERT_CHITIETMONAN54);
		db.execSQL(INSERT_CHITIETMONAN55);
		db.execSQL(INSERT_CHITIETMONAN56);
		db.execSQL(INSERT_CHITIETMONAN57);
		db.execSQL(INSERT_CHITIETMONAN58);
		db.execSQL(INSERT_CHITIETMONAN59);
		db.execSQL(INSERT_CHITIETMONAN60);
		db.execSQL(INSERT_CHITIETMONAN61);
		db.execSQL(INSERT_CHITIETMONAN62);
		db.execSQL(INSERT_CHITIETMONAN63);
		db.execSQL(INSERT_CHITIETMONAN64);
		db.execSQL(INSERT_CHITIETMONAN65);
		db.execSQL(INSERT_CHITIETMONAN66);
		db.execSQL(INSERT_CHITIETMONAN67);
		db.execSQL(INSERT_CHITIETMONAN68);
		db.execSQL(INSERT_CHITIETMONAN69);
		db.execSQL(INSERT_CHITIETMONAN70);
		db.execSQL(INSERT_CHITIETMONAN71);
		db.execSQL(INSERT_CHITIETMONAN72);
		db.execSQL(INSERT_CHITIETMONAN73);
		db.execSQL(INSERT_CHITIETMONAN74);
		db.execSQL(INSERT_CHITIETMONAN75);
		db.execSQL(INSERT_CHITIETMONAN76);
		db.execSQL(INSERT_CHITIETMONAN77);
		db.execSQL(INSERT_CHITIETMONAN78);
		db.execSQL(INSERT_CHITIETMONAN79);
		db.execSQL(INSERT_CHITIETMONAN80);
		db.execSQL(INSERT_CHITIETMONAN81);
		db.execSQL(INSERT_CHITIETMONAN82);
		db.execSQL(INSERT_CHITIETMONAN83);
		db.execSQL(INSERT_CHITIETMONAN84);
		db.execSQL(INSERT_CHITIETMONAN85);
		db.execSQL(INSERT_CHITIETMONAN86);
		db.execSQL(INSERT_CHITIETMONAN87);
		db.execSQL(INSERT_CHITIETMONAN88);
		db.execSQL(INSERT_CHITIETMONAN89);
		db.execSQL(INSERT_CHITIETMONAN90);
		db.execSQL(INSERT_CHITIETMONAN91);
		db.execSQL(INSERT_CHITIETMONAN92);
		db.execSQL(INSERT_CHITIETMONAN93);
		db.execSQL(INSERT_CHITIETMONAN94);
		db.execSQL(INSERT_CHITIETMONAN95);
		db.execSQL(INSERT_CHITIETMONAN96);
		db.execSQL(INSERT_CHITIETMONAN97);
		db.execSQL(INSERT_CHITIETMONAN98);
		db.execSQL(INSERT_CHITIETMONAN99);
		db.execSQL(INSERT_CHITIETMONAN100);
		db.execSQL(INSERT_CHITIETMONAN101);
		db.execSQL(INSERT_CHITIETMONAN102);
		db.execSQL(INSERT_CHITIETMONAN103);
		db.execSQL(INSERT_CHITIETMONAN104);
		db.execSQL(INSERT_CHITIETMONAN105);
		db.execSQL(INSERT_CHITIETMONAN106);
		db.execSQL(INSERT_CHITIETMONAN107);
		db.execSQL(INSERT_CHITIETMONAN108);
		db.execSQL(INSERT_CHITIETMONAN109);
		db.execSQL(INSERT_CHITIETMONAN110);
		db.execSQL(INSERT_CHITIETMONAN111);
		db.execSQL(INSERT_CHITIETMONAN112);
		db.execSQL(INSERT_CHITIETMONAN113);
		db.execSQL(INSERT_CHITIETMONAN114);
		db.execSQL(INSERT_CHITIETMONAN115);
		db.execSQL(INSERT_CHITIETMONAN116);
		db.execSQL(INSERT_CHITIETMONAN117);
		db.execSQL(INSERT_CHITIETMONAN118);
		db.execSQL(INSERT_CHITIETMONAN119);
		db.execSQL(INSERT_CHITIETMONAN120);
		db.execSQL(INSERT_CHITIETMONAN121);
		db.execSQL(INSERT_CHITIETMONAN122);
		db.execSQL(INSERT_CHITIETMONAN123);
		db.execSQL(INSERT_CHITIETMONAN124);
		db.execSQL(INSERT_CHITIETMONAN125);
		db.execSQL(INSERT_CHITIETMONAN126);
		db.execSQL(INSERT_CHITIETMONAN127);
		db.execSQL(INSERT_CHITIETMONAN128);
		db.execSQL(INSERT_CHITIETMONAN129);
		db.execSQL(INSERT_CHITIETMONAN130);
		db.execSQL(INSERT_CHITIETMONAN131);
		db.execSQL(INSERT_CHITIETMONAN132);
		db.execSQL(INSERT_CHITIETMONAN133);
		db.execSQL(INSERT_CHITIETMONAN134);
		db.execSQL(INSERT_CHITIETMONAN135);
		db.execSQL(INSERT_CHITIETMONAN136);
		db.execSQL(INSERT_CHITIETMONAN137);
		db.execSQL(INSERT_CHITIETMONAN138);
		db.execSQL(INSERT_CHITIETMONAN139);
		db.execSQL(INSERT_CHITIETMONAN140);
		db.execSQL(INSERT_CHITIETMONAN141);
		db.execSQL(INSERT_CHITIETMONAN142);
		db.execSQL(INSERT_CHITIETMONAN143);
		db.execSQL(INSERT_CHITIETMONAN144);
		db.execSQL(INSERT_CHITIETMONAN145);
		db.execSQL(INSERT_CHITIETMONAN146);
		db.execSQL(INSERT_CHITIETMONAN147);
		db.execSQL(INSERT_CHITIETMONAN148);
		db.execSQL(INSERT_CHITIETMONAN149);
		db.execSQL(INSERT_CHITIETMONAN150);
		db.execSQL(INSERT_CHITIETMONAN151);
		db.execSQL(INSERT_CHITIETMONAN152);
		db.execSQL(INSERT_CHITIETMONAN153);
		db.execSQL(INSERT_CHITIETMONAN154);
		db.execSQL(INSERT_CHITIETMONAN155);
		db.execSQL(INSERT_CHITIETMONAN156);
		db.execSQL(INSERT_CHITIETMONAN157);
		db.execSQL(INSERT_CHITIETMONAN158);
		db.execSQL(INSERT_CHITIETMONAN159);
		db.execSQL(INSERT_CHITIETMONAN160);
		db.execSQL(INSERT_CHITIETMONAN161);
		db.execSQL(INSERT_CHITIETMONAN162);
		db.execSQL(INSERT_CHITIETMONAN163);
		db.execSQL(INSERT_CHITIETMONAN164);
		db.execSQL(INSERT_CHITIETMONAN165);
		db.execSQL(INSERT_CHITIETMONAN166);
		db.execSQL(INSERT_CHITIETMONAN167);
		db.execSQL(INSERT_CHITIETMONAN168);
		db.execSQL(INSERT_CHITIETMONAN169);
		db.execSQL(INSERT_CHITIETMONAN170);
		db.execSQL(INSERT_CHITIETMONAN171);
		db.execSQL(INSERT_CHITIETMONAN172);
		db.execSQL(INSERT_CHITIETMONAN173);
		db.execSQL(INSERT_CHITIETMONAN174);
		db.execSQL(INSERT_CHITIETMONAN175);
		db.execSQL(INSERT_CHITIETMONAN176);
		db.execSQL(INSERT_CHITIETMONAN177);
		db.execSQL(INSERT_CHITIETMONAN178);
		db.execSQL(INSERT_CHITIETMONAN179);
		db.execSQL(INSERT_CHITIETMONAN180);
		db.execSQL(INSERT_CHITIETMONAN181);
		db.execSQL(INSERT_CHITIETMONAN182);
		db.execSQL(INSERT_CHITIETMONAN183);
		db.execSQL(INSERT_CHITIETMONAN184);
		db.execSQL(INSERT_CHITIETMONAN185);
		db.execSQL(INSERT_CHITIETMONAN186);
		db.execSQL(INSERT_CHITIETMONAN187);
		db.execSQL(INSERT_CHITIETMONAN188);
		db.execSQL(INSERT_CHITIETMONAN189);
		db.execSQL(INSERT_CHITIETMONAN190);
		db.execSQL(INSERT_CHITIETMONAN191);
		db.execSQL(INSERT_CHITIETMONAN192);
		db.execSQL(INSERT_CHITIETMONAN193);
		db.execSQL(INSERT_CHITIETMONAN194);
		db.execSQL(INSERT_CHITIETMONAN195);
		db.execSQL(INSERT_CHITIETMONAN196);
		db.execSQL(INSERT_CHITIETMONAN197);
		db.execSQL(INSERT_CHITIETMONAN198);
		db.execSQL(INSERT_CHITIETMONAN199);
		db.execSQL(INSERT_CHITIETMONAN200);
		db.execSQL(INSERT_CHITIETMONAN201);
		// Insert NhacNho
		/*db.execSQL(INSERT_NHACNHO1);
		db.execSQL(INSERT_NHACNHO2);
		db.execSQL(INSERT_NHACNHO3);
		db.execSQL(INSERT_NHACNHO4);
		// insert quyenhan
		db.execSQL(INSERT_QUYENHAN1);
		db.execSQL(INSERT_QUYENHAN2);
		db.execSQL(INSERT_QUYENHAN3);
		db.execSQL(INSERT_QUYENHAN4);

		// insert nguoidung
		db.execSQL(INSERT_NGUOIDUNG1);
		db.execSQL(INSERT_NGUOIDUNG2);
		db.execSQL(INSERT_NGUOIDUNG3);
	}

	public AdapterDB(Context ctx) {
		this.mContext = ctx;
		mDbHelper = new DatabaseHelper(ctx, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public AdapterDB open() {
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null,
				DATABASE_VERSION);
		if (mDbHelper != null)
		{
			mDB = mDbHelper.getWritableDatabase();
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public static void capnhatmonanmoi(String id, String name, int kp,
			String ct, String pic, String type) {
		String INSERT = "INSERT INTO monan VALUES ('" + id + "', '" + name
				+ "', " + kp + ", '" + ct + ".', '" + pic + "', '" + type
				+ "');";
		SQLiteDatabase db = mDB;
		db.execSQL(INSERT);
	}

	public void capnhatmonanmoi(String id, String name, int kp, String ct,
			String tp, String pic, String type, String videourl) {
		Cursor mCount = mDB.rawQuery("select count(*) from monan", null);
		mCount.moveToFirst();
		int count = mCount.getInt(0);
		String INSERT = "INSERT INTO monan VALUES ('" + count + "_" + id
				+ "', '" + name + "', " + kp + ", '" + ct + ".', '" + pic
				+ "', '" + type + "', '" + videourl + "');";
		mDB.execSQL(INSERT);

		String nl[] = tp.split(";");

		for (int i = 0; i < nl.length; i++) {
			String tam[] = nl[i].split("-");
			String tnl = AdapterDB.layidnguyenlieu(tam[0]);
			if (tnl == "") {
				INSERT = "INSERT INTO nguyenlieu VALUES ('id"
						+ tam[0].replace(" ", "_") + "', '" + tam[0] + "','"
						+ tam[2] + "', 'Kh');";
				mDB.execSQL(INSERT);
				tnl = "id" + tam[0].replace(" ", "_");
			}
			if (i > 0 && !tam[i].equals(tam[i - 1])) {
				INSERT = "INSERT INTO chitietmonan VALUES ('" + count + "_"
						+ id + "', '" + tnl + "', '" + tam[1] + "');";
				mDB.execSQL(INSERT);
			}
		}
	}

	public Cursor laybenh() 
	{
		
		return mDB.query("benh", new String[] { "tenbenh", "idbenh",
				"idloaibenh" }, null, null, null, null, null);
	}

	public Cursor laynguyenlieu() {
		return mDB.query("nguyenlieu", new String[] { "tennguyenlieu",
				"idnguyenlieu" }, null, null, null, null, null);
	}

	public Cursor getChiTietBenh(String idbenh) {
		return mDB.query("benh", new String[] { "tenbenh", "congthuc",
				"hinhanh", "idloaibenh" }, "idbenh = '" + idbenh + "'",
				null, null, null, null);
	}

	public Cursor getLichDiCho(String thuTrongTuan) {
		return mDB.query("NhacNho",
				new String[] { "Thu", "Gio", "Phut", "Note" }, "Thu = '"
						+ thuTrongTuan + "'", null, null, null, null);
	}

	public String getLoaiMonAn(String idloaimonan) {
		String loaimonan = null;
		Cursor mCursor = mDB.query("loaimonan",
				new String[] { "tenloaimonan" }, "idloaimonan = '"
						+ idloaimonan + "'", null, null, null, null);
		mCursor.moveToFirst();
		if (mCursor.moveToFirst()) {
			loaimonan = mCursor.getString(0);
		}
		mCursor.close();
		return loaimonan;
	}

	public ArrayList<Materials> getNguyenLieuMonAn(String idmonan) {
		Cursor mCursor, nCursor = null;
		ArrayList<Materials> array = new ArrayList<Materials>();
		mCursor = mDB.query("chitietmonan", new String[] { "idnguyenlieu",
				"soluong" }, "idmonan = '" + idmonan + "'", null, null, null,
				null);
		mCursor.moveToFirst();
		if (mCursor.moveToFirst()) {
			do {
				nCursor = mDB.query("nguyenlieu", new String[] {
						"tennguyenlieu", "idnguyenlieu" }, "idnguyenlieu = '"
						+ mCursor.getString(0) + "'", null, null, null, null);
				nCursor.moveToFirst();
				array.add(new Materials(nCursor.getString(0), mCursor
						.getString(0)));
				mCursor.moveToNext();
			} while (!mCursor.isAfterLast());
		}
		nCursor.close();
		mCursor.close();
	
		return array;
	}

	public ArrayList<String> laycacmonantheokhauphankhongtrung(int khauphan,
			ArrayList<String> monandaco) {
		ArrayList<String> laycacmonantheokhauphan = new ArrayList<String>();
		ArrayList<String> cacmonantheokhauphan = new ArrayList<String>();

		Cursor cur = null;
		cur = mDB.query("monan", new String[] { "idmonan" }, "khauphan = '"
				+ khauphan + "'", null, null, null, null);
		cur.moveToFirst();

		while (cur.isAfterLast() == false) {
			laycacmonantheokhauphan.add(cur.getString(0));
			cur.moveToNext();
		}

		cur.close();

		if (monandaco.size() != 0) {
			for (int i = 0; i < laycacmonantheokhauphan.size(); i++) 
			{
				int trung = 0;
				for (int j = 0; j < monandaco.size(); j++)
					if (laycacmonantheokhauphan.get(i).equals(monandaco.get(j)) == true)
					{
						trung = 1;
						break;
					}
				if (trung == 0)
					cacmonantheokhauphan.add(laycacmonantheokhauphan.get(i));
			}
			return cacmonantheokhauphan;
		}

		return laycacmonantheokhauphan;
	}

	public String laycacmonantheothuvabuoi(int thu, int buoi, String idnguoidung) {
		Cursor cur = null;
		String thucdontrongmotbuoi = null, idBenh = null;
		cur = mDB.query("thucdontrongtuan", new String[] { "idmonan" },
						"idnguoidung = '" + idnguoidung + "' AND thu = '" + thu
								+ "' AND buoi = '" + buoi + "'", null, null,
						null, null);
		cur.moveToFirst();

		idBenh = cur.getString(0);
		thucdontrongmotbuoi = AdapterDB.laytenbenh(idBenh);
		cur.moveToNext();

		while (cur.isAfterLast() == false) {
			idBenh = cur.getString(0);
			thucdontrongmotbuoi += "," + AdapterDB.laytenbenh(idBenh);
			cur.moveToNext();
		}
		cur.close();
		
		return thucdontrongmotbuoi;
	}

	public ArrayList<String> laycacmonantrongmotbuoi(int thu, int buoi) {
		ArrayList<String> cacmonantrongmotbuoi = new ArrayList<String>();
		Cursor cur = null;
		cur = mDB.query("thucdontrongtuan", new String[] { "idmonan" },
				"thu = '" + thu + "' AND buoi = '" + buoi + "'", null, null,
				null, null);

		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			cacmonantrongmotbuoi.add(cur.getString(0));
			cur.moveToNext();
		}
		cur.close();

		return cacmonantrongmotbuoi;
	}

	public int laykhauphancuamonan(String idmonan) {
		Cursor cur = null;
		cur = mDB.query("monan", new String[] { "khauphan" }, "idmonan = '"
				+ idmonan + "'", null, null, null, null);
		cur.moveToFirst();
		int khauphan = Integer.parseInt(cur.getString(0));
		cur.close();
		return khauphan;
	}

	public String laycachthuchienmonan(String idmonan) {
		Cursor cur = null;
		cur = mDB.query("monan", new String[] { "congthuc" }, "idmonan = '"
				+ idmonan + "'", null, null, null, null);
		cur.moveToFirst();
		String cachthuchien = cur.getString(0);
		cur.close();
		return cachthuchien;
	}

	public int laymamonanlonhat() {
		String query = "SELECT Count(*) FROM monan";
		Cursor cursor = mDB.rawQuery(query, null);

		int id = 0;
		if (cursor.moveToFirst()) {
			do {
				id = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return id;
	}

	public String layanhmonan(String idmonan) {
		Cursor cur = null;
		cur = mDB.query("monan", new String[] { "hinhanh" }, "idmonan = '"
				+ idmonan + "'", null, null, null, null);
		cur.moveToFirst();
		String anhmonan = cur.getString(0);
		cur.close();
		return anhmonan;
	}

	public static ArrayList<String> laytatcanguyenlieutrongthucdon(
			String idnguoidung) {
		ArrayList<String> tatcamonantrongthucdon = new ArrayList<String>();
		ArrayList<String> cacnguyenlieutrongmonan = new ArrayList<String>();
		ArrayList<String> tatcanguyenlieutrongthucdon = new ArrayList<String>();

		Cursor cur = null;
		cur = mDB.query("thucdontrongtuan", new String[] { "idmonan" },
				"idnguoidung = '" + idnguoidung + "'", null, null, null, null);
		cur.moveToFirst();

		while (cur.isAfterLast() == false) {
			tatcamonantrongthucdon.add(cur.getString(0));
			cur.moveToNext();
		}
		cur.close();

		for (int i = 0; i < tatcamonantrongthucdon.size(); i++) {
			cacnguyenlieutrongmonan = laycacnguyenlieutrongmonan(tatcamonantrongthucdon
					.get(i));
			for (int j = 0; j < cacnguyenlieutrongmonan.size(); j++)
				tatcanguyenlieutrongthucdon.add(cacnguyenlieutrongmonan.get(j));
		}

		return tatcanguyenlieutrongthucdon;
	}

	public static ArrayList<String> laycacnguyenlieutrongmonan(String idmonan) {
		ArrayList<String> cacnguyenlieu = new ArrayList<String>();
		Cursor cur;
		cur = mDB.query("chitietmonan", new String[] { "idnguyenlieu",
				"soluong" }, "idmonan = '" + idmonan + "'", null, null, null,
				null);
		cur.moveToFirst();

		while (cur.isAfterLast() == false) {
			String nguyenlieu = cur.getString(0) + ":" + cur.getString(1);
			cacnguyenlieu.add(nguyenlieu);
			cur.moveToNext();
		}
		cur.close();
		return cacnguyenlieu;
	}

	public ArrayList<String> layrocacnguyenlieutrongmonan(String idmonan) {
		ArrayList<String> cacnguyenlieu = new ArrayList<String>();
		Cursor cur = null, curs = null;
		cur = mDB.query("chitietmonan", new String[] { "idnguyenlieu",
				"soluong" }, "idmonan = '" + idmonan + "'", null, null, null,
				null);
		cur.moveToFirst();

		while (cur.isAfterLast() == false) {
			curs = mDB.query("nguyenlieu", new String[] { "donvi" },
					"idnguyenlieu = '" + cur.getString(0) + "'", null, null,
					null, null);
			curs.moveToFirst();
			String nguyenlieu;
			nguyenlieu = AdapterDB.laytennguyenlieu(cur.getString(0)) + " : "
					+ cur.getString(1) + " " + curs.getString(0);
			cacnguyenlieu.add(nguyenlieu);
			curs.close();
			cur.moveToNext();
		}
		cur.close();
		return cacnguyenlieu;
	}

	public ArrayList<String> laymangtatcanguyenlieudicho(String idnguoidung)
			throws JSONException {
		ArrayList<String> mangnguyenlieu = new ArrayList<String>();
		String nguyenlieu = null;

		Cursor cur, curs;
		cur = mDB.query("nguyenlieudicho", new String[] { "idnguyenlieu",
				"tongsoluong", "isbought" }, "idnguoidung = '" + idnguoidung
				+ "'", null, null, null, null);
		cur.moveToFirst();

		while (cur.isAfterLast() == false) {
			curs = mDB.query("nguyenlieu", new String[] { "donvi",
					"idloainguyenlieu" }, "idnguyenlieu = '" + cur.getString(0)
					+ "'", null, null, null, null);
			curs.moveToFirst();
			try {
				nguyenlieu = laytenloainguyenlieu(curs.getString(1)) + "-"
						+ laytennguyenlieu(cur.getString(0)) + " : "
						+ cur.getString(1) + " " + curs.getString(0) + " "
						+ cur.getString(2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			curs.close();

			mangnguyenlieu.add(nguyenlieu);
			cur.moveToNext();
		}

		cur.close();

		return mangnguyenlieu;
	}

	public ArrayList<MarketMaterial> laytatcanguyenlieudicho(String idnguoidung)
			throws JSONException {
		ArrayList<MarketMaterial> mangnguyenlieu = new ArrayList<MarketMaterial>();
		MarketMaterial nguyenlieu = null;

		Cursor cur, curs;
		cur = mDB.query("nguyenlieudicho", new String[] { "idnguyenlieu",
				"tongsoluong", "isbought" }, "idnguoidung = '" + idnguoidung
				+ "'", null, null, null, null);
		cur.moveToFirst();

		while (cur.isAfterLast() == false) {
			curs = mDB.query("nguyenlieu", new String[] { "donvi",
					"idloainguyenlieu" }, "idnguyenlieu = '" + cur.getString(0)
					+ "'", null, null, null, null);
			curs.moveToFirst();
			try {
				int sl = cur.getInt(1);
				String n = laytennguyenlieu(cur.getString(0));
				String g = laytenloainguyenlieu(curs.getString(1));
				String dv = curs.getString(0);
				int k = Integer.parseInt(cur.getString(1));
				if (cur.getString(2).equals("1"))
					nguyenlieu = new MarketMaterial(n, g, dv, k, true, sl);
				else
					nguyenlieu = new MarketMaterial(n, g, dv, k, false, sl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			curs.close();

			mangnguyenlieu.add(nguyenlieu);
			cur.moveToNext();
		}

		cur.close();

		return mangnguyenlieu;
	}

	public ArrayList<MarketScheduleList> laylichdicho() throws JSONException {
		ArrayList<MarketScheduleList> lichdicho = new ArrayList<MarketScheduleList>();
		Cursor cur;
		cur = mDB.query("NhacNho", new String[] { "Thu", "Gio", "Phut" }, null,
				null, null, null, null);
		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			MarketScheduleList temp = new MarketScheduleList(cur.getString(0),
					Integer.parseInt(cur.getString(1)), Integer.parseInt(cur
							.getString(2)));
			lichdicho.add(temp);
			cur.moveToNext();
		}
		cur.close();
		return lichdicho;
	}

	public static String laytenbenh(String idbenh) {
		Cursor cur;
		cur = mDB.query("benh", new String[] { "tenbenh" }, "idbenh = '"
				+ idbenh + "'", null, null, null, null);
		cur.moveToFirst();
		String Values = cur.getString(0);
		cur.close();
		return Values;
	}

	public static ArrayList<String[]> timmonan(String key) {
		ArrayList<String[]> listmonan = new ArrayList<String[]>();
		String sql = "SELECT tenmonan,idmonan FROM monan WHERE tenmonan LIKE '%"
				+ key + "%'";
		Cursor cur;
		cur = mDB.rawQuery(sql, null);
		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			String[] tam = new String[] { cur.getString(0), cur.getString(1) };
			// tam[0] = cur.getString(0);
			// tam[1] = cur.getString(1);
			listmonan.add(tam);
			cur.moveToNext();
		}
		cur.close();
		return listmonan;
	}

	public Cursor timkiembenh(String key) {
		String sql = "SELECT tenbenh,idbenh FROM benh WHERE tenbenh LIKE '%"
				+ key + "%'";
		Cursor cur;
		cur = mDB.rawQuery(sql, null);
		return cur;
	}

	public static String laytennguyenlieu(String idnguyenlieu) {
		Cursor cur;
		cur = mDB
				.query("nguyenlieu", new String[] { "tennguyenlieu" },
						"idnguyenlieu = '" + idnguyenlieu + "'", null, null,
						null, null);
		cur.moveToFirst();
		String Values = cur.getString(0);
		cur.close();
		return Values;
	}

	public static String laytenloainguyenlieu(String idloainguyenlieu)
			throws JSONException {
		Cursor cur;
		cur = mDB.query("loainguyenlieu", new String[] { "tenloai" },
				"idloainguyenlieu = '" + idloainguyenlieu + "'", null, null,
				null, null);
		cur.moveToFirst();
		String tenloainguyenlieu = cur.getString(0);
		cur.close();
		return tenloainguyenlieu;
	}

	public static String laysongaytrongtuan(String idnguoidung)
			throws JSONException {
		Cursor cur = null;
		String songaytrongtuan = "";
		try
		{
		cur = mDB.query("phatsinh", new String[] { "songaytrongtuan" },
				"idnguoidung = '" + idnguoidung + "'", null, null, null, null);
		cur.moveToFirst();
		songaytrongtuan = cur.getString(0);
		}catch(Exception e){}
		finally
		{
		cur.close();
		}
		return songaytrongtuan;
	}

	public static String laysobuaan(String idnguoidung) throws JSONException {
		Cursor cur;
		cur = mDB.query("phatsinh", new String[] { "sobuaan" },
				"idnguoidung = '" + idnguoidung + "'", null, null, null, null);
		cur.moveToFirst();
		String sobuaan = cur.getString(0);
		cur.close();
		return sobuaan;
	}

	public void themmonanvaothucdontrongtuan(ContentValues cacgiatriphatsinh) {
		mDB.insert("thucdontrongtuan", null, cacgiatriphatsinh);
	}

	public static void themnguyenlieuvaodanhsachdicho(
			ContentValues cacgiatriphatsinh) {
		mDB.insert("nguyenlieudicho", null, cacgiatriphatsinh);
	}

	public void themvaolichdicho(MarketScheduleList nhacnho) {
		ContentValues cacgiatriphatsinh = new ContentValues();
		cacgiatriphatsinh.put("Thu", nhacnho.getDay());
		cacgiatriphatsinh.put("Gio", nhacnho.getHours());
		cacgiatriphatsinh.put("Phut", nhacnho.getMinutes());
		mDB.insert("NhacNho", null, cacgiatriphatsinh);
	}

	public void capnhatnguoidung(ContentValues cacgiatriphatsinh,
			String idnguoidung) {
		mDB.delete("phatsinh", "idnguoidung = '" + idnguoidung + "'", null);
		mDB.delete("thucdontrongtuan", "idnguoidung = '" + idnguoidung + "'",
				null);
		mDB.delete("nguyenlieudicho", "idnguoidung = '" + idnguoidung + "'",
				null);
		mDB.insert("phatsinh", null, cacgiatriphatsinh);
	}

	public static void capnhatlaimonanmoi(String idmoncu, String idmonmoi,
			int thu, int buoi) {
		String sql = "UPDATE thucdontrongtuan SET idmonan='" + idmonmoi
				+ "' WHERE  idmonan='" + idmoncu + "' and thu='" + thu
				+ "' and buoi='" + buoi + "'";
		mDB.execSQL(sql);

		mDB.delete("nguyenlieudicho", null, null);

		ArrayList<String> tatcanguyenlieutrongthucdon = new ArrayList<String>();

		try {
			tatcanguyenlieutrongthucdon = laytatcanguyenlieutrongthucdon("hoangvu");
		} catch (Exception e) {
		}

		for (int i = 0; i < tatcanguyenlieutrongthucdon.size(); i++) {
			String mangnguyenlieuhientai = tatcanguyenlieutrongthucdon.get(i);
			String[] nguyenlieuhientai = mangnguyenlieuhientai.split(":");
			float Luong = Float.parseFloat(nguyenlieuhientai[1]);
			for (int j = i; j < tatcanguyenlieutrongthucdon.size(); j++) {
				String mangktnguyenlieutrung = tatcanguyenlieutrongthucdon
						.get(j);
				String[] ktnguyenlieutrung = mangktnguyenlieutrung.split(":");
				if (nguyenlieuhientai[0].equals(ktnguyenlieutrung[0]) == true) {
					Luong += Float.parseFloat(ktnguyenlieutrung[1]);
					tatcanguyenlieutrongthucdon.remove(j);
					j--;
				}
			}
			if (Luong <= 1)
				Luong = 1;
			if (Luong % 1 != 0)
				Luong = (int) Luong + 1;
			ContentValues nguyenlieu = new ContentValues();
			nguyenlieu.put("idnguoidung", "hoangvu");
			nguyenlieu.put("idnguyenlieu", nguyenlieuhientai[0]);
			nguyenlieu.put("tongsoluong", (int) Luong);
			nguyenlieu.put("isbought", 0);

			try {
				themnguyenlieuvaodanhsachdicho(nguyenlieu);
			} catch (Exception e) {
			}
		}
	}

	public void xoalichdicho(String thu, int gio, int phut) {
		mDB.delete("NhacNho", "Thu=? AND Gio=? AND Phut=?", new String[] { thu,
				String.valueOf(gio), String.valueOf(phut) });
	}

	public boolean kiemtranguoidung(String idnguoidung) {
		Cursor cur;
		boolean flag =false;
		cur = mDB.query("nguoidung", new String[] { "idnguoidung" },
				"idnguoidung = '" + idnguoidung + "'", null, null, null, null);
		if (cur.getCount() > 0)
		{
			cur.close();
			flag =  true;
		}
		cur.close();
		return flag;
	}
	public boolean kiemtranguoidung(String idnguoidung, String password) {
		Cursor cur;
		String sqlQuery = "select idnguoidung from nguoidung where idnguoidung='"
                + idnguoidung + "' AND  matkhau = '" + password + "'";
		boolean flag =false;
		cur = mDB.rawQuery(sqlQuery, null);
		if (cur.getCount() > 0)
		{
			cur.close();
			flag =  true;
		}
		else
		{
		cur.close();
		flag = false;
		}
		return flag;
	}
	public String getPassWordUser(String idnguoidung)
	{
		
		Cursor cur = null;
		String password = "";
		try
		{
		cur = mDB.query("nguoidung", new String[] {"matkhau"},
				"idnguoidung = '" + idnguoidung + "'", null, null, null, null);
		if (cur.getCount() < 1)
		{
			password =  "NOT EXIT";
		}
		else
		{
		cur.moveToFirst();
		password  = cur.getString(cur.getColumnIndex("matkhau"));
		}
		}finally
		{
			 if (cur != null) {
		            cur.close();
		        }
		}
		return password;
		
	}

	public static boolean kiemtranguoidungphatsinh(String idnguoidung) {
		boolean flag = false;
		Cursor cur;
		cur = mDB.query("phatsinh", new String[] { "songaytrongtuan" },
				"idnguoidung = '" + idnguoidung + "'", null, null, null, null);
		if (cur.getCount() > 0)
		{	
			cur.close();
			flag = true;
		}
		else
		{
		   cur.close();
		   flag = false;
		}
		return flag;
	}

	public void themnguoidung(String idnguoidung) {
		String INSERT_NGUOIDUNG = "INSERT INTO nguoidung VALUES ('"
				+ idnguoidung + "', '', '', ' ', '', 'NDCC');";
		mDB.execSQL(INSERT_NGUOIDUNG);
	}
	public void themnguoidung(String idnguoidung ,String password) {
		String INSERT_NGUOIDUNG = "INSERT INTO nguoidung VALUES ('"
				+ idnguoidung + "', '"+ password + "', '', ' ', '', 'NDCC');";
		mDB.execSQL(INSERT_NGUOIDUNG);
	}

	public void update(String string, ContentValues args, String where,
			String[] whereArgs) {
		String update = "UPDATE nguyenlieudicho SET isBought=" + 1
				+ " WHERE idnguyenlieu='" + whereArgs[0] + "'";
		mDB.execSQL(update);
	}

	public void update(String string) {
		mDB.execSQL(string);
	}

	public void themLichDiCho(String thu, int gio, int phut, String note) {
		ContentValues values = new ContentValues();

		values.put("Thu", thu);
		values.put("Gio", gio);
		values.put("Phut", phut);
		values.put("Note", note);
		mDB.insert("NhacNho", null, values);
	}

	public static String layidnguyenlieu(String tennguyenlieu) {
		Cursor cur;
		cur = mDB.query("nguyenlieu", new String[] { "idnguyenlieu" },
				"tennguyenlieu = '" + tennguyenlieu + "'", null, null, null,
				null);
		cur.moveToFirst();
		String Values;
		if (cur.getCount() > 0)
			Values = cur.getString(0);
		else
			Values = "";
		cur.close();
		return Values;
	}

	public static String layidmonan(String ten) {
		Cursor cur;
		cur = mDB.query("monan", new String[] { "idmonan" }, "tenmonan = '"
				+ ten + "'", null, null, null, null);
		cur.moveToFirst();
		String Values = cur.getString(0);
		cur.close();
		return Values;
	}

	public String laylinkvideothuchienmonan(String idmonan) {
		Cursor cur = null;
		cur = mDB.query("monan", new String[] { "videourl" }, "idmonan = '"
				+ idmonan + "'", null, null, null, null);
		cur.moveToFirst();
		String link = cur.getString(0);
		cur.close();
		return link;
	}

	public Cursor getBenhTheoTen(String s) {
		Cursor mCursor = null;
		mCursor = mDB.query("benh", new String[] { "tenbenh", "idbenh" },
				"tenbenh LIKE '%" + s + "%'", null, null, null, null);
		return mCursor;
	}

	public Cursor getNguyenLieuTheoTen(String s) {
		Cursor mCursor = null;
		mCursor = mDB.query("nguyenlieu", new String[] { "tennguyenlieu",
				"idnguyenlieu" }, "tennguyenlieu LIKE '%" + s + "%'", null,
				null, null, null);
		return mCursor;
	}

	public Cursor getTheLoai() {
		Cursor mCursor = null;
		mCursor = mDB.query("loaimonan", new String[] { "tenloaimonan",
				"idloaimonan" }, null, null, null, null, null);
		return mCursor;
	}

	public Cursor getTheLoaiTheoTen(String s) {
		Cursor mCursor = null;
		mCursor = mDB.query("loaimonan", new String[] { "tenloaimonan",
				"idloaimonan" }, "tenloaimonan LIKE '%" + s + "%'", null, null,
				null, null);
		return mCursor;
	}

	public Cursor laybenhtheloai(String idtheloai) {
		Cursor mCursor = null;
		mCursor = mDB.query("benh", new String[] { "tenbenh", "idbenh" },
				"idloaibenh LIKE '%" + idtheloai + "%'", null, null, null,
				null);
		return mCursor;
	}

	public ArrayList<String> tatcamonan() {
		ArrayList<String> listmonan = new ArrayList<String>();
		String sql = "SELECT idmonan FROM monan";
		Cursor cur;
		cur = mDB.rawQuery(sql, null);
		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			String tam = cur.getString(0);
			listmonan.add(tam);
			cur.moveToNext();
		}
		cur.close();
		return listmonan;
	}
}*/