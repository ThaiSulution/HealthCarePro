package app.healthcare.readnews.models;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import app.healthcare.R;

@SuppressLint("UseSparseArrays")
public class Variables {
	public static final String[] PAPERS = { "Tuổi trẻ Online", "VnExpress",
			"Afamily", "General Health and Medical Channels" };
	public static final int[] ICONS = { R.drawable.tto, R.drawable.vnepress,
			R.drawable.afamily, R.drawable.medicinenet_logo };
	public static final String[] TUOITRE_CATEGORIES = { "Trang chủ",
			"Chính trị - Xã hội", "Thế giới", "Pháp luật", "Kinh tế",
			"Sống khỏe", "Giáo dục", "Thể thao", "Văn hóa - Giải trí",
			"Nhịp sống trẻ", "Nhịp sống số", "Bạn đọc", "Du lịch" };
	public static final String[] TUOITRE_LINKS = {
			"http://tuoitre.vn/rss/tt-tin-moi-nhat.rss",
			"http://tuoitre.vn/rss/tt-chinh-tri-xa-hoi.rss",
			"http://tuoitre.vn/rss/tt-the-gioi.rss",
			"http://tuoitre.vn/rss/tt-phap-luat.rss",
			"http://tuoitre.vn/rss/tt-kinh-te.rss",
			"http://tuoitre.vn/rss/tt-song-khoe.rss",
			"http://tuoitre.vn/rss/tt-giao-duc.rss",
			"http://tuoitre.vn/rss/tt-the-thao.rss",
			"http://tuoitre.vn/rss/tt-van-hoa-giai-tri.rss",
			"http://tuoitre.vn/rss/tt-nhip-song-tre.rss",
			"http://tuoitre.vn/rss/tt-nhip-song-so.rss",
			"http://tuoitre.vn/rss/tt-ban-doc.rss",
			"http://tuoitre.vn/rss/tt-du-lich.rss" };
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
	public static final String[] MEDICINE_CATEGORIES = { "Allergies",
			"Lung Conditions", "Alzheimer's Disease", "Medications",
			"Arthritis", "Menopause", "Asthma", "Men's health", "Cancer",
			"Mental Health", "Cholesterol", "Migraine Headaches",
			"Chronic Pain", "Neurology", "Cold & Flu",
			"Nutrition, Food, & Recipes", "Depression", "Oral Health",
			"Diabetes", "Pediatrics / Healthy Kids",
			"Diet & Weight Management", "Pregnancy", "Digestion",
			"Prevention & Wellness", "Exercise & Fitness", "Senior Health",
			"Eyesight", "Sexual Health", "Hearing", "Skin", "Heart", "Sleep",
			"High Blood Pressure", "Thyroid", "HIV", "Travel Health",
			"Infectious Disease", "Women's Health" };
	public static final String[] MEDICINE_LINKS = {
			"http://www.medicinenet.com/rss/general/allergies.xml",
			"http://www.medicinenet.com/rss/general/lung_conditions.xml",
			"http://www.medicinenet.com/rss/general/alzheimers.xml",
			"http://www.medicinenet.com/rss/general/medications.xml",
			"http://www.medicinenet.com/rss/general/arthritis.xml",
			"http://www.medicinenet.com/rss/general/menopause.xml",
			"http://www.medicinenet.com/rss/general/asthma.xml",
			"http://www.medicinenet.com/rss/general/mens_health.xml",
			"http://www.medicinenet.com/rss/general/cancer.xml",
			"http://www.medicinenet.com/rss/general/mental_health.xml",
			"http://www.medicinenet.com/rss/general/cholesterol.xml",
			"http://www.medicinenet.com/rss/general/migraine.xml",
			"http://www.medicinenet.com/rss/general/chronic_pain.xml",
			"http://www.medicinenet.com/rss/general/neurology.xml",
			"http://www.medicinenet.com/rss/general/cold_and_flu.xml",
			"http://www.medicinenet.com/rss/general/nutrition_food_and_recipes.xml",
			"http://www.medicinenet.com/rss/general/depression.xml",
			"http://www.medicinenet.com/rss/general/oral_health.xml",
			"http://www.medicinenet.com/rss/general/diabetes.xml",
			"http://www.medicinenet.com/rss/general/kids_health.xml",
			"http://www.medicinenet.com/rss/general/diet_and_weight_management.xml",
			"http://www.medicinenet.com/rss/general/pregnancy.xml",
			"http://www.medicinenet.com/rss/general/digestion.xml",
			"http://www.medicinenet.com/rss/general/prevention_and_wellness.xml",
			"http://www.medicinenet.com/rss/general/exercise_and_fitness.xml",
			"http://www.medicinenet.com/rss/general/senior_health.xml",
			"http://www.medicinenet.com/rss/general/eyesight.xml",
			"http://www.medicinenet.com/rss/general/sexual_health.xml",
			"http://www.medicinenet.com/rss/general/hearing.xml",
			"http://www.medicinenet.com/rss/general/skin.xml",
			"http://www.medicinenet.com/rss/general/heart.xml",
			"http://www.medicinenet.com/rss/general/sleep.xml",
			"http://www.medicinenet.com/rss/general/high_blood_pressure.xml",
			"http://www.medicinenet.com/rss/general/thyroid.xml",
			"http://www.medicinenet.com/rss/general/hiv.xml",
			"http://www.medicinenet.com/rss/general/travel_health.xml",
			"http://www.medicinenet.com/rss/general/infectious_disease.xml",
			"http://www.medicinenet.com/rss/general/womens_health.xml"

	};
	public static final String[][] CATEGORIES = { TUOITRE_CATEGORIES,
			VNEXPRESS_CATEGORIES, AFAMILY_CATEGORIES, MEDICINE_CATEGORIES };
	public static final String[][] LINKS = { TUOITRE_LINKS, VNEXPRESS_LINKS,
			AFAMILY_LINKS, MEDICINE_LINKS };

	public static final String paper = "paper";
	public static final String category = "category";
	public static final String key = "key";
	public static final String position = "position";

	public static HashMap<Integer, List<RssItem>> MAP = new HashMap<Integer, List<RssItem>>();
}
