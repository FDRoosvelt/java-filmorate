package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private static long postId = 1;
    private static final Map<Long, User> users = new HashMap<>();

    private static long nextPostId() {
        return postId++;
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public User addUser(User user) {
        nameLogic(user);
        user.setId(nextPostId());
        users.put(user.getId(), user);
        return user;
    }

    public User findUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        return users.get(id);
    }

    public User updateUser(User user) {
        nameLogic(user);
        if (!users.containsKey(user.getId())) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        users.put(user.getId(), user);
        return user;
    }

    public String deleteUser(Long id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        users.remove(id);
        return "Пользователь удален";
    }

    private void nameLogic(User user) {
        if (user.getName()==null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
