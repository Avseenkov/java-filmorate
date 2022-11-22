package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    private final JdbcTemplate jdbcTemplate;

    private Film film;

    @BeforeEach
    public void preparedData() {
        jdbcTemplate.execute("DELETE FROM USERS");
        jdbcTemplate.execute("DELETE FROM FILMS");
        jdbcTemplate.execute("DELETE FROM FILM_GENRE");
        jdbcTemplate.execute("DELETE FROM FRIENDS");
        jdbcTemplate.execute("DELETE FROM LIKES");
        jdbcTemplate.update("ALTER TABLE USERS ALTER COLUMN user_id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE FILMS ALTER COLUMN film_id RESTART WITH 1");
        film = Film.builder()
                .name("Тест")
                .description("Описание")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(120)
                .mpa(MPA.builder().id(1).name("G").build())
                .build();

        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().id(1).build());
        genres.add(Genre.builder().id(2).build());
    }

    @Test
    void add() {
        Film filmDb = filmDbStorage.add(film);
        assertEquals(filmDb.getId(), 1, "Не удалось создать фильм");
    }

    @Test
    void delete() {
        Film filmDb = filmDbStorage.add(film);
        assertTrue(filmDbStorage.delete(filmDb.getId()), "Не удалось удалить фильм");
        assertThrows(FilmNotFoundException.class, () -> filmDbStorage.get(filmDb.getId()));
    }

    @Test
    void update() {
        filmDbStorage.add(film);
        Film newFilm = Film.builder()
                .name("Тест2")
                .description("Описание2")
                .releaseDate(LocalDate.of(1991, 1, 1))
                .duration(200)
                .id(1)
                .mpa(MPA.builder().id(2).name("PG").build())
                .build();

        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().id(3).build());
        genres.add(Genre.builder().id(4).build());
        genres.add(Genre.builder().id(5).build());

        Film filmAfterUpdate = filmDbStorage.update(newFilm);

        assertEquals(newFilm.getName(), filmAfterUpdate.getName(),"Название фильма не обновилось") ;
        assertEquals(newFilm.getDescription(), filmAfterUpdate.getDescription(),"Описание фильма не обновилось") ;
        assertEquals(newFilm.getDuration(), filmAfterUpdate.getDuration(),"Продолжительность фильма не обновилась") ;
        assertEquals(newFilm.getReleaseDate(), filmAfterUpdate.getReleaseDate(),"Дата релизи фильма не обновилась") ;
        assertEquals(newFilm.getMpa().getId(), filmAfterUpdate.getMpa().getId(),"Рейтинг фильма не обновился") ;
        assertEquals(newFilm.getGenres(), filmAfterUpdate.getGenres(),"Жанры фильма не обновились") ;

        Film filmFromDb = filmDbStorage.get(1);

        assertEquals(newFilm.getName(), filmFromDb.getName(),"Название фильма не совпадает") ;
        assertEquals(newFilm.getDescription(), filmFromDb.getDescription(),"Описание фильма не совпадает") ;
        assertEquals(newFilm.getDuration(), filmFromDb.getDuration(),"Продолжительность фильма не совпадает") ;
        assertEquals(newFilm.getReleaseDate(), filmFromDb.getReleaseDate(),"Дата релизи фильма не совпадает") ;
        assertEquals(newFilm.getMpa().getId(), filmFromDb.getMpa().getId(),"Рейтинг фильма не совпадает") ;
        assertEquals(newFilm.getGenres(), filmFromDb.getGenres(),"Жанры фильма не совпадает") ;
    }

    @Test
    void findAll() {

        filmDbStorage.add(film);
        Film newFilm = Film.builder()
                .name("Тест2")
                .description("Описание2")
                .releaseDate(LocalDate.of(1991, 1, 1))
                .duration(200)
                .id(2)
                .mpa(MPA.builder().id(2).name("PG").build())
                .build();

        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().id(3).build());
        genres.add(Genre.builder().id(4).build());
        genres.add(Genre.builder().id(5).build());

        filmDbStorage.add(newFilm);

        Collection<Film> films = filmDbStorage.findAll();
        assertEquals(films.size(), 2, "Не верное количество фильмов в базе");
    }

    @Test
    void get() {
        filmDbStorage.add(film);
        Film filmFromDb = filmDbStorage.get(1);

        assertEquals(film.getName(), filmFromDb.getName(),"Название фильма не совпадает") ;
        assertEquals(film.getDescription(), filmFromDb.getDescription(),"Описание фильма не совпадает") ;
        assertEquals(film.getDuration(), filmFromDb.getDuration(),"Продолжительность фильма не совпадает") ;
        assertEquals(film.getReleaseDate(), filmFromDb.getReleaseDate(),"Дата релизи фильма не совпадает") ;
        assertEquals(film.getMpa().getId(), filmFromDb.getMpa().getId(),"Рейтинг фильма не совпадает") ;
        assertEquals(film.getGenres(), filmFromDb.getGenres(),"Жанры фильма не совпадает") ;
    }
}