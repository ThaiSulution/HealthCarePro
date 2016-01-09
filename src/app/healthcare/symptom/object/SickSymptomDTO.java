package app.healthcare.symptom.object;

public class SickSymptomDTO {
	private int id;
	private String tenBenh;
	private String trieuChung;
	private String viTri;

	public String getViTri() {
		return viTri;
	}

	public void setViTri(String viTri) {
		this.viTri = viTri;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTenBenh() {
		return tenBenh;
	}

	public void setTenBenh(String tenBenh) {
		this.tenBenh = tenBenh;
	}

	public String getTrieuChung() {
		return trieuChung;
	}

	public void setTrieuChung(String trieuChung) {
		this.trieuChung = trieuChung;
	}
}
