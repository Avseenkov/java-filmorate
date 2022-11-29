package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreStorageTest {

    private final GenreStorage genreDbStorage;


    @Test
    void getAll() {
        Collection<Genre> genres = genreDbStorage.getAll();
        assertEquals(genres.size(), 6);
    }

    @Test
    void get() {
        Genre genre = genreDbStorage.get(1);
        assertEquals(genre.getId(), 1);
        assertEquals(genre.getName(), "Комедия");

        genre = genreDbStorage.get(2);
        assertEquals(genre.getId(), 2);
        assertEquals(genre.getName(), "Драма");
    }
}