package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import static java.time.Month.JANUARY;

public class UserControllerTest {

    @Test
    void userMustBeLogin() {
        InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
        User user = new User("login", null, "test@test.test", LocalDate.of(2000, JANUARY, 1));
        inMemoryUserStorage.addUser(user);
        assertEquals("login", user.getName());
    }
}
