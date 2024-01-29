package ru.fsv67.services;

import org.springframework.stereotype.Service;
import ru.fsv67.models.Book;
import ru.fsv67.repositories.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Класс описывает логику взаимодействия пользователя с хранилищем книг
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Метод проверки информации о книге
     *
     * @param id идентификатор книги
     * @return если данные не пусты, то метод возвращает книгу по идентификатору, иначе исключение
     */
    public Book getBookById(long id) {
        Book book = bookRepository.getBookById(id);
        if (Objects.isNull(book)) {
            throw new NoSuchElementException("Книга с ID = " + id + " не найдена");
        }
        return book;
    }

    /**
     * Метод обрабатывает получение списка книг
     *
     * @return если список не пуст, то метод возвращает список книг, иначе исключение
     */
    public List<Book> getBooksList() {
        List<Book> books = bookRepository.getBooksList();
        if (books.isEmpty()) {
            throw new NoSuchElementException("Список книг в библиотеке пуст");
        }
        return books;
    }

    /**
     * Метод обрабатывает данные введенные пользователем для записи
     *
     * @param book данные о книге, введенные пользователем
     * @return информацию о книге подлежащие записи
     */
    public Book addNewBook(Book book) {
        if (book.getName().isEmpty()) {
            throw new RuntimeException("Название книги не задано");
        }
        return bookRepository.saveBook(book);
    }

    /**
     * Метод проверяет информацию перед удалением книги
     *
     * @param id идентификатор книги подлежащей удалению
     * @return описание удаленной книги
     */
    public Book deleteBookById(long id) {
        Book book = bookRepository.deleteBookById(id);
        if (Objects.isNull(book)) {
            throw new NoSuchElementException("Книга с ID = " + id + " не найдена");
        }
        return book;
    }
}
