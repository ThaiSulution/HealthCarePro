package app.healthcare.dataoffline;

public class DoctorDTO {
	private String numerCall;
	private String viber;
	private String skype;
	private String name;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumerCall() {
		return numerCall;
	}

	public void setNumerCall(String numerCall) {
		this.numerCall = numerCall;
	}

	public String getViber() {
		return viber;
	}

	public void setViber(String viber) {
		this.viber = viber;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
