package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;
import com.epam.homework.annotations_reflection.cache.injection.InjectCache;
import com.epam.homework.annotations_reflection.cache.injection.Injectable;

public class Injectee extends BasicInjectee implements Injectable {

    @InjectCache(name = CacheNamesContainer.CACHE_B_NAME)
    public Cache cache3;

    @InjectCache(name = CacheNamesContainer.CACHE_C_NAME)
    private Cache cache4;

    public int dummyInt;

    @Override
    public void printFromCachesByKey(int k) {
        super.printFromCachesByKey(k);

        if (cache3 == null) {
            System.out.println("Cache 3 is not injected");
        } else {
            System.out.println("Cache 3 - " + (cache3.containsKey(k) ? cache3.get(k) : ("No entry with key " + k)));
        }

        if (cache4 == null) {
            System.out.println("Cache 4 is not injected");
        } else {
            System.out.println("Cache 4 - " + (cache4.containsKey(k) ? cache4.get(k) : ("No entry with key " + k)));
        }
    }
}