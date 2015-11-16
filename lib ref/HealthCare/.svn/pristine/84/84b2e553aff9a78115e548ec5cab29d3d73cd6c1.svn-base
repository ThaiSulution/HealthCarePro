package app.healthcare;

import android.text.format.Time;

public class Constants {
	public static final String KEY_HISCORE = "hiscore";
	public static final String CHECK_DATA = "createdata";
	public static final String CHECK_TIME = "time";
	public static final String TIME_COUNT = "THIEN1";
	public static final String COUNT_TIME = "THIEN";
	public static final String INTENT_GET_BEAT = "INTENT_GET_BEAT";
	public int TARGETS = 1500;

	Time today = new Time(Time.getCurrentTimezone());

	public static class ActionReceiver {
		public static final String ACTION_RECEIVER = "ACTION_RECEIVER";
		public static final String ACTION_VALUE = "ACTION_VALUE";
		public static final String SHOW_TEXT = "SHOW_TEXT";

	}

	private Constants() {

	}

	private static class ConstantsHolder {
		public static final Constants INSTANCE = new Constants();
	}

	public static Constants getInstance() {
		return ConstantsHolder.INSTANCE;
	}

	public void setTargets(int targets) {
		TARGETS = targets;
	}

	public Time getTime() {
		return today;
	}
}