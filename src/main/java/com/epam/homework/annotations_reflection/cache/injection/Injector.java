package com.epam.homework.annotations_reflection.cache.injection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;
import com.epam.homework.annotations_reflection.reflection.ClassFinder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Injector {

    private static final String CACHES_PACKAGE = "com.epam.homework.annotations_reflection.cache.caches";

    public static void inject(Injectable injectee) throws InjectionException, NoCacheFoundException {

        List<Class> implementationsOfCache = ClassFinder.find(CACHES_PACKAGE);
        List<Field> fields = findFields(injectee.getClass());

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

    private static List<Field> findFields(Class type) {
        List<Field> result = new ArrayList<>();

        Class cl = type;
        while (cl != null && cl != Object.class) {
            result.addAll(Arrays.asList(cl.getDeclaredFields()));
            cl = cl.getSuperclass();
        }

        return result;
    }
}