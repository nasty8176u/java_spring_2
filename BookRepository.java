package ru.fsv67.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс описывает взаимодействие с хранилищем книг
 */
@Repository
public class BookRepository {
    private final List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        books.addAll(List.of(
                new Book("Чистый код"),
                new Book("Паттерны проектирования"),
                new Book("Совершенный код"),
                new Book("Программист-прагматик"),
                new Book("Идеальный программист"),
                new Book("Карьера программиста")
        ));
    }

    /**
     * Получение всех книг в библиотеке
     *
     * @return список книг
     */
    public List<Book> getBooksList() {
        return books;
    }

    /**
     * Получение книги по её ID
     *
     * @param id идентификатор книги
     * @return найденную книгу
     */
    public Book getBookById(long id) {
        return books.stream().filter(book -> Objects.equals(book.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Сохранение новой книги в хранилище
     *
     * @param book описание новой книги
     * @return новую книгу
     */
    public Book saveBook(Book book) {
        books.add(book);
        return book;
    }

    /**
     * Удаление книги по ID
     *
     * @param id идентификатор книги
     * @return информацию по удаленной книги
     */
    public Book deleteBookById(long id) {
        Book book = getBookById(id);
        books.remove(book);
        return book;
    }
}
