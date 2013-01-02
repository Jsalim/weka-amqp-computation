package pl.poznan.put.miabsr.weka.initiator.domain.training;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.RawInstance;
import pl.poznan.put.miabsr.weka.shared.RawInstancePackage;

@Component
public class AmqpTrainDataSender implements TrainDataSender {

	private static final int TRAIN_BATCH_SIZE = 100;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("trainExchangeTemplate")
	private volatile RabbitTemplate template;

	@Override
	public void trainNodes(List<RawInstance> records) {
		int recordsCount = records.size();
		int batchCount = (int) Math.ceil(recordsCount / TRAIN_BATCH_SIZE * 1.0);
		int currentIndex = 0;
		int id = 0;
		while (currentIndex + 1 < recordsCount) {
			logger.trace("Sending training package: {}/{}.", id, batchCount);

			int copyToIndex = Math.min(currentIndex + TRAIN_BATCH_SIZE,
					recordsCount);
			List<RawInstance> batchRecords = records.subList(currentIndex,
					copyToIndex);
			template.convertAndSend(new RawInstancePackage(id, batchCount,
					batchRecords));
			currentIndex = copyToIndex;
			++id;
		}
	}
}
