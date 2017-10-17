package com.epam.homework.annotations_reflection.cache.caches;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = CacheNamesContainer.CACHE_A_NAME)
public class CacheA<Integer, String> implements Cache<Integer, String> {

    private static CacheA instance;
    private Map<Integer, String> map;

    private CacheA() {
        if (instance == null) {
            map = new HashMap<>();
        } else {
            map = getInstance().getMap();
        }
    }

    private Map<Integer, String> getMap() {
        return map;
    }

    public static CacheA getInstance() {
        if (instance == null) {
            instance = new CacheA();
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