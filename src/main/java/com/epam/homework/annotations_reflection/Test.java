package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.injection.InjectionException;
import com.epam.homework.annotations_reflection.cache.injection.Injector;
import com.epam.homework.annotations_reflection.cache.injection.NoCacheFoundException;

public class Test {

    public static void main(String[] args) {
        perform_test();
    }

    private static void perform_test() {

        Injectee injectee = new Injectee();
        InjecteeInheritor injecteeInheritor = new InjecteeInheritor();
        int key = 1; //for example 1, 2, 3, 4, 5, 6 for tests

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