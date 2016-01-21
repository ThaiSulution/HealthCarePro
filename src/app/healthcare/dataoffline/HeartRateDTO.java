package app.healthcare.dataoffline;

public class HeartRateDTO {
	private int id;
	private String time;
	private String date;
	private String statusSport;
	private String bodyCo;
	private String note;
	private int heartRate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatusSport() {
		return statusSport;
	}
	public void setStatusSport(String statusSport) {
		this.statusSport = statusSport;
	}
	public String getBodyCo() {
		return bodyCo;
	}
	public void setBodyCo(String bodyCo) {
		this.bodyCo = bodyCo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
}
