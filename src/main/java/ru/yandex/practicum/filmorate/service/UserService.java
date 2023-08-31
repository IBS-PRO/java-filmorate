package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info("'{}' добавил '{}' в список друзей", userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        User user = userStorage.getUser(userId);
        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) {
            throw new NotFoundException("Список друзей пользователя с id '" + userId + "' пуст");
        }
        return friends.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        log.info("'{}' запросил список общих друзей '{}'", userId, friendId);
        if (userFriends.stream().anyMatch(friendFriends::contains)) {
            return userFriends.stream()
                    .filter(userFriends::contains)
                    .filter(friendFriends::contains)
                    .map(userStorage::getUser).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info("'{}' удален '{}' из списка друзей", userId, friendId);
    }


}
