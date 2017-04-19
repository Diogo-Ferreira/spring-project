package application.services;

import application.entities.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);

    User findById(long id);
}
