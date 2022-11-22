package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

@Service
@AllArgsConstructor
public class GenreService {

    GenreStorage genreStorage;

    public Genre get(int id) {
        return genreStorage.get(id);
    }

    public Collection<Genre> getAll() {
        return genreStorage.getAll();
    }
}
