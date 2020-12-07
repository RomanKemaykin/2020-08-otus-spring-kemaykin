package ru.otus.library.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookRestController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/api/book")
    public Mono<ResponseEntity<List<BookDto>>> getAllBooks() {
        return bookRepository.findAll()
                .map(book -> BookDto.toDto(book))
                .collectList()
                .map(bookDtoList -> ResponseEntity.ok(bookDtoList))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(book -> BookDto.toDto(book))
                .map(bookDto -> ResponseEntity.ok(bookDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        Mono<Author> authorMono = authorRepository.findByName(bookDto.getAuthorName())
                .switchIfEmpty(authorRepository.save(new Author(bookDto.getAuthorName())));

        Mono<Genre> genreMono = genreRepository.findByName(bookDto.getGenreName())
                .switchIfEmpty(genreRepository.save(new Genre(bookDto.getGenreName())));

        Mono<Book> bookMono = Mono.zip(authorMono, genreMono)
                .flatMap(objects -> Mono.just(new Book(bookDto.getId(), bookDto.getBookTitle(), objects.getT1(), objects.getT2())));

        return Mono.zip(bookMono, Mono.just(1))
                .flatMap(objects -> bookRepository.save(objects.getT1()))
                .map(bookLambda -> ResponseEntity.ok(bookDto));
    }

    @Transactional
    @PostMapping("/api/book")
    public Mono<ResponseEntity<Void>> addBook(UriComponentsBuilder builder, @RequestBody @Validated BookDto bookDto) {
        Mono<Author> authorMono = authorRepository.findByName(bookDto.getAuthorName())
                .switchIfEmpty(authorRepository.save(new Author(bookDto.getAuthorName())));

        Mono<Genre> genreMono = genreRepository.findByName(bookDto.getGenreName())
                .switchIfEmpty(genreRepository.save(new Genre(bookDto.getGenreName())));

        Mono<Book> bookMono = Mono.zip(authorMono, genreMono)
                .flatMap(objects -> Mono.just(new Book(bookDto.getId(), bookDto.getBookTitle(), objects.getT1(), objects.getT2())));

        return Mono.zip(bookMono, Mono.just(1))
                .flatMap(objects -> bookRepository.save(objects.getT1()))
                .map(bookLambda -> ResponseEntity.created(builder.path("/api/book/{id}").buildAndExpand(bookLambda.getId()).toUri()).build());
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable String id) {
        return bookRepository.deleteById(id)
                .map(unused -> ResponseEntity.noContent().build());
    }
}
