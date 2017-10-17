package com.epam.homework.annotations_reflection.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class ClassFinder {

    private final static String CLASS_SUFFIX = ".class";

    public static List<Class> find(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to get resources from path " + path, e);
        }
        List<Class> classes = new ArrayList<>();
        while (resources.hasMoreElements()) {
            File file = new File(resources.nextElement().getFile());
            classes.addAll(findClasses(file, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File file, String packageName) {
        List<Class> classes = new ArrayList<>();
        if (!file.exists()) {
            return classes;
        }
        if (file.isDirectory()) {
            for (File nestedFile : file.listFiles()) {
                classes.addAll(findClasses(nestedFile, packageName + '.' + nestedFile.getName()));
            }
        } else if (file.getName().endsWith(CLASS_SUFFIX)) {
            int endIndex = file.getName().length() - CLASS_SUFFIX.length();
            String className = file.getName().substring(0, endIndex);
            String resource = packageName + '.' + className;
            try {
                classes.add(Class.forName(resource));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}