package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MPANotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
@AllArgsConstructor
public class MPADbStorage implements MPAStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<MPA> getAll() {
        final String GET_ALL_MPA_SQL = "SELECT * FROM MPA";
        return jdbcTemplate.query(GET_ALL_MPA_SQL, this::makeMPA);
    }

    @Override
    public MPA get(int mpa_id) {
        try {
            final String GET_MPA_SQL = "SELECT * FROM MPA WHERE mpa_id=?";
            return jdbcTemplate.queryForObject(GET_MPA_SQL, this::makeMPA, mpa_id);
        } catch (RuntimeException e) {
            throw new MPANotFoundException(String.format("MPA c id %s не найден", mpa_id));
        }
    }

    private MPA makeMPA(ResultSet rs, int rowNum) throws SQLException {
        return MPA.builder().id(rs.getInt("mpa_id")).name(rs.getString("name")).description(rs.getString("description")).build();
    }
}
