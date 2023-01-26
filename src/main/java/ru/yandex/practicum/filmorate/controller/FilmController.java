package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmService filmService;


    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return filmService.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable("filmId") Long id) {
        if (id < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос GET /film/{filmId}");
        return filmService.findFilmById(id);
    }

    @GetMapping("/popular")
    public List<Film> popularFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        if (count < 1) {
            throw new ValidationException("Неверное кол-во фильмов");
        }
        log.info("Получен запрос GET /films/popular");
        return filmService.getPopularFilms(count);
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody @NotNull Film film) {
        log.info("Получен запрос POST /films");
        return filmService.addFilm(film);
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films");
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable("id") long id,
                         @PathVariable("userId") long userId) {
        if (id < 1 || userId < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос PUT /films/{id}/like/{userId}");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable("id") long id,
                           @PathVariable("userId") long userId) {
        if (id < 1 || userId < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос DELETE /films/{id}/like/{userId}");
        return filmService.deleteLike(id, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteFilm(@PathVariable("id") long id) {
        if (id < 1) {
            throw new ValidationException("Данные введены неверно");
        }
        log.info("Получен запрос DELETE /films/{id}/");
        return filmService.deleteFilm(id);
    }
}
