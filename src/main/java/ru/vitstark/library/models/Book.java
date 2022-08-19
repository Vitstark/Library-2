package ru.vitstark.library.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private Long id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название должно иметь от 2 до 50 символов")
    private String name;

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно иметь от 2 до 100 символов")
    private String author;

    @NotNull(message = "Дата написания не должна быть пустой")
    @Max(value = 2022, message = "Дата написания должна быть до 2022")
    private Integer date;

    private Long personId;
}
