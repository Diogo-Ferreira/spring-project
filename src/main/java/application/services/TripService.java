package application.services;

import application.entities.Trip;
import application.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by diogo on 3/17/17.
 */
@Service
public class TripService {

    private TripRepository tripRepository;

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Autowired
    public TripRepository getTripRepository() {
        return tripRepository;
    }


    public Iterable<Trip> getAll(){
        return tripRepository.findAll();
    }
}
