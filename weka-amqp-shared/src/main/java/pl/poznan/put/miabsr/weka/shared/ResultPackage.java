package pl.poznan.put.miabsr.weka.shared;

import java.util.List;

public class ResultPackage implements Comparable<ResultPackage> {
	private int id;

	private int count;

	private List<Result> results;

	public ResultPackage() {

	}

	public ResultPackage(int id, int count, List<Result> results) {
		this.id = id;
		this.count = count;
		this.results = results;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@Override
	public int compareTo(ResultPackage o) {
		return this.id - o.id;
	}

}
