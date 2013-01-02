package pl.poznan.put.miabsr.weka.shared;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class RawInstancePackage implements Comparable<RawInstancePackage>,
		Serializable {
	private int id;

	private int count;

	private List<RawInstance> rawInstances;

	public RawInstancePackage() {
	}

	public RawInstancePackage(int id, int count, List<RawInstance> rawInstances) {
		this.id = id;
		this.count = count;
		this.rawInstances = rawInstances;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<RawInstance> getRawInstances() {
		return rawInstances;
	}

	public void setRawInstances(List<RawInstance> rawInstances) {
		this.rawInstances = rawInstances;
	}

	@Override
	public int compareTo(RawInstancePackage o) {
		return this.id - o.id;
	}

	public boolean firstBatchPackage() {
		return id == 0;
	}

	public boolean lastBatchPackage() {
		return (id + 1) == count;
	}
}
