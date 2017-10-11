package com.epam.homework.java8_test;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        perform_tests();
    }

    private static void perform_tests() {

        Author author1 = new Author("Author 1", LocalDate.parse("1980-01-13"), Gender.MALE);
        Author author2 = new Author("Author 2", LocalDate.parse("1942-05-24"), Gender.FEMALE);
        Author author3 = new Author("Author 3", LocalDate.parse("1979-12-05"), Gender.FEMALE);
        Author author4 = new Author("Author 4", LocalDate.parse("1958-08-15"), LocalDate.parse("2012-03-15"), Gender.MALE);
        Author author5 = new Author("Author 5", LocalDate.parse("1951-03-12"), LocalDate.parse("2016-07-24"), Gender.MALE);
        Author author6 = new Author("Author 6", LocalDate.parse("1946-09-18"), LocalDate.parse("2014-11-07"), Gender.FEMALE);
        Author author7 = new Author("Author 7", LocalDate.parse("1953-03-07"), Gender.MALE);
        Author author8 = new Author("Author 8", LocalDate.parse("1953-04-25"), Gender.FEMALE);
        Author author9 = new Author("Author 9", LocalDate.parse("1937-01-26"), Gender.MALE);
        Author author10 = new Author("Author 10", LocalDate.parse("1955-10-15"), LocalDate.parse("2007-12-03"), Gender.FEMALE);

        List<Author> authors = new ArrayList<>(Arrays.asList(
                author1, author2, author3, author4, author5, author6, author7, author8, author9, author10
        ));

        List<Book> books = new ArrayList<>(Arrays.asList(
                new Book("Book 1", Year.of(2015), Arrays.asList(new Author[]{author1})),
                new Book("Book 2", Year.of(2003), Arrays.asList(new Author[]{author2, author3})),
                new Book("Book 3", Year.of(2004), Arrays.asList(new Author[]{author4})),
                new Book("Book 4", Year.of(1985), Arrays.asList(new Author[]{author5})),
                new Book("Book 5", Year.of(1993), Arrays.asList(new Author[]{author5, author6})),
                new Book("Book 6", Year.of(1973), Arrays.asList(new Author[]{author7})),
                new Book("Book 7", Year.of(2007), Arrays.asList(new Author[]{author1, author6, author8})),
                new Book("Book 8", Year.of(1997), Arrays.asList(new Author[]{author9})),
                new Book("Book 9", Year.of(2012), Arrays.asList(new Author[]{author10})),
                new Book("Book 10", Year.of(2009), Arrays.asList(new Author[]{author1, author2}))
        ));

        System.out.println("-- Authors --");
        authors.forEach(author -> System.out.println(author));

        System.out.println("\n-- Books --");
        books.forEach(book -> System.out.println(book));

        calculateAverageAuthorAge(authors);

        sortAuthorsByAge(authors);

        printRetiredAuthors(authors);

        printAgesOfBooks(books);

        printAuthorsWhoCooped(authors, books);

        printBooksByAuthors(authors, books);
    }

    private static void calculateAverageAuthorAge(List<Author> authors) {
        double averageAge = authors.stream()
                .mapToLong(author -> ChronoUnit.YEARS.between(author.getDateOfBirth(), LocalDate.now()))
                .average()
                .getAsDouble();
        System.out.println("\n-- Average age of authors --\n" + averageAge);
    }

    private static void sortAuthorsByAge(List<Author> authors) {
        List<Author> authorsToSort = new ArrayList<>(authors);
        Collections.sort(authorsToSort, (a1, a2) -> a2.getDateOfBirth().compareTo(a1.getDateOfBirth()));
        System.out.println("\n-- Authors sorted by age, ascending --");
        authorsToSort.forEach(author -> System.out.println(author));
    }

    private static void printRetiredAuthors(List<Author> authors) {
        List retired = authors.stream().filter(author ->
                !author.getDateOfDeath().isPresent()
                        && ((author.getGender() == Gender.MALE
                        && ChronoUnit.YEARS.between(author.getDateOfBirth(), LocalDate.now()) >= 65)
                        || (author.getGender() == Gender.FEMALE
                        && ChronoUnit.YEARS.between(author.getDateOfBirth(), LocalDate.now()) >= 63)))
                .collect(Collectors.toList());
        System.out.println("\n-- Retired authors --");
        retired.forEach(author -> System.out.println(author));
    }

    private static void printAgesOfBooks(List<Book> books) {
        System.out.println("\n-- Ages of books (in years) --");
        books.forEach(book -> System.out.println(book.getName() + " - "
                + ChronoUnit.YEARS.between(book.getYearOfPublication(), Year.now())));
    }

    private static void printAuthorsWhoCooped(List<Author> authors, List<Book> books) {
        List authorsWhoCooped = authors.stream()
                .filter(author -> !books.stream()
                        .filter(book -> book.getAuthors().size() > 1 && book.getAuthors().contains(author))
                        .collect(Collectors.toList())
                        .isEmpty()
                ).collect(Collectors.toList());
        System.out.println("\n-- Authors who cooped with other authors --");
        authorsWhoCooped.forEach(author -> System.out.println(author));
    }

    private static void printBooksByAuthors(List<Author> authors, List<Book> books) {
        System.out.print("\n-- Books by authors --");
        authors.forEach(author -> {
            System.out.print("\n" + author.getName() + " - ");
            books.stream()
                    .filter(book -> book.getAuthors().contains(author))
                    .collect(Collectors.toList())
                    .forEach(book -> System.out.print(book.getName() + "; "));
        });
    }
}