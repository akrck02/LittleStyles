package com.akrck02.littlestyles;


import com.akrck02.littlestyles.cli.Commands;
import com.akrck02.littlestyles.cli.Logger;
import static com.akrck02.littlestyles.cli.Logger.Status.*;

public class LittleStyles {

	public static void main(String[] args) {
		Logger.line();
		Logger.log(NONE, "            LITTLE STYLES v1.0            ");
		Logger.line();
		Commands.handle(args);
	}
}