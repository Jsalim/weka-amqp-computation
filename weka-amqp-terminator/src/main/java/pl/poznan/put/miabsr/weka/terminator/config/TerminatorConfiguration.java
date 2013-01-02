package pl.poznan.put.miabsr.weka.terminator.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.poznan.put.miabsr.weka.shared.config.AmqpConfiguration;
import pl.poznan.put.miabsr.weka.terminator.domain.ResultQueueListener;

/**
 * Application base configuration.
 * 
 * @author pmendelski
 * 
 */
@Configuration
@ComponentScan(basePackages = { "pl.poznan.put.miabsr.weka.terminator" })
public class TerminatorConfiguration {

	@Bean
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
				AmqpConfiguration.HOST, AmqpConfiguration.PORT);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}

	@Bean
	public MessageConverter messageConverter() {
		return new JsonMessageConverter();
	}

	@Bean
	public Queue resultQueue() {
		return new Queue(AmqpConfiguration.RESULT_QUEUE_NAME);
	}

	@Bean
	@Autowired
	public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	@Autowired
	public SimpleMessageListenerContainer testQueueLazyListenerContainer(
			@Qualifier("resultQueue") Queue resultQueue,
			ConnectionFactory connectionFactory,
			MessageConverter messageConverter,
			ResultQueueListener resultQueueListener) throws Exception {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(resultQueue);
		container.setMessageListener(new MessageListenerAdapter(
				resultQueueListener, messageConverter));
		return container;
	}

	@Bean
	@Autowired
	public RabbitTemplate resultQueueTemplate(
			ConnectionFactory connectionFactory,
			MessageConverter messageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setQueue(AmqpConfiguration.RESULT_QUEUE_NAME);
		template.setRoutingKey(AmqpConfiguration.RESULT_QUEUE_NAME);
		template.setMessageConverter(messageConverter);
		return template;
	}

}