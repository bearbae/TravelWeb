package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByName(String name);
    List<Airport> findByNameContainingIgnoreCase(String name); // Tìm kiếm tên sân bay
}