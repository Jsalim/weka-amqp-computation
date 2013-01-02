package pl.poznan.put.miabsr.weka.shared;

public class Result {
	private int id;

	private String value;

	public Result() {
	}

	public Result(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
