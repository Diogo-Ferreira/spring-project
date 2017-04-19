package application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by diogo on 4/18/17.
 */

@Controller
public class WallController {

    /**
     * For each followed users, shows last 2-3 trips
     * @return
     */
    @RequestMapping("/wall")
    public String wall(){

        return "";
    }
}
