package app.dto;

public class UserDTO {
	Integer userId;
	String userName;
	String timeStrat;
	Integer target;
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTimeStrat() {
		return timeStrat;
	}
	public void setTimeStrat(String timeStrat) {
		this.timeStrat = timeStrat;
	}
}