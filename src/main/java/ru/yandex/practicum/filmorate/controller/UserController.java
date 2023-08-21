package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }

    public void validate(User user) {
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) throws NotFoundException {
        log.info(">>> POST. Пришел  запрос /users с телом: {}", user);
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь");
        log.info("Пользователь с ником {} имеет имя {}", user.getLogin(), user.getName());
        log.info("<<< POST. Отправлен ответ /users с телом: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) throws NotFoundException {
        log.info(">>> PUT. Пришел  запрос /users с телом: {}", user);
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь не найден");
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя обновлены");
        log.info("<<< PUT. Отправлен ответ /users с телом: {}", user);
        return user;
    }
}
