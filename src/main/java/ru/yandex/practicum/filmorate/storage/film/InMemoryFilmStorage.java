package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static java.time.Month.DECEMBER;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static long filmId = 0;
    private static final Map<Long, Film> films = new HashMap<>();

    private static long nextFilmId() {
        return filmId++;
    }

    public Collection<Film> getFilms() {
        return films.values();
    }

    public Film findFilmById(Long id) {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException("Фильм не найден");
        }
        return films.get(id);
    }

    public Film addFilm(Film film) {
        if (!releaseDateLogic(film)) {
            throw new ValidationException("Неверная дата релиза");
        }
        film.setId(nextFilmId());
        films.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ObjectNotFoundException("Фильм не найден");
        }
        if (!releaseDateLogic(film)) {
            throw new ValidationException("Неверная дата релиза");
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
}
