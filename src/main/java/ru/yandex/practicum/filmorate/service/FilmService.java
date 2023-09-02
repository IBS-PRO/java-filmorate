package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    public void like(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        userService.getUserStorage().getUser(userId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        film.addLike(userId);
        log.info("Пользователь с id {} поставил like фильму с id {}", userId, filmId);
    }

    public void disLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        userService.getUserStorage().getUser(userId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        film.removeLike(userId);
        log.info("Пользователь с id {} убрал like фильму с id {}", userId, filmId);
    }

    public List<Film> getFirstMostPopularFilms(Integer count) {
        log.info("Получение списка из " + count + " популярных фильмов");
        return filmStorage.getFilms()
                .stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

}
