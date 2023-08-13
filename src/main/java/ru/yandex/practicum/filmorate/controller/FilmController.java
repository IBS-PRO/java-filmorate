package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

import static ru.yandex.practicum.filmorate.utility.Validator.validate;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    private int generateId() {
        return id++;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Запрошены все фильмы" + films.values());
        return new ArrayList<>(films.values());

    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) throws NotFoundException {
        validate(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм");
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) throws NotFoundException {
        validate(film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм не найден");
        }
        films.put(film.getId(), film);
        log.info("Фильм обновлен");
        return film;
    }

}
