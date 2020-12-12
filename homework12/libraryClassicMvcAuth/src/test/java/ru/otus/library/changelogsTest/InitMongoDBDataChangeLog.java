package ru.otus.library.changelogsTest;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookComment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookCommentRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    @ChangeSet(order = "000", id = "dropDB", author = "rkemaykin", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    private Author authorOne;
    private Author authorTwo;
    private Author authorThree;
    private Author authorFour;

    private Genre genreOne;
    private Genre genreTwo;
    private Genre genreThree;
    private Genre genreFour;

    private Book bookOne;
    private Book bookTwo;
    private Book bookThree;
    private Book bookFour;

    @ChangeSet(order = "001", id = "initAuthors", author = "rkemaykin", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        authorOne = repository.save(new Author("author one"));
        authorTwo = repository.save(new Author("author two"));
        authorThree = repository.save(new Author("author three"));
        authorFour = repository.save(new Author("author four"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "rkemaykin", runAlways = true)
    public void initGenres(GenreRepository repository){
        genreOne = repository.save(new Genre("genre one"));
        genreTwo = repository.save(new Genre("genre two"));
        genreThree = repository.save(new Genre("genre three"));
        genreFour = repository.save(new Genre("genre four"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "rkemaykin", runAlways = true)
    public void initBooks(BookRepository repository){
        bookOne = repository.save(new Book("book one", authorOne, genreOne));
        bookTwo = repository.save(new Book("book two", authorTwo, genreTwo));
        bookThree = repository.save(new Book("book three", authorThree, genreThree));
        bookFour = repository.save(new Book("book four", authorFour, genreFour));
    }

    @ChangeSet(order = "004", id = "initBookComments", author = "rkemaykin", runAlways = true)
    public void initBookComments(BookCommentRepository repository){
        repository.save(new BookComment(null, "book one comment1", bookOne));
        repository.save(new BookComment(null, "book one comment2", bookOne));
        repository.save(new BookComment(null, "book one comment3", bookOne));
        repository.save(new BookComment(null, "book three comment1", bookThree));
    }
}
