package com.epam.homework.annotations_reflection.injection;

import com.epam.homework.annotations_reflection.cache.Cache;
import com.epam.homework.annotations_reflection.cache.CacheDeclaration;
import com.epam.homework.annotations_reflection.cache.InjectCache;
import com.epam.homework.annotations_reflection.cache.caches.CachesPackageAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Injector {

    public static void inject(Injectable injectee) throws InjectionException, NoCacheFoundException {

        Package[] packages = Package.getPackages();
        Class[] implementationsOfCache = new Class[]{};
        for (Package p : packages) {
            CachesPackageAnnotation annotation = p.getAnnotation(CachesPackageAnnotation.class);
            if (annotation != null) {
                implementationsOfCache = annotation.implementationsOfCache();
            }
        }

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