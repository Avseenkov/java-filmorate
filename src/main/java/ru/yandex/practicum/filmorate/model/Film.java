package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmReleaseConstrain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class Film {
    @NotNull
    final int id;

    @NotBlank(message = "название фильма не может быть пустым")
    final String name;

    @Size(max = 200, message = "максимальная длина описания не может быть больше 200 символов")
    final String description;

    @FilmReleaseConstrain
    final LocalDate releaseDate;

    @Positive(message = "продолжительность фильма должна быть положительной")
    final int duration;

    @NotNull
    final MPA mpa;

    final Set<Integer> likes = new HashSet<>();

    final Set<Genre> genres = new HashSet<>();

    public void setLike(Integer id) {
        likes.add(id);
    }

    public void setGenre(Genre genre) {
        genres.add(genre);
    }

    public void removeLike(Integer id) {
        likes.remove(id);
    }

    public Set<Integer> getLikes() {
        return Set.copyOf(likes);
    }

    public Optional<Integer> getDuration() {
        return Optional.of(duration);
    }

    public Optional<LocalDate> getReleaseDate() {
        return Optional.of(releaseDate);
    }
}
