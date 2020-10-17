package ru.otus.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Дао для работы с авторами")
@DataJpaTest
@Import({AuthorRepositoryJpa.class})
class AuthorRepositoryJpaTest {
    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    private static final long AUTHOR_ONE_ID = 1;
    private static final String AUTHOR_ONE_NAME = "author one";
    private static final Author AUTHOR_ONE = new Author(AUTHOR_ONE_ID, AUTHOR_ONE_NAME);
    private static final long AUTHOR_NEW_ID = 5;
    private static final String AUTHOR_NEW_NAME = "author new";
    private static final Author AUTHOR_NEW = new Author(AUTHOR_NEW_ID, AUTHOR_NEW_NAME);

    @DisplayName("возвращать автора по id")
    @Test
    void shouldReturnAuthorById() {
       Author actualAuthor = authorRepositoryJpa.getById(AUTHOR_ONE_ID);
       assertThat(actualAuthor).isEqualTo(AUTHOR_ONE);
    }

    @DisplayName("возвращать автора по имени")
    @Test
    void shouldReturnAuthorByName() {
        Author actualAuthor = authorRepositoryJpa.getByName(AUTHOR_ONE_NAME);
        assertThat(actualAuthor).isEqualTo(AUTHOR_ONE);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author authorForInsert = new Author(0, AUTHOR_NEW_NAME);
        authorRepositoryJpa.save(authorForInsert);
        Author actualAuthor = authorRepositoryJpa.getById(authorForInsert.getId());
        assertThat(actualAuthor).isEqualTo(AUTHOR_NEW);
    }

}