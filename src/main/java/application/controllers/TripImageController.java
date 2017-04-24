package application.controllers;

import application.entities.Trip;
import application.entities.TripImage;
import application.entities.User;
import application.services.TripImageService;
import application.services.TripService;
import application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


@Controller
public class TripImageController {

    @Autowired
    private ServletContext context;

    @Autowired
    private TripService tripService;

    @Autowired
    private UserService userService;

    @Autowired
    private TripImageService tripImageService;

    @Autowired
    public void setTripService(TripService tripService){
        this.tripService = tripService;
    }

    private ResourceLoader resourceLoader = new DefaultResourceLoader();


    /*Delete trip image*/
    @RequestMapping(value = "/trip/image/delete/{id}", method = RequestMethod.POST)
    public String deleteImage(@PathVariable Integer id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        TripImage tripImage = tripImageService.getTripImageById(id);
        Trip trip = tripImage.getTrip();

        if(trip.getTraveler().equals(authenticatedUser)){
            try {
                deleteLocal(tripImage.getUrl());
            } catch (IOException e) {
                // nothing
            }
            trip.getImages().remove(tripImage);
            tripService.saveTrip(trip);
        }

        return "redirect:/trip/"+trip.getId();
    }


    @RequestMapping(value="/trip/{trip_id}/new/image")
    public String newTripImage(@PathVariable Integer trip_id, Model model){
        model.addAttribute("tripImage", new TripImage());
        model.addAttribute("tripId", trip_id);

        return "addimageform";
    }

    @PostMapping(value="trip/{trip_id}/image")
    public String handelFileUpload(@PathVariable Integer trip_id, TripImage image, @RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("body") String body , RedirectAttributes redirectAttributes)
    {
        Trip trip = tripService.getTripById(trip_id);
        trip.getImages().add(image);
        tripImageService.saveTripImage(image);

        try {
            String url = "images/"+image.getId();
            image.setUrl(url);
            image.setTitle(title);
            image.setBody(body);
            tripImageService.saveTripImage(image);
            saveLocal(url,file);

        } catch (IOException e) {
            tripImageService.deleteTripImage((int) image.getId());
            e.printStackTrace();
        }
        return "redirect:/trip/"+trip.getId();
    }


    @ResponseBody
    @RequestMapping(value = "trip/images/{id}",  produces = "image/jpg")
    public Resource getimage(@PathVariable("id") String id) {
        return resourceLoader.getResource("classpath:images/" + id + ".jpg");
    }

    private void saveLocal(String url, MultipartFile image) throws IOException
    {
        File file = new File(getAbsPath(url));
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(image.getBytes());
        fos.close();
    }

    private void deleteLocal(String url) throws IOException
    {
        File file = new File(getAbsPath(url));
        file.delete();
    }

    private String getAbsPath(String url)
    {
        URL urlAbs = context.getClassLoader().getResource("");

        // urlAbs commance par / et il est indésirable donc substring
        return urlAbs.getPath().substring(1) + url + ".jpg";
    }
}
