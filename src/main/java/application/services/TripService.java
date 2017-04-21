package application.services;

import application.entities.Trip;
import application.entities.TripImage;
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

    public Trip saveTrip(Trip trip){return  tripRepository.save(trip);}

    public Trip getTripById(Integer id){return tripRepository.findOne(Long.valueOf(id));}

    public Iterable<Trip> listAllTrips(){
        return tripRepository.findAll();
    }

    public void deleteTrip(Trip trip){
        tripRepository.delete(trip);
    }
}
