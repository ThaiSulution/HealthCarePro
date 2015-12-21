package app.healthcare;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.text.format.Time;
import app.healthcare.dataobject.DoctorDTO;
import app.healthcare.dataobject.HeartRateDTO;
import app.healthcare.dataobject.RatioBMIDTO;
import app.healthcare.dataobject.RatioWHRDTO;
import app.healthcare.dataobject.StepRunDTO;

public class Constants {
	public static final String CHECK_DATA = "createdata";
	public static final String CHECK_TIME = "time";
	public static final String INTENT_GET_BEAT = "INTENT_GET_BEAT";
	public List<Fragment> activeFuntion;
	private int heartRate;
	private int heartRateAvg;
	private long stepRuns;
	private float calos;
	private int totalHour;
	private float distance;
	private long target;
	private long stepsAvg;
	public int sex = 0;
	public int age = 0;
	public String email = "";
	public String name = "";
	public boolean isStart = true;
	public List<RatioBMIDTO> listDataBMI = new ArrayList<RatioBMIDTO>();
	public List<RatioWHRDTO> listDataWHR = new ArrayList<RatioWHRDTO>();
	public List<HeartRateDTO> listDataHR = new ArrayList<HeartRateDTO>();
	public List<StepRunDTO> listDataStepDTO = new ArrayList<StepRunDTO>();
	public List<DoctorDTO> listDoctorDTO = new ArrayList<DoctorDTO>();
	public ArrayList<HistoryStepObject> listDataStep = new ArrayList<HistoryStepObject>();

	public long getStepsAvg() {
		return stepsAvg;
	}

	public void setStepsAvg(long stepsAvg) {
		this.stepsAvg = stepsAvg;
	}

	public long getTarget() {
		return target;
	}

	public void setTarget(long target) {
		this.target = target;
	}

	public int getHeartRate() {

		return heartRate;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public int getHeartRateAvg() {
		return heartRateAvg;
	}

	public void setHeartRateAvg(int heartRateAvg) {
		this.heartRateAvg = heartRateAvg;
	}

	public long getStepRuns() {
		return stepRuns;
	}

	public void setStepRuns(long l) {
		this.stepRuns = l;
	}

	public float getCalos() {
		return calos;
	}

	public void setCalos(float calos) {
		this.calos = calos;
	}

	public int getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(int totalHour) {
		this.totalHour = totalHour;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	Time today = new Time(Time.getCurrentTimezone());

	public static class ActionReceiver {
		public static final String ACTION_RECEIVER = "ACTION_RECEIVER";
		public static final String ACTION_VALUE = "ACTION_VALUE";
		public static final String SHOW_TEXT = "SHOW_TEXT";

	}

	private Constants() {
		activeFuntion = new ArrayList<Fragment>();
		heartRate = 0;
		heartRateAvg = 0;
		stepRuns = 0;
		calos = 0;
		totalHour = 0;
		distance = 0;
		target = 0;
		stepsAvg = 0;
	}

	private static class ConstantsHolder {
		public static final Constants INSTANCE = new Constants();
	}

	public static Constants getInstance() {
		return ConstantsHolder.INSTANCE;
	}

	public Time getTime() {
		return today;
	}
}