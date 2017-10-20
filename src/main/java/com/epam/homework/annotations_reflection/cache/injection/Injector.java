package com.epam.homework.annotations_reflection.cache.injection;

import com.epam.homework.annotations_reflection.cache.Cache;

import java.lang.reflect.Field;
import java.util.*;

public class Injector {

    private static final String CACHES_PACKAGE = "com.epam.homework.annotations_reflection.cache.caches";

    private static Map<String, Class> implementationsOfCache = initializeMapOfCaches();

    private  static Map<String, Class> initializeMapOfCaches() {
        Map<String, Class> mapOfCaches;
        try {
            mapOfCaches = CacheFinder.find(CACHES_PACKAGE);
        } catch (IllegalArgumentException | ClassNotFoundException e) {
            throw new InjectionException("Unable to get cache classes from package " + CACHES_PACKAGE, e);
        }
        return mapOfCaches;
    }

    public static void inject(Injectable injectee) throws InjectionException, NoCacheFoundException {
        List<Field> fields = findFields(injectee.getClass());

        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectCache.class)) {
                InjectCache fieldAnnotation = field.getAnnotation(InjectCache.class);
                Class requiredCacheClass;
                if (implementationsOfCache.containsKey(fieldAnnotation.name())) {
                    requiredCacheClass = implementationsOfCache.get(fieldAnnotation.name());
                } else {
                    throw new NoCacheFoundException("Cache " + fieldAnnotation.name() + " not found");
                }
                try {
                    Cache cache = (Cache) requiredCacheClass.newInstance();
                    CacheFiller.fillCache(cache);
                    field.setAccessible(true);
                    field.set(injectee, cache);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new InjectionException("Can not inject cache " + requiredCacheClass.getName(), e);
                }
            }
        }
    }

    private static List<Field> findFields(Class type) {
        List<Field> result = new ArrayList<>();

        Class cl = type;
        while (cl != Object.class) {
            result.addAll(Arrays.asList(cl.getDeclaredFields()));
            cl = cl.getSuperclass();
        }

        return result;
    }
}