package ru.yandex.practicum.filmorate.storage.friends;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.utils.MakeObjectFromResultSet;

import java.util.Collection;

@Component
@AllArgsConstructor
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        final String ADD_FRIEND_SQL = "INSERT INTO FRIENDS (user_id, friend_id) VALUES(?, ?)";
        jdbcTemplate.update(ADD_FRIEND_SQL, userId, friendId);
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        final String ADD_FRIEND_SQL = "DELETE FROM FRIENDS WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(ADD_FRIEND_SQL, userId, friendId);
    }

    @Override
    public Collection<User> getFriends(Integer userId) {
        final String GET_FRIENDS_SQL = "SELECT f.friend_id as user_id, u.name as name, u.login  as login, u.email as email, u.birthday as birthday FROM FRIENDS f INNER JOIN USERS u ON f.friend_id=u.user_id WHERE f.user_id=?";
        return jdbcTemplate.query(GET_FRIENDS_SQL, MakeObjectFromResultSet::makeUser, userId);
    }

    @Override
    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        final String GET_COMMON_FRIENDS_SQL = "SELECT f.friend_id as user_id, u.name as name, u.login  as login, u.email as email, u.birthday as birthday FROM FRIENDS f INNER JOIN USERS u ON f.friend_id=u.user_id WHERE f.user_id=? AND friend_id IN (SELECT friend_id FROM FRIENDS WHERE user_id =?)";
        return jdbcTemplate.query(GET_COMMON_FRIENDS_SQL, MakeObjectFromResultSet::makeUser, userId, friendId);
    }


}
