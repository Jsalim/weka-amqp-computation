package pl.poznan.put.miabsr.weka.initiator;

import java.io.FileNotFoundException;

/**
 * Responsible for executing high level application tasks.
 * 
 * @author pmendelski
 * 
 */
public interface Initiator {

	void sendTrainData(String filePath) throws FileNotFoundException;

	void sendTestData(String filePath) throws FileNotFoundException;

	void stop();

}
