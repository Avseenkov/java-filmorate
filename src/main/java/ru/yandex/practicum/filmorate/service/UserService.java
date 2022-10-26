package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.add(user);
    }

    public Collection<User> findAll() {
        return userStorage.allUsers();
    }

    public User delete(User user) {
        return userStorage.delete(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        friend.addFriend(userId);
        user.addFriend(friendId);

    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        friend.removeFriend(userId);
        user.removeFriend(friendId);
    }

    public Collection<User> getFriends(Integer userId) {
        User user = userStorage.getUser(userId);
        return user.getFriends().stream().map(userStorage::getUser).collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        Set<Integer> friends = user.getFriends();
        friends.retainAll(friend.getFriends());
        return friends.stream().map(userStorage::getUser).collect(Collectors.toList());
    }

    public User getUser(Integer id) {
        return userStorage.getUser(id);
    }

}
