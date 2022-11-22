package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MPADbStorageTest {

    private final MPAStorage mpaDbStorage;

    @Test
    void getAll() {
        Collection<MPA> ratings = mpaDbStorage.getAll();
        assertEquals(ratings.size(), 5);
    }

    @Test
    void get() {
        MPA mpa = mpaDbStorage.get(1);
        assertEquals(mpa.getId(), 1);
        assertEquals(mpa.getName(), "G");
        assertEquals(mpa.getDescription(), "нет возрастных ограничений");
    }
}