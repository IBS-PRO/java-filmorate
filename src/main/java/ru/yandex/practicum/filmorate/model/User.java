package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "Поле login не может содержать пробелы")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

}