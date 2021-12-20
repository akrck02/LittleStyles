package com.akrck02.littlestyles.io;

import com.akrck02.littlestyles.cli.Configurations;
import com.akrck02.littlestyles.cli.Logger;

import java.io.*;
public class FileManager {

    /**
     * Access a file/directory
     *
     * @param file The current file or directory
     */
    public static void access(final Configurations config, final File file, final BufferedWriter master) {

        if (file.isDirectory()) {
            return;
        }

        final String[] name = file.getName().split("\\.");
        final String extension = name[name.length - 1];

        if(extension.equals("css")){
            Logger.log(Logger.Status.INFO, "   File: " + file.getAbsolutePath());
            addToMaster(config,file,master);
        }

    }

    /**
     * Add file to master
     * @param file The current file
     */
    public static void addToMaster(final Configurations config, final File file, final BufferedWriter master) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String line = "";

            while (line != null) {

                if (line.contains("@import") && !line.contains("http")) {
                    final String url = line.substring(line.indexOf("\".")+1,line.lastIndexOf("\""));
                    final String localURL = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("/") + 1) + url;

                    addToMaster(config, new File(localURL), master);
                }else {
                    line = line.trim()
                            .replaceAll(" {4}","")
                            .replaceAll("\n","")
                    ;

                    if(!"".equals(line)){
                        master.write(line);
                        if(config.isReadable())
                            master.write("\n");
                    }
                }

                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException ignored) {
            Logger.log(Logger.Status.INFO, file.getAbsolutePath() + " failed to write.");
        }
    }

    /**
     * Generates a configuration file on current directory
     */
    public static void generateConfigurationFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./styles.config"))){
            writer.write("input:./main.css");
            writer.newLine();
            writer.write("output:./");
            writer.newLine();
            writer.write("name:main-mini.css");
            writer.newLine();
            writer.write("readable:false");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
