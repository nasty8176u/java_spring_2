package ru.fsv67.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {
    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new Reader("Алексей"),
                new Reader("Николай"),
                new Reader("Михаил"),
                new Reader("Вераника"),
                new Reader("Сергей"),
                new Reader("Марина")
        ));
    }

    /**
     * Получение всех читателей
     *
     * @return список читателей
     */
    public List<Reader> getReaderList() {
        return readers;
    }

    /**
     * Получение читателя по ID
     *
     * @param id идентификатор читателя
     * @return найденного читателя
     */
    public Reader getReaderById(long id) {
        return readers.stream()
                .filter(reader -> Objects.equals(reader.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Сохранение нового читателя в хранилище
     *
     * @param reader описание нового читателя
     * @return нового читателя
     */
    public Reader saveReader(Reader reader) {
        readers.add(reader);
        return reader;
    }

    /**
     * Удаление читателя по ID
     *
     * @param id идентификатор читателя
     * @return информацию по удаленному читателю
     */
    public Reader deleteReaderById(long id) {
        Reader reader = getReaderById(id);
        readers.remove(reader);
        return reader;
    }
}
