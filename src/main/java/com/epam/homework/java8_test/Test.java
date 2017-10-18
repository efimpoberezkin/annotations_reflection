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

        List<Author> authors = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        initializeData(authors, books);

        System.out.println("-- Authors --");
        authors.forEach(System.out::println);

        System.out.println("\n-- Books --");
        books.forEach(System.out::println);

        calculateAverageAuthorAge(authors);

        sortAuthorsByAge(authors);

        printRetiredAuthors(authors);

        printAgesOfBooks(books);

        printAuthorsWhoCooped(authors, books);

        printBooksByAuthors(authors, books);
    }

    private static void initializeData(List<Author> authors, List<Book> books) {
        Author authorPushkin
                = new Author("Pushkin", LocalDate.parse("1980-01-13"), Gender.MALE);
        Author authorLermontov
                = new Author("Lermontov", LocalDate.parse("1942-05-24"), Gender.FEMALE);
        Author authorTolstoy
                = new Author("Tolstoy", LocalDate.parse("1979-12-05"), Gender.FEMALE);
        Author authorDostoevsky
                = new Author("Dostoevsky", LocalDate.parse("1958-08-15"), LocalDate.parse("2012-03-15"), Gender.MALE);
        Author authorGogol
                = new Author("Gogol", LocalDate.parse("1951-03-12"), LocalDate.parse("2016-07-24"), Gender.MALE);
        Author authorBulgakov
                = new Author("Bulgakov", LocalDate.parse("1946-09-18"), LocalDate.parse("2014-11-07"), Gender.FEMALE);
        Author authorChekhov
                = new Author("Chekhov", LocalDate.parse("1953-03-07"), Gender.MALE);
        Author authorTurgenev
                = new Author("Turgenev", LocalDate.parse("1953-04-25"), Gender.FEMALE);
        Author authorYesenin
                = new Author("Yesenin", LocalDate.parse("1937-01-26"), Gender.MALE);
        Author authorNekrasov
                = new Author("Nekrasov", LocalDate.parse("1955-10-15"), LocalDate.parse("2007-12-03"), Gender.FEMALE);

        authors.addAll(new ArrayList<>(Arrays.asList(
                authorPushkin, authorLermontov, authorTolstoy, authorDostoevsky, authorGogol,
                authorBulgakov, authorChekhov, authorTurgenev, authorYesenin, authorNekrasov
        )));


        books.addAll(
                new ArrayList<>(Arrays.asList(
                new Book("Book 1", Year.of(2015), authorPushkin),
                new Book("Book 2", Year.of(2003), authorLermontov, authorTolstoy),
                new Book("Book 3", Year.of(2004), authorDostoevsky),
                new Book("Book 4", Year.of(1985), authorGogol),
                new Book("Book 5", Year.of(1993), authorGogol, authorBulgakov),
                new Book("Book 6", Year.of(1973), authorChekhov),
                new Book("Book 7", Year.of(2007), authorPushkin, authorBulgakov, authorTurgenev),
                new Book("Book 8", Year.of(1997), authorYesenin),
                new Book("Book 9", Year.of(2012), authorNekrasov),
                new Book("Book 10", Year.of(2009), authorPushkin, authorLermontov)
        )));
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