package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class MPA {

    @NotNull
    private int id;

    @NotBlank(message = "Название рейтинга не может быть пустым")
    private String name;

    @NotBlank(message = "Описание рейтинга не может быть пустым")
    private String description;

}
