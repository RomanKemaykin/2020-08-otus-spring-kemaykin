package ru.otus.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.models.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Дао для работы с комментариями к книгам должно:")
@DataJpaTest
class BookCommentRepositoryTest {
    @Autowired
    private BookCommentRepository bookCommentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final long AUTHOR_ONE_ID = 1;
    private static final String AUTHOR_ONE_NAME = "author one";
    private static final Author AUTHOR_ONE = new Author(AUTHOR_ONE_ID, AUTHOR_ONE_NAME);
    private static final long AUTHOR_TWO_ID = 2;
    private static final String AUTHOR_TWO_NAME = "author two";
    private static final Author AUTHOR_TWO = new Author(AUTHOR_TWO_ID, AUTHOR_TWO_NAME);
    private static final long AUTHOR_NEW_ID = 5;
    private static final String AUTHOR_NEW_NAME = "author new";
    private static final Author AUTHOR_NEW = new Author(AUTHOR_NEW_ID, AUTHOR_NEW_NAME);
    private static final Author AUTHOR_NEW_WITHOUT_ID = new Author(0, AUTHOR_NEW_NAME);

    private static final long GENRE_ONE_ID = 1;
    private static final String GENRE_ONE_NAME = "genre one";
    private static final Genre GENRE_ONE = new Genre(GENRE_ONE_ID, GENRE_ONE_NAME);
    private static final long GENRE_TWO_ID = 2;
    private static final String GENRE_TWO_NAME = "genre two";
    private static final Genre GENRE_TWO = new Genre(GENRE_TWO_ID, GENRE_TWO_NAME);
    private static final long GENRE_NEW_ID = 5;
    private static final String GENRE_NEW_NAME = "genre new";
    private static final Genre GENRE_NEW = new Genre(GENRE_NEW_ID, GENRE_NEW_NAME);
    private static final Genre GENRE_NEW_WITHOUT_ID = new Genre(0, GENRE_NEW_NAME);

    private static final long BOOK_ONE_ID = 1;

    private static final String BOOK_ONE_TITLE = "book one";
    private static final Book BOOK_ONE = new Book(BOOK_ONE_ID, BOOK_ONE_TITLE, AUTHOR_ONE, GENRE_ONE, null);
    private static final long BOOK_NEW_ID = 5;
    private static final String BOOK_NEW_TITLE = "book new";
    private static final Book BOOK_NEW = new Book(BOOK_NEW_ID, BOOK_NEW_TITLE, AUTHOR_NEW, GENRE_NEW, null);

    private static final long BOOK_ONE_COMMENT1_ID = 1;
    private static final String BOOK_ONE_COMMENT1_COMMENT = "book one comment1";
    private static final BookComment BOOK_ONE_COMMENT1 = new BookComment(BOOK_ONE_COMMENT1_ID, BOOK_ONE_COMMENT1_COMMENT, BOOK_ONE);
    private static final long BOOK_ONE_COMMENT2_ID = 2;
    private static final String BOOK_ONE_COMMENT2_COMMENT = "book one comment2";
    private static final BookComment BOOK_ONE_COMMENT2 = new BookComment(BOOK_ONE_COMMENT2_ID, BOOK_ONE_COMMENT2_COMMENT, BOOK_ONE);
    private static final long BOOK_ONE_COMMENT3_ID = 3;
    private static final String BOOK_ONE_COMMENT3_COMMENT = "book one comment3";
    private static final BookComment BOOK_ONE_COMMENT3 = new BookComment(BOOK_ONE_COMMENT3_ID, BOOK_ONE_COMMENT3_COMMENT, BOOK_ONE);
    private static final String BOOK_ONE_COMMENT1_COMMENT_NEW = "book one comment111";
    private static final BookComment BOOK_ONE_COMMENT1_NEW = new BookComment(BOOK_ONE_COMMENT1_ID, BOOK_ONE_COMMENT1_COMMENT_NEW, BOOK_ONE);
    private static final long BOOK_ONE_COMMENT_NEW_ID = 7;
    private static final String BOOK_ONE_COMMENT_NEW_COMMENT = "book one comment new";
    private static final BookComment BOOK_ONE_COMMENT_NEW = new BookComment(BOOK_ONE_COMMENT_NEW_ID, BOOK_ONE_COMMENT_NEW_COMMENT, BOOK_ONE);

    private static final String FIELD_ID = "id";
    private static final String FIELD_COMMENT = "comment";

    @DisplayName("сохранять комментарий")
    @Test
    void shouldSaveBookComment() {
        Book book = em.find(Book.class, BOOK_ONE_ID);
        BookComment expectedBookComment = new BookComment(0, BOOK_ONE_COMMENT_NEW_COMMENT, book);
        bookCommentRepositoryJpa.save(expectedBookComment);
        BookComment actualBookComment = em.find(BookComment.class, BOOK_ONE_COMMENT_NEW_ID);
        assertThat(actualBookComment)
                .hasFieldOrPropertyWithValue(FIELD_COMMENT, BOOK_ONE_COMMENT_NEW_COMMENT);
    }

    @DisplayName("возвращать комментарий по id")
    @Test
    void shouldReturnBookCommentById() {
        BookComment BookComment = bookCommentRepositoryJpa.getById(BOOK_ONE_COMMENT1_ID);
        assertThat(BookComment)
                .isEqualToComparingOnlyGivenFields(BOOK_ONE_COMMENT1, FIELD_ID)
                .isEqualToComparingOnlyGivenFields(BOOK_ONE_COMMENT1, FIELD_COMMENT);
    }

    @DisplayName("изменять текст заданного комментария")
    @Test
    void shouldUpdateCommentById() {
        BookComment BookCommentForUpdate = em.find(BookComment.class, BOOK_ONE_COMMENT1_ID);
        BookCommentForUpdate.setComment(BOOK_ONE_COMMENT1_COMMENT_NEW);
        bookCommentRepositoryJpa.save(BookCommentForUpdate);
        BookComment actualBookComment = em.find(BookComment.class, BOOK_ONE_COMMENT1_ID);
        assertThat(actualBookComment)
                .hasFieldOrPropertyWithValue(FIELD_COMMENT, BOOK_ONE_COMMENT1_COMMENT_NEW);
    }

    @DisplayName("удалять заданный комментарий")
    @Test
    void shouldDelete() {
        BookComment deletingBookComment = em.find(BookComment.class, BOOK_ONE_COMMENT1_ID);
        bookCommentRepositoryJpa.delete(deletingBookComment);
        assertThat(em.find(BookComment.class, BOOK_ONE_COMMENT1_ID)).isNull();
    }
}