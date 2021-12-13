package com.akrck02.littlestyles.cli;

import com.akrck02.littlestyles.exception.ConfigurationException;
import com.akrck02.littlestyles.io.FileManager;
import com.akrck02.littlestyles.io.ConfigReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.akrck02.littlestyles.cli.Logger.Status.INFO;
import static com.akrck02.littlestyles.cli.Logger.log;

public class Commands {

    /**
     * Handle commands reading options
     * @param args CLI arguments
     */
    public static void handle(String[] args){

        List<String> arguments = Arrays.asList(args);

        if(arguments.contains("-h")) {
            help();
            return;
        }

        if(arguments.contains("-g")) {
            generateConfigurationFile();
            return;
        }

        File configFile = new File("./styles.config");
        if(!configFile.exists()){
            Logger.log(INFO, "Config file not found.");
            Logger.log(INFO, "Generate new configuration file using lit -g.");
            return;
        }

        try {
            Configurations config = ConfigReader.read(configFile);
            minify(config);
        } catch (ConfigurationException e){ Logger.log(Logger.Status.ERROR, e.getMessage());}

    }

    /**
     * Help message for command line interface
     * showing basic commands and descriptions
     */
    public static void help() {
        log(Logger.Status.NONE, " Little styles help: ");
        log(Logger.Status.NONE, "------------------------------------------------------");
        log(Logger.Status.NONE, "-g : Generate configuration file");
        log(Logger.Status.NONE, "-h : Useful help");
        log(Logger.Status.NONE, "\n Lit creates a ./dist/ directory by default to store the generated sources.");
    }

    /**
     * Generate a configuration file in current directory
     */
    public static void generateConfigurationFile() {



    }

    /**
     * Minify the CSS code.
     * @param config The command line interface configurations
     */
    public static void minify(Configurations config) {
        try {

            File dir = new File(config.getInput());
            File outputDir = new File(config.getOutput() + "/dist/");
            File outputFile = new File(config.getOutput() + "/dist/" + config.getName());

            if (!outputDir.exists() && !outputDir.mkdirs())
                log(Logger.Status.ERROR, "Couldn't create dir: " + outputDir.getAbsolutePath());

            if (!outputFile.exists())
                if(outputFile.createNewFile())
                    log(Logger.Status.INFO, "Created file " + outputFile.getAbsolutePath());

            BufferedWriter master = new BufferedWriter(new FileWriter(outputFile));
            FileManager.access(dir,master);

            master.close();
        } catch (IOException e) {
            log(Logger.Status.ERROR, "Cannot compile this version.");
        }
    }


}
