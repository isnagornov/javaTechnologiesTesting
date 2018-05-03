package classloaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

public class ClazzLoader extends ClassLoader {

    private Map<String, Class> classCache;

    private JarFile jarFile;

    private String pathToJar;

    public ClazzLoader(String pathToJar, JarFile jarFile) {
        super(ClazzLoader.class.getClassLoader());
        this.jarFile = jarFile;
        this.pathToJar = pathToJar;

        classCache = new HashMap<>();
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {

        Class clazz = null;

        try {

            String className;

            if (name.contains(".class") && jarFile.getEntry(name) != null) {
                className = name.substring(0, name.length() - ".class".length()).replace('/', '.');
            } else {
                String nameWithClass = name.replace('.', '/') + ".class";
                if (jarFile.getEntry(nameWithClass) == null) {
                    return super.loadClass(name);
                } else {
                    className = name;
                    name = nameWithClass;
                }
            }

            clazz = classCache.get(className);
            if (clazz != null) {
                return clazz;
            }

            String url = pathToJar + name;
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();

            byte[] classData;

            try (InputStream input = connection.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                int data = input.read();

                while (data != -1) {
                    buffer.write(data);
                    data = input.read();
                }

                classData = buffer.toByteArray();
            }

            clazz = defineClass(className, classData, 0, classData.length);
            classCache.put(className, clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return clazz;
    }

    public Map<String, Class> getClassCache() {
        return classCache;
    }
}
