package pl.poznan.put.miabsr.weka.initiator.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.poznan.put.miabsr.weka.shared.config.AmqpConfiguration;

/**
 * Application base configuration.
 * 
 * @author pmendelski
 * 
 */
@Configuration
@ComponentScan(basePackages = { "pl.poznan.put.miabsr.weka.initiator" })
public class InitializatorConfiguration {

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
	@Autowired
	public RabbitTemplate trainExchangeTemplate(
			ConnectionFactory connectionFactory, MessageConverter converter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setExchange(AmqpConfiguration.TRAIN_EXCHANGE_NAME);
		template.setMessageConverter(converter);
		return template;
	}

	@Bean
	@Autowired
	public RabbitTemplate testQueueTemplate(
			ConnectionFactory connectionFactory, MessageConverter converter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setQueue(AmqpConfiguration.TEST_QUEUE_NAME);
		template.setRoutingKey(AmqpConfiguration.TEST_QUEUE_NAME);
		template.setMessageConverter(converter);
		return template;
	}
}