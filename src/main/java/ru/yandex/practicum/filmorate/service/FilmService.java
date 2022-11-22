package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service

public class FilmService {
    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    private final LikeStorage likeStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    public Film create(Film film) {
        return filmStorage.add(film);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public boolean delete(int id) {
        return filmStorage.delete(id);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void setLike(Integer filmId, Integer userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        likeStorage.setLike(filmId, userId);
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
