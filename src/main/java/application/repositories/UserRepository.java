package application.repositories;

import application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Iterable<User> whereUsername(String query);
}