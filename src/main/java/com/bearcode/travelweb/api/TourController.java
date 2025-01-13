package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.TourDTO;
import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.repositories.TourRepository;
import com.bearcode.travelweb.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tour")
public class TourController {

    @Autowired
    private TourService tourService;
    @Autowired
    private TourRepository tourRepository;

    @PostMapping("/add")
    public ResponseEntity<Tour> addTour(@RequestBody TourDTO tourDTO){
        return tourService.addTour(tourDTO) ;
    }

    @GetMapping("/getAll")
    public List<Tour> getAllTour(){
        return tourRepository.findAll();
    }

    @GetMapping("/getTour/{id}")
    public Tour getTourById(@PathVariable Long id){
        return tourRepository.findTourById(id);
    }
    @PutMapping("/update/{idTour}")
    public Tour updateTour(@PathVariable Long idTour, @RequestBody TourDTO tourDTO){
        return tourService.updateTour(idTour,tourDTO);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id){
        return tourService.deleteTour(id);
    }

    @GetMapping("/getTourByAddress/{address}")
    public List<Tour> getToursByAddress(@PathVariable String address){
        return tourRepository.findToursByAddress(address) ;
    }
}
