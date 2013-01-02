package pl.poznan.put.miabsr.weka.initiator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Application options.
 * 
 * @author pmendelski
 * 
 */
@SuppressWarnings("serial")
public class InitiatorOptions extends Options {
	private final Option printHelpOption;
	private final Option nodeAddressesOption;
	private final Option distributeEvaluationOption;
	private final Option trainNodesOption;
	private final Option classifierOption;
	private final Option problemOption;

	// Kept to keep non alphabetical order
	private final List<Option> options = new ArrayList<Option>();

	@SuppressWarnings("static-access")
	public InitiatorOptions() {
		printHelpOption = OptionBuilder.withLongOpt("help")
				.withDescription("Prints this information").create("h");
		trainNodesOption = OptionBuilder.withLongOpt("train-nodes")
				.withDescription("Train nodes with train data.").hasArg()
				.withArgName("train_file").create("t");
		distributeEvaluationOption = OptionBuilder.withLongOpt("load-triplets")
				.withDescription("Distributes evaluation through all nodes.")
				.hasArg().withArgName("test_file").create("e");
		nodeAddressesOption = OptionBuilder
				.withLongOpt("node-addresses")
				.withDescription(
						"Addresses of all available nodes. Must be set for options: train-nodes, test-nodes.")
				.hasArgs().withArgName("addresses").create("n");
		classifierOption = OptionBuilder
				.withLongOpt("classifier-id")
				.withDescription(
						"Id of classiffier to use or create. Must be set for options: train-nodes, test-nodes.")
				.hasArg().withArgName("id").create("c");
		problemOption = OptionBuilder
				.withLongOpt("problem-id")
				.withDescription(
						"Id of problem to be resolved. Problem must be compatybile nodes' defined problems. Must be set for options: train-nodes, test-nodes.")
				.hasArg().withArgName("problem").create("p");
		this.addOption(printHelpOption);
		this.addOption(nodeAddressesOption);
		this.addOption(classifierOption);
		this.addOption(problemOption);
		this.addOption(trainNodesOption);
		this.addOption(distributeEvaluationOption);
	}

	@Override
	public Options addOption(Option opt) {
		options.add(opt);
		return super.addOption(opt);
	}

	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(new Comparator<Option>() {

			@Override
			public int compare(Option o1, Option o2) {
				int idx1 = options.indexOf(o1);
				int idx2 = options.indexOf(o2);
				return idx1 - idx2;
			}
		});
		formatter
				.printHelp(
						"ant",
						"Program that resolves MSD challenge problem."
								+ System.lineSeparator()
								+ "See: http://www.kaggle.com/c/msdchallenge)"
								+ System.lineSeparator()
								+ " Usage: Load data (triplets and test users), analyze data, resolve problem.",
						this, null);
	}

	public boolean hasOption(Option opt) {
		return super.hasOption(opt.getOpt());
	}

	@SuppressWarnings("unchecked")
	public boolean hasAnyOption(CommandLine commandLine) {
		Collection<Option> options = this.getOptions();
		for (Option option : options) {
			if (commandLine.hasOption(option.getOpt())) {
				return true;
			}
		}
		return false;
	}

	public String getPrintHelpOption() {
		return printHelpOption.getOpt();
	}

	public String getNodeAddressesOption() {
		return nodeAddressesOption.getOpt();
	}

	public String getDistributeEvaluationOption() {
		return distributeEvaluationOption.getOpt();
	}

	public String getTrainNodesOption() {
		return trainNodesOption.getOpt();
	}

	public String getClassifierOption() {
		return classifierOption.getOpt();
	}

	public String getProblemOption() {
		return problemOption.getOpt();
	}

}
