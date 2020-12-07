package ru.otus.library.rest.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BookRestControllerTest {

    @Autowired
    private BookRestController bookRestController;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    private WebTestClient client;

    @Test
    void getBook() {
        WebTestClient client = WebTestClient
                .bindToController(bookRestController)
                .build();

        Author author = new Author("author1");
        Genre genre = new Genre("genre1");
        Book book = new Book("123", "title", author, genre);

        String bookDtoInJson = "{\"id\":\"123\",\"bookTitle\":\"title\",\"authorName\":\"author1\",\"genreName\":\"genre1\"}";

        given(bookRepository.findById("123")).willReturn(Mono.just(book));

        client.get()
                .uri("/api/book/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(bookDtoInJson);
    }

    @Test
    void addBook() {
        WebTestClient client = WebTestClient
                .bindToController(bookRestController)
                .build();

        Author author = new Author("1", "author1");
        Genre genre = new Genre("1", "genre1");
        Book book = new Book("1234", "title", author, genre);

        given(bookRepository.save(any())).willReturn(Mono.just(book));
        given(authorRepository.findByName(any())).willReturn(Mono.just(author));
        given(authorRepository.save(any())).willReturn(Mono.just(author));
        given(genreRepository.findByName(genre.getName())).willReturn(Mono.just(genre));
        given(genreRepository.save(any())).willReturn(Mono.just(genre));

        String bookDtoInJson = "{\"id\":\"1234\",\"bookTitle\":\"title\",\"authorName\":\"author1\",\"genreName\":\"genre1\"}";

        client.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookDtoInJson)
                .exchange()
                .expectStatus().isCreated();
    }
}