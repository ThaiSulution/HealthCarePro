package app.dto;

public class RatioWHRDTO {
	Integer ratioWHRId;
	String date;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	String time;
	String ratio;
	String status;
	Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRatioWHRId() {
		return ratioWHRId;
	}

	public void setRatioWHRId(Integer ratioWHRid) {
		this.ratioWHRId = ratioWHRid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
