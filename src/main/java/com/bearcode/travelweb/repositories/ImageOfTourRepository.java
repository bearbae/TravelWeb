package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.ImageOfTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageOfTourRepository extends JpaRepository<ImageOfTour, Long> {

    Optional<ImageOfTour> getImageOfTourById(Long id);

    ImageOfTour findImageOfTourByTour_Id(Long id);

    ImageOfTour findImageOfTourById(Long id) ;
}
