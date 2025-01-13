package com.bearcode.travelweb.api;

import com.bearcode.travelweb.models.ImageOfTour;
import com.bearcode.travelweb.models.Room;
import com.bearcode.travelweb.repositories.ImageOfTourRepository;
import com.bearcode.travelweb.services.ImageOfTourService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/admin/image")
@RestController
public class ImageOfTourController {
    @Autowired
    private ImageOfTourService imageOfTourService;
    @Autowired
    private ImageOfTourRepository imageOfTourRepository;

    @PostMapping("/add/{tourId}")
    public ResponseEntity<String> addImage(@PathVariable Long tourId,@RequestParam("img1")MultipartFile mul1,
                                           @RequestParam("img2")MultipartFile mul2,
                                           @RequestParam("img3")MultipartFile mul3,
                                           @RequestParam("img4")MultipartFile mul4,
                                           @RequestParam("img5")MultipartFile mul5) {
        return imageOfTourService.addImage(tourId,mul1, mul2, mul3, mul4, mul5);
    }

    @PutMapping("/update/{id}")
    public ImageOfTour updateImage(@PathVariable Long id,@RequestParam("img1")MultipartFile mul1,
                                @RequestParam("img2")MultipartFile mul2,
                                @RequestParam("img3")MultipartFile mul3,
                                @RequestParam("img4")MultipartFile mul4,
                                @RequestParam("img5")MultipartFile mul5) {
        return imageOfTourService.updateImage(id, mul1, mul2, mul3, mul4, mul5);
    }

    @GetMapping("/getAll")
    public List<ImageOfTour> getAllImgTour(){
        return imageOfTourRepository.findAll();
    }

    @GetMapping("/idTour/{idTour}")
    public ImageOfTour getByIdTour(@PathVariable Long idTour){
        return imageOfTourService.getImageByIdtour(idTour);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteImage(@PathVariable Long id) {
        return imageOfTourService.deleteImage(id);
    }

    @GetMapping("/{id}")
    public ImageOfTour getById(@PathVariable Long id) { return imageOfTourService.getImageById(id);}

}
