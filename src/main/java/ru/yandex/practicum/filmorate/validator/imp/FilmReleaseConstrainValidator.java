package ru.yandex.practicum.filmorate.validator.imp;

import ru.yandex.practicum.filmorate.validator.FilmReleaseConstrain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmReleaseConstrainValidator implements ConstraintValidator<FilmReleaseConstrain, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.of(1895, 12, 28));
    }
}
