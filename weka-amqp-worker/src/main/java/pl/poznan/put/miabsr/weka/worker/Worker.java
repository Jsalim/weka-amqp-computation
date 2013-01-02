package pl.poznan.put.miabsr.weka.worker;

/**
 * Responsible for executing high level application tasks.
 * 
 * @author pmendelski
 * 
 */
public interface Worker {

	void activate(String filteredClassValue);

	void stop();

}
