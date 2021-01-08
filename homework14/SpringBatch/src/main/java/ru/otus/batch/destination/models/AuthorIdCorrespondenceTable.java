package ru.otus.batch.destination.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class AuthorIdCorrespondenceTable {
    @Id
    private String destinationId;
    private long sourceId;
}
