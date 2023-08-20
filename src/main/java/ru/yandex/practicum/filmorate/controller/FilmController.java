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
        return ++id;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Запрошены все фильмы" + films.values());
        return new ArrayList<>(films.values());

    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info(">>> POST. Пришел  запрос /films с телом: {}", film);
        log.info("Выполнение процесса добавление фильма");
        log.info("Валидация фильма - START");
        validate(film);
        log.info("Валидация фильма - SUCCESS");
        log.info("Генерируем идентификатор");
        film.setId(generateId());
        log.info("Идентификатор создан: {}", film.getId());
        films.put(film.getId(), film);
        log.info("Процесса добавления фильма успешно завершен");
        log.info("<<< POST. Отправлен ответ /films с телом: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) throws NotFoundException {
        log.info(">>> PUT. Пришел запрос /films с телом: {}", film);
        log.info("Процесс по обновлению фильма с ид: {}", film.getId());
        log.info("Валидация фильма - START");
        validate(film);
        log.info("Валидация фильма - SUCCESS");
        log.info("Поиск записи с фильмом для обновления");
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм не найден");
        }
        log.info("Фильм найден");
        films.put(film.getId(), film);
        log.info("Фильм обновлен");
        log.info("<<< PUT. Отправлен ответ /films с телом: {}", film);
        return film;
    }

}
