package app.dto;

public class StepRunDTO {
	Integer stepRunId;
	String time;
	Integer tagets;
	Integer userId;
	Integer totalRun;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStepRunId() {
		return stepRunId;
	}

	public void setStepRunId(Integer stepRunId) {
		this.stepRunId = stepRunId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String totalTime) {
		this.time = totalTime;
	}

	public Integer getTagets() {
		return tagets;
	}

	public void setTagets(Integer tagets) {
		this.tagets = tagets;
	}

	public Integer getTotalRun() {
		return totalRun;
	}

	public void setTotalRun(Integer totalRun) {
		this.totalRun = totalRun;
	}
}