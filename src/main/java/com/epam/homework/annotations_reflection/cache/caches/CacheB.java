package com.epam.homework.annotations_reflection.cache.caches;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;

import java.util.HashMap;
import java.util.Map;

@CacheDeclaration(name = CacheNamesContainer.CACHE_B_NAME)
public class CacheB<Integer, String> implements Cache<Integer, String> {

    private Map<Integer, String> map;

    public CacheB() {
        map = new HashMap<>();
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