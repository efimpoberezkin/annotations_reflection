package com.epam.homework.annotations_reflection.cache.caches;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = CacheNamesContainer.CACHE_C_NAME)
public class CacheC<Integer, String> implements Cache<Integer, String> {

    private static CacheC instance;
    private Map<Integer, String> map;

    private CacheC() {
        if (instance == null) {
            map = new HashMap<>();
        } else {
            map = getInstance().getMap();
        }
    }

    private Map<Integer, String> getMap() {
        return map;
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