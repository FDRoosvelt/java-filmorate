package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public Film addLike(FilmStorage filmStorage, UserStorage userStorage, long filmId, long userId) {
        if (userStorage.findUserById(userId) == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        if (filmStorage.findFilmById(filmId) == null) {
            throw new ObjectNotFoundException("Фильм не найден");
        }
        filmStorage.findFilmById(filmId).getLikesList().add(userId);
        filmStorage.findFilmById(filmId).setLikes(filmStorage.findFilmById(filmId).getLikesList().size());
        return filmStorage.findFilmById(filmId);
    }

    public Film deleteLike(FilmStorage filmStorage, UserStorage userStorage, long filmId, long userId) {
        if (userStorage.findUserById(userId) == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        if (filmStorage.findFilmById(filmId) == null) {
            throw new ObjectNotFoundException("Фильм не найден");
        }
        filmStorage.findFilmById(filmId).getLikesList().remove(userId);
        filmStorage.findFilmById(filmId).setLikes(filmStorage.findFilmById(filmId).getLikesList().size());
        return filmStorage.findFilmById(filmId);
    }

    public List<Film> getPopularFilms(FilmStorage filmStorage, Integer count) {
          return filmStorage.getFilms().stream()
                .sorted(Comparator.comparing(Film::getLikes).reversed().thenComparing(Film::getName))
                .limit(count)
                .collect(Collectors.toList());
    }
}
