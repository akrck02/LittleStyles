package com.akrck02.littlestyles.cli;

import com.akrck02.littlestyles.cli.Configurations.ConfigurationsBuilder;
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
        ConfigurationsBuilder builder = new ConfigurationsBuilder();

        if(arguments.contains("-h")) {
            help();
            return;
        }

        File configFile = new File("./styles.config");
        if(!configFile.exists()){
            Logger.log(INFO, "Config file not found.");
            return;
        }

        Configurations config = ConfigReader.read(configFile);

        if(config != null)
            minify(config);
    }

    /**
     * Help message for command line interface
     * showing basic commands and descriptions
     */
    public static void help() {
        log(Logger.Status.NONE, " Little styles help: ");
        log(Logger.Status.NONE, "------------------------------------------------------");
        log(Logger.Status.NONE, "-i : Input directory");
        log(Logger.Status.NONE, "-o : Output directory");
        log(Logger.Status.NONE, "-n : Output filename");
        log(Logger.Status.NONE, "-h : Useful help");
        log(Logger.Status.NONE, "\n Minify creates a ./dist/ directory by default to store the generated sources.");
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
            //e.printStackTrace();
            log(Logger.Status.ERROR, "Cannot compile this version.");
        }
    }




}
