package app.dto;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("HeartRateDTO")
public class HeartRateDTO extends ParseObject {
	public static ParseQuery<HeartRateDTO> getQuery() {
		return ParseQuery.getQuery(HeartRateDTO.class);
	}

	public ParseUser getUser() {
		return getParseUser("user");
	}

	public void setUser(ParseUser value) {
		put("user", value);
	}

	public String getTime() {
		return getString("time");
	}

	public void setTime(String value) {
		put("time", value);
	}

	public String getDate() {
		return getString("date1");
	}

	public void setDate(String value) {
		put("date1", value);
	}

	public int getStatusSport() {
		return getInt("statusSport");
	}

	public void setStatusSport(int value) {
		put("statusSport", value);
	}

	public int getBodyCo() {
		return getInt("bodyCo");
	}

	public void setBodyCo(int value) {
		put("bodyCo", value);
	}

	public String getNote() {
		return getString("note");
	}

	public void setNote(String value) {
		put("note", value);
	}

	public Integer getHeartRateId() {
		return getInt("heartRateId");
	}

	public void setHeartRateId(Integer value) {
		put("heartRateId", value);
	}

	public Integer getHeartRate() {
		return getInt("heartRate");
	}

	public void setHeartRate(Integer value) {
		put("heartRate", value);
	}

}
