package com.epam.homework.annotations_reflection.cache.injection;

import com.epam.homework.annotations_reflection.cache.CacheDeclaration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public final class CacheFinder {

    private final static String CLASS_SUFFIX = ".class";

    /**
     * Returns a map, values are Cache classes with according Cache Declaration names as keys.
     */
    public static Map<String, Class> find(String packageName) throws IllegalArgumentException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to get resources from path " + path, e);
        }
        Map<String, Class> mapOfCaches = new HashMap<>();
        while (resources.hasMoreElements()) {
            File directory = new File(resources.nextElement().getFile());
            addCachesFromDirectory(mapOfCaches, directory, packageName);
        }
        return mapOfCaches;
    }

    private static void addCachesFromDirectory(Map mapOfCaches, File directory, String packageName)
            throws ClassNotFoundException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    addCachesFromDirectory(mapOfCaches, file, packageName + "." + file.getName());
                } else if (file.getName().endsWith(CLASS_SUFFIX)) {
                    int endIndex = file.getName().length() - CLASS_SUFFIX.length();
                    String className = file.getName().substring(0, endIndex);
                    String resource = packageName + '.' + className;
                    Class cl = Class.forName(resource);
                    if (cl.isAnnotationPresent(CacheDeclaration.class)) {
                        CacheDeclaration clAnnotation = (CacheDeclaration) cl.getAnnotation(CacheDeclaration.class);
                        String clName = clAnnotation.name();
                        mapOfCaches.put(clName, cl);
                    }
                }
            }
        }
    }
}