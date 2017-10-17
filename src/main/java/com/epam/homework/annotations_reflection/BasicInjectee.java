package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheNamesContainer;
import com.epam.homework.annotations_reflection.cache.InjectCache;
import com.epam.homework.annotations_reflection.cache.injection.Injectable;

public class BasicInjectee implements Injectable {

    @InjectCache(name = CacheNamesContainer.CACHE_A_NAME)
    public Cache cache1;

}