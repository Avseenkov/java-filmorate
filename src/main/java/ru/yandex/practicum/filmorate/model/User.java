package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {
    @NotNull
    final int id;
    @NotBlank(message = "email не может быть пустым")
    @Email(message = "email должен иметь формат адреса электронной почты")
    final String email;

    @NotBlank(message = "логин не можеть быть пустым")
    @Pattern(regexp = "[^\\u0020]+",message = "логин не может содержать пробелы")
    final String login;

    final String name;

    @Past(message = "дата рождения не может быть будущей датой")
    final LocalDate birthday;
}
