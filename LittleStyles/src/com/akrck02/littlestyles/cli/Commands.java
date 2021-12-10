package com.akrck02.littlestyles.cli;

import com.akrck02.littlestyles.cli.Configurations.ConfigurationsBuilder;
import com.akrck02.littlestyles.io.FileManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.akrck02.littlestyles.cli.Logger.log;

public class Commands {

    /**
     * Handle commands reading options
     * @param args CLI arguments
     */
    public static void handle(String[] args){

        List<String> arguments = Arrays.asList(args);
        ConfigurationsBuilder builder = new ConfigurationsBuilder();

        if(arguments.contains("-h")) {
            help();
            return;
        }

        if(arguments.contains("-i")) {
            try {
                String input = arguments.get(arguments.indexOf("-i") + 1);
                builder.setInput(input);
            } catch (IndexOutOfBoundsException e) { log("No input detected, using default one."); }
        }

        if(arguments.contains("-o")) {
            try {
                String output = arguments.get(arguments.indexOf("-o") + 1);
                builder.setOutput(output);
            } catch (IndexOutOfBoundsException e) { log("No output directory detected, using default one."); }
        }

        if(arguments.contains("-n")) {
            try {
                String name = arguments.get(arguments.indexOf("-n") + 1);
                builder.setName(name);
            } catch (IndexOutOfBoundsException e) { log("No output name detected, using default one."); }
        }

        Configurations config = builder.build();
        minify(config);

    }

    /**
     * Help message for command line interface
     * showing basic commands and descriptions
     */
    public static void help() {
        log(" Little styles help: ");
        log("------------------------------------------------------");
        log("-i : Input directory");
        log("-o : Output directory");
        log("-n : Output filename");
        log("-h : Useful help");
        log("\n Minify creates a ./dist/ directory by default to store the generated sources.");
    }

    /**
     * Minify the CSS code.
     * @param config The command line interface configurations
     */
    public static void minify(Configurations config) {
        try {
            log("-------------------------------------------------------------------------------------------------------------------");
            log("   CSS Minify by Akrck02");
            log("   Github: https://github.com/akrck02");
            log("-------------------------------------------------------------------------------------------------------------------");

            File dir = new File(config.getInput());
            File outputDir = new File(config.getOutput() + "/dist/");
            File outputFile = new File(config.getOutput() + "/dist/" + config.getName());

            log("   > Input directory: " + dir.getAbsolutePath());
            log("   > Output directory: " + outputDir.getAbsolutePath());
            log("   > File name: " + outputFile.getName());
            log("");


            if (!outputDir.exists() && !outputDir.mkdirs())
                log("ERROR: Couldn't create dir: " + outputDir.getAbsolutePath());

            if (!outputFile.exists())
                if(outputFile.createNewFile())
                    log("Created file " + outputFile.getAbsolutePath());

            BufferedWriter master = new BufferedWriter(new FileWriter(outputFile));
            FileManager.access(dir,master);

            master.close();
        } catch (IOException e) {
            e.printStackTrace();
            log("ERROR: Cannot compile this version.");
        }
    }




}
