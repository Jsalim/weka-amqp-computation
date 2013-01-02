package pl.poznan.put.miabsr.weka.worker.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.poznan.put.miabsr.weka.shared.config.AmqpConfiguration;

@Configuration
@ComponentScan(basePackages = { "pl.poznan.put.miabsr.weka.worker" })
@Import({ TrainQueueConfiguration.class, TestQueueConfiguration.class })
public class NodeConfiguration {
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
	public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
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
