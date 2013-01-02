package pl.poznan.put.miabsr.weka.initiator.domain.training;

import java.util.List;

import pl.poznan.put.miabsr.weka.shared.RawInstance;

public interface NodeTrainer {
	void trainNodes(List<RawInstance> records);
}