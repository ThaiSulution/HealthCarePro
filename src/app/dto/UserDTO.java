package app.dto;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("UserDTO")
public class UserDTO extends ParseObject {
	public static ParseQuery<HeartRateDTO> getQuery() {
		return ParseQuery.getQuery(HeartRateDTO.class);
	}

	public ParseUser getUser() {
		return getParseUser("user");
	}

	public void setUser(ParseUser value) {
		put("user", value);
	}

	public String getUserId() {
		return getString("userId");
	}

	public void setUserId(String value) {
		put("userId", value);
	}

	public Double getHeight() {
		return getDouble("height");
	}

	public void setHeight(Double value) {
		put("height", value);
	}

	public Double getWeight() {
		return getDouble("weight");
	}

	public void setWeight(Double value) {
		put("weight", value);
	}
	
	public int getTarget() {
		return getInt("target");
	}

	public void setTarget(int value) {
		put("target", value);
	}

	public String getSex() {
		return getString("sex");
	}

	public void setSex(String value) {
		put("sex", value);
	}
}