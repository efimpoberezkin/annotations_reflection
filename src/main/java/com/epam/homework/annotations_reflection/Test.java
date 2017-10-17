package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.caches.CacheA;
import com.epam.homework.annotations_reflection.cache.caches.CacheB;
import com.epam.homework.annotations_reflection.cache.caches.CacheC;
import com.epam.homework.annotations_reflection.injection.InjectionException;
import com.epam.homework.annotations_reflection.injection.Injector;
import com.epam.homework.annotations_reflection.injection.NoCacheFoundException;

public class Test {

    public static void main(String[] args) {
        perform_test();
    }

    private static void perform_test() {

        Injectee injectee = new Injectee();
        InjecteeInheritor injecteeInheritor = new InjecteeInheritor();
        int key = 1; //for example 1, 2, 3, 4, 5, 6 for tests

        initializeCaches();

        System.out.println("-- Prior to injecting --");
        testInjectee(injectee, "injectee", key);
        testInjectee(injecteeInheritor, "injecteeInheritor", key);

        System.out.println("\n-- Injection --");
        injectIntoIjectee(injectee, "injectee");
        injectIntoIjectee(injecteeInheritor, "injecteeInheritor");

        System.out.println("\n-- After injecting --");
        testInjectee(injectee, "injectee", key);
        testInjectee(injecteeInheritor, "injecteeInheritor", key);
    }

    private static void initializeCaches() {

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

    private static void testInjectee(Injectee injectee, String injecteeName, Integer key) {

        System.out.println("\nTrying to print from caches of " + injecteeName + " by key " + key + "...");
        injectee.printFromCachesByKey(key);
    }

    private static void injectIntoIjectee(Injectee injectee, String injecteeName) {

        System.out.println("\nTrying to inject into " + injecteeName + "...");
        try {
            Injector.inject(injectee);
            System.out.println("Injection successful");
        } catch (InjectionException | NoCacheFoundException e) {
            System.out.println("Injection failed");
        }
    }
}