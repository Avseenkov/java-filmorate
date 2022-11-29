package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LikeDbStorageTest {

    private final UserStorage userDbStorage;

    private final FilmStorage filmDbStorage;

    private final LikeStorage likeDbStorage;

    private final JdbcTemplate jdbcTemplate;

    private User user;
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
        user = User.builder()
                .name("Тест")
                .login("Логин")
                .email("Почта")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        film = Film.builder()
                .name("Тест")
                .description("Описание")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(120)
                .mpa(MPA.builder().id(1).name("G").build())
                .build();
    }

    @Test
    void setLike() {
        User userDb = userDbStorage.add(user);
        Film filmDb = filmDbStorage.add(film);

        likeDbStorage.setLike(filmDb.getId(), userDb.getId());

        Film filmBd = filmDbStorage.get(filmDb.getId());
        assertEquals(filmBd.getLikes().size(), 1);
        assertEquals(filmBd.getLikes().toArray()[0], userDb.getId());
    }

    @Test
    void removeLike() {

        User userDb = userDbStorage.add(user);
        Film filmDb = filmDbStorage.add(film);

        likeDbStorage.setLike(filmDb.getId(), userDb.getId());

        filmDb = filmDbStorage.get(filmDb.getId());
        assertEquals(filmDb.getLikes().size(), 1);
        assertEquals(filmDb.getLikes().toArray()[0], userDb.getId());

        likeDbStorage.removeLike(filmDb.getId(), userDb.getId());
        filmDb = filmDbStorage.get(filmDb.getId());
        assertEquals(filmDb.getLikes().size(), 0);

    }
}