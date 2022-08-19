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
public class Person {

    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно иметь от 2 до 100 символов")
    private String name;

    @NotNull(message = "Год рождения не должен быть пустой")
    @Max(value = 2022, message = "Год рождения должен быть в промежутке от 1900 до 2022")
    @Min(value = 1900, message = "Год рождения должен быть в промежутке от 1900 до 2022")
    private Integer yearOfBirth;

}
