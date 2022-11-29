package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("genres")
@AllArgsConstructor
public class GenreController {

    GenreService genreService;

    @GetMapping("{id}")
    public Genre get(@PathVariable Integer id) {
        return genreService.get(id);
    }

    @GetMapping
    public Collection<Genre> getAll() {
        return genreService.getAll();
    }
}
