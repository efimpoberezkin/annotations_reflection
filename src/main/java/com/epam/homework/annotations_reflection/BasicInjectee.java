package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.InjectCache;
import com.epam.homework.annotations_reflection.injection.Injectable;

public class BasicInjectee implements Injectable {

    @InjectCache(name = "A")
    public Cache cache1;

}