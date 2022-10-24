package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.validator.imp.FilmReleaseConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilmReleaseConstraintValidator.class)
public @interface FilmReleaseConstrain {
    String message() default "дата релиза не может быть ранее 28 декабря 1895";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
