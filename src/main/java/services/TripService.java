package services;

import entities.TripEntity;

/**
 * Created by diogo on 3/17/17.
 */
public interface TripService {

    Iterable<TripEntity> all();

    TripEntity getTripById(Integer id);

    TripEntity saveTrip(TripEntity tripEntity);

    void deleteProduct(Integer id);

}
