package seborama.demo2.kafka.interfaces.cli;

import org.apache.commons.cli.*;

public class CommandLineParser {
    private Options options;
    private CommandLine cmd;

    public CommandLineParser(String[] args) throws ParseException {
        options = new Options();

        Option helpOption = Option.builder("h")
                .longOpt("help")
                .desc("usage help")
                .build();

//        Option configFileOption = Option.builder("c")
//                .argName("config_file")
//                .longOpt("config-file")
//                .desc("configuration file path and name")
//                .hasArg()
//                .build();

        OptionGroup optionGroup = new OptionGroup()
//                .addOption(configFileOption)
                .addOption(helpOption);
//        optionGroup.setRequired(true);
        options.addOptionGroup(optionGroup);

        org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
    }

    public boolean isHelpRequest() {
        return cmd.hasOption("h");
    }

    public void showUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("order-lifecycle-demo-stream", options);
    }

    public String getConfigFilename() {
        return cmd.getOptionValue("c");
    }
}
