package pl.poznan.put.miabsr.weka.node.domain;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.RawInstance;
import pl.poznan.put.miabsr.weka.shared.RawInstancePackage;
import pl.poznan.put.miabsr.weka.shared.Result;
import pl.poznan.put.miabsr.weka.shared.ResultPackage;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

@Component
public class ShuttleEvaluationService implements EvaluationService {
	private NaiveBayesUpdateable naiveBayesClassifier;

	private static final int ATTRIBUTES_COUNT = 10;

	private static final String ATTRIBUTE_SEPARATOR = "\\s+";

	private static final String[] CLASS_NOMINAL_VALUES = { "1", "2", "3", "4",
			"5", "6", "7" };

	private String filterClassValue = null;

	@Override
	public void train(RawInstancePackage dataPackage) throws Exception {
		Instances instances = loadInstances(getRawTextInstances(dataPackage));
		if (naiveBayesClassifier == null) {
			naiveBayesClassifier = new NaiveBayesUpdateable();
			naiveBayesClassifier.buildClassifier(instances);
		} else {
			int count = instances.numInstances();
			for (int i = 0; i < count; ++i) {
				naiveBayesClassifier.updateClassifier(instances.instance(i));
			}
		}
	}

	@Override
	public ResultPackage evaluate(RawInstancePackage testData) throws Exception {
		Instances instances = loadInstances(getRawTextInstances(testData));
		Evaluation eval = new Evaluation(instances);
		double[] predictions = eval.evaluateModel(naiveBayesClassifier,
				instances);
		return buildResult(testData, predictions);
	}

	@Override
	public void setFilterClassValue(String value) {
		filterClassValue = value;
	}

	@Override
	public void resetClassfier() {
		naiveBayesClassifier = null;
	}

	private ResultPackage buildResult(RawInstancePackage testData,
			double[] predictionIdxs) {
		List<Result> results = new ArrayList<Result>();
		int idx = 0;
		for (RawInstance rawInstance : testData.getRawInstances()) {
			int predictionIdx = (int) predictionIdxs[idx];
			String prediction = CLASS_NOMINAL_VALUES[predictionIdx];
			if (filterClassValue == null
					|| !filterClassValue.equals(prediction)) {
				results.add(new Result(rawInstance.getId(), prediction));
			}
		}
		return new ResultPackage(testData.getId(), testData.getCount(), results);
	}

	private Instances loadInstances(List<String> rawTextInstances)
			throws FileNotFoundException {
		Instances instances = createInstances();
		loadInstances(rawTextInstances, instances);
		return instances;
	}

	private Instances createInstances() {
		FastVector attributes = new FastVector();
		for (int i = 0; i < ATTRIBUTES_COUNT - 1; ++i) {
			attributes.addElement(new Attribute("attr" + i));
		}
		FastVector classNominalValues = new FastVector();
		for (String classNominalValue : CLASS_NOMINAL_VALUES) {
			classNominalValues.addElement(classNominalValue);
		}
		attributes.addElement(new Attribute("classAttr", classNominalValues));
		Instances dataset = new Instances("dataset", attributes, 0);
		dataset.setClassIndex(ATTRIBUTES_COUNT - 1);
		return dataset;
	}

	private void loadInstances(List<String> rawTextInstances,
			Instances instances) throws FileNotFoundException {
		for (String record : rawTextInstances) {
			if (record.length() > 0) {
				String[] chunks = record.split(ATTRIBUTE_SEPARATOR);
				Instance instance = new Instance(ATTRIBUTES_COUNT);
				for (int i = 0; i < ATTRIBUTES_COUNT - 1; ++i) {
					instance.setValue(instances.attribute(i),
							Integer.parseInt(chunks[i]));
				}
				instance.setValue(instances.attribute(ATTRIBUTES_COUNT - 1),
						chunks[ATTRIBUTES_COUNT - 1]);
				instances.add(instance);
			}
		}
	}

	private List<String> getRawTextInstances(
			RawInstancePackage rawInstancePackage) {
		List<String> rawTextInstances = new ArrayList<String>();
		for (RawInstance rawInstance : rawInstancePackage.getRawInstances()) {
			rawTextInstances.add(rawInstance.getRecord());
		}
		return rawTextInstances;
	}

}
