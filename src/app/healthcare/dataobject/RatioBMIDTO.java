package app.healthcare.dataobject;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("RatioBMIDTO")
public class RatioBMIDTO extends ParseObject {
	public static ParseQuery<RatioBMIDTO> getQuery() {
		return ParseQuery.getQuery(RatioBMIDTO.class);
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
		return getString("date");
	}

	public void setDate(String value) {
		put("date", value);
	}

	public Integer getRatioBMIId() {
		return getInt("ratioBMIId");
	}

	public void setRatioBMIId(Integer value) {
		put("ratioBMIId", value);
	}

	public Double getRatio() {
		return getDouble("ratio");
	}

	public void setRatio(Double value) {
		put("ratio", value);
	}

	public String getStatus() {
		return getString("status");
	}

	public void setStatus(String value) {
		put("status", value);
	}

}
