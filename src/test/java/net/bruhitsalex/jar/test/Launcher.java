package net.bruhitsalex.jar.test;

import net.bruhitsalex.jar.Processor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        List<String> annotationsToRemove = Arrays.asList(
                "net.bruhitsalex.jnicinterface.JNIC",
                "net.bruhitsalex.jnicinterface.Performance",
                "kotlin.Metadata"
        );

        File input = new File("jars/test.jar");
        File output = new File("jars/test-output.jar");

        Processor processor = new Processor(input, output, annotationsToRemove, "net.bruhitsalex.testprogram");
        processor.start();
    }

}
