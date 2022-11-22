package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {
    void setLike(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
