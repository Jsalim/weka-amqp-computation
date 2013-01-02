package pl.poznan.put.miabsr.weka.worker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.RawInstancePackage;
import pl.poznan.put.miabsr.weka.shared.ResultPackage;

@Component
public class TestQueueListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ShuttleEvaluationService evaluationService;

	@Autowired
	@Qualifier("resultQueueTemplate")
	private RabbitTemplate resultQueueTemplate;

	public void handleMessage(RawInstancePackage dataPackage) throws Exception {
		logger.debug("Received test data package. Id: {}", dataPackage.getId());
		ResultPackage result = evaluationService.evaluate(dataPackage);
		resultQueueTemplate.convertAndSend(result);
	}
}
