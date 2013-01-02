package pl.poznan.put.miabsr.weka.terminator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminatorShutdownHook extends Thread {
	private final Terminator terminator;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public TerminatorShutdownHook(Terminator terminator) {
		this.terminator = terminator;
	}

	@Override
	public void run() {
		logger.debug("Closing application...");
		terminator.stop();
	}
}