package ru.otus.batch.source.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-author-genre-entity-graph",
                    attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class BookSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @OneToOne(targetEntity = AuthorSource.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private AuthorSource author;

    @OneToOne(targetEntity = GenreSource.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "genre_id")
    private GenreSource genre;

    @Override
    public String toString() {
        return "Book: id = " + this.id + ", title = " + this.title +
                ", " + this.author.toString() +
                ", " + this.genre.toString();
    }
}
