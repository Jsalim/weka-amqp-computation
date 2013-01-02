package pl.poznan.put.miabsr.weka.terminator.domain;

import java.io.IOException;
import java.util.List;

import pl.poznan.put.miabsr.weka.shared.ResultPackage;

public interface ResultSaver {

	public abstract void saveResults(List<ResultPackage> resultPackages)
			throws IOException;

}