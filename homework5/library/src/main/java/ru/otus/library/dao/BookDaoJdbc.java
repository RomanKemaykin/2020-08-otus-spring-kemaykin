package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject(
                "select b.id, b.title, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name "
                        + " from books b "
                        + "inner join authors a "
                        + "        on a.id = b.author_id "
                        + "inner join genres g "
                        + "        on g.id = b.genre_id "
                        + "where b.id = :id"
                , params, new BookRowMapper());
    }

    @Override
    public void update(Book book) {
        Author author = book.getAuthor();
        long authorId = author.getId();
        if (authorId == 0) {
            Author existingAuthor;
            long existingAuthorId;
            try {
                existingAuthor = authorDao.getByName(author.getName());
                existingAuthorId = existingAuthor.getId();
            } catch (Exception e) {
                existingAuthorId = authorDao.insert(author);
            }
            authorId = existingAuthorId;
        }

        Genre genre = book.getGenre();
        long genreId = genre.getId();
        if (genreId == 0) {
            Genre existingGenre;
            long existingGenreId;
            try {
                existingGenre = genreDao.getByName(genre.getName());
                existingGenreId = existingGenre.getId();
            } catch (Exception e) {
                existingGenreId = genreDao.insert(genre);
            }
            genreId = existingGenreId;
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", authorId);
        params.addValue("genre_id", genreId);
        jdbc.update("update books " +
                "set title = :title, " +
                "author_id = :author_id, " +
                "genre_id = :genre_id " +
                "where id = :id", params);
    }

    @Override
    public void delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete books where id = :id", params);
    }

    @Override
    public List<Book> getAll() {
        Map<Long, Book> books = jdbc.query(
                "select b.id, b.title, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name "
                        + " from books b "
                        + "inner join authors a "
                        + "        on a.id = b.author_id "
                        + "inner join genres g "
                        + "        on g.id = b.genre_id ",
                new BookResultSetExtractor());
        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public long insert(Book book) {
        Author author = book.getAuthor();
        long authorId = author.getId();
        if (authorId == 0) {
            Author existingAuthor;
            long existingAuthorId;
            try {
                existingAuthor = authorDao.getByName(author.getName());
                existingAuthorId = existingAuthor.getId();
            } catch (Exception e) {
                existingAuthorId = authorDao.insert(author);
            }
            authorId = existingAuthorId;
        }

        Genre genre = book.getGenre();
        long genreId = genre.getId();
        if (genreId == 0) {
            Genre existingGenre;
            long existingGenreId;
            try {
                existingGenre = genreDao.getByName(genre.getName());
                existingGenreId = existingGenre.getId();
            } catch (Exception e) {
                existingGenreId = genreDao.insert(genre);
            }
            genreId = existingGenreId;
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", authorId);
        params.addValue("genre_id", genreId);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into books (`title`, `author_id`, `genre_id`) values (:title, :author_id, :genre_id)", params, kh);
        return kh.getKey().longValue();
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = extractBookFromResultSet(resultSet);
            return book;
        }
    }

    private class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {
        @Override
        public Map<Long, Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            while (resultSet.next()) {
                Book book = extractBookFromResultSet(resultSet);
                books.put(book.getId(), book);
            }
            return books;
        }
    }

    private static Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        long authorId = resultSet.getLong("author_id");
        String authorName = resultSet.getString("author_name");
        Author author = new Author(authorId, authorName);
        long genreId = resultSet.getLong("genre_id");
        String genreName = resultSet.getString("genre_name");
        Genre genre = new Genre(genreId, genreName);
        Book book = new Book(id, title, author, genre);
        return book;
    }
}
