package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    public final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET. Пришел  запрос /films на получение списка фильмов");
        List<Film> response = filmStorage.getFilms();
        log.info("GET. Отправлен ответ /films на получение списка фильмов");
        return response;

    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("POST. Пришел  запрос /films с телом: {}", film);
        Film response = filmStorage.addFilm(film);
        log.info("POST. Отправлен ответ /films с телом: {}", response);
        return response;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("PUT. Пришел запрос /films с телом: {}", film);
        filmService.validate(film);
        Film response = filmStorage.updateFilm(film);
        log.info("PUT. Отправлен ответ /films с телом: {}", film);
        return response;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmStorage.getFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.disLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularMovies(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getFirstMostPopularFilms(count);
    }

}
