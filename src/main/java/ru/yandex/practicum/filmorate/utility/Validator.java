package ru.yandex.practicum.filmorate.utility;

import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    public static void validate(Film film) throws BadRequestException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new BadRequestException("Ошибка по минимальной дате релиза фильма," +
                    "releaseDate не может быть меньше 28 декабря 1895 года");
        }
    }

    public static void validate(User user) throws BadRequestException {
        if (user.getLogin().contains(" ")) {
            throw new BadRequestException("Ошибка валидации, поле login не может содержать пробелы");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

}
