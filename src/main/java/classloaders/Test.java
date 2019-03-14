package classloaders;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Test {

    public static void main(String[] args) throws IOException {

        try {
            String jerseyJar = System.getProperty("user.home") + "\\.m2\\repository\\com\\sun\\jersey\\jersey-core\\1.19.4\\jersey-core-1.19.4.jar";
            String javaeeJar = System.getProperty("user.home") + "\\.m2\\repository\\javax\\javaee-api\\7.0\\javaee-api-7.0.jar";

            JarFile jarFile1 = new JarFile(javaeeJar);
            JarFile jarFile2 = new JarFile(jerseyJar);
            ClazzLoader clazzLoader1 = new ClazzLoader("jar:file:" + javaeeJar + "!/", jarFile1);

            Class responseClass1 = loadClassFromJar(clazzLoader1, jarFile1, "javax.ws.rs.core.Response");
            Class uriBuilder1 = loadClassFromJar(clazzLoader1, jarFile1, "javax.ws.rs.core.UriBuilder");

            System.out.println(responseClass1.getMethods().length);
            System.out.println(uriBuilder1.getMethods().length);

            loadPackageFromJar(jerseyJar, "javax.ws.rs", clazzLoader1);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Class loadClassFromJar(ClazzLoader clazzLoader, JarFile jarFile,
                                         String className) throws IOException, ClassNotFoundException {

        Class clazz = null;
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        className = className.replace('.', '/') + ".class";

        while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry jarEntry = jarEntryEnumeration.nextElement();
            String name = jarEntry.getName();
            if (!jarEntry.isDirectory() && name.equals(className)) {
                clazz = clazzLoader.loadClass(name);
                break;
            }
        }

        return clazz;
    }

    public static void loadPackageFromJar(String pathToJar, String packageName) throws IOException, ClassNotFoundException {

        try (JarFile jarFile = new JarFile(pathToJar)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            try (URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls)) {

                while (entries.hasMoreElements()) {
                    JarEntry je = entries.nextElement();
                    if (!je.isDirectory() && je.getName().endsWith(".class")) {
                        String className = je.getName().substring(0, je.getName().length() - ".class".length());
                        className = className.replace('/', '.');
                        if (className.contains(packageName)) {
                            urlClassLoader.loadClass(className);
                        }
                    }

                }
            }
        }

    }

    private static void loadPackageFromJar(String pathToJar, String packageName, ClassLoader classLoader)
            throws ClassNotFoundException {

        try (JarFile jarFile = new JarFile(pathToJar)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String suffix = ".class";
                if (!entry.isDirectory() && entry.getName().endsWith(suffix)) {
                    String className = entry.getName().substring(0, entry.getName().length() - suffix.length());
                    className = className.replace('/', '.');
                    if (className.contains(packageName)) {
                        classLoader.loadClass(className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
