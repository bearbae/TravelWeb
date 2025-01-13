package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.TourDTO;
import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.repositories.ImageOfTourRepository;
import com.bearcode.travelweb.repositories.TourRepository;
import com.bearcode.travelweb.services.TourService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;
    @Override
    public ResponseEntity<Tour> addTour(TourDTO tourDTO) {
        Tour tour = new Tour();
        tour.setTitleTour(tourDTO.getTitleTour());
        tour.setAddress(tourDTO.getAddress());
        tour.setTypeTour(tourDTO.getTypeTour());
        tour.setRateStar(tourDTO.getRateStar());
        tour.setQuantityComment(tourDTO.getQuantityComment());
        tour.setBooked(tourDTO.getBooked());
        tour.setPrice(tourDTO.getPrice());
        tour.setHighlight(tourDTO.getHighlight());
        tour.setContent(tourDTO.getContent());
        tourRepository.save(tour);
        return ResponseEntity.ok(tour);
    }

    // xoa tour
    @Override
    public String deleteTour(Long idTour) {
            Optional<Tour> tourOptional = tourRepository.getTourById(idTour);
            if(tourOptional.isPresent()){
                tourRepository.deleteById(idTour);
                return "Xóa Thành công!";
            }
            throw new EntityNotFoundException("Không tồn tại tour với id: "+ idTour);
    }


    // cập nhật tour
    @Override
    public Tour updateTour(Long idTour, TourDTO tourDTO) {
        Optional<Tour> tourOptional = tourRepository.getTourById(idTour);
        Tour tour = tourOptional.get();
        if(!tourOptional.isPresent()){
            throw new EntityNotFoundException("Không tồn tại tour với id: "+ idTour);
        } else {
            tour.setTitleTour(tourDTO.getTitleTour());
            tour.setAddress(tourDTO.getAddress());
            tour.setTypeTour(tourDTO.getTypeTour());
            tour.setRateStar(tourDTO.getRateStar());
            tour.setQuantityComment(tourDTO.getQuantityComment());
            tour.setBooked(tourDTO.getBooked());
            tour.setPrice(tourDTO.getPrice());
            tour.setHighlight(tourDTO.getHighlight());
            tour.setContent(tourDTO.getContent());
            tourRepository.save(tour);
        }
        return tour;
    }


}
