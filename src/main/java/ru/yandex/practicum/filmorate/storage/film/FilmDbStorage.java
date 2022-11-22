package ru.yandex.practicum.filmorate.storage.film;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.utils.MakeObjectFromResultSet;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        final String ADD_SQL = "INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(ADD_SQL, new String[]{"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);

        updateGenres(film, keyHolder.getKey().intValue());
        Film filmDb = get(keyHolder.getKey().intValue());
        return filmDb;
    }

    @Override
    public boolean delete(int id) {
        get(id);
        final String DELETE_SQL = "DELETE FROM FILMS WHERE FILM_ID=?";
        return jdbcTemplate.update(DELETE_SQL, id) != 0;
    }

    @Override
    public Film update(Film film) {
        get(film.getId());
        final String UPDATE_SQL = "UPDATE FILMS SET NAME=? ,DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, MPA_ID=? WHERE FILM_ID=?";
        final String DELETE_GENRE = "DELETE FROM FILM_GENRE WHERE film_id=?";

        jdbcTemplate.update(UPDATE_SQL, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        jdbcTemplate.update(DELETE_GENRE, film.getId());
        updateGenres(film, film.getId());
        return get(film.getId());
    }

    @Override
    public Collection<Film> findAll() {
        final String FIND_ALL_SQL = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.mpa_id , m.name as nameMPA, m.description AS descriptionMPA  FROM FILMS f LEFT JOIN MPA m ON f.mpa_id= m.MPA_ID";
        final String LIKES_FOR_FILMS = "SELECT f.film_id as film_id, l.user_id as user_id FROM FILMS f INNER JOIN LIKES l ON f.film_id = l.film_id ";
        final String GENRE_FOR_FILMS = "SELECT f.film_id ,fg.genre_id as genre_id, g.name " +
                "FROM FILMS f " +
                "INNER JOIN FILM_GENRE fg ON fg.film_id = f.film_id " +
                "LEFT JOIN GENRES g ON fg.genre_id = g.genre_id ";

        Collection<Film> films = jdbcTemplate.query(FIND_ALL_SQL, MakeObjectFromResultSet::makeFilm);
        jdbcTemplate.query(
                LIKES_FOR_FILMS,
                (rs, rowNum) -> addLikeToFilm((films.stream().collect(Collectors.toMap(Film::getId, Function.identity()))), rs)
        );

        jdbcTemplate.query(GENRE_FOR_FILMS,
                (rs, rowNum) -> addGenreToFilm((films.stream().collect(Collectors.toMap(Film::getId, Function.identity()))), rs, rowNum)
        );
        return films;
    }

    @Override
    public Film get(Integer id) {
        final String GET_FILM_SQL = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, m.mpa_id, m.mpa_id, m.name AS nameMPA, m.description AS descriptionMPA" +
                " FROM FILMS f LEFT JOIN MPA m ON f.mpa_id= m.MPA_ID WHERE FILM_ID=?";
        final String LIKES_FOR_FILM = "SELECT f.film_id , l.user_id as user_id " +
                "FROM FILMS f " +
                "INNER JOIN LIKES l ON f.film_id = l.film_id " +
                "WHERE f.film_id=?";
        final String GET_GENRES_SQL = "SELECT f.film_id ,fg.genre_id as genre_id, g.name " +
                "FROM FILMS f " +
                "INNER JOIN FILM_GENRE fg ON fg.film_id = f.film_id " +
                "LEFT JOIN GENRES g ON fg.genre_id = g.genre_id " +
                "WHERE f.film_id=? ORDER BY g.genre_id";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(GET_FILM_SQL, MakeObjectFromResultSet::makeFilm, id);
        } catch (RuntimeException e) {
            throw new FilmNotFoundException(String.format("Фильм с id %s не найден", id));
        }
        jdbcTemplate.query(LIKES_FOR_FILM, (rs, rowNum) -> {
            film.setLike(rs.getInt("user_id"));
            return film;
        }, id);

        jdbcTemplate.query(GET_GENRES_SQL, (rs, rowNum) -> {
            film.setGenre(MakeObjectFromResultSet.makeGenre(rs, rowNum));
            return film;
        }, id);

        return film;
    }


    private boolean addLikeToFilm(Map<Integer, Film> films, ResultSet rs) throws SQLException {
        Film film = films.get(rs.getInt("film_id"));
        film.setLike(rs.getInt("user_id"));
        return true;
    }

    private boolean addGenreToFilm(Map<Integer, Film> films, ResultSet rs, int rowNum) throws SQLException {
        Film film = films.get(rs.getInt("film_id"));
        film.setGenre(MakeObjectFromResultSet.makeGenre(rs, rowNum));
        return true;
    }

    private void updateGenres(Film film, int id) {
        final String UPDATE_GENRE = "INSERT INTO FILM_GENRE (film_id, genre_id) VALUES(? , ?)";

        List<Object[]> params = new ArrayList<>();
        if (film.getGenres().size() != 0) {

            for (Genre genre : film.getGenres()) {
                params.add(new Object[]{id, genre.getId()});
            }

            jdbcTemplate.batchUpdate(UPDATE_GENRE, params);
        }
    }
}
