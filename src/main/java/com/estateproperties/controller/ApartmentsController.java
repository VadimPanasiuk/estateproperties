package com.estateproperties.controller;

import com.estateproperties.model.Apartment;
import com.estateproperties.model.User;
import com.estateproperties.service.ApartmentServiceImpl;
import com.estateproperties.service.EmailServiceImpl;
import com.estateproperties.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/buyApartment")
public class ApartmentsController {

    @Autowired
    private ApartmentServiceImpl apartmentServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailServiceImpl emailService;


    @RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
    public ModelAndView getApartmentsById(Model model, @PathVariable int id) {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        if (user == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        Apartment apartment = apartmentServiceImpl.getApartment(id);
        modelAndView.addObject("apartment", apartment);
        modelAndView.setViewName("buyApartment");
        return modelAndView;
    }

    @RequestMapping(value = { "/sendApplication/{id}" }, method = RequestMethod.POST)
    public ModelAndView buyApartmentById(ModelAndView modelAndView, @PathVariable int id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        if (user == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        Apartment apartment = apartmentServiceImpl.getApartment(id);
        modelAndView.addObject("apartment", apartment);

        emailService.sendEmail(emailService.getApplicationConfirmEmailForUser(user));
        emailService.sendEmail(emailService.getApplicationConfirmEmailForAdmin(user, apartment));

        modelAndView.addObject("confirmationMessage", "You application confirmated " + user.getEmail());
        modelAndView.setViewName("buyApartment");

        return modelAndView;
    }
}
