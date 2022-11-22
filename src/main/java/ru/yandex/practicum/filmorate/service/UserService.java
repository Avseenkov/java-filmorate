package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
public class UserService {

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final FriendsStorage friendsStorage;
    private final LikeStorage likeStorage;

    public User create(User user) {
        return userStorage.add(user);
    }

    public Collection<User> findAll() {
        return userStorage.getAll();
    }

    public UserService(
            @Qualifier("userDbStorage") UserStorage userStorage,
            @Qualifier("filmDbStorage") FilmStorage filmStorage,
            FriendsStorage friendsStorage,
            LikeStorage likeStorage
    ) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
        this.friendsStorage = friendsStorage;
        this.likeStorage = likeStorage;
    }

    public boolean delete(int id) {
        for (Film film : filmStorage.findAll()) {
            likeStorage.removeLike(film.getId(), id);
        }
        return userStorage.delete(id);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        friendsStorage.addFriend(userId, friendId);

    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        friendsStorage.removeFriend(userId, friendId);

    }

    public Collection<User> getFriends(Integer userId) {
        return friendsStorage.getFriends(userId);
    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        return friendsStorage.getCommonFriends(userId, friendId);
    }

    public User getUser(Integer id) {
        return userStorage.get(id);
    }

}
