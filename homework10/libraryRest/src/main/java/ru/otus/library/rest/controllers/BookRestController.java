package ru.otus.library.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/api/book")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtoList = bookService.findAll();
        return ResponseEntity.ok(bookDtoList);
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable String id) {
        BookDto bookDto = bookService.findBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @PutMapping("/api/book/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        bookService.modifyBook(bookDto);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/api/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        BookDto bookDto = bookService.findBookById(id);
        if (!bookDto.getId().isEmpty()) {
            bookService.deleteBookById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/book")
    public ResponseEntity<Void> addBook(UriComponentsBuilder builder, @RequestBody @Validated BookDto bookDto) {
        bookService.add(bookDto);
        UriComponents uriComponents =
                builder.path("/api/book/{id}").buildAndExpand(bookDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
}
