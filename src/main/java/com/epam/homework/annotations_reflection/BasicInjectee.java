package com.epam.homework.annotations_reflection;

public class BasicInjectee implements Injectable {

    @InjectCache(name = "A")
    public Cache cache1;

}