package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MPAStorage;

import java.util.Collection;

@Service
@AllArgsConstructor
public class MPAService {
    MPAStorage mpaStorage;

    public MPA get(int id) {
        return mpaStorage.get(id);
    }

    public Collection<MPA> getAll() {
        return mpaStorage.getAll();
    }
}
