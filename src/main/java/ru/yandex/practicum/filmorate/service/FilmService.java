package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film create(Film film) {
        return filmStorage.add(film);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film delete(int id) {
        return filmStorage.delete(id);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void setLike(Integer filmId, Integer userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        film.setLike(userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        film.removeLike(userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findAll().stream().sorted(Comparator.comparingInt(o -> -o.getLikes().size())).limit(count).collect(Collectors.toList());
    }

    public Film getFilm(Integer id) {
        return filmStorage.get(id);
    }
}
