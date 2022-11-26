package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {

    private final UserDbStorage userDbStorage;

    private final JdbcTemplate jdbcTemplate;

    private User user;

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
    }


    @Test
    void getAll() {
        userDbStorage.add(user);
        User newUser = User.builder()
                .name("Обновленный")
                .login("ОбновленныйЛогин")
                .email("ОбновленныйПочта")
                .birthday(LocalDate.of(1991, 1, 1))
                .id(2)
                .build();
        userDbStorage.add(newUser);

        Collection<User> users = userDbStorage.getAll();
        assertEquals(users.size(), 2, "Не корректное количество пользователей в базе");

    }

    @Test
    void add() {
        User userDb = userDbStorage.add(user);
        assertEquals(userDb.getId(), 1, "Не удалось создать пользователя");
    }

    @Test
    void delete() {
        User userDb = userDbStorage.add(user);
        userDbStorage.delete(userDb.getId());
        assertThrows(UserNotFoundException.class, () -> userDbStorage.get(userDb.getId()));
    }

    @Test
    void update() {

        userDbStorage.add(user);

        User newUser = User.builder()
                .name("Обновленный")
                .login("ОбновленныйЛогин")
                .email("ОбновленныйПочта")
                .birthday(LocalDate.of(1991, 1, 1))
                .id(1)
                .build();

        User userDb = userDbStorage.update(newUser);
        assertEquals(userDb.getName(), newUser.getName(), "Имя не обновилось");
        assertEquals(userDb.getLogin(), newUser.getLogin(), "Логин не обновился");
        assertEquals(userDb.getEmail(), newUser.getEmail(), "Email не обновилося");
        assertEquals(userDb.getBirthday(), newUser.getBirthday(), "День рождения не обновился");

        userDb = userDbStorage.get(newUser.getId());

        assertEquals(userDb.getName(), newUser.getName(), "Имя не корректное");
        assertEquals(userDb.getLogin(), newUser.getLogin(), "Логин не правильный");
        assertEquals(userDb.getEmail(), newUser.getEmail(), "Email не корректный");
        assertEquals(userDb.getBirthday(), newUser.getBirthday(), "День рождения не корректная");
    }

    @Test
    void get() {
        User userAdd = userDbStorage.add(user);
        User userDb = userDbStorage.get(userAdd.getId());
        assertEquals(userDb.getName(), user.getName(), "Имя не корректное");
        assertEquals(userDb.getLogin(), user.getLogin(), "Логин не правильный");
        assertEquals(userDb.getEmail(), user.getEmail(), "Email не корректный");
        assertEquals(userDb.getBirthday(), user.getBirthday(), "День рождения не корректная");
    }
}
