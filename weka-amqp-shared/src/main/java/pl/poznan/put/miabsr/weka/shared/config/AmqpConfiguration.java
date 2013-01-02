package pl.poznan.put.miabsr.weka.shared.config;

public class AmqpConfiguration {
	public static final String HOST = "localhost";

	public static final int PORT = 5672;

	public static final String TRAIN_EXCHANGE_NAME = "TrainExchange";

	public static final String TEST_QUEUE_NAME = "TestQueue";

	public static final String RESULT_QUEUE_NAME = "ResultQueue";

}
