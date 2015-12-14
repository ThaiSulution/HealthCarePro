package app.dto;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("StepRunDTO")
public class StepRunDTO extends ParseObject {
	public static ParseQuery<StepRunDTO> getQuery() {
		return ParseQuery.getQuery(StepRunDTO.class);
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

	public int getTarget() {
		return getInt("target");
	}

	public void setTarget(int value) {
		put("target", value);
	}

	public int getStep() {
		return getInt("step");
	}

	public void setStep(int value) {
		put("step", value);
	}

	public Double getDistance() {
		return getDouble("step");
	}

	public void setDistance(Double value) {
		put("step", value);
	}

	public Double getCalos() {
		return getDouble("calos");
	}

	public void setCalos(Double value) {
		put("calos", value);
	}

	public Integer getStepID() {
		return getInt("stepID");
	}

	public void setStepID(Integer value) {
		put("stepID", value);
	}

	public long getTime() {
		return getLong("time");
	}

	public void setTime(long value) {
		put("time", value);
	}
}
