package com.epam.homework.annotations_reflection;

public class InjecteeInheritor extends Injectee {

    @InjectCache(name = "A")
    public Cache cache3;

    @InjectCache(name = "C")
    private Cache cache4;

    private String dummyStr;

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