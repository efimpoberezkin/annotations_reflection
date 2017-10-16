package com.epam.homework.annotations_reflection.cache.caches;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "A")
public class CacheA<Integer, String> implements Cache<Integer, String> {

    private static CacheA instance;
    private Map<Integer, String> map;

    private CacheA() {
        map = new HashMap<>();
    }

    public static CacheA getInstance() {
        if (instance == null) {
            instance = new CacheA();
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