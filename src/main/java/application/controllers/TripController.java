package application.controllers;

import application.entities.TripEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import application.services.TripService;

/**
 * Created by diogo on 3/17/17.
 */
@Controller
public class TripController {

    private TripService tripService;

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * List all products.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/trips", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("trips", tripService.all());
        System.out.println("Returning trips:");
        return "trips";
    }

    /**
     * View a specific product by its id.
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("trip/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", tripService.getTripById(id));
        return "tripshow";
    }

    // Afficher le formulaire de modification du Product
    @RequestMapping("trip/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("trip", tripService.getTripById(id));
        return "tripform";
    }

    @RequestMapping(value = "/application/controllers", method = RequestMethod.GET)
    public String test(){
        return "application/controllers";
    }

    /**
     * New product.
     *
     * @param model
     * @return
     */
    @RequestMapping("trip/new")
    public String newProduct(Model model) {
        model.addAttribute("trip", new TripEntity());
        return "tripform";
    }

    /**
     * Save product to database.
     *
     * @param product
     * @return
     */
    @RequestMapping(value = "trip", method = RequestMethod.POST)
    public String saveProduct(TripEntity product) {
        tripService.saveTrip(product);
        return "redirect:/trip/" + product.getId();
    }

    /**
     * Delete product by its id.
     *
     * @param id
     * @return
     */
    @RequestMapping("trip/delete/{id}")
    public String delete(@PathVariable Integer id) {
        tripService.deleteProduct(id);
        return "redirect:/trips";
    }
}
