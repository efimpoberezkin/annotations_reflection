package com.epam.homework.annotations_reflection.caches;

import com.epam.homework.annotations_reflection.Cache;
import com.epam.homework.annotations_reflection.CacheDeclaration;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "B")
public class CacheB<Integer, String> implements Cache<Integer, String> {

    private static CacheB instance;
    private Map<Integer, String> map;

    private CacheB() {
        map = new HashMap<>();
    }

    public static CacheB getInstance() {
        if (instance == null) {
            instance = new CacheB();
        }
        return instance;
    }

    public void put(Integer k, String v) {
        map.put(k, v);
    }

    public String get(Integer k) {
        return map.get(k);
    }

    public boolean containsKey(Integer k) {
        return map.containsKey(k);
    }
}