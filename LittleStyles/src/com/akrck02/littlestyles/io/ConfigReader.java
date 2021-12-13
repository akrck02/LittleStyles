package com.akrck02.littlestyles.io;

import com.akrck02.littlestyles.cli.Configurations;
import com.akrck02.littlestyles.cli.Configurations.ConfigurationsBuilder;
import com.akrck02.littlestyles.cli.Logger;
import com.akrck02.littlestyles.exception.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.akrck02.littlestyles.cli.Logger.Status.INFO;

public class ConfigReader {

    public static Configurations read(File file) throws ConfigurationException {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);

            if (lines.size() < 3)
                throw new ConfigurationException("Malformed configuration file");

            String input = lines.get(0).split(":")[1].trim();
            String output = lines.get(1).split(":")[1].trim();
            String name = lines.get(2).split(":")[1].trim();

            Logger.log(INFO, "input: " + input);
            Logger.log(INFO,"output: " + output);
            Logger.log(INFO, "name: " + name);

            return new ConfigurationsBuilder()
                    .setInput(input)
                    .setOutput(output)
                    .setName(name)
                    .build();

        } catch (IOException e) {
            throw new ConfigurationException("Cannot read config file");
        }
    }

}
