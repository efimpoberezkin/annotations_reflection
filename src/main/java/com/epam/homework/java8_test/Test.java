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
        Author authorCervantes
                = new Author("Cervantes", LocalDate.parse("1980-01-13"), Gender.MALE);
        Author authorChristie
                = new Author("Christie", LocalDate.parse("1942-05-24"), Gender.FEMALE);
        Author authorTolstoy
                = new Author("Tolstoy", LocalDate.parse("1979-12-05"), Gender.MALE);
        Author authorShakespeare
                = new Author("Shakespeare", LocalDate.parse("1958-08-15"), LocalDate.parse("2012-03-15"), Gender.MALE);
        Author authorHomer
                = new Author("Homer", LocalDate.parse("1951-03-12"), LocalDate.parse("2016-07-24"), Gender.MALE);
        Author authorAkhmatova
                = new Author("Akhmatova", LocalDate.parse("1946-09-18"), LocalDate.parse("2014-11-07"), Gender.FEMALE);
        Author authorTwain
                = new Author("Twain", LocalDate.parse("1953-03-07"), Gender.MALE);
        Author authorTsvetaeva
                = new Author("Tsvetaeva", LocalDate.parse("1953-04-25"), Gender.FEMALE);
        Author authorYesenin
                = new Author("Yesenin", LocalDate.parse("1937-01-26"), Gender.MALE);
        Author authorRowling
                = new Author("Rowling", LocalDate.parse("1955-10-15"), LocalDate.parse("2007-12-03"), Gender.FEMALE);

        authors.addAll(new ArrayList<>(Arrays.asList(
                authorCervantes, authorChristie, authorTolstoy, authorShakespeare, authorHomer,
                authorAkhmatova, authorTwain, authorTsvetaeva, authorYesenin, authorRowling
        )));


        books.addAll(
                new ArrayList<>(Arrays.asList(
                        new Book("Don Quixote", Year.of(2015), authorCervantes),
                        new Book("Murder on the Orient Express", Year.of(2003), authorChristie),
                        new Book("War and Peace", Year.of(2004), authorTolstoy),
                        new Book("Hamlet", Year.of(1985), authorShakespeare),
                        new Book("The Odyssey", Year.of(1993), authorHomer),
                        new Book("Poems", Year.of(1973), authorAkhmatova, authorTsvetaeva, authorYesenin),
                        new Book("The Adventures of Huckleberry Finn", Year.of(2007), authorTwain),
                        new Book("Harry Potter", Year.of(1997), authorRowling),
                        new Book("Macbeth", Year.of(2012), authorShakespeare),
                        new Book("Some random book", Year.of(2009), authorCervantes, authorChristie)
                )));
    }

    private static void calculateAverageAuthorAge(List<Author> authors) {

        double averageAge = authors.stream()
                .mapToLong(author -> ChronoUnit.YEARS.between(
                        author.getDateOfBirth(),
                        author.getDateOfDeath().orElse(LocalDate.now())))
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