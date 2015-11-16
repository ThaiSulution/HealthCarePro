package docbaoonline.models;

import java.util.HashMap;
import java.util.List;

import docbaoonline.activity.R;

public class Variables {

	public static final String[] PAPERS = {"Bác Sĩ Gia Đình",  "Afamily", "Y Học Sức Khỏe"};
	public static final int[] ICONS = {R.drawable.doctor, R.drawable.afamily, R.drawable.yhocsuckhoe};
	
	//Bac si gia dinh
	public static final String[] BACSIGIADINH_CATEGORIES = {
		"Tin tức", 
		"Chương trình BSGD", 
		"Sức khoẻ thường thức",
		"Dinh dưỡng", "Làm đẹp", 
		"Đời sống tinh thần", 
		"Bệnh", 
		"Sản phẩm cho sức khoẻ", 
		"Dịch vụ Y tế",
		"Bác sĩ gia đình giải đáp", 
		"Bác sĩ gia đình Cười"};
	public static final String[] BACSIGIADINH_LINKS = {
		"http://www.bacsigiadinh.org/rss/tin-tuc.xml",
		"http://www.bacsigiadinh.org/rss/chuong-trinh-bsgd.xml", 
		"http://www.bacsigiadinh.org/rss/suc-khoe-thuong-thuc.xml",
		"http://www.bacsigiadinh.org/rss/dinh-duong.xml",
		"http://www.bacsigiadinh.org/rss/lam-dep.xml",
		"http://www.bacsigiadinh.org/rss/doi-song-tinh-than.xml",
		"http://www.bacsigiadinh.org/rss/benh.xml",
		"http://www.bacsigiadinh.org/rss/san-pham-cho-suc-khoe.xml",
		"http://www.bacsigiadinh.org/rss/dich-vu-y-te.xml",
		"http://www.bacsigiadinh.org/rss/bac-si-gia-dinh-giai-dap.xml"};
	
	//Afamily
	public static final String[] AFAMILY_CATEGORIES = {
		"Trang chủ", 
		"Sức khỏe", 
		"Mẹ và bé"};
	public static final String[] AFAMILY_LINKS = {
		"http://afamily.vn/trang-chu.rss",
		"http://afamily.vn/suc-khoe.rss",
		"http://afamily.vn/me-va-be.rss"};
	
	//y hoc suc khoe
	public static final String[] YHOCSUCKHOE_CATEGORIES = {
		"Cẩm Nang Bệnh",
		"Sức khỏe",
		"Tin tức"};
	public static final String[] YHOCSUCKHOE_LINKS = {
		"http://www.yhocsuckhoe.com/rss/_t19-m1.html?param=BF30CQM2BBIcdRdDPw4hARIfaFRcZkkWFThJCzZg",
		"http://www.yhocsuckhoe.com/rss/_t19-m1.html?param=BCC0CQM2BBIcdRdDPw4hARIfaFRcYEkWFThJCzZg",
		"http://www.yhocsuckhoe.com/rss/_t19-m1.html?param=BB70CQM2BBIcdRdDPw4hARIfaFRcY0kWFThJCzZg"};

	
	//All
	public static final String[][] CATEGORIES = {BACSIGIADINH_CATEGORIES, AFAMILY_CATEGORIES, YHOCSUCKHOE_CATEGORIES};
	public static final String[][] LINKS = {BACSIGIADINH_LINKS, AFAMILY_LINKS, YHOCSUCKHOE_LINKS};
	
	public static final String paper = "paper";
	public static final String category = "category";
	public static final String key = "key";
	public static final String position = "position";
	
	public static HashMap<Integer, List<RssItem>> MAP = new HashMap<Integer, List<RssItem>>();
}
