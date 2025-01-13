package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {
    Boolean existsByName(String name);

    Boolean existsByLocation(String location);

    Optional<Restaurant> getRestaurantById(Long id);

    Restaurant findRestaurantById(Long id);

    ArrayList<Restaurant> findByLocationContainingIgnoreCase(String location);
    List<Restaurant> getRestaurantsByLocation(String location) ;
}
