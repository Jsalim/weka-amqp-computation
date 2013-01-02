package pl.poznan.put.miabsr.weka.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RawInstance implements Comparable<RawInstance>, Serializable {
	private int id;

	private String record;

	public RawInstance() {
	}

	public RawInstance(int id, String record) {
		this.id = id;
		this.record = record;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public int getId() {
		return id;
	}

	public String getRecord() {
		return record;
	}

	@Override
	public int compareTo(RawInstance o) {
		return this.id - o.id;
	}

}
