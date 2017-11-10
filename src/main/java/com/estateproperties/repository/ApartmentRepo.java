package com.estateproperties.repository;

import com.estateproperties.model.Apartment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("apartmentRepository")
public interface ApartmentRepo extends CrudRepository<Apartment, Integer> {
}
