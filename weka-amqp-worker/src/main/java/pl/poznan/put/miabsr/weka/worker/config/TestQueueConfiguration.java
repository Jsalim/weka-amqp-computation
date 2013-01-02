package pl.poznan.put.miabsr.weka.worker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.poznan.put.miabsr.weka.shared.config.AmqpConfiguration;
import pl.poznan.put.miabsr.weka.worker.domain.TestQueueListener;

@Configuration
public class TestQueueConfiguration {
	@Bean
	public Queue testQueue() {
		return new Queue(AmqpConfiguration.TEST_QUEUE_NAME);
	}

	@Bean
	@Autowired
	public LazyMessageListenerContainer testQueueLazyListenerContainer(
			@Qualifier("testQueue") Queue testQueue,
			ConnectionFactory connectionFactory,
			MessageConverter messageConverter,
			TestQueueListener testQueueListener) throws Exception {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(testQueue);
		container.setPrefetchCount(1);
		container.setMessageListener(new MessageListenerAdapter(
				testQueueListener, messageConverter));
		return new LazyMessageListenerContainer(container);
	}
}
