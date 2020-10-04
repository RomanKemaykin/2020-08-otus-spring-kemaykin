package ru.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Дао для работы с авторами")
@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc jdbc;

    private static final long AUTHOR_ONE_ID = 1;
    private static final String AUTHOR_ONE_NAME = "author one";
    private static final Author AUTHOR_ONE = new Author(AUTHOR_ONE_ID, AUTHOR_ONE_NAME);
    private static final long AUTHOR_NEW_ID = 5;
    private static final String AUTHOR_NEW_NAME = "author new";
    private static final Author AUTHOR_NEW = new Author(AUTHOR_NEW_ID, AUTHOR_NEW_NAME);

    @DisplayName("возвращать автора по id")
    @Test
    void shouldReturnAuthorById() {
       Author actualAuthor = jdbc.getById(AUTHOR_ONE_ID);
       assertThat(actualAuthor).isEqualTo(AUTHOR_ONE);
    }

    @DisplayName("возвращать автора по имени")
    @Test
    void shouldReturnAuthorByName() {
        Author actualAuthor = jdbc.getByName(AUTHOR_ONE_NAME);
        assertThat(actualAuthor).isEqualTo(AUTHOR_ONE);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author authorForInsert = new Author(0, AUTHOR_NEW_NAME);
        long actual_id = jdbc.insert(authorForInsert);
        Author actualAuthor = jdbc.getById(actual_id);
        assertThat(actualAuthor).isEqualTo(AUTHOR_NEW);
    }

}