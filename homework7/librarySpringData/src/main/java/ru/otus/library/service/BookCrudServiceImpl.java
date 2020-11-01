package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCrudServiceImpl implements BookCrudService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            BookDto bookDto = new BookDto(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor().getName(),
                    book.getGenre().getName()
            );
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }

    @Override
    public BookDto findBookById(long id) {
        Book book = bookRepository.findById(id);
        BookDto bookDto;
        if (book != null) {
            bookDto = new BookDto(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor().getName(),
                    book.getGenre().getName()
            );
        } else {
            bookDto = new BookDto(
                    0,
                    "",
                    "",
                    ""
            );
        }
        return bookDto;
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        Book deletingBook = bookRepository.findById(id);
        bookRepository.delete(deletingBook);
    }

    @Override
    @Transactional
    public void add(BookDto bookDto) {
        Author author;
        Author existingAuthor;
        existingAuthor = authorRepository.getByName(bookDto.getAuthorName());
        if (existingAuthor != null) {
            author = existingAuthor;
        } else {
            Author authorNew = new Author(0, bookDto.getAuthorName());
            authorRepository.save(authorNew);
            author = authorNew;
        }

        Genre genre;
        Genre existingGenre;
        existingGenre = genreRepository.getByName(bookDto.getGenreName());
        if (existingGenre != null) {
            genre = existingGenre;
        } else {
            Genre genreNew = new Genre(0, bookDto.getGenreName());
            genreRepository.save(genreNew);
            genre = genreNew;
        }

        Book book = new Book(0, bookDto.getBookTitle(), author, genre, null);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void modifyBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId());

        Author author;
        Author existingAuthor;
        existingAuthor = authorRepository.getByName(bookDto.getAuthorName());
        if (existingAuthor != null) {
            author = existingAuthor;
        } else {
            Author authorNew = new Author(0, bookDto.getAuthorName());
            authorRepository.save(authorNew);
            author = authorNew;
        }

        Genre genre;
        Genre existingGenre;
        existingGenre = genreRepository.getByName(bookDto.getGenreName());
        if (existingGenre != null) {
            genre = existingGenre;
        } else {
            Genre genreNew = new Genre(0, bookDto.getGenreName());
            genreRepository.save(genreNew);
            genre = genreNew;
        }

        Book bookForUpdate = new Book(bookDto.getId(), bookDto.getBookTitle(), author, genre, book.getBookComment());
        bookRepository.save(bookForUpdate);
    }
}
