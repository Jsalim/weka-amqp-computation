package pl.poznan.put.miabsr.weka.initiator.domain.evaluation;

import java.util.List;

import pl.poznan.put.miabsr.weka.shared.RawInstance;

public interface TestDataSender {
	void distributeEvaluation(List<RawInstance> records);
}
