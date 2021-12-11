package com.akrck02.littlestyles.io;

import com.akrck02.littlestyles.cli.Configurations;
import com.akrck02.littlestyles.cli.Configurations.ConfigurationsBuilder;
import com.akrck02.littlestyles.cli.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader {

    public static Configurations read(File file) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);

            if (lines.size() < 3)
            {
                Logger.log(Logger.Status.ERROR,"Cannot read config file");
                return null;
            }

            String input = lines.get(0).split(":")[1].trim();
            String output = lines.get(1).split(":")[1].trim();
            String name = lines.get(2).split(":")[1].trim();

            System.out.println("input: " + input);
            System.out.println("output: " + output);
            System.out.println("name: " + name);

            return new ConfigurationsBuilder()
                    .setInput(input)
                    .setOutput(output)
                    .setName(name)
                    .build();

        } catch (IOException e) {
            Logger.log(Logger.Status.ERROR,"Cannot read config file");
        }
        return null;
    }

}
