package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

import static ru.yandex.practicum.filmorate.utility.Validator.validate;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    private int generateId() {
        return id++;
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        validate(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь");
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) throws NotFoundException {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь не найден");
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя обновлены");
        return user;
    }

}
