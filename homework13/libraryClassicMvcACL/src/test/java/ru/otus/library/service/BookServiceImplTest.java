package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.jdbc.JdbcAclService;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.dto.BookDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис BookCrudServiceImpl должен:")
@DataJpaTest
@Import(BookServiceImpl.class)
class BookServiceImplTest {
    @MockBean
    private MutableAclService mutableAclService;

    @Autowired
    private BookServiceImpl bookCrudService;


    @DisplayName("возвращать dto по всем книгам")
    @Test
    void findAll() {
        List<BookDto> bookDtoList = bookCrudService.findAll();
        assertThat(bookDtoList).hasSize(4);
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
}