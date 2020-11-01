package ru.otus.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Дао для работы с авторами")
@DataJpaTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    private static final long GENRE_ONE_ID = 1;
    private static final String GENRE_ONE_NAME = "genre one";
    private static final Genre GENRE_ONE = new Genre(GENRE_ONE_ID, GENRE_ONE_NAME);
    private static final long GENRE_NEW_ID = 5;
    private static final String GENRE_NEW_NAME = "genre new";
    private static final Genre GENRE_NEW = new Genre(GENRE_NEW_ID, GENRE_NEW_NAME);

    @DisplayName("возвращать жанр по id")
    @Test
    void shouldReturnGenreById() {
        Genre actualGenre = genreRepository.getById(GENRE_ONE_ID);
        assertThat(actualGenre).isEqualTo(GENRE_ONE);
    }

    @DisplayName("возвращать жанр по имени")
    @Test
    void shouldReturnGenreByName() {
        Genre actualGenre = genreRepository.getByName(GENRE_ONE_NAME);
        assertThat(actualGenre).isEqualTo(GENRE_ONE);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre genreForInsert = new Genre(0, GENRE_NEW_NAME);
        genreRepository.save(genreForInsert);
        Genre actualGenre = genreRepository.getById(genreForInsert.getId());
        assertThat(actualGenre).isEqualTo(GENRE_NEW);
    }
}