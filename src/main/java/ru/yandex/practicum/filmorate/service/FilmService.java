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

    public Film delete(Film film) {
        return filmStorage.delete(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void setLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.setLike(user.getId());
    }

    public void removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.removeLike(user.getId());
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findAll().stream().sorted(Comparator.comparingInt(o -> -o.getLikes().size())).limit(count).collect(Collectors.toList());
    }

    public Film getFilm(Integer id) {
        return filmStorage.getFilm(id);
    }
}
