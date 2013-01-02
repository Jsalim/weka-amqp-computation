package pl.poznan.put.miabsr.weka.initiator;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.initiator.domain.FileDataLoader;
import pl.poznan.put.miabsr.weka.initiator.domain.evaluation.TestDataSender;
import pl.poznan.put.miabsr.weka.initiator.domain.training.TrainDataSender;
import pl.poznan.put.miabsr.weka.initiator.utils.Timer;
import pl.poznan.put.miabsr.weka.shared.RawInstance;

/**
 * Implementation of {@link Initiator}
 * 
 * @author pmendelski
 * 
 */
@Component
public class InitiatorImpl implements Initiator {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TrainDataSender nodeTrainer;

	@Autowired
	private FileDataLoader fileDataLoader;

	@Autowired
	private TestDataSender evaluationDistributer;

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Override
	public void sendTrainData(String filePath) throws FileNotFoundException {
		List<RawInstance> records = loadRecords(filePath);
		logger.info("Starting node training process");
		Timer timer = new Timer().start();
		nodeTrainer.trainNodes(records);
		timer.stop();
		logger.info("Finished node training process. Time: {}",
				timer.getFormattedResult());
	}

	@Override
	public void sendTestData(String filePath) throws FileNotFoundException {
		List<RawInstance> records = loadRecords(filePath);
		logger.info("Starting distributed evaluation process");
		Timer timer = new Timer().start();
		evaluationDistributer.distributeEvaluation(records);
		timer.stop();
		logger.info("Finished distributed evaluation process. Time: {}",
				timer.getFormattedResult());
	}

	private List<RawInstance> loadRecords(String filePath)
			throws FileNotFoundException {
		logger.info("Loading file: {}", filePath);
		Timer timer = new Timer().start();
		List<RawInstance> records = fileDataLoader.loadFile(filePath);
		timer.stop();
		logger.info("File loaded. Time: {}", timer.getFormattedResult());
		return records;
	}

	@Override
	public void stop() {
		connectionFactory.destroy();
	}
}
