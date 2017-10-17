package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;
import com.epam.homework.annotations_reflection.cache.injection.InjectCache;

public class Injectee extends BasicInjectee {

    @InjectCache(name = CacheNamesContainer.CACHE_B_NAME)
    public Cache cache2;

    public int dummyInt;

    public void printFromCachesByKey(int k) {
        if (cache1 == null) {
            System.out.println("Cache 1 is not injected");
        } else {
            System.out.println("Cache 1 - " + (cache1.containsKey(k) ? cache1.get(k) : ("No entry with key " + k)));
        }

        if (cache2 == null) {
            System.out.println("Cache 2 is not injected");
        } else {
            System.out.println("Cache 2 - " + (cache2.containsKey(k) ? cache2.get(k) : ("No entry with key " + k)));
        }
    }
}