package com.akrck02.littlestyles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Minifier {

	/**
	 * Minify the CSS code.
	 * 
	 * @param path - The path to minify.
	 * @param out  - The output file name.
	 */
	public static void minify(String path, String out, String name) {
		try {
			log("-------------------------------------------------------------------------------------------------------------------");
			log("   CSS Minify by Akrck02");
			log("   Github: https://github.com/akrck02");
			log("-------------------------------------------------------------------------------------------------------------------");

			File dir = new File(path);
			File outputDir = new File(out + "/dist/");
			File outputFile = new File(out + "/dist/" + name);
			
			log("   > Input directory: " + dir.getAbsolutePath());
			log("   > Output directory: " + outputDir.getAbsolutePath());
			log("   > File name: " + outputFile.getName());
			log("");
			

			if (!outputDir.exists() && !outputDir.mkdirs())
				log("ERROR: Couldn't create dir: " + outputDir.getAbsolutePath());

			if (!outputFile.exists())
				outputFile.createNewFile();

			BufferedWriter master = new BufferedWriter(new FileWriter(outputFile));
			access(dir, master);

			master.close();
		} catch (IOException e) {
			e.printStackTrace();
			log("ERROR: Cannot compile this version.");
		}
	}

	/**
	 * Access a file/directory
	 * 
	 * @param file
	 * @param master
	 */
	public static void access(File file, BufferedWriter master) {

		if (file.isDirectory()) {
			if (file.getName().equals("dist") || file.getName().equals("routes"))
				return;

			String[] children = file.list();
			for (String child : children) {

				log("   File: " + file.getPath() + "/" + child);
				File childFile = new File(file.getPath() + "/" + child);

				if (childFile.isDirectory()) {
					access(childFile, master);
				} else {
					addToMaster(childFile, master);
				}
			}
		} else {
			addToMaster(file, master);
		}
	}

	/**
	 * Add file to master
	 * 
	 * @param file
	 * @param master
	 */
	public static void addToMaster(File file, BufferedWriter master) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			String line = "";

			while (line != null) {
				if (!line.contains("@import \"."))
					master.write(line);
				line = bufferedReader.readLine();
				if (line != null)
					line = line.trim();
			}
			bufferedReader.close();

		} catch (IOException ignored) {
		}
	}

	/**
	 * Log in console
	 * 
	 * @param msg - The message
	 */
	public static void log(String msg) {
		System.out.println(msg);
	}

	public static void help() {
		System.out.println(
				  " Minify help: "
				+ "\n------------------------------------------------------" 
				+ "\n 1st parameter : Input directory" 
				+ "\n 2nd parameter : Output directory" 
				+ "\n 3rd parameter : Output filename"
				+ "\n\n Minify creates a ./dist/ directory by default to store the generated sources."
		);
	}

	public static void main(String[] args) {
		String in = "./";
		String out = "./";
		String name = "master.css";

		if (args.length >= 3) { 
			in = args[0];
			out = args[1];
			name = args[2];
		}

		minify(in,out,name);
	}

}