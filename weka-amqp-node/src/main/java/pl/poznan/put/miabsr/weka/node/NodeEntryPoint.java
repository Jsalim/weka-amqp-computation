package pl.poznan.put.miabsr.weka.node;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.poznan.put.miabsr.weka.node.config.NodeConfiguration;

/**
 * Application entry point. Responsible for handling start parameters and
 * starting up program environment.
 * 
 * @author pmendelski
 * 
 */
public final class NodeEntryPoint {

	private NodeEntryPoint() {

	}

	public static void main(String... args) {
		String filteredClassValue = args[0];
		Node node = createApplication();
		node.activate(filteredClassValue);
		Runtime.getRuntime().addShutdownHook(new NodeShutdownHook(node));
	}

	private static Node createApplication() {
		AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
		rootContext.register(NodeConfiguration.class);
		rootContext.refresh();
		return rootContext.getBean(Node.class);
	}

	private static void print(String message) {
		System.out.println(message);
	}
}
