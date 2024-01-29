package ru.fsv67.models;

import lombok.Data;

@Data
public class Book {
    private static long idCounter = 1L;

    private final long id;
    private final String name;

    public Book(String name) {
        this.id = idCounter++;
        this.name = name;
    }
}
