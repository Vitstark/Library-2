package ru.vitstark.library.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название должно иметь от 2 до 50 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно иметь от 2 до 100 символов")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Дата написания не должна быть пустой")
    @Max(value = 2022, message = "Дата написания должна быть до 2022")
    @Column(name = "date")
    private Integer date;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person reader;

    @Column(name = "date_of_borrow")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBorrow;

    @Transient
    private boolean isOverdue;

    public Book(String name, String author, Integer date) {
        this.name = name;
        this.author = author;
        this.date = date;
    }
}
