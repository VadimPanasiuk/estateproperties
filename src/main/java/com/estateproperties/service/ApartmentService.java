package com.estateproperties.service;

import com.estateproperties.model.Apartment;

import java.util.List;

public interface ApartmentService {
    public List<Apartment> getApartments(String sort, String pricefilter);
    public Apartment getApartment(int id);
}
