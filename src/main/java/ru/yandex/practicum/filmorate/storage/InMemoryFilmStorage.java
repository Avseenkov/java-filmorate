package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;


    @Override
    public Film add(Film film) {
        Film filmWithGeneratedId = film.toBuilder().id(++id).build();
        films.put(filmWithGeneratedId.getId(), filmWithGeneratedId);
        return filmWithGeneratedId;
    }

    @Override
    public Film delete(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильм с id %s не найдет", film.getId()));
        }
        films.remove(film.getId());
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильм с id %s не найдет", film.getId()));
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    public Film getFilm(Integer id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id %s не найдет", id));
        }

        return films.get(id);
    }
}
