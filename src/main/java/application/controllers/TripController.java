package application.controllers;

import application.entities.Trip;
import application.entities.User;
import application.services.UserService;
import application.validator.TripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import application.services.TripService;

import java.util.*;

/**
 * Created by diogo on 3/17/17.
 */
@Controller
public class TripController {

    private TripService tripService;

    @Autowired
    private UserService userService;

    @Autowired
    private TripValidator tripValidator;

    @Autowired
    public void setTripService(TripService tripService){
        this.tripService = tripService;
    }


    @RequestMapping(value="/trips", method = RequestMethod.GET)
    public String list(Model model)
    {
        model.addAttribute("trips", tripService.listAllTrips());
        return "trips";
    }

    @RequestMapping("/trip/{id}")
    public String showTrip(@PathVariable Integer id, Model model){

        model.addAttribute("trip", tripService.getTripById(id));
        return "tripshow";
    }

    @RequestMapping("/trip/new")
    public String newTrip(Model model){
        model.addAttribute("trip", new Trip());
        return "tripform";
    }
    /*Save trip*/
    @RequestMapping(value="trip", method=RequestMethod.POST)
    public  String saveTrip(Trip trip, Authentication authentication, BindingResult bindingResult, Model model){

        tripValidator.validate(trip, bindingResult);


        if (bindingResult.hasErrors()) {
            model.addAttribute("hasErrors",true);
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "tripform";
        }

        if(trip.getId() != null){
            Trip oldTrip = tripService.getTripById(Math.toIntExact(trip.getId()));
            trip.setImages(oldTrip.getImages());
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());
        trip.setTraveler(authenticatedUser);
        tripService.saveTrip(trip);
        return  "redirect:/trip/"+trip.getId();
    }

    /*Delete trip*/
    @RequestMapping(value = "/trip/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Integer id){
        Trip trip = tripService.getTripById(id);
        tripService.deleteTrip(trip);
        return "redirect:/profile/show/me";
    }

    /*Delete trip*/
    @RequestMapping(value = "/trip/image/delete/{id}", method = RequestMethod.POST)
    public String deleteImage(@PathVariable Integer id){
        //tripService.deleteTrip(id);
        return "redirect:/trips";
    }



    /*Edit a trip*/
    @RequestMapping("/trip/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("trip", tripService.getTripById(id));
        return "tripform";
    }
}
