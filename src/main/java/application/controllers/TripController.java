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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileWriter;
import java.util.*;

/**
 * Created by diogo on 3/17/17.
 */
@Controller
public class TripController {

    @Autowired
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
    public String delete(@PathVariable Integer id, Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        Trip trip = tripService.getTripById(id);

        if(trip.getTraveler().equals(authenticatedUser)){
            trip.getImages().clear();
            tripService.saveTrip(trip);
            User user = trip.getTraveler();
            user.getTrips().remove(trip);
            userService.save(user);
        }

        return "redirect:/profile/show/me";
    }

    /*Edit a trip*/
    @RequestMapping("/trip/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());
        Trip trip = tripService.getTripById(id);

        if(trip.getTraveler().equals(authenticatedUser)){
            model.addAttribute("trip", trip);
            return "tripform";
        }else{
            return "redirect:/profile/show/me";
        }

    }

}
