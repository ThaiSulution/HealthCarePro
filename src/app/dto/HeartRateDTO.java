package app.dto;

public class HeartRateDTO {
	Integer heartRateId;
	String time;
	Integer heartRate;
	int statusSport;
	int bodyCo;
	String note;

	public int getStatusSport() {
		return statusSport;
	}

	public void setStatusSport(int status) {
		this.statusSport = status;
	}

	public int getBodyCo() {
		return bodyCo;
	}

	public void setBodyCo(int bodyCo) {
		this.bodyCo = bodyCo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getHeartRateId() {
		return heartRateId;
	}

	public void setHeartRateId(Integer heartRateId) {
		this.heartRateId = heartRateId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

}
