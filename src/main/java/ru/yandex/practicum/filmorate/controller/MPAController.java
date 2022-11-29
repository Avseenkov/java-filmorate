package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.Collection;

@RestController
@RequestMapping("mpa")
@AllArgsConstructor
public class MPAController {

    MPAService mpaService;

    @GetMapping("{id}")
    public MPA get(@PathVariable Integer id) {
        return mpaService.get(id);
    }

    @GetMapping
    public Collection<MPA> getAll() {
        return mpaService.getAll();
    }
}
