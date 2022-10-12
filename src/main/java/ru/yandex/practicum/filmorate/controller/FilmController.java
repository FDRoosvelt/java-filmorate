package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.time.Month.DECEMBER;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    int id = 1;
    private Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Получен запрос GET");
        return films.values();
    }

    @PostMapping()
    public Film addFilm(@Valid @RequestBody @NotNull Film film) throws ValidationExeption {
        if (releaseDateLogic(film)) {
            film.setId(id++);
            films.put(film.getId(), film);
        } else {
            throw new ValidationExeption("Неверная дата релиза");
        }
        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationExeption {
        if (!films.containsKey(film.getId())) {
            throw new ValidationExeption("Фильм не найден");
        }
        if (!releaseDateLogic(film)) {
            throw new ValidationExeption("Неверная дата релиза");
        }
        films.put(film.getId(), film);
        return film;
    }

    private boolean releaseDateLogic(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, DECEMBER, 28))) {
            return false;
        }
        return true;
    }

    class ValidationExeption extends Exception {
        public ValidationExeption(final String message) {
            super(message);
        }
    }
}
