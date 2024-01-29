package ru.fsv67.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fsv67.controllers.IssuanceRequest;
import ru.fsv67.models.Issuance;
import ru.fsv67.repositories.BookRepository;
import ru.fsv67.repositories.IssuanceRepository;
import ru.fsv67.repositories.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class IssuanceService {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssuanceRepository issuanceRepository;
    private final int maxIssuedBooks;

    public IssuanceService(BookRepository bookRepository,
                           ReaderRepository readerRepository,
                           IssuanceRepository issuanceRepository,
                           @Value("${application.max-allowed-books:1}") int maxIssuedBooks) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.issuanceRepository = issuanceRepository;
        this.maxIssuedBooks = maxIssuedBooks;
    }

    /**
     * Метод проверяет получение списка всех выдач книг
     *
     * @return если список не пуст, то метод возвращает список всех выдач книг, иначе исключение
     */
    public List<Issuance> getIssuanceList() {
        if (issuanceRepository.readIssuanceList().isEmpty()) {
            throw new NullPointerException("Книги не кому не выдавались");
        }
        return issuanceRepository.readIssuanceList();
    }

    /**
     * Метод обработки получения выдачи по ID
     *
     * @param id идентификатор выдачи
     * @return если выдач с ID найдена, то метод выведет выдачу, иначе исключение
     */
    public Issuance getIssuanceById(long id) {
        Issuance issuance = issuanceRepository.getIssuanceById(id);
        if (Objects.isNull(issuance)) {
            throw new NoSuchElementException("Выдача с ID = " + id + " не найдена");
        }
        return issuance;
    }

    /**
     * Метод поиска выдачи книг читателю по ID
     *
     * @param id идентификатор читателя
     * @return список всех выдач книг читателю c ID
     */
    public List<Issuance> getIssuanceByIdReader(long id) {
        return issuanceRepository.getIssuanceListByIdReader(id);
    }

    /**
     * Метод обрабатывает введенные данные пользователем
     * при выдаче книг читателю
     *
     * @param issuanceRequest данные введенные пользователем
     * @return если данные введенные пользователем корректны, то метод вернет информацию о выдаче книги читателю,
     * иначе исключение
     */
    public Issuance issuanceBook(IssuanceRequest issuanceRequest) {
        if (bookRepository.getBookById(issuanceRequest.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с ID = " + issuanceRequest.getBookId());
        }
        if (readerRepository.getReaderById(issuanceRequest.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с ID = " + issuanceRequest.getReaderId());
        }
        if (getIssuanceByIdReader(issuanceRequest.getReaderId()).size() >= maxIssuedBooks) {
            throw new IllegalStateException(
                    "Читатель с ID = " + issuanceRequest.getReaderId() + " превысил лимит книг в одни руки"
            );
        }
        Issuance issuance = new Issuance(issuanceRequest.getBookId(), issuanceRequest.getReaderId());
        issuanceRepository.saveIssuance(issuance);
        return issuance;
    }

    /**
     * Метод проставляет дату возврата книги читателем, тем самым закрывает выдачу
     *
     * @param issuance выдача которую необходимо закрыть
     */
    public void returnBookByReader(Issuance issuance) {
        if (!Objects.isNull(issuance.getReturned_at())) {
            throw new NoSuchElementException("Выдача с ID = " + issuance.getId() + " закрыта");
        }
        issuanceRepository.returnBookByReader(issuance);
    }
}
