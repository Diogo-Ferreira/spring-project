package application.controllers;

import application.entities.Trip;
import application.entities.TripImage;
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

    private TripService tripService;

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
    public  String saveTrip(Trip trip){
        tripService.saveTrip(trip);
        return  "redirect:/trip/"+trip.getId();
    }

    /*Delete trip*/
    @RequestMapping("/trip/delete/{id}")
    public String delete(@PathVariable Integer id){
        tripService.deleteTrip(id);
        return "redirect:/trips";
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
