package net.bruhitsalex.jar;

import com.google.common.io.ByteStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Processor {

    private final File input;
    private final File output;
    private final List<Class> annotationsToRemove;

    public Processor(File input, File output, List<Class> annotationsToRemove) {
        this.input = input;
        this.output = output;
        this.annotationsToRemove = annotationsToRemove;
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

        System.out.println("Searching jar...");
        System.out.println("---------------------------------------------");
        zipFile.stream().forEach(clazz -> {
            try {
                checkClass(zos, zipFile, clazz);
            } catch (IOException e) {
                System.out.println("Exception when viewing class: " + clazz.getName());
                e.printStackTrace();
            }
        });
        System.out.println("---------------------------------------------");
        System.out.println("Finished scan.");

        try {
            zos.close();
            zipFile.close();
        } catch (Exception e) {
            System.out.println("Issue with closing output streams.");
            e.printStackTrace();
            return;
        }

        System.out.println("Result saved.");
    }

    private void checkClass(ZipOutputStream zos, ZipFile zipFile, ZipEntry clazz) throws IOException {
        if (clazz.getName().endsWith("/")) {
            return;
        }

        InputStream is = zipFile.getInputStream(clazz);
        byte[] bytes = ByteStreams.toByteArray(is);

        if (clazz.getName().endsWith(".class")) {
            ClassReader cr = new ClassReader(bytes);
            ClassNode cn = new ClassNode();

            byte[] transformed = transform(cr, cn);

            byte[] result = clazz.getName().endsWith(".class") ? transformed : bytes;
            ZipUtils.addFileToZip(zos, clazz.getName(), result);
        } else {
            ZipUtils.addFileToZip(zos, clazz.getName(), bytes);
        }
    }

    private byte[] transform(ClassReader cr, ClassNode cn) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cr.accept(cn, 0);

        cn.methods.forEach(methodNode -> removeAnnotation(methodNode.visibleAnnotations, "'" + methodNode.name + "' in '" + cn.name + "'"));
        cn.methods.forEach(methodNode -> removeAnnotation(methodNode.invisibleAnnotations, "'" + methodNode.name + "' in '" + cn.name + "'"));
        removeAnnotation(cn.invisibleAnnotations, "'" + cn.name + "'");
        removeAnnotation(cn.visibleAnnotations, "'" + cn.name + "'");
        cn.fields.forEach(fieldNode -> removeAnnotation(fieldNode.visibleAnnotations, "'" + fieldNode.name + "' in '" + cn.name + "'"));
        cn.fields.forEach(fieldNode -> removeAnnotation(fieldNode.invisibleAnnotations, "'" + fieldNode.name + "' in '" + cn.name + "'"));

        cn.accept(cw);
        return cw.toByteArray();
    }

    private void removeAnnotation(List<AnnotationNode> annotations, String location) {
        if (annotations != null) {
            Iterator<AnnotationNode> iterator = annotations.iterator();

            while (iterator.hasNext()) {
                AnnotationNode annotation = iterator.next();
                for (Class annotationToRemove : annotationsToRemove) {
                    if (annotation.desc.equals("L" + annotationToRemove.getName().replace(".", "/") + ";")) {
                        iterator.remove();
                        System.out.println("Removing '" + annotationToRemove.getSimpleName() + "' from " + location);
                    }
                }
            }
        }
    }

}
