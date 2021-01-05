package ru.otus.batch.destination.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class BookDestination {
    @Id
    private String id;

    private String title;

    private AuthorDestination author;

    private GenreDestination genre;
}
