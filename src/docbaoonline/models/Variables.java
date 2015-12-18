package docbaoonline.models;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import app.healthcare.R;

@SuppressLint("UseSparseArrays")
public class Variables {
	// Thai update start - thay doi title
	// public static final String[] PAPERS = {"Bác Sĩ Gia Đình", "Afamily",
	// "Y Học Sức Khỏe"};
	public static final String[] PAPERS = { "Sức khỏe đời sống", "Afamily",
			"VnExpress", "KhoaHoc.TV" };
	// Thai update end
	public static final int[] ICONS = { R.drawable.skds, R.drawable.afamily,
			R.drawable.vnepress, R.drawable.khtv };
	// Thai update start - thay doi trang lay tin tuc
	// Bac si gia dinh
	// public static final String[] BACSIGIADINH_CATEGORIES = {
	// "Tin tức",
	// "Chương trình BSGD",
	// "Sức khoẻ thường thức",
	// "Dinh dưỡng", "Làm đẹp",
	// "Đời sống tinh thần",
	// "Bệnh",s	// "Sản phẩm cho sức khoẻ",
	// "Dịch vụ Y tế",
	// "Bác sĩ gia đình giải đáp",
	// "Bác sĩ gia đình Cười"};
	// public static final String[] BACSIGIADINH_LINKS = {
	// "http://www.bacsigiadinh.org/rss/tin-tuc.xml",
	// "http://www.bacsigiadinh.org/rss/chuong-trinh-bsgd.xml",
	// "http://www.bacsigiadinh.org/rss/suc-khoe-thuong-thuc.xml",
	// "http://www.bacsigiadinh.org/rss/dinh-duong.xml",
	// "http://www.bacsigiadinh.org/rss/lam-dep.xml",
	// "http://www.bacsigiadinh.org/rss/doi-song-tinh-than.xml",
	// "http://www.bacsigiadinh.org/rss/benh.xml",
	// "http://www.bacsigiadinh.org/rss/san-pham-cho-suc-khoe.xml",
	// "http://www.bacsigiadinh.org/rss/dich-vu-y-te.xml",
	// "http://www.bacsigiadinh.org/rss/bac-si-gia-dinh-giai-dap.xml"};

	public static final String[] SUCKHOEDOISONG_CATEGORIES = { "Trang chủ",
			"Bạn cần biết về y học", "Diễn đàn",
			"Tư vấn truyền hình trực tuyến", "Tra cứu sức khỏe", "Thời sự",
			"Mẹ và bé", "Dược sĩ tư vấn", "Y học cổ truyền",
			"Tình yêu và giới tính", "Văn hóa - Thể thao", "Làm đẹp",
			"Quốc tế", "Bác sĩ trả lời", "Phòng mạch Online" };
	public static final String[] SUCKHOEDOISONG_LINKS = {
			"http://suckhoedoisong.vn/rss/home",
			"http://suckhoedoisong.vn/rss/ban-can-biet-ve-y-hoc",
			"http://suckhoedoisong.vn/rss/dien-dan",
			"http://suckhoedoisong.vn/rss/tu-van-truyen-hinh-truc-tuyen",
			"http://suckhoedoisong.vn/rss/tra-cuu-suc-khoe",
			"http://suckhoedoisong.vn/rss/thoi-su",
			"http://suckhoedoisong.vn/rss/me-va-be",
			"http://suckhoedoisong.vn/rss/duoc-si-tu-van",
			"http://suckhoedoisong.vn/rss/y-hoc-co-truyen",
			"http://suckhoedoisong.vn/rss/tinh-yeu-va-gioi-tinh",
			"http://suckhoedoisong.vn/rss/van-hoa-the-thao",
			"http://suckhoedoisong.vn/rss/lam-dep",
			"http://suckhoedoisong.vn/rss/quoc-te",
			"http://suckhoedoisong.vn/rss/bac-si-tra-loi",
			"http://suckhoedoisong.vn/rss/phong-mach-online" };
	// Thai update end
	// Afamily
	public static final String[] AFAMILY_CATEGORIES = { "Trang chủ",
			"Sức khỏe", "Mẹ và bé" };
	public static final String[] AFAMILY_LINKS = {
			"http://afamily.vn/trang-chu.rss",
			"http://afamily.vn/suc-khoe.rss", "http://afamily.vn/me-va-be.rss" };
	// Thai update start - thay doi y hoc suc khoe thanh khoa hoc.tv
	// y hoc suc khoe
	// public static final String[] YHOCSUCKHOE_CATEGORIES = {
	// "Trang chủ",
	// "Sức khỏe",
	// "Tin tức"};
	// public static final String[] YHOCSUCKHOE_LINKS = {
	// "http://www.yhocsuckhoe.com/rss/_t19-m1.html?param=BF30CQM2BBIcdRdDPw4hARIfaFRcZkkWFThJCzZg",
	// "http://www.yhocsuckhoe.com/rss/_t19-m1.html?param=BCC0CQM2BBIcdRdDPw4hARIfaFRcYEkWFThJCzZg",
	// "http://www.yhocsuckhoe.com/rss/_t19-m1.html?param=BB70CQM2BBIcdRdDPw4hARIfaFRcY0kWFThJCzZg"};
	public static final String[] VNEXPRESS_CATEGORIES = { "Trang chủ",
			"Thời sự", "Thế giới", "Kinh doanh", "Giải trí ", "Thể thao",
			"Pháp luật", "Giáo dục", "Sức khỏe", "Gia đình", "Du lịch",
			"Khoa học", "Số hóa", "Cộng đồng", "Tâm sự" };
	public static final String[] VNEXPRESS_LINKS = {
			"http://vnexpress.net/rss/tin-moi-nhat.rss",
			"http://vnexpress.net/rss/thoi-su.rss",
			"http://vnexpress.net/rss/the-gioi.rss",
			"http://vnexpress.net/rss/kinh-doanh.rss",
			"http://vnexpress.net/rss/giai-tri.rss",
			"http://vnexpress.net/rss/the-thao.rss",
			"http://vnexpress.net/rss/phap-luat.rss",
			"http://vnexpress.net/rss/giao-duc.rss",
			"http://vnexpress.net/rss/suc-khoe.rss",
			"http://vnexpress.net/rss/gia-dinh.rss",
			"http://vnexpress.net/rss/du-lich.rss",
			"http://vnexpress.net/rss/khoa-hoc.rss",
			"http://vnexpress.net/rss/so-hoa.rss",
			"http://vnexpress.net/rss/cong-dong.rss",
			"http://vnexpress.net/rss/tam-su.rss" };
	// Thai update end
	public static final String[] KHOAHOCTV_CATEGORIES = { "Trang chủ",
			"Công nghệ mới", "Khoa học vũ trụ", "Khoa học máy tính",
			"Phần mềm hữu ích", "Phát minh khoa học", "Sinh vật học",
			"Khảo cổ học", "Y học - Sức khỏe", "Môi trường", "Đại dương học",
			"Thế giới động vật", "Ứng dụng khoa học", "Khám phá", "1001 bí ẩn",
			"Câu chuyện khoa học", "Công trình khoa học", "Sự kiện Khoa học",
			"Thư viện ảnh", "Góc hài hước", "Lịch sử", "Khoa học & Bạn đọc",
			"Video" };
	public static final String[] KHOAHOCTV_LINKS = {
			"http://www.KhoaHoc.tv/rss/index.aspx",
			"http://khoahoc.tv/congnghemoi/cong-nghe-moi/rss.aspx",
			"http://khoahoc.tv/khampha/vu-tru/rss.aspx",
			"http://khoahoc.tv/congnghemoi/may-tinh/rss.aspx",
			"http://khoahoc.tv/congnghemoi/phan-mem/rss.aspx",
			"http://khoahoc.tv/congnghemoi/phat-minh/rss.aspx",
			"http://khoahoc.tv/khampha/sinh-vat-hoc/rss.aspx",
			"http://khoahoc.tv/khampha/khao-co-hoc/rss.aspx",
			"http://khoahoc.tv/doisong/yhoc/rss.aspx",
			"http://khoahoc.tv/doisong/moi-truong/rss.aspx",
			"http://khoahoc.tv/khampha/dai-duong-hoc/rss.aspx",
			"http://khoahoc.tv/khampha/the-gioi-dong-vat/rss.aspx",
			"http://khoahoc.tv/doisong/ung-dung/rss.aspx",
			"http://khoahoc.tv/khampha/kham-pha/rss.aspx",
			"http://khoahoc.tv/khampha/1001-bi-an/rss.aspx",
			"http://khoahoc.tv/sukien/cau-chuyen/rss.aspx",
			"http://khoahoc.tv/sukien/cong-trinh/rss.aspx",
			"http://khoahoc.tv/sukien/su-kien/rss.aspx",
			"http://khoahoc.tv/giaitri/thu-vien-anh/rss.aspx",
			"http://khoahoc.tv/giaitri/hai-huoc/rss.aspx",
			"http://khoahoc.tv/khampha/lich-su/rss.aspx",
			"http://khoahoc.tv/bandoc/ban-doc/rss.aspx",
			"http://khoahoc.tv/giaitri/video/rss.aspx" };
	// All
	public static final String[][] CATEGORIES = { SUCKHOEDOISONG_CATEGORIES,
			AFAMILY_CATEGORIES, VNEXPRESS_CATEGORIES , KHOAHOCTV_CATEGORIES};
	public static final String[][] LINKS = { SUCKHOEDOISONG_LINKS,
			AFAMILY_LINKS, VNEXPRESS_LINKS ,KHOAHOCTV_LINKS};

	public static final String paper = "paper";
	public static final String category = "category";
	public static final String key = "key";
	public static final String position = "position";

	public static HashMap<Integer, List<RssItem>> MAP = new HashMap<Integer, List<RssItem>>();
}
