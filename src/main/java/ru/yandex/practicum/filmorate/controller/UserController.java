package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    UserStorage userStorage;
    UserService userService;

    @Autowired
    public  UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        userStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @GetMapping()
    public Collection<User> getUsers() {
        log.info("Получен запрос GET /users");
        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        log.info("Получен запрос GET /users/{id}");
        return userStorage.findUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable("id") long id) {
        log.info("Получен запрос GET /users/{id}/friends");
        return userService.getUserFriends(userStorage, id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable("id") long id,
                                      @PathVariable("otherId") long otherId) {
        log.info("Получен запрос GET /users/{id}/friends/common/{otherId}");
        return userService.getMutualFriends(userStorage, id, otherId);
    }

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос POST /users");
        return userStorage.addUser(user);
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос PUT /users");
        return userStorage.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") long id,
                          @PathVariable("friendId") long friendId) {
        log.info("Получен запрос PUT /users/{id}/friends/{friendId}");
        return userService.addFriend(userStorage, id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") long id,
                             @PathVariable("friendId") long friendId) {
        log.info("Получен запрос DELETE /users/{id}/friends/{friendId}");
        return userService.deleteFriend(userStorage, id, friendId);
    }
}

