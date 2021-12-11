
package com.akrck02.littlestyles.cli;
public class Logger {

    public enum Status {
        INFO ("INFO"),
        ERROR ("ERROR"),
        NONE ("");

        private final String name;

        Status (String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void log(Status status, String message) {
        if(status == Status.NONE)
            System.out.println(message);
        else
            System.out.println("[" + status.getName() + "] " + message);
    }

    public static void line() {
        System.out.println("------------------------------------------");
    }

}
