package pl.poznan.put.miabsr.weka.node.domain;

import pl.poznan.put.miabsr.weka.shared.RawInstancePackage;
import pl.poznan.put.miabsr.weka.shared.ResultPackage;

public interface EvaluationService {
	void train(RawInstancePackage dataPackage) throws Exception;

	ResultPackage evaluate(RawInstancePackage testData) throws Exception;

	void resetClassfier();

	void setFilterClassValue(String value);
}
