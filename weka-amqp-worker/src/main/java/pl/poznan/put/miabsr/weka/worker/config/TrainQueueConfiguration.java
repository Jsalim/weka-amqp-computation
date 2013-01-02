package pl.poznan.put.miabsr.weka.worker.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
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
import pl.poznan.put.miabsr.weka.worker.domain.TrainQueueListener;

@Configuration
public class TrainQueueConfiguration {
	@Bean
	public Exchange trainExchange() {
		return new FanoutExchange(AmqpConfiguration.TRAIN_EXCHANGE_NAME);
	}

	@Bean
	public Queue trainQueue() {
		return new AnonymousQueue();
	}

	@Bean
	@Autowired
	public Binding trainBinding(@Qualifier("trainQueue") Queue trainQueue,
			@Qualifier("trainExchange") Exchange trainExchange) {
		return BindingBuilder.bind(trainQueue).to(trainExchange).with("*")
				.noargs();
	}

	@Bean
	@Autowired
	public LazyMessageListenerContainer trainQueueLazyListenerContainer(
			@Qualifier("trainQueue") Queue trainQueue,
			ConnectionFactory connectionFactory,
			MessageConverter messageConverter,
			TrainQueueListener trainQueueListener) throws Exception {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(trainQueue);
		container.setMessageListener(new MessageListenerAdapter(
				trainQueueListener, messageConverter));
		return new LazyMessageListenerContainer(container);
	}
}
