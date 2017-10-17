package com.epam.homework.annotations_reflection.injection;

public class InjectionException extends RuntimeException {

    public InjectionException(String message) {
        super(message);
    }

    public InjectionException(String message, Exception e) {
        super(message, e);
    }
}