package application.controllers;

import application.entities.Trip;
import application.entities.TripImage;
import application.entities.User;
import application.services.TripImageService;
import application.services.TripService;
import application.services.UserService;
import application.validator.TripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by diogo on 4/18/17.
 */

@Controller
public class WallController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserService userService;

    @Autowired
    private TripValidator tripValidator;

    @Autowired
    private TripImageService tripImageService;
    /**
     * For each followed users, shows last 2-3 trips
     * @return
     */
    @RequestMapping("/")
    public String wall(Authentication authentication, Model model){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Set<User> usersFollowing = user.getFollowing();

        HashMap<User,List<TripImage>> tripImagesByUsers = new HashMap<>();


        //Note: This code ain't efficient at all and should be replaced by a specific SQL query in the future
        for(User userFollowing : usersFollowing){
            tripImagesByUsers.put(userFollowing, new ArrayList<>());
            for(Trip trip : userFollowing.getTrips()){
                ArrayList<TripImage> tripImagesList = new ArrayList<>();
                tripImagesList.addAll(trip.getImages());
                int index = tripImagesList.size() > 2 ? 3 : tripImagesList.size();
                tripImagesByUsers.get(userFollowing).addAll(tripImagesList.subList(0,index));
            }
        }
        model.addAttribute("users",tripImagesByUsers);

        return "wall";
    }
}
