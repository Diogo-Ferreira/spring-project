package application.repositories;

import application.entities.TripImage;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Sylvain Ramseyer on 21.04.2017.
 */
public interface TripImageRepository extends CrudRepository<TripImage, Long> {
}
