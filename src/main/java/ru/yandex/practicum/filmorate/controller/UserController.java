package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public  UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Collection<User> getUsers() {
        log.info("Получен запрос GET /users");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        if (id < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос GET /users/{id}");
        return userService.findUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable("id") long id) {
        if (id < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос GET /users/{id}/friends");
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getMutualFriends(@PathVariable("id") long id,
                                      @PathVariable("otherId") long otherId) {
        if (id < 1 || otherId < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос GET /users/{id}/friends/common/{otherId}");
        return userService.getMutualFriends(id, otherId);
    }

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос POST /users");
        return userService.addUser(user);
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос PUT /users");
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") long id,
                          @PathVariable("friendId") long friendId) {
        if (id < 1 || friendId < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос PUT /users/{id}/friends/{friendId}");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") long id,
                             @PathVariable("friendId") long friendId) {
        if (id < 1 || friendId < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос DELETE /users/{id}/friends/{friendId}");
        return userService.deleteFriend(id, friendId);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        if (id < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос DELETE /users/{id}/");
        return userService.deleteUser(id);
    }
}

