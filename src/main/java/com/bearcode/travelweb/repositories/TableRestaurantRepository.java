package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.TableRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface TableRestaurantRepository extends JpaRepository<TableRestaurant, Long> {
    Boolean existsByTableRestaurantNameAndAreaAndRestaurantId(String tableRestaurantName, int area, Long restaurantId);
    Optional<TableRestaurant> getTableRestaurantById(Long id);
    TableRestaurant findTableRestaurantById(Long id);
    ArrayList<TableRestaurant> getAllByRestaurant_Id(Long restaurantId);
}
