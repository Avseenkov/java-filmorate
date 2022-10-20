package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.utils.ValidatorTestUtils.hasErrorMessage;

class UserTestValidation {

    @Autowired
    public User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "1@1.ru", "login", "name", LocalDate.of(1990, 1, 1));
    }

    @Test
    public void notAllowedUserWithEmptyEmail() {
        User user1 = user.toBuilder().email("").build();
        Assertions.assertTrue(hasErrorMessage(user1, "email не может быть пустым"));
    }

    @Test
    public void notAllowedUserWithWrongEmail() {
        User user1 = user.toBuilder().email("@a.rr").build();
        Assertions.assertTrue(hasErrorMessage(user1, "email должен иметь формат адреса электронной почты"));
    }

    @Test
    public void notAllowedEmptyLogin() {
        User user1 = user.toBuilder().login("").build();
        Assertions.assertTrue(hasErrorMessage(user1, "логин не можеть быть пустым"));
    }

    @Test
    public void notAllowedSpaceInLogin() {
        User user1 = user.toBuilder().login("dd dd").build();
        Assertions.assertTrue(hasErrorMessage(user1, "логин не может содержать пробелы"));
    }

    @Test
    public void dateOfBirthdayCouldNotBeInTheFuture() {
        User user1 = user.toBuilder().birthday(LocalDate.now().plusDays(1)).build();
        Assertions.assertTrue(hasErrorMessage(user1, "дата рождения не может быть будущей датой"));
    }
}