package pl.poznan.put.miabsr.weka.node;

/**
 * Responsible for executing high level application tasks.
 * 
 * @author pmendelski
 * 
 */
public interface Node {

	void activate(String filteredClassValue);

	void stop();

}
