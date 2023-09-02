package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

public class UserControllerTest {

    private InMemoryUserStorage storage = new InMemoryUserStorage();
    private UserService service = new UserService(storage);
    private UserController controller = new UserController(storage, service);

    private final User user = User.builder()
            .id(1L)
            .email("testUser@domain.ru")
            .login("zxc")
            .name("Иван")
            .birthday(LocalDate.of(1980, 1, 1))
            .build();

    @Test
    void addUserTest() {
        controller.addUser(user);

        Assertions.assertEquals(1, controller.getUsers().size());
    }
}
