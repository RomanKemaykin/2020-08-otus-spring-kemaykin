package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookIOServiceConsole implements BookIOService {

    private final YamlProps props;
    private final MessageSource messageSource;
    private final IOService ioService;
    private final BookCrudService bookCrudService;

    @Override
    public void listAll() {
        List<BookDto> booksDtoList = bookCrudService.findAll();
        for (int i = 0; i < booksDtoList.size(); i++) {
            printBookData(booksDtoList.get(i));
        }
    }

    @Override
    public void showBookById(long id) {
        BookDto bookDto = bookCrudService.findBookById(id);
        printBookData(bookDto);
    }

    @Override
    public void deleteBookById(long id) {
        bookCrudService.deleteBookById(id);
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

        BookDto bookDto = new BookDto(0, title, authorName, genreName);
        bookCrudService.add(bookDto);
    }

    @Override
    public void modifyBookById(long id) {
        BookDto bookDto = bookCrudService.findBookById(id);
        printBookData(bookDto);
        String requestText;
        requestText = messageSource.getMessage("enter.new.title.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String title = ioService.readString();
        if (title.isEmpty()) {
            title = bookDto.getBookTitle();
        }
        requestText = messageSource.getMessage("enter.new.author.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String authorName = ioService.readString();
        if (authorName.isEmpty()) {
            authorName = bookDto.getAuthorName();
        }
        requestText = messageSource.getMessage("enter.new.genre.of.book", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String genreName = ioService.readString();
        if (genreName.isEmpty()) {
            genreName = bookDto.getGenreName();
        }

        BookDto bookDtoForUpdate = new BookDto(id, title, authorName, genreName);
        bookCrudService.modifyBook(bookDtoForUpdate);
    }

    private void printBookData(BookDto bookDto) {
        String outLine;
        outLine = messageSource.getMessage("book.with.id", new String[]{String.valueOf(bookDto.getId())}, props.getLocale());
        ioService.out(outLine);
        outLine = messageSource.getMessage("book.title", new String[]{bookDto.getBookTitle()}, props.getLocale());
        ioService.out(outLine);
        outLine = messageSource.getMessage("book.author", new String[]{bookDto.getAuthorName()}, props.getLocale());
        ioService.out(outLine);
        outLine = messageSource.getMessage("book.genre", new String[]{bookDto.getGenreName()}, props.getLocale());
        ioService.out(outLine);
    }
}
