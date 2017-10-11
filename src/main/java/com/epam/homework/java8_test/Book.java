package com.epam.homework.java8_test;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private String name;
    private Year yearOfPublication;
    private List<Author> authors;

    public Book(String name, Year yearOfPublication, List<Author> authors) {
        this.name = name;
        this.yearOfPublication = yearOfPublication;
        this.authors = new ArrayList<>(authors);
    }

    public String getName() {
        return name;
    }

    public Year getYearOfPublication() {
        return yearOfPublication;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", authors=" + authors +
                '}';
    }
}