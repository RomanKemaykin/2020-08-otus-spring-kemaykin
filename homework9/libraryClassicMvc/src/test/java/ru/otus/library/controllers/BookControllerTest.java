package ru.otus.library.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookCrudService;
import ru.otus.library.service.BookCrudServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Configuration
    static class TestConfiguration {
        @Bean
        public BookCrudService bookCrudService() {
            return new BookCrudServiceImpl(null, null, null, null);
        }

        @Bean
        public BookController bookController() {
            return new BookController(bookCrudService());
        }
    }

    @Autowired
    MockMvc mvc;
    @Autowired
    private BookController bookController;

    @MockBean
    private BookCrudService bookCrudService;

    @Test
    void bookListPage() throws Exception {
        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(new BookDto("", "", "", ""));
        given(bookCrudService.findAll()).willReturn(bookDtoList);
        given(bookCrudService.findBookById(any())).willReturn(new BookDto("", "", "", ""));
        this.mvc.perform(get("/book"))
                .andExpect(status().isOk());

    }
}