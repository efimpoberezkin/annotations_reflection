package com.epam.homework.annotations_reflection.cache.injection;

import com.epam.homework.annotations_reflection.cache.CacheDeclaration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class CacheFinder {

    private final static String CLASS_SUFFIX = ".class";

    public static List<Class> find(String packageName) throws IllegalArgumentException, ClassNotFoundException {
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
            File directory = new File(resources.nextElement().getFile());
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(CLASS_SUFFIX)) {
                int endIndex = file.getName().length() - CLASS_SUFFIX.length();
                String className = file.getName().substring(0, endIndex);
                String resource = packageName + '.' + className;
                Class cl = Class.forName(resource);
                if (cl.isAnnotationPresent(CacheDeclaration.class)) {
                    classes.add(Class.forName(resource));
                }
            }
        }
        return classes;
    }
}