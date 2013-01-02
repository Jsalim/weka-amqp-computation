package pl.poznan.put.miabsr.weka.terminator.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.Result;
import pl.poznan.put.miabsr.weka.shared.ResultPackage;

@Component
public class FileResultSaver implements ResultSaver {
	private String resultFileName = "results.txt";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void saveResults(List<ResultPackage> resultPackages)
			throws IOException {
		logger.debug("Writing results to file: {}", resultFileName);
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

	public void setResultfilename(String filePath) {
		resultFileName = filePath;
	}
}
