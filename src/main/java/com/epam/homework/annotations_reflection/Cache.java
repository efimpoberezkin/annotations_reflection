package com.epam.homework.annotations_reflection;

public interface Cache<Integer, String> {
    void put(Integer k, String v);
    String get(Integer k);
    boolean containsKey(Integer k);
}