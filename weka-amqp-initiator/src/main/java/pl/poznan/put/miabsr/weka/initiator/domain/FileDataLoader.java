package pl.poznan.put.miabsr.weka.initiator.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.poznan.put.miabsr.weka.shared.RawInstance;

@Component
public class FileDataLoader {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public List<RawInstance> loadFile(String filePath)
			throws FileNotFoundException {
		List<RawInstance> records = new ArrayList<RawInstance>();
		int id = 0;
		try (Scanner scanner = new Scanner(new File(filePath))) {
			while (scanner.hasNext()) {
				RawInstance record = new RawInstance(id++, scanner.nextLine());
				records.add(record);
			}
		}
		logger.trace("Loaded file: {}. Line count: {}", filePath,
				records.size());
		return records;
	}
}
