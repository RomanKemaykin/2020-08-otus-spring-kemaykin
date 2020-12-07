package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.library.models.Book;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private String id;
    private String bookTitle;
    private String authorName;
    private String genreName;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());
    }
}
