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
@Controller
public class TripController {

    @RequestMapping("/trips")
    public List<Trip> trips()
    {
        List<Trip> trips = (List<Trip>) tripService.getAll();
        return trips;
    }

    @RequestMapping("/trip/{id}")
    public String showTrip(@PathVariable Integer id, Model model){
        //TODO
        return "tripshow";
    }

    @RequestMapping("/trip/new")
    public String newTrip(Model model){
        model.addAttribute("trip", new Trip());
        return "tripform";
    }
    /*Save trip*/
    @RequestMapping(value="trip", method=RequestMethod.POST)
    public  String saveTrip(Trip trip){
        tripService.saveTrip(trip);
        return  "redirect:/trip/"+trip.getId();
    }

    @Autowired
    public void setTripService(TripService tripService){
        this.tripService = tripService;
    }
    private TripService tripService;
}
