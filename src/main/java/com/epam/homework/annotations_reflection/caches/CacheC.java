package com.epam.homework.annotations_reflection.caches;

import com.epam.homework.annotations_reflection.Cache;
import com.epam.homework.annotations_reflection.CacheDeclaration;

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