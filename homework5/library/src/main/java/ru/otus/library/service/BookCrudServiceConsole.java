package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCrudServiceConsole implements BookCrudService {

    private final YamlProps props;
    private final MessageSource messageSource;
    private final IOService ioService;
    private final BookDao bookDao;

    @Override
    public void listAll() {
        List<Book> books = bookDao.getAll();
        for (int i = 0; i < books.size(); i++) {
            printBookData(books.get(i));
        }
    }

    @Override
    public void showBookById(long id) {
        Book book = bookDao.getById(id);
        printBookData(book);
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.delete(id);
    }

    @Override
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
        Author author = new Author(0, authorName);
        Genre genre = new Genre(0, genreName);
        Book book = new Book(0, title, author, genre);
        bookDao.insert(book);
    }

    @Override
    public void modifyBookById(long id) {
        Book book = bookDao.getById(id);
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

        Author author = new Author(0, authorName);
        Genre genre = new Genre(0, genreName);
        Book bookForUpdate = new Book(id, title, author, genre);
        bookDao.update(bookForUpdate);
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
