package ru.otus.library.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;
import ru.otus.library.service.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {
    @Configuration
    static class TestConfiguration {
        @Bean
        public BookService bookCrudService() {
            return new BookServiceImpl(null, null, null, null);
        }

        @Bean
        public BookRestController bookController() {
            return new BookRestController(bookCrudService());
        }
    }

    @Autowired
    MockMvc mvc;

    @Autowired
    private BookRestController bookRestController;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() {
    }

    @Test
    void getBook() throws Exception {
        BookDto bookDto = new BookDto("12345", "title", "author1", "genre1");
        given(bookService.findBookById("1")).willReturn(bookDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String bookDtoInJson = "{\"id\":\"12345\",\"bookTitle\":\"title\",\"authorName\":\"author1\",\"genreName\":\"genre1\"}";
        mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(bookDtoInJson))
                .andDo(print());
    }

    @Test
    void deleteBook() throws Exception {
        given(bookService.findBookById(any())).willReturn(new BookDto("", "", "", ""));
        mvc.perform(delete("/api/book/1"))
                .andExpect(status().isNoContent());
    }

}