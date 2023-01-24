package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import static java.time.Month.JANUARY;

class FilmControllerTest {

    @Test
    void realeseDateMustBeAfter28DECEMBER1895() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        Film film = new Film("test", "test description", LocalDate.of(1800, JANUARY, 1), 100);
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            inMemoryFilmStorage.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }
}