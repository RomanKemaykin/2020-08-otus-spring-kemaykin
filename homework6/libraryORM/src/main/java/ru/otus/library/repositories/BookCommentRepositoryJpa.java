package ru.otus.library.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.models.BookCommentWithBook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BookCommentWithBook getById(long id) {
        return em.find(BookCommentWithBook.class, id);
    }

    @Override
    public void save(BookCommentWithBook bookCommentWithBook) {
        if (bookCommentWithBook.getId() <= 0) {
            em.persist(bookCommentWithBook);
        } else {
            em.merge(bookCommentWithBook);
        }
    }

    @Override
    public void update(BookCommentWithBook bookCommentWithBook) {
        em.merge(bookCommentWithBook);
    }

    @Override
    public void delete(BookCommentWithBook bookCommentWithBook) {
        em.remove(bookCommentWithBook);
    }
}
