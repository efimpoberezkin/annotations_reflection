package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;
import com.epam.homework.annotations_reflection.cache.injection.InjectCache;
import com.epam.homework.annotations_reflection.cache.injection.Injectable;

public class InjecteeInheritor extends Injectee implements Injectable {

    @InjectCache(name = CacheNamesContainer.CACHE_A_NAME)
    public Cache cache5;

    @InjectCache(name = CacheNamesContainer.CACHE_C_NAME)
    private Cache cache6;

    private String dummyStr;

    @Override
    public void printFromCachesByKey(int k) {
        super.printFromCachesByKey(k);

        if (cache5 == null) {
            System.out.println("Cache 5 is not injected");
        } else {
            System.out.println("Cache 5 - " + (cache5.containsKey(k) ? cache5.get(k) : ("No entry with key " + k)));
        }

        if (cache6 == null) {
            System.out.println("Cache 6 is not injected");
        } else {
            System.out.println("Cache 6 - " + (cache6.containsKey(k) ? cache6.get(k) : ("No entry with key " + k)));
        }
    }
}