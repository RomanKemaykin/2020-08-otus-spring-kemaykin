package ru.otus.library.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BookComment getById(long id) {
        TypedQuery<BookComment> query = em.createQuery("select bc from BookComment bc where id = :id", BookComment.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(BookComment bookComment) {
        if (bookComment.getId() <= 0) {
            em.persist(bookComment);
        } else {
            em.merge(bookComment);
        }
    }

    @Override
    public List<BookComment> getListByBookId(long bookId) {
        TypedQuery<BookComment> query = em.createQuery("select bc from BookComment bc where book_Id = :bookId", BookComment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public void updateCommentById(long id, String newComment) {
        Query query = em.createQuery("update BookComment set comment = :newComment where id = :id");
        query.setParameter("id", id);
        query.setParameter("newComment", newComment);
        query.executeUpdate();
    }

    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete from BookComment bc where bc.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
