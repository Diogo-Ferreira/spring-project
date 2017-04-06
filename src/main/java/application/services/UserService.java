package application.services;

import application.entities.User;

public interface UserService {
    public void save(User user);
    public User findByUsername(String username);
}
