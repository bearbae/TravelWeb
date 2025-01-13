package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.RestaurantBookingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantBookingTableRepository extends JpaRepository<RestaurantBookingTable, Long> {
}
