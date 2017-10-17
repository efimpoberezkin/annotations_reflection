package com.epam.homework.annotations_reflection.cache.injection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;

public class CacheFiller {

    public static void fillCache(Cache cache) {

        String cacheName = "";
        if (cache.getClass().isAnnotationPresent(CacheDeclaration.class)) {
            cacheName = cache.getClass().getAnnotation(CacheDeclaration.class).name();
        }
        cache.put(1, "Entry 1 in cache " + cacheName);
        cache.put(2, "Entry 2 in cache " + cacheName);
    }
}