package pl.poznan.put.miabsr.weka.initiator;

import java.io.FileNotFoundException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.poznan.put.miabsr.weka.initiator.config.InitializatorConfiguration;

/**
 * Application entry point. Responsible for handling start parameters and
 * starting up program environment.
 * 
 * @author pmendelski
 * 
 */
public final class InitiatorEntryPoint {

	private InitiatorEntryPoint() {

	}

	public static void main(String... args) throws FileNotFoundException {
		String mode = args[0];
		String dataFilePath = args[1];

		Initiator initiator = createApplication();
		if ("-t".equals(mode)) {
			initiator.sendTrainData(dataFilePath);
		} else if ("-e".equals(mode)) {
			initiator.sendTestData(dataFilePath);
		} else {
			print("Unrecognized variable: " + mode + ". Use -t or -e.");
		}
		initiator.stop();
	}

	private static Initiator createApplication() {
		AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
		rootContext.register(InitializatorConfiguration.class);
		rootContext.refresh();
		return rootContext.getBean(Initiator.class);
	}

	private static void print(String message) {
		System.out.println(message);
	}
}
