package com.akrck02.littlestyles.cli;

/**
 * Command line interface configurations
 */
public class Configurations {

    private String input;
    private String output;
    private String name;
    private boolean readable;

    private Configurations() {}

    public Configurations setInput(String input) {
        this.input = input;
        return this;
    }

    public String getInput() {
        return input;
    }

    public Configurations setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Configurations setOutput(String output) {
        this.output = output;
        return this;
    }

    public Configurations setReadable(boolean readable) {
        this.readable = readable;
        return this;
    }

    public boolean isReadable() {
        return readable;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "Configurations{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static class ConfigurationsBuilder {

        private String input;
        private String output;
        private String name;
        private boolean readable;

        public ConfigurationsBuilder() {
            this.input = "./";
            this.output = "./";
            this.name = "master.css";
            this.readable = false;
        }

        public ConfigurationsBuilder setInput(String input) {
            this.input = input;
            return this;
        }

        public ConfigurationsBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ConfigurationsBuilder setOutput(String output) {
            this.output = output;
            return this;
        }

        public ConfigurationsBuilder setReadable(boolean readable) {
            this.readable = readable;
            return this;
        }

        public Configurations build(){
            return new Configurations()
            .setInput(this.input)
            .setName(this.name)
            .setOutput(this.output)
            .setReadable(this.readable);
        }
    }
}
