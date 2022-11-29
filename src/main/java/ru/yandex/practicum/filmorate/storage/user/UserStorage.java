package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> getAll();

    User add(User user);

    void delete(int id);

    User update(User user);

    User get(Integer id);

}
