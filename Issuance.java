package ru.fsv67.models;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс описывает процесс выдачи книги в БД
 */
@Data
public class Issuance {
    private static long idCounter = 1L;

    private final long id;
    private final long bookId;
    private final long readerId;
    /**
     * Дата выдачи книги
     */
    private final LocalDateTime issuance_at;
    /**
     * Дата возврата книги
     */
    private LocalDateTime returned_at;

    public Issuance(long bookId, long readerId) {
        this.id = idCounter++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.issuance_at = LocalDateTime.now();
    }
}
