package com.epam.homework.annotations_reflection.cache.caches;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CachesPackageAnnotation {
    Class<?>[] implementationsOfCache() default {};
}