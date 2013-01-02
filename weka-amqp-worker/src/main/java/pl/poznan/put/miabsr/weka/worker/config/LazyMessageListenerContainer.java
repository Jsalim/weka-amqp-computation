package pl.poznan.put.miabsr.weka.worker.config;

import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;

public class LazyMessageListenerContainer {
	private final AbstractMessageListenerContainer messageListenerContainer;

	public LazyMessageListenerContainer(
			AbstractMessageListenerContainer messageListenerContainer) {
		super();
		this.messageListenerContainer = messageListenerContainer;
	}

	public AbstractMessageListenerContainer getMessageListenerContainer() {
		return messageListenerContainer;
	}

}
