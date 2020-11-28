package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.dto.BookDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис BookCrudServiceImpl должен:")
@DataMongoTest
@Import(BookServiceImpl.class)
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookCrudService;

    @DisplayName("возвращать dto по всем книгам")
    @Test
    void findAll() {
        List<BookDto> bookDtoList = bookCrudService.findAll();
        assertThat(bookDtoList).hasSize(4);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void deleteBookById() {
        List<BookDto> bookDtoList = bookCrudService.findAll();
        bookCrudService.deleteBookById(bookDtoList.get(0).getId());
        bookDtoList = bookCrudService.findAll();
        assertThat(bookDtoList).hasSize(3);
    }

    @DisplayName("возвращать dto заданной по id книги")
    @Test
    void findBookById() {
        List<BookDto> bookDtoList = bookCrudService.findAll();
        BookDto actualBookDto = bookCrudService.findBookById(bookDtoList.get(0).getId());
        assertThat(actualBookDto)
                .hasFieldOrPropertyWithValue("bookTitle", "book one")
                .hasFieldOrPropertyWithValue("authorName", "author one")
                .hasFieldOrPropertyWithValue("genreName", "genre one");
    }

    @DisplayName("добавлять книгу по атрибутам, заданным в dto")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void add() {
        BookDto bookDto = new BookDto(null, "book new", "author new", "genre new");
        bookCrudService.add(bookDto);
        List<BookDto> bookDtoList = bookCrudService.findAll();
        assertThat(bookDtoList.get(4))
                .isEqualToComparingOnlyGivenFields(bookDto, "bookTitle", "authorName", "genreName");
    }

    @DisplayName("изменять книгу по атрибутам из dto")
    @Test
    void modifyBook() {
        List<BookDto> bookDtoList = bookCrudService.findAll();
        BookDto firstBookDto = bookDtoList.get(0);
        firstBookDto.setBookTitle("t1");
        firstBookDto.setAuthorName("a1");
        firstBookDto.setGenreName("g1");
        bookCrudService.modifyBook(firstBookDto);
        BookDto actualBookDto = bookCrudService.findBookById(firstBookDto.getId());
        assertThat(actualBookDto).isEqualTo(firstBookDto);
    }
}