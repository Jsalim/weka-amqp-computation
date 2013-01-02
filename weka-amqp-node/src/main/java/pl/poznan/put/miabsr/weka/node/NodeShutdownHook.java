package pl.poznan.put.miabsr.weka.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeShutdownHook extends Thread {
	private final Node node;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public NodeShutdownHook(Node node) {
		this.node = node;
	}

	@Override
	public void run() {
		logger.debug("Closing application...");
		node.stop();
	}
}