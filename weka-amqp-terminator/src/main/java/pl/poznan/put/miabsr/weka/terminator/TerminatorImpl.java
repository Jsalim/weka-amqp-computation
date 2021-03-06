package pl.poznan.put.miabsr.weka.terminator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.terminator.domain.FileResultSaver;

/**
 * Implementation of {@link Terminator}
 * 
 * @author pmendelski
 * 
 */
@Component
public class TerminatorImpl implements Terminator {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Autowired
	private FileResultSaver fileResultSaver;

	@Override
	public void activate(String filePath) {
		fileResultSaver.setResultfilename(filePath);
	}

	@Override
	public void stop() {
		connectionFactory.destroy();
	}

}
