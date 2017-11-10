package com.estateproperties.controller;

import com.estateproperties.message.Response;
import com.estateproperties.model.Apartment;
import com.estateproperties.service.ApartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apartment")
public class RestApartmentController {

    @Autowired
    ApartmentServiceImpl apartmentServiceImpl;

    @GetMapping(value = "/all")
    public Response getResource(@RequestParam String sort, @RequestParam String pricefilter) {
        return new Response("Done", apartmentServiceImpl.getApartments(sort, pricefilter));
    }
}
