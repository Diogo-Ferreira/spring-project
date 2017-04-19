package application.repositories;

import application.entities.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by diogo on 3/10/17.
 */
public interface TripRepository extends CrudRepository<Trip, Long> {

}
