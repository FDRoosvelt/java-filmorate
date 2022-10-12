package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    int id = 1;
    private Map<Integer, User> users = new HashMap<>();

    @GetMapping()
    public Collection<User> getUsers() {
        log.info("Получен запрос GET /users");
        return users.values();
    }

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен запрос POST /users");
        nameLogic(user);
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) throws ValidationExeption {
        log.info("Получен запрос PUT /users");
        nameLogic(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationExeption("Пользователь не найден");
        }
        users.put(user.getId(), user);
        return user;
    }

    private void nameLogic(User user) {
        if (user.getName()==null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    class ValidationExeption extends Exception {
        public ValidationExeption(final String message) {
            super(message);
        }
    }
}

