package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;


    @Override
    public Collection<User> allUsers() {
        return users.values();
    }

    @Override
    public User add(User user) {
        User userForSave = user.toBuilder().id(++id).build();
        if (userForSave.getName() == null || userForSave.getName().isBlank()) {
            userForSave = userForSave.toBuilder().name(userForSave.getLogin()).build();
        }
        users.put(userForSave.getId(), userForSave);
        return userForSave;
    }

    @Override
    public User delete(User user) {

        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException(String.format("Пользователь с id %s не найдет", user.getId()));
        }

        users.remove(user.getId());
        return user;
    }

    @Override
    public User update(User user) {

        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException(String.format("Пользователь с id %s не найдет", user.getId()));
        }

        User userForSave = user;
        if (user.getName().isBlank()) {
            userForSave = user.toBuilder().name(user.getLogin()).build();
        }
        users.put(userForSave.getId(), userForSave);
        return user;
    }

    @Override
    public User getUser(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException(String.format("Пользователь с id %s не найдет", id));
        }

        return users.get(id);
    }

}
