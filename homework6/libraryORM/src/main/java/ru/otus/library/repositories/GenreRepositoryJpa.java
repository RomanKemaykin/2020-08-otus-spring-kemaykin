package ru.otus.library.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where a.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
    }
}
