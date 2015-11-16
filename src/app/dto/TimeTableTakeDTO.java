package app.dto;

public class TimeTableTakeDTO {
	Integer timeTableTakeId;
	String sick;
	String time;
	String status;
	Integer userId;
	Integer countTime;
	String timeSpacing;
	
	
	public Integer getCountTime() {
		return countTime;
	}
	public void setCountTime(Integer countTime) {
		this.countTime = countTime;
	}
	public String getTimeSpacing() {
		return timeSpacing;
	}
	public void setTimeSpacing(String timeSpacing) {
		this.timeSpacing = timeSpacing;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTimeTableTakeId() {
		return timeTableTakeId;
	}
	public void setTimeTableTakeId(Integer timeTableTakeId) {
		this.timeTableTakeId = timeTableTakeId;
	}
	public String getSick() {
		return sick;
	}
	public void setSick(String sick) {
		this.sick = sick;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}