package pl.poznan.put.miabsr.weka.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerShutdownHook extends Thread {
	private final Worker node;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public WorkerShutdownHook(Worker node) {
		this.node = node;
	}

	@Override
	public void run() {
		logger.debug("Closing application...");
		node.stop();
	}
}