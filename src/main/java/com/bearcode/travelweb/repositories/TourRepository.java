package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Tour findTourById(Long id);

    Optional<Tour> getTourById(Long id);

    List<Tour> findToursByAddress(String address);
}
