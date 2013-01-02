package pl.poznan.put.miabsr.weka.terminator.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.Result;
import pl.poznan.put.miabsr.weka.shared.ResultPackage;

@Component
public class ResultQueueListener {

	private String resultFileName = "results.txt";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final List<ResultPackage> resultPackages = new ArrayList<ResultPackage>();

	public void handleMessage(ResultPackage resultPackage) throws IOException {
		logger.debug("Received result data package. Id: {}",
				resultPackage.getId());
		resultPackages.add(resultPackage);
		if (resultPackages.size() == resultPackage.getCount()) {
			logger.debug("Writing results to file: {}", resultFileName);
			writeResultToFile();
			resultPackages.clear();
		}
	}

	public void setResultfilename(String filePath) {
		resultFileName = filePath;
	}

	private void writeResultToFile() throws IOException {
		Collections.sort(resultPackages);
		BufferedWriter out = new BufferedWriter(new FileWriter(resultFileName));
		for (ResultPackage resultPackage : resultPackages) {
			for (Result result : resultPackage.getResults()) {
				out.write(Integer.toString(result.getId()));
				out.write(": ");
				out.write(result.getValue());
				out.newLine();
			}
		}
		out.flush();
		out.close();
	}
}
