package net.bruhitsalex.jar.test;

import com.stringer.annotations.HideAccess;
import com.stringer.annotations.StringEncryption;
import net.bruhitsalex.jar.Processor;
import net.bruhitsalex.jnicinterface.ExtraObfuscation;
import net.bruhitsalex.jnicinterface.JNIC;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        List<Class> annotationsToRemove = Arrays.asList(
                ExtraObfuscation.class,
                JNIC.class,
                HideAccess.class,
                StringEncryption.class
        );

        File input = new File("jars/test.jar");
        File output = new File("jars/test-output.jar");

        Processor processor = new Processor(input, output, annotationsToRemove);
        processor.start();
    }

}
