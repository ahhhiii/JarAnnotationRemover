package net.bruhitsalex.jar;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void addFileToZip(ZipOutputStream zipOutputStream, String path, byte[] bytes) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(path));
        zipOutputStream.write(bytes, 0, bytes.length);
        zipOutputStream.closeEntry();
    }

}
