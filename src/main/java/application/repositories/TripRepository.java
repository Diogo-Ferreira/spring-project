package application.repositories;

import application.entities.TripEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by diogo on 3/10/17.
 */
public interface TripRepository extends CrudRepository<TripEntity, Long> {

}
