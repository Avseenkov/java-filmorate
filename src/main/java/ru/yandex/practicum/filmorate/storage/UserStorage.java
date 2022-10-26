package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> allUsers();

    User add(User user);

    User delete(User user);

    User update(User user);

    User getUser(Integer id);

}
