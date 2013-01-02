package pl.poznan.put.miabsr.weka.initiator.domain.evaluation;

import java.util.List;

import pl.poznan.put.miabsr.weka.shared.RawInstance;

public interface EvaluationDistributer {
	void distributeEvaluation(List<RawInstance> records);
}
