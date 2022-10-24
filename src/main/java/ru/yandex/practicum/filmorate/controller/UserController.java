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
        User userForSave;
        userForSave = user.toBuilder().id(++id).build();
        if (userForSave.getName() == null || userForSave.getName().isBlank()) {
            userForSave = userForSave.toBuilder().name(userForSave.getLogin()).build();
        }
        users.put(userForSave.getId(), userForSave);
        log.info("Добавление пользователя {}", userForSave);
        return userForSave;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.info("Пользователь не найдет {}", user);
            throw new RuntimeException(String.format("Пользователь с id %s не найдет", user.getId()));
        }
        User userForSave = user;
        if (user.getName().isBlank()) {
            userForSave = user.toBuilder().name(user.getLogin()).build();
        }
        users.put(userForSave.getId(), userForSave);
        log.info("Обновление пользователя {}", userForSave);
        return user;
    }
}
