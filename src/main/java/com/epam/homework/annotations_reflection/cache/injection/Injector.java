package com.epam.homework.annotations_reflection.cache.injection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;
import com.epam.homework.annotations_reflection.cache.InjectCache;
import com.epam.homework.annotations_reflection.reflection.ClassFinder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Injector {

    public static void inject(Injectable injectee) throws InjectionException, NoCacheFoundException {

        List<Class> implementationsOfCache = ClassFinder.find("com.epam.homework.annotations_reflection.cache.caches");

        Set<Field> fields = new HashSet(Arrays.asList(injectee.getClass().getFields()));
        fields.addAll(Arrays.asList(injectee.getClass().getDeclaredFields()));
        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectCache.class)) {
                InjectCache fieldAnnotation = field.getAnnotation(InjectCache.class);
                Class requiredCacheClass = null;
                for (Class cl : implementationsOfCache) {
                    CacheDeclaration clAnnotation = (CacheDeclaration) cl.getAnnotation(CacheDeclaration.class);
                    if (fieldAnnotation.name().equals(clAnnotation.name())) {
                        requiredCacheClass = cl;
                    }
                }
                try {
                    Constructor cacheConstructor = requiredCacheClass.getDeclaredConstructor();
                    cacheConstructor.setAccessible(true);
                    Cache cache = (Cache) cacheConstructor.newInstance();
                    field.setAccessible(true);
                    field.set(injectee, cache);
                } catch (NullPointerException e) {
                    throw new NoCacheFoundException("Cache " + fieldAnnotation.name() + " not found", e);
                } catch (IllegalAccessException | NoSuchMethodException
                        | InstantiationException | InvocationTargetException e) {
                    throw new InjectionException("Can not inject cache " + requiredCacheClass.getName(), e);
                }
            }
        }
    }
}