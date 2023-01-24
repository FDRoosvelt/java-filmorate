package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
public class User {
    @NotNull
    @NotBlank
    private String login;
    private String name;
    private long id;
    @Email
    private String email;
    @Past
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}
