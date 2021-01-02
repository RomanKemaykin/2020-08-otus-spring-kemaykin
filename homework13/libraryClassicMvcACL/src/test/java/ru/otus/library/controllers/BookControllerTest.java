package ru.otus.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;
import ru.otus.library.service.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BookController")
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Configuration
    static class TestConfiguration {
        @Bean
        public BookService bookCrudService() {
            return new BookServiceImpl(null, null, null, null, null);
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
    private BookService bookService;

    @DisplayName("should return unauthorised without user")
    @Test
    void shouldReturnUnauthorisedWithoutUser() throws Exception {
        this.mvc.perform(get("/book"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("should Return Ok With User")
    @Test
    @WithMockUser(username = "user")
    void shouldReturnOkWithUser() throws Exception {
        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(new BookDto("", "", "", ""));
        given(bookService.findAll()).willReturn(bookDtoList);
        given(bookService.findBookById(any())).willReturn(new BookDto("", "", "", ""));
        this.mvc.perform(get("/book"))
                .andExpect(status().isOk());
    }
}