package application.validator;

import application.entities.Trip;
import application.entities.User;
import application.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by diogo on 4/21/17.
 */
@Component
public class TripValidator implements Validator {

    @Autowired
    private TripService tripService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Trip trip = (Trip) target;

        if(trip.getStartDate().after(trip.getEndDate())){
            errors.reject("start date after end date");
        }
    }
}
