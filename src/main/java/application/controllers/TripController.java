package application.controllers;

import application.entities.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import application.services.TripService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by diogo on 3/17/17.
 */
@RestController
public class TripController {

    @RequestMapping("/trips")
    public List<Trip> trips()
    {

        List<Trip> trips = (List<Trip>) tripService.getAll();
        return trips;
    }

    @Autowired
    public TripService tripService;
}
