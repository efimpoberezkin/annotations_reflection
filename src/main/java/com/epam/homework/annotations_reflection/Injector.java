package com.epam.homework.annotations_reflection;

import com.epam.homework.annotations_reflection.caches.CachesPackageAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Injector {

    static void inject(Injectable injectee) throws InjectionException {

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
                Class requiredCacheClass = null;
                for (Class cl : implementationsOfCache) {
                    CacheDeclaration clAnnotation = (CacheDeclaration) cl.getAnnotation(CacheDeclaration.class);
                    InjectCache fieldAnnotation = field.getAnnotation(InjectCache.class);
                    if (fieldAnnotation.name().equals(clAnnotation.name())) {
                        requiredCacheClass = cl;
                    }
                }
                try {
                    Cache cache = (Cache) requiredCacheClass.getDeclaredMethod("getInstance").invoke(null);
                    field.setAccessible(true);
                    field.set(injectee, cache);
                } catch (IllegalAccessException | NoSuchMethodException
                        | InvocationTargetException | NullPointerException e) {
                    throw new InjectionException();
                }
            }
        }
    }
}