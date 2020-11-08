package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookCommentRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookCrudServiceImpl implements BookCrudService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookCommentRepository bookCommentRepository;

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
    public BookDto findBookById(String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        BookDto bookDto;
        if (!optionalBook.isEmpty()) {
            Book book = optionalBook.get();
            bookDto = new BookDto(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor().getName(),
                    book.getGenre().getName()
            );
        } else {
            bookDto = new BookDto(
                    "",
                    "",
                    "",
                    ""
            );
        }
        return bookDto;
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isEmpty()) {
            bookCommentRepository.deleteAllByBook(optionalBook.get());
            bookRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void add(BookDto bookDto) {
        Author author;
        Author existingAuthor;
        existingAuthor = authorRepository.findByName(bookDto.getAuthorName());
        if (existingAuthor != null) {
            author = existingAuthor;
        } else {
            Author authorNew = new Author(bookDto.getAuthorName());
            author = authorRepository.save(authorNew);
        }
        System.out.println(author);

        Genre genre;
        Genre existingGenre;
        existingGenre = genreRepository.findByName(bookDto.getGenreName());
        if (existingGenre != null) {
            genre = existingGenre;
        } else {
            Genre genreNew = new Genre(bookDto.getGenreName());
            genre = genreRepository.save(genreNew);
        }
        System.out.println(genre);

        Book book = new Book(bookDto.getBookTitle(), author, genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void modifyBook(BookDto bookDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getId());
        if (!optionalBook.isEmpty()) {
            Book book = optionalBook.get();

            Author author;
            Author existingAuthor;
            existingAuthor = authorRepository.findByName(bookDto.getAuthorName());
            if (existingAuthor != null) {
                author = existingAuthor;
            } else {
                Author authorNew = new Author(bookDto.getAuthorName());
                author = authorRepository.save(authorNew);
            }

            Genre genre;
            Genre existingGenre;
            existingGenre = genreRepository.findByName(bookDto.getGenreName());
            if (existingGenre != null) {
                genre = existingGenre;
            } else {
                Genre genreNew = new Genre(bookDto.getGenreName());
                genre = genreRepository.save(genreNew);
            }

            Book bookForUpdate = new Book(bookDto.getId(), bookDto.getBookTitle(), author, genre);
            bookRepository.save(bookForUpdate);
        }
    }
}
