package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Genre {

    @NotNull
    private int id;

    @NotBlank(message = "Название жанра не может быть пустым")
    private String name;
}
