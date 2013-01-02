package pl.poznan.put.miabsr.weka.terminator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.poznan.put.miabsr.weka.terminator.config.TerminatorConfiguration;

/**
 * Application entry point. Responsible for handling start parameters and
 * starting up program environment.
 * 
 * @author pmendelski
 * 
 */
public final class TerminatorEntryPoint {

	private TerminatorEntryPoint() {

	}

	public static void main(String... args) {
		String resultFile = args[0];

		Terminator terminator = createApplication();
		terminator.activate(resultFile);
		Runtime.getRuntime().addShutdownHook(
				new TerminatorShutdownHook(terminator));
	}

	private static Terminator createApplication() {
		AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
		rootContext.register(TerminatorConfiguration.class);
		rootContext.refresh();
		return rootContext.getBean(Terminator.class);
	}

	private static void print(String message) {
		System.out.println(message);
	}
}
