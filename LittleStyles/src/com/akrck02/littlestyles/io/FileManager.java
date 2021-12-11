package com.akrck02.littlestyles.io;

import com.akrck02.littlestyles.cli.Logger;

import java.io.*;
public class FileManager {

    /**
     * Access a file/directory
     *
     * @param file The current file or directory
     */
    public static void access(File file, BufferedWriter master) {

        if (file.isDirectory()) {
            return;
        }

        String[] name = file.getName().split("\\.");
        String extension = name[name.length - 1];

        if(extension.equals("css")){
            Logger.log(Logger.Status.INFO, "   File: " + file.getAbsolutePath());
            addToMaster(file,master);
        }

    }

    /**
     * Add file to master
     *
     * @param file The current file
     */
    public static void addToMaster(File file, BufferedWriter master) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String line = "";

            while (line != null) {

                if (line.contains("@import") && !line.contains("http")) {
                    String url = line.substring(line.indexOf("\".")+1,line.lastIndexOf("\""));
                    String localURL = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("\\")) + url;

                    addToMaster(new File(localURL), master);
                }else {
                    line = line.trim()
                            .replaceAll(" {4}","")
                            .replaceAll("\n","")
                    ;

                    if(!"".equals(line)){
                        master.write(line);
                        master.write("\n");
                    }
                }

                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException ignored) {
        }
    }


}
