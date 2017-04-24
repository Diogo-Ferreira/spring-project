package application.services;

import application.entities.User;

import java.util.Set;

public interface UserService {
    void save(User user);
    User findByUsername(String username);

    User findById(long id);

    Iterable<User> getAll();

    Iterable<User> whereUsername(String query);
}
