package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCrudServiceConsole implements BookCrudService {

    private final YamlProps props;
    private final MessageSource messageSource;
    private final IOService ioService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void listAll() {
        List<Book> books = bookRepository.findAll();
        for (int i = 0; i < books.size(); i++) {
            printBookData(books.get(i));
        }
    }

    @Override
    public void showBookById(long id) {
        Book book = bookRepository.getById(id);
        printBookData(book);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        Book deletingBook = bookRepository.getById(id);
        bookRepository.delete(deletingBook);
    }

    @Override
    @Transactional
    public void add() {
        String requestText;
        requestText = messageSource.getMessage("enter.title.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String title = ioService.readString();
        requestText = messageSource.getMessage("enter.author.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String authorName = ioService.readString();
        requestText = messageSource.getMessage("enter.genre.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String genreName = ioService.readString();

        Author author;
        Author existingAuthor;
        existingAuthor = authorRepository.getByName(authorName);
        if (existingAuthor != null) {
            author = existingAuthor;
        } else {
            Author authorNew = new Author(0, authorName);
            authorRepository.save(authorNew);
            author = authorNew;
        }

        Genre genre;
        Genre existingGenre;
        existingGenre = genreRepository.getByName(genreName);
        if (existingGenre != null) {
            genre = existingGenre;
        } else {
            Genre genreNew = new Genre(0, genreName);
            genreRepository.save(genreNew);
            genre = genreNew;
        }

        Book book = new Book(0, title, author, genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void modifyBookById(long id) {
        Book book = bookRepository.getById(id);
        printBookData(book);
        String requestText;
        requestText = messageSource.getMessage("enter.new.title.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String title = ioService.readString();
        if (title.isEmpty()) {
            title = book.getTitle();
        }
        requestText = messageSource.getMessage("enter.new.author.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String authorName = ioService.readString();
        if (authorName.isEmpty()) {
            authorName = book.getAuthor().getName();
        }
        requestText = messageSource.getMessage("enter.new.genre.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String genreName = ioService.readString();
        if (genreName.isEmpty()) {
            genreName = book.getGenre().getName();
        }

        Author author;
        Author existingAuthor;
        existingAuthor = authorRepository.getByName(authorName);
        if (existingAuthor != null) {
            author = existingAuthor;
        } else {
            Author authorNew = new Author(0, authorName);
            authorRepository.save(authorNew);
            author = authorNew;
        }

        Genre genre;
        Genre existingGenre;
        existingGenre = genreRepository.getByName(genreName);
        if (existingGenre != null) {
            genre = existingGenre;
        } else {
            Genre genreNew = new Genre(0, genreName);
            genreRepository.save(genreNew);
            genre = genreNew;
        }

        Book bookForUpdate = new Book(id, title, author, genre);
        bookRepository.save(bookForUpdate);
    }

    private void printBookData(Book book) {
        String outLine;
        outLine = messageSource.getMessage("book.with.id", new String[]{String.valueOf(book.getId())}, props.getLocale());
        ioService.out(outLine);
        outLine = messageSource.getMessage("book.title", new String[]{book.getTitle()}, props.getLocale());
        ioService.out(outLine);
        outLine = messageSource.getMessage("book.author", new String[]{book.getAuthor().getName()}, props.getLocale());
        ioService.out(outLine);
        outLine = messageSource.getMessage("book.genre", new String[]{book.getGenre().getName()}, props.getLocale());
        ioService.out(outLine);
    }
}
