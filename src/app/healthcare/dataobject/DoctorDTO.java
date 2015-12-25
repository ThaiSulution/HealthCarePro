package app.healthcare.dataobject;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("DoctorDTO")
public class DoctorDTO extends ParseObject {
	public static ParseQuery<DoctorDTO> getQuery() {
		return ParseQuery.getQuery(DoctorDTO.class);
	}

	public ParseUser getUser() {
		return getParseUser("user");
	}

	public void setUser(ParseUser value) {
		put("user", value);
	}

	public String getNumberCall() {
		return getString("numberCall");
	}

	public void setNumberCall(String value) {
		put("numberCall", value);
	}

	public String getSkypeNumber() {
		return getString("skypeNumber");
	}

	public void setSkypeNumber(String value) {
		put("skypeNumber", value);
	}
	
	public String getViberNumber() {
		return getString("viberNumber");
	}

	public void setViberNumber(String value) {
		put("viberNumber", value);
	}

	public String getDoctorName() {
		return getString("doctorName");
	}

	public void setDoctorName(String value) {
		put("doctorName", value);
	}

	public Integer getDoctorId() {
		return getInt("doctorId");
	}

	public void setDoctorId(Integer value) {
		put("doctorId", value);
	}

}