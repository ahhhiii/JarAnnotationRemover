package net.bruhitsalex.jar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Processor {

    private final File input;
    private final File output;
    private final List<Class> annotations;

    public Processor(File input, File output, List<Class> annotations) {
        this.input = input;
        this.output = output;
        this.annotations = annotations;
    }

    public void start() {
        ZipOutputStream zos;
        ZipFile zipFile;
        try {
            FileOutputStream fos = new FileOutputStream(output, false);
            zos = new ZipOutputStream(fos);
            zipFile = new ZipFile(input);
        } catch (Exception e) {
            System.out.println("IO actions blocked:");
            e.printStackTrace();
            return;
        }

        zipFile.stream().forEach(clazz -> {
            checkClass(zos, zipFile, clazz);
        });

        try {
            zos.close();
            zipFile.close();
        } catch (Exception e) {
            System.out.println("Issue with closing output streams.");
            e.printStackTrace();
        }
    }

    private void checkClass(ZipOutputStream zos, ZipFile zipFile, ZipEntry clazz) {

    }

    public File getInput() {
        return input;
    }

    public File getOutput() {
        return output;
    }

    public List<Class> getAnnotations() {
        return annotations;
    }

}
