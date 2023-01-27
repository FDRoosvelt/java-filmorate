package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService (InMemoryUserStorage inMemoryUserStorage) {
        userStorage = inMemoryUserStorage;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User findUserById(Long id) {
        return userStorage.findUserById(id);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public String deleteUser(Long id) {
        return userStorage.deleteUser(id);
    }

    public User addFriend(Long id, Long friendId) {
        if (userStorage.findUserById(id) == null || userStorage.findUserById(friendId) == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        if (userStorage.findUserById(id).getFriends().contains(friendId) &&
                userStorage.findUserById(friendId).getFriends().contains(id)) {
            throw new ValidationException("Пользователь уже ваш друг");
        }
        userStorage.findUserById(id).getFriends().add(friendId);
        userStorage.findUserById(friendId).getFriends().add(id);
        return userStorage.findUserById(id);
    }

    public User deleteFriend(Long id, Long friendId) {
        if (userStorage.findUserById(id) == null || userStorage.findUserById(friendId) == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        if (!userStorage.findUserById(id).getFriends().contains(friendId) &&
                !userStorage.findUserById(friendId).getFriends().contains(id)) {
            throw new ValidationException("Пользователя нет в списке ваших друзей");
        }
        userStorage.findUserById(id).getFriends().remove(friendId);
        userStorage.findUserById(friendId).getFriends().remove(id);
        return userStorage.findUserById(id);
    }

    public List<User> getUserFriends(Long id) {
        if (userStorage.findUserById(id) == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        List<User> friendList = new ArrayList<>();
        if (userStorage.findUserById(id).getFriends() != null) {
            for (Long friendId : userStorage.findUserById(id).getFriends()) {
                friendList.add(userStorage.findUserById(friendId));
            }
        }
        return friendList;
    }

    public Set<User> getMutualFriends(Long id, Long friendId) {
        if (userStorage.findUserById(id) == null || userStorage.findUserById(friendId) == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        Set<User> mutualFriends = new HashSet<>();
        for (long friend : userStorage.findUserById(id).getFriends()) {
            if (userStorage.findUserById(friendId).getFriends().contains(friend)) {
                mutualFriends.add(userStorage.findUserById(friend));
            }
        }
        return mutualFriends;
    }
}
