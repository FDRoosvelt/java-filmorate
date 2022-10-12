package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    void realeseDateMustBeAfter28DECEMBER1895() {
        FilmController filmController = new FilmController();
        Film film = new Film("test", 100, "test description", LocalDate.of(1800, JANUARY, 1), 100);
        Throwable thrown = assertThrows(FilmController.ValidationExeption.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }
}