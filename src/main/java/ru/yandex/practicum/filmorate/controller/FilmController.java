package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        if (!films.containsKey(film.getId())) {
            log.info("Фильм не найдет {}", film);
            throw new RuntimeException(String.format("Фильм с id %s не найдет", film.getId()));
        }
        films.put(film.getId(), film);
        log.info("Обновление фильма {}", film);
        return film;
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        Film filmWithGeneratedId = film.toBuilder().id(++id).build();
        films.put(filmWithGeneratedId.getId(), filmWithGeneratedId);
        log.info("Добавление фильма {}", filmWithGeneratedId);
        return filmWithGeneratedId;
    }


}
