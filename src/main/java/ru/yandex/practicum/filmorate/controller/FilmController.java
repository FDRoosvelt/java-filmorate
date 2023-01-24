package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmStorage filmStorage;
    UserStorage userStorage;
    FilmService filmService;


    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage,
                          InMemoryUserStorage inMemoryUserStorage,
                          FilmService filmService) {
        filmStorage = inMemoryFilmStorage;
        userStorage = inMemoryUserStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return filmStorage.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable("filmId") Long id) {
        log.info("Получен запрос GET /film/{filmId}");
        return filmStorage.findFilmById(id);
    }

    @GetMapping("/popular")
    public List<Film> popularFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        if (count < 1) {
            throw new ValidationException("Неверное кол-во фильмов");
        }
        log.info("Получен запрос GET /films/popular");
        return filmService.getPopularFilms(filmStorage, count);
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody @NotNull Film film) {
        log.info("Получен запрос POST /films");
        return filmStorage.addFilm(film);
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films");
        return filmStorage.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable("id") long id,
                         @PathVariable("userId") long userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}");
        return filmService.addLike(filmStorage, userStorage, id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable("id") long id,
                           @PathVariable("userId") long userId) {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}");
        return filmService.deleteLike(filmStorage, userStorage, id, userId);
    }
}
