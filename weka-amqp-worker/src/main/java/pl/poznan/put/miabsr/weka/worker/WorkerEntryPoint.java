package pl.poznan.put.miabsr.weka.worker;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.poznan.put.miabsr.weka.worker.config.NodeConfiguration;

/**
 * Application entry point. Responsible for handling start parameters and
 * starting up program environment.
 * 
 * @author pmendelski
 * 
 */
public final class WorkerEntryPoint {

	private WorkerEntryPoint() {

	}

	public static void main(String... args) {
		String filteredClassValue = args[0];
		Worker node = createApplication();
		node.activate(filteredClassValue);
		Runtime.getRuntime().addShutdownHook(new WorkerShutdownHook(node));
	}

	private static Worker createApplication() {
		AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
		rootContext.register(NodeConfiguration.class);
		rootContext.refresh();
		return rootContext.getBean(Worker.class);
	}

	private static void print(String message) {
		System.out.println(message);
	}
}
