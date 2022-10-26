package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@Getter
public class User {
    @NotNull
    final int id;
    @NotBlank(message = "email не может быть пустым")
    @Email(message = "email должен иметь формат адреса электронной почты")
    final String email;

    @NotBlank(message = "логин не можеть быть пустым")
    @Pattern(regexp = "[^\\u0020]+", message = "логин не может содержать пробелы")
    final String login;

    final String name;

    @Past(message = "дата рождения не может быть будущей датой")
    final LocalDate birthday;

    final Set<Integer> friends = new HashSet<>();

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public void removeFriend(Integer id) {
        friends.remove(id);
    }

    public Set<Integer> getFriends() {
        return new HashSet<>(friends);
    }
}
