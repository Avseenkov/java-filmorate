package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.utils.ValidatorTestUtils.hasErrorMessage;

class FilmTestValidation {

    @Autowired
    public Film film;

    @BeforeEach
    void setUp() {
        film = Film.builder().id(1)
                .name("Терминатор")
                .description("Что-то там с роботами")
                .releaseDate(LocalDate.of(1994, 1, 1))
                .duration(94)
                .mpa(MPA.builder().id(1).name("G").description("нет возрастных ограничений").build())
                .build();
    }

    @Test
    public void nameCouldNotBeEmpty() {
        Film film1 = film.toBuilder().name("").build();
        Assertions.assertTrue(hasErrorMessage(film1, "название фильма не может быть пустым"));
    }

    @Test
    public void descriptionCouldNotBeMoreThan200() {
        Film film1 = film.toBuilder().description("Равным образом постоянный количественный рост и сфера нашей активности влечет за собой процесс внедрения и модернизации существующих финансовых и административных условий. Равным образом социально-экономическое развитие в значительной степени обуславливает создание направлений прогрессивного развития. Повседневная практика показывает, что постоянное информационно-техническое обеспечение нашей деятельности способствует подготовке и реализации экономической целесообразности принимаемых решений.").build();
        Assertions.assertTrue(hasErrorMessage(film1, "максимальная длина описания не может быть больше 200 символов"));
    }

    @Test
    public void dataOfReleaseNotBeEarlyThen1985_28_12() {
        Film film1 = film.toBuilder().releaseDate(LocalDate.of(1895, 12, 27)).build();
        Assertions.assertTrue(hasErrorMessage(film1, "дата релиза не может быть ранее 28 декабря 1895"));
    }

    @Test
    void durationShouldBePositive() {
        Film film1 = film.toBuilder().duration(-12).build();
        Assertions.assertTrue(hasErrorMessage(film1, "продолжительность фильма должна быть положительной"));
    }
}

