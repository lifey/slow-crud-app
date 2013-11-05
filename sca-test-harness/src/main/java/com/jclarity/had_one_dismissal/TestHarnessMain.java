package com.jclarity.had_one_dismissal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class TestHarnessMain {
    private static Logger LOGGER = LoggerFactory.getLogger(TestHarnessMain.class);

    public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
        Options options = new Options();
        options.addOption("f", true, "csv file to use for exercises, default is 'exercises.csv'");
        options.addOption("c", true, "single class to run");
        options.addOption("t", true, "time limit to run for");
        options.addOption("h", false, "display this help message");

        CommandLine cmd = new GnuParser().parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Main", options);
            return;
        }
        
        if (cmd.hasOption("f")) {
            runFromCsv(new FileInputStream(cmd.getOptionValue("f")));
        } else if (cmd.hasOption("c")) {
            String exercise = cmd.getOptionValue("c");
            long timeLimit = Long.parseLong(cmd.getOptionValue("t"));
            Exercise.runExercise(exercise, timeLimit, new String[0]);
        } else {
            InputStream exercises = TestHarnessMain.class.getResourceAsStream("exercises.csv");
            runFromCsv(exercises);
        }
    }

    public static void runFromCsv(InputStream csvStream) throws IOException {
        List<String[]> exercises;
        try (CSVReader reader = new CSVReader(new InputStreamReader(csvStream))) {
            exercises = reader.readAll();
        }

        while (true) {
            for (String[] exercise : exercises) {
                if (exercise.length < 2)
                    continue;

                if (exercise[0].startsWith("#"))
                    continue;

                LOGGER.info("Running {} for {}", exercise[0], exercise[1]);
                String[] arguments = Arrays.copyOfRange(exercise, 2, exercise.length);
                Exercise.runExercise(exercise[0], Long.parseLong(exercise[1]), arguments);
            }
        }
    }

}
