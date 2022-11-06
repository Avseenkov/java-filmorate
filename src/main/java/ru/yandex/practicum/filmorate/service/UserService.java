package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public User create(User user) {
        return userStorage.add(user);
    }

    public Collection<User> findAll() {
        return userStorage.getAll();
    }

    public User delete(int id) {
        for (Film film : filmStorage.findAll()) {
            film.removeLike(id);
        }
        return userStorage.delete(id);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        friend.addFriend(userId);
        user.addFriend(friendId);

    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        friend.removeFriend(userId);
        user.removeFriend(friendId);
    }

    public Collection<User> getFriends(Integer userId) {
        User user = userStorage.get(userId);
        return user.getFriends().stream().map(userStorage::get).collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);
        Set<Integer> friends = user.getFriends();
        friends.retainAll(friend.getFriends());
        return friends.stream().map(userStorage::get).collect(Collectors.toList());
    }

    public User getUser(Integer id) {
        return userStorage.get(id);
    }

}
