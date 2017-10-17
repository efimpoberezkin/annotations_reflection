package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.caches.CacheA;
import com.epam.homework.annotations_reflection.cache.caches.CacheB;
import com.epam.homework.annotations_reflection.cache.caches.CacheC;

public class CacheInitializer {

    public static void initializeCaches() {

        Cache cacheA = CacheA.getInstance();
        Cache cacheB = CacheB.getInstance();
        Cache cacheC = CacheC.getInstance();

        cacheA.put(1, "Entry 1 in cache A");
        cacheA.put(2, "Entry 2 in cache A");
        cacheA.put(3, "Entry 3 in cache A");
        cacheB.put(1, "Entry 1 in cache B");
        cacheB.put(2, "Entry 2 in cache B");
        cacheB.put(4, "Entry 4 in cache B");
        cacheC.put(1, "Entry 1 in cache C");
        cacheC.put(2, "Entry 2 in cache C");
        cacheC.put(5, "Entry 5 in cache C");
    }
}
