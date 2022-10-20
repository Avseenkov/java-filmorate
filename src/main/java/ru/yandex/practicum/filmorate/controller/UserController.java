package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public Collection<User> allUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        user = user.toBuilder().id(++id).build();
        if (user.getName() == null || user.getName().isBlank()) {
            user = user.toBuilder().name(user.getLogin()).build();
        }
        users.put(user.getId(), user);
        log.info("Добавление пользователя {}", user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.info("Пользователь не найдет {}", user);
            throw new RuntimeException(String.format("Пользователь с id %s не найдет", user.getId()));
        }
        if (user.getName().isBlank()) {
            user = user.toBuilder().email(user.getEmail()).build();
        }
        users.put(user.getId(), user);
        log.info("Обновление пользователя {}", user);
        return user;
    }
}
