package application.controllers;

import application.entities.Trip;
import application.entities.User;
import application.services.SecurityService;
import application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

/**
 * Created by diogo on 4/18/17.
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping("profile/show/{id}")
    public String show(@PathVariable long id, Model model,Authentication authentication){
        User user = userService.findById(id);
        UserDetails authUser = (UserDetails) authentication.getPrincipal();
        Set<Trip> tripsOfUser = user.getTrips();
        Set<User> followingUsers = user.getFollowing();
        Set<User> followedByUsers = user.getFollowedBy();
        model.addAttribute("user", user);
        model.addAttribute("trips", tripsOfUser);
        model.addAttribute("following",followingUsers);
        model.addAttribute("followedBy",followedByUsers);
        model.addAttribute("isAuthUser",authUser.getUsername().equals(user.getUsername()));
        model.addAttribute("followsAlready",followedByUsers.contains(userService.findByUsername(authUser.getUsername())));
        return "profile";
    }

    @RequestMapping("/profile/show/me")
    public String showMe(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());
        return "redirect:/profile/show/"+ authenticatedUser.getId();
    }

    @RequestMapping(value = "profile/follow/{id}",method = RequestMethod.POST)
    public String follow(Authentication authentication, HttpServletRequest request, @PathVariable long id){

        User userToFollow = userService.findById(id);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());
        authenticatedUser.getFollowing().add(userToFollow);
        userService.save(authenticatedUser);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "profile/unfollow/{id}",method = RequestMethod.POST)
    public String unFollow(Authentication authentication, HttpServletRequest request, @PathVariable long id){

        User userToFollow = userService.findById(id);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());
        authenticatedUser.getFollowing().remove(userToFollow);
        userService.save(authenticatedUser);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

}
