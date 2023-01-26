package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService (InMemoryFilmStorage inMemoryFilmStorage,
                        InMemoryUserStorage inMemoryUserStorage) {
        filmStorage = inMemoryFilmStorage;
        userStorage = inMemoryUserStorage;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film findFilmById(Long id) {
        return filmStorage.findFilmById(id);
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public String deleteFilm(Long id) {
        return filmStorage.deleteFilm(id);
    }

    public Film addLike(long filmId, long userId) {
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

    public Film deleteLike(long filmId, long userId) {
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

    public List<Film> getPopularFilms(Integer count) {
          return filmStorage.getFilms().stream()
                .sorted(Comparator.comparing(Film::getLikes).reversed().thenComparing(Film::getName))
                .limit(count)
                .collect(Collectors.toList());
    }


}
