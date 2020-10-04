package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Дао для работы с авторами")
@JdbcTest
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc jdbc;

    private static final long GENRE_ONE_ID = 1;
    private static final String GENRE_ONE_NAME = "genre one";
    private static final Genre GENRE_ONE = new Genre(GENRE_ONE_ID, GENRE_ONE_NAME);
    private static final long GENRE_NEW_ID = 5;
    private static final String GENRE_NEW_NAME = "genre new";
    private static final Genre GENRE_NEW = new Genre(GENRE_NEW_ID, GENRE_NEW_NAME);

    @DisplayName("возвращать жанр по id")
    @Test
    void shouldReturnGenreById() {
        Genre actualGenre = jdbc.getById(GENRE_ONE_ID);
        assertThat(actualGenre).isEqualTo(GENRE_ONE);
    }

    @DisplayName("возвращать жанр по имени")
    @Test
    void shouldReturnGenreByName() {
        Genre actualGenre = jdbc.getByName(GENRE_ONE_NAME);
        assertThat(actualGenre).isEqualTo(GENRE_ONE);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre genreForInsert = new Genre(0, GENRE_NEW_NAME);
        long actual_id = jdbc.insert(genreForInsert);
        Genre actualGenre = jdbc.getById(actual_id);
        assertThat(actualGenre).isEqualTo(GENRE_NEW);
    }
}