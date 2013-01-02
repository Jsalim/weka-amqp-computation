package pl.poznan.put.miabsr.weka.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.node.config.LazyMessageListenerContainer;
import pl.poznan.put.miabsr.weka.node.domain.EvaluationService;

/**
 * Implementation of {@link Node}
 * 
 * @author pmendelski
 * 
 */
@Component
public class NodeImpl implements Node {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Autowired
	@Qualifier("trainQueueLazyListenerContainer")
	private LazyMessageListenerContainer trainListenerContainer;

	@Autowired
	@Qualifier("testQueueLazyListenerContainer")
	private LazyMessageListenerContainer testListenerContainer;

	@Autowired
	private EvaluationService evaluationService;

	@Override
	public void activate(String filteredClassValue) {
		trainListenerContainer.getMessageListenerContainer().start();
		evaluationService.setFilterClassValue(filteredClassValue);
	}

	@Override
	public void stop() {
		trainListenerContainer.getMessageListenerContainer().stop();
		testListenerContainer.getMessageListenerContainer().stop();
		connectionFactory.destroy();
	}

}
