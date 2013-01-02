package pl.poznan.put.miabsr.weka.terminator.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.ResultPackage;

@Component
public class ResultQueueListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final List<ResultPackage> resultPackages = new ArrayList<ResultPackage>();

	@Autowired
	private ResultSaver resultSaver;

	public void handleMessage(ResultPackage resultPackage) throws IOException {
		logger.debug("Received result data package. Id: {}",
				resultPackage.getId());
		resultPackages.add(resultPackage);
		if (resultPackages.size() == resultPackage.getCount()) {
			resultSaver.saveResults(resultPackages);
			resultPackages.clear();
		}
	}

}
