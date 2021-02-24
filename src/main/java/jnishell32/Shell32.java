package jnishell32;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

public class Shell32 {

    public static String knownFolderPath(String rfid) {
        return GetKnownFolderPath(rfid);
    }

    static native String GetKnownFolderPath(String rfid);

    static File fromClassPath(ClassLoader cl) {
        if (cl == null)
            return null;

        if (cl instanceof URLClassLoader) {
            URLClassLoader ucl = (URLClassLoader) cl;
            for (URL url: ucl.getURLs()) {
                if (url.getProtocol().equals("file")) {
                    File f;
                    try {
                        f = new File(url.toURI()).getAbsoluteFile();
                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (f.getName().equals("shell32helper.dll") && f.isFile())
                        return f;
                }
            }
        } else if (cl.getClass().getName().startsWith("jdk.internal.loader.ClassLoaders$AppClassLoader")) {
            String strCp = System.getProperty("java.class.path");
            for (String elem: strCp.split(File.pathSeparator)) {
                File f = new File(elem).getAbsoluteFile();
                if (f.getName().equals("shell32helper.dll") && f.isFile())
                    return f;
            }
        }

        return fromClassPath(cl.getParent());
    }

    public static void main(String[] args) {
        File dll = fromClassPath(Thread.currentThread().getContextClassLoader());
        if (dll == null)
            throw new RuntimeException("shell32helper.dll not found");
        System.load(dll.getAbsolutePath());
        String guid;
        if (args.length == 0) {
            guid = "{3EB685DB-65F9-4CF6-A03A-E3EF65729F3D}";
        } else {
            guid = "{" + args[0] + "}";
        }
        String value = GetKnownFolderPath(guid);
        if (value == null) {
            System.err.println("No value found");
            System.exit(1);
        }
        System.out.println(value);
    }
}
