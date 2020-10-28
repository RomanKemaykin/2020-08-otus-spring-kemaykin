package ru.otus.library.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.library.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Author getById(long id) {
        Author author = em.find(Author.class, id);
        return author;
    }

}
