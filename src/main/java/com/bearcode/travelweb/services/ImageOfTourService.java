package com.bearcode.travelweb.services;

import com.bearcode.travelweb.models.ImageOfTour;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageOfTourService {

    ResponseEntity<String> addImage(Long tourId, MultipartFile mul1, MultipartFile mul2,
                                    MultipartFile mul3, MultipartFile mul4,
                                    MultipartFile mul5);

    // Sửa anh
    ImageOfTour updateImage(Long id, MultipartFile mul1, MultipartFile mul2, MultipartFile mul3,
                            MultipartFile mul4, MultipartFile mul5 );

    // get All
    ImageOfTour getImageByIdtour(Long idTour);
    String deleteImage(Long id) ;
    // get by id
    ImageOfTour getImageById(Long id) ;
}
