package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserStorage userStorage;
    @Autowired
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info("GET. Пришел  запрос /users на получение списка пользователей");
        log.info("GET. Отправлен ответ /users на получение списка пользователей");
        return userStorage.getUsers();
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        log.info("POST. Пришел  запрос /users с телом: {}", user);
        log.info("POST. Отправлен ответ /users с телом: {}", user);
        return userStorage.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) throws NotFoundException {
        log.info("PUT. Пришел  запрос /users с телом: {}", user);
        log.info("PUT. Отправлен ответ /users с телом: {}", user);
        return userStorage.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userStorage.getUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getMutualFriends(id, otherId);
    }
}
