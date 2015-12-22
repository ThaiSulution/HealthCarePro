package app.healthcare.readnews.models;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import app.healthcare.R;

@SuppressLint("UseSparseArrays")
public class Variables {
	public static final String[] PAPERS = { "Sức khỏe đời sống", "Afamily",
			"VnExpress" };
	public static final int[] ICONS = { R.drawable.skds, R.drawable.afamily,
			R.drawable.vnepress };
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
	public static final String[] AFAMILY_CATEGORIES = { "Trang chủ",
			"Sức khỏe", "Mẹ và bé" };
	public static final String[] AFAMILY_LINKS = {
			"http://afamily.vn/trang-chu.rss",
			"http://afamily.vn/suc-khoe.rss", "http://afamily.vn/me-va-be.rss" };
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
	public static final String[][] CATEGORIES = { SUCKHOEDOISONG_CATEGORIES,
			AFAMILY_CATEGORIES, VNEXPRESS_CATEGORIES };
	public static final String[][] LINKS = { SUCKHOEDOISONG_LINKS,
			AFAMILY_LINKS, VNEXPRESS_LINKS };

	public static final String paper = "paper";
	public static final String category = "category";
	public static final String key = "key";
	public static final String position = "position";

	public static HashMap<Integer, List<RssItem>> MAP = new HashMap<Integer, List<RssItem>>();
}
