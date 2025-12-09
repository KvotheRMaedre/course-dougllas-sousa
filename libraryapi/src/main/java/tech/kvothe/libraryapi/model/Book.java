package tech.kvothe.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false, length = 30)
    private BookGenre genre;

    @Column(name = "price", precision = 18, scale = 2)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    public Book() {
    }


}
