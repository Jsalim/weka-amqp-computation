package pl.poznan.put.miabsr.weka.terminator;

/**
 * Responsible for executing high level application tasks.
 * 
 * @author pmendelski
 * 
 */
public interface Terminator {

	void activate(String filePath);

	void stop();

}
