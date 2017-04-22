package application.controllers;

import application.entities.Trip;
import application.entities.User;
import application.services.TripImageService;
import application.services.UserService;
import application.validator.TripValidator;
import application.entities.TripImage;
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
    private TripImageService tripImageService;

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
    public String showTrip(Authentication authentication, @PathVariable Integer id, Model model){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());
        Trip trip = tripService.getTripById(id);
        model.addAttribute("trip", trip);
        model.addAttribute("isTraveler",trip.getTraveler().equals(authenticatedUser));
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
        trip.getImages().clear();
        tripService.saveTrip(trip);
        User user = trip.getTraveler();
        user.getTrips().remove(trip);
        userService.save(user);
        return "redirect:/profile/show/me";
    }

    /*Delete trip image*/
    @RequestMapping(value = "/trip/image/delete/{id}", method = RequestMethod.POST)
    public String deleteImage(@PathVariable Integer id){
        TripImage tripImage = tripImageService.getTripImageById(id);
        Trip trip = tripImage.getTrip();
        trip.getImages().remove(tripImage);
        tripService.saveTrip(trip);
        return "redirect:/trip/"+trip.getId();
    }



    /*Edit a trip*/
    @RequestMapping("/trip/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("trip", tripService.getTripById(id));
        return "tripform";
    }

    /*ADD Image*/
    @RequestMapping(value="trip/{trip_id}/image")
    public  String addImage(@PathVariable Integer trip_id,TripImage image){

        Trip trip = tripService.getTripById(trip_id);
        image.setUrl("www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg");
        trip.getImages().add(image);

        //tripImageService.saveTripImage(image);
        return  "redirect:/trip/"+trip.getId();
    }

    @RequestMapping(value="/trip/{trip_id}/new/image")
    public String newTripImage(@PathVariable Integer trip_id, Model model){
        model.addAttribute("tripImage", new TripImage());
        return "addimageform";
    }
}
