package ru.vitstark.library.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно иметь от 2 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Год рождения не должен быть пустой")
    @Max(value = 2022, message = "Год рождения должен быть в промежутке от 1900 до 2022")
    @Min(value = 1900, message = "Год рождения должен быть в промежутке от 1900 до 2022")
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String name, Integer yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }
}
