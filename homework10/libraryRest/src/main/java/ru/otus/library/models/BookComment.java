package ru.otus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class BookComment {
    @Id
    private String id;

    private String comment;

    @DBRef
    private Book book;

    public BookComment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }
}
