package ru.yandex.practicum.filmorate.storage.like;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void setLike(int filmId, int userId) {
        final String SET_LIKE_SQL = "INSERT INTO LIKES (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(SET_LIKE_SQL, filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        final String DELETE_LIKE_SQL = "DELETE FROM LIKES WHERE film_id=? AND user_id=?";
        jdbcTemplate.update(DELETE_LIKE_SQL, filmId, userId);
    }
}
