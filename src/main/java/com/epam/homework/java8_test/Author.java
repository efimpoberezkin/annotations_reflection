package com.epam.homework.java8_test;

import java.time.LocalDate;
import java.util.Optional;

public class Author {

    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private Gender gender;

    public Author(String name, LocalDate dateOfBirth, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = null;
        this.gender = gender;
    }

    public Author(String name, LocalDate dateOfBirth, LocalDate dateOfDeath, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Optional<LocalDate> getDateOfDeath() {
        Optional<LocalDate> opt = Optional.ofNullable(dateOfDeath);
        return opt;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", gender=" + gender +
                '}';
    }
}