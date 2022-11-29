package ru.yandex.practicum.filmorate.storage.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.utils.MakeObjectFromResultSet;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Collection;

@Component
@AllArgsConstructor
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<User> getAll() {
        final String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, MakeObjectFromResultSet::makeUser);
    }

    @Override
    public User add(User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            user = user.toBuilder().name(user.getLogin()).build();
        }
        final User userForSave = user.toBuilder().build();

        final String ADD_SQL = "INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) values ( ?, ?, ?, ? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(ADD_SQL, new String[]{"user_id"});
                    ps.setString(1, userForSave.getEmail());
                    ps.setString(2, userForSave.getLogin());
                    ps.setString(3, userForSave.getName());
                    ps.setDate(4, Date.valueOf(userForSave.getBirthday().orElse(LocalDate.MIN)));
                    return ps;
                }, keyHolder);

        user = userForSave.toBuilder().id((keyHolder.getKey().intValue())).build();
        return user;
    }

    @Override
    public void delete(int id) {
        final String sql = "DELETE FROM USERS WHERE user_id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User update(User user) {
        User userForSave = user;
        if (user.getName().isBlank()) {
            userForSave = user.toBuilder().name(user.getLogin()).build();
        }
        get(user.getId());
        final String UPDATE_SQL = "UPDATE USERS SET name=?, login=?, email=?, birthday=? WHERE user_id = ?";
        jdbcTemplate.update(UPDATE_SQL,
                userForSave.getName(),
                userForSave.getLogin(),
                userForSave.getEmail(),
                userForSave.getBirthday().orElse(LocalDate.MIN),
                userForSave.getId());
        return user;
    }

    @Override
    public User get(Integer id) {
        final String SELECT_SQL = "SELECT * FROM USERS WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(SELECT_SQL, MakeObjectFromResultSet::makeUser, id);
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }


}
