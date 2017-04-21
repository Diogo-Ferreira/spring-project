package application.services;

import application.entities.TripImage;
import application.repositories.TripImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sylvain Ramseyer on 21.04.2017.
 */
@Service
public class TripImageService {
    private TripImageRepository tripImageRepository;

    @Autowired
    public void setTripImageRepository(TripImageRepository tripImageRepository) {
        this.tripImageRepository = tripImageRepository;
    }

    @Autowired
    public TripImageRepository getTripRepository() {
        return tripImageRepository;
    }

    public TripImage saveTripImage(TripImage tripImage){return  tripImageRepository.save(tripImage);}

    public TripImage getTripImageById(Integer id){return tripImageRepository.findOne(Long.valueOf(id));}

    public Iterable<TripImage> listAllTripImages(){
        return tripImageRepository.findAll();
    }

    public void deleteTripImage(Integer id){
        tripImageRepository.delete(Long.valueOf(id));
    }
}
