package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void userMustBeLogin() {
        UserController userController = new UserController();
        User user = new User("login", null,100, "test@test.test", LocalDate.of(2000, JANUARY, 1));
        userController.addUser(user);
        assertEquals("login", user.getName());
    }
}