package app.healthcare.dataoffline;

public class StepRunDTO {
	private int id;
	private int taget;
	private int step;
	private float distance;
	private float calos;
	private int time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaget() {
		return taget;
	}
	public void setTaget(int taget) {
		this.taget = taget;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getCalos() {
		return calos;
	}
	public void setCalos(float calos) {
		this.calos = calos;
	}
	public float getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
