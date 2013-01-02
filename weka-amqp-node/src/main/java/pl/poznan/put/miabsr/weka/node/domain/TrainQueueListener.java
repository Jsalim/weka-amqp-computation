package pl.poznan.put.miabsr.weka.node.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.node.config.LazyMessageListenerContainer;
import pl.poznan.put.miabsr.weka.shared.RawInstancePackage;

@Component
public class TrainQueueListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("testQueueLazyListenerContainer")
	private LazyMessageListenerContainer testListenerContainer;

	@Autowired
	private ShuttleEvaluationService evaluationService;

	public void handleMessage(RawInstancePackage dataPackage) throws Exception {
		if (dataPackage.firstBatchPackage()) {
			logger.debug(
					"Received training data from first package from batch. Stopping test queue listener...",
					dataPackage.getId());
			testListenerContainer.getMessageListenerContainer().stop();
			evaluationService.resetClassfier();
		}
		train(dataPackage);
		if (dataPackage.lastBatchPackage()) {
			logger.debug(
					"Received training data from last package from batch. Starting test queue listener...",
					dataPackage.getId());
			testListenerContainer.getMessageListenerContainer().start();
		}
	}

	private void train(RawInstancePackage dataPackage) throws Exception {
		logger.debug("Received training data package. Id: {}",
				dataPackage.getId());
		evaluationService.train(dataPackage);
	}
}
