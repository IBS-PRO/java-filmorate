package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.utility.Validator.validate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidatorTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void minimalReleaseFilmDateTest() {
        Film oldFilm = Film.builder()
                .id(1)
                .name("Фильм 1")
                .description("Очень старый фильм")
                .releaseDate(LocalDate.now().minusYears(150))
                .duration(120)
                .build();
        BadRequestException e = assertThrows(BadRequestException.class, () -> validate(oldFilm));
        assertEquals("Ошибка по минимальной дате релиза фильма," +
                "releaseDate не может быть меньше 28 декабря 1895 года", e.getMessage());
    }

    @Test
    public void filmReleaseLimitTest() {
        Film filmReleaseLimit = Film.builder()
                .id(1)
                .name("Фильм 2")
                .description("Первый старый фильм")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(120)
                .build();
        assertDoesNotThrow(() -> validate(filmReleaseLimit));
    }

    @Test
    public void spaceValidationInLoginFieldTest() {
        User testLoginField = User.builder()
                    .id(1)
                    .email("testUser@domain.ru")
                    .login("  ")
                    .name("Иван")
                    .birthday(LocalDate.of(1980, 1, 1))
                    .build();
        ResponseEntity<User> response = restTemplate.postForEntity("/users", testLoginField, User.class);
        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

}
