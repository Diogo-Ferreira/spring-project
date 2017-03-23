package application.services;

import application.entities.TripEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import application.repositories.TripRepository;

/**
 * Created by diogo on 3/17/17.
 */

@Service("TripService")
public class TripServiceImplementation implements TripService {

    private TripRepository tripRepository;

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Bean
    public TripRepository getTripRepository(){
        return this.tripRepository;
    }

    @Override
    public Iterable<TripEntity> all() {
        return this.tripRepository.findAll();
    }

    @Override
    public TripEntity getTripById(Integer id) {
        return this.tripRepository.findOne(Long.valueOf(id));
    }

    @Override
    public TripEntity saveTrip(TripEntity tripEntity) {
        return this.tripRepository.save(tripEntity);
    }

    @Override
    public void deleteProduct(Integer id) {
        this.tripRepository.delete(Long.valueOf(id));
    }
}
