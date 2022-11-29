package ru.yandex.practicum.filmorate.storage.genre;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.utils.MakeObjectFromResultSet;

import java.util.Collection;

@Component
@AllArgsConstructor
public class GenreDbStorage implements GenreStorage {

    JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> getAll() {
        final String GET_ALL_GENRE_SQL = "SELECT * FROM GENRES";
        return jdbcTemplate.query(GET_ALL_GENRE_SQL, MakeObjectFromResultSet::makeGenre);
    }

    @Override
    public Genre get(int genre_id) {
        final String GET_GENRE_SQL = "SELECT * FROM GENRES WHERE genre_id=?";
        try {
            return jdbcTemplate.queryForObject(GET_GENRE_SQL, MakeObjectFromResultSet::makeGenre, genre_id);
        } catch (RuntimeException e) {
            throw new GenreNotFoundException(String.format("Жанр с id %s не найден", genre_id));
        }
    }


}
