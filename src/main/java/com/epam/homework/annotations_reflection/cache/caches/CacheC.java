package com.epam.homework.annotations_reflection.cache.caches;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = "C")
public class CacheC<Integer, String> implements Cache<Integer, String> {

    private static CacheC instance;
    private Map<Integer, String> map;

    private CacheC() {
        map = new HashMap<>();
    }

    public static CacheC getInstance() {
        if (instance == null) {
            instance = new CacheC();
        }
        return instance;
    }

    @Override
    public void put(Integer k, String v) {
        map.put(k, v);
    }

    @Override
    public String get(Integer k) {
        return map.get(k);
    }

    @Override
    public boolean containsKey(Integer k) {
        return map.containsKey(k);
    }
}