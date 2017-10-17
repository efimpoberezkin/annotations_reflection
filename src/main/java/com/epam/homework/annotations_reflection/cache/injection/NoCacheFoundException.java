package com.epam.homework.annotations_reflection.cache.injection;

public class NoCacheFoundException extends RuntimeException {

    public NoCacheFoundException(String message) {
        super(message);
    }

    public NoCacheFoundException(String message, Exception e) {
        super(message, e);
    }
}