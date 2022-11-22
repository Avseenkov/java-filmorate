package ru.yandex.practicum.filmorate.storage.utils;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MakeObjectFromResultSet {

    public static User makeUser(ResultSet rs, int rowNum) throws SQLException {

        int id = rs.getInt("user_id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        return User.builder().id(id).name(name).login(login).email(email).birthday(birthday).build();
    }

    public static Film makeFilm(ResultSet rs, int numRow) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .mpa(MPA.builder().id(rs.getInt("mpa_id")).name(rs.getString("nameMPA")).description(rs.getString("descriptionMPA")).build())
                .build();
    }

    public static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder().id(rs.getInt("genre_id")).name(rs.getString("name")).build();
    }
}
