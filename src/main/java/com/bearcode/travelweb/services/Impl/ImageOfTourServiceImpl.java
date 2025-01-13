package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.models.ImageOfTour;
import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.repositories.ImageOfTourRepository;
import com.bearcode.travelweb.repositories.TourRepository;
import com.bearcode.travelweb.services.ImageOfTourService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ImageOfTourServiceImpl implements ImageOfTourService {
    private final String UPLOAD_DIR = "src/main/resources/static/uploadsTour/";

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private ImageOfTourRepository imageOfTourRepository;
    @Override
    public ResponseEntity<String> addImage(Long tourId, MultipartFile mul1, MultipartFile mul2,
                                           MultipartFile mul3, MultipartFile mul4,
                                           MultipartFile mul5) {
        if (mul1.isEmpty() || mul2.isEmpty() || mul3.isEmpty() ||
        mul4.isEmpty() || mul5.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File ảnh không được để trống");
        }
        try {
            String fileName1 = StringUtils.cleanPath(mul1.getOriginalFilename());
            String fileName2 = StringUtils.cleanPath(mul2.getOriginalFilename());
            String fileName3 = StringUtils.cleanPath(mul3.getOriginalFilename());
            String fileName4 = StringUtils.cleanPath(mul4.getOriginalFilename());
            String fileName5 = StringUtils.cleanPath(mul5.getOriginalFilename());


            Path uploadPath1 = Paths.get(UPLOAD_DIR + fileName1);
            Path uploadPath2 = Paths.get(UPLOAD_DIR + fileName2);
            Path uploadPath3 = Paths.get(UPLOAD_DIR + fileName3);
            Path uploadPath4 = Paths.get(UPLOAD_DIR + fileName4);
            Path uploadPath5 = Paths.get(UPLOAD_DIR + fileName5);

            if (!Files.exists(uploadPath1.getParent())) {
                Files.createDirectories(uploadPath1.getParent());
            }
            if (!Files.exists(uploadPath2.getParent())) {
                Files.createDirectories(uploadPath2.getParent());
            }
            if (!Files.exists(uploadPath3.getParent())) {
                Files.createDirectories(uploadPath3.getParent());
            }
            if (!Files.exists(uploadPath4.getParent())) {
                Files.createDirectories(uploadPath3.getParent());
            }
            if (!Files.exists(uploadPath5.getParent())) {
                Files.createDirectories(uploadPath3.getParent());
            }

            // Lưu ảnh vào thư mục
            Files.copy(mul1.getInputStream(), uploadPath1, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(mul2.getInputStream(), uploadPath2, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(mul3.getInputStream(), uploadPath3, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(mul4.getInputStream(), uploadPath4, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(mul5.getInputStream(), uploadPath5, StandardCopyOption.REPLACE_EXISTING);

            Tour tour = tourRepository.findTourById(tourId);
            // Tạo mới anh
            ImageOfTour imgs = new ImageOfTour();
            imgs.setImg1(fileName1);
            imgs.setImg2(fileName2);
            imgs.setImg3(fileName3);
            imgs.setImg4(fileName4);
            imgs.setImg5(fileName5);
            imgs.setTour(tour);
            tour.setImageOfTour(imgs);
            imageOfTourRepository.save(imgs);
            tourRepository.save(tour);
            return ResponseEntity.ok("Anh đã được tạo thành công với ID: " + imgs.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi upload file: " + e.getMessage());
        }
    }

    @Override
    public ImageOfTour updateImage(Long id, MultipartFile mul1, MultipartFile mul2,
                                   MultipartFile mul3, MultipartFile mul4, MultipartFile mul5) {
        Optional<ImageOfTour> imgOptional = imageOfTourRepository.getImageOfTourById(id);
        if(!imgOptional.isPresent()){
            throw new EntityNotFoundException("Không tồn tại imgs với id: " + id);
        } else {
            if (mul1.isEmpty() || mul2.isEmpty() || mul3.isEmpty() ||
                    mul4.isEmpty() || mul5.isEmpty()) {
                throw new Error("Khong duoc de anh trong");
            }
            try {
                String fileName1 = StringUtils.cleanPath(mul1.getOriginalFilename());
                String fileName2 = StringUtils.cleanPath(mul2.getOriginalFilename());
                String fileName3 = StringUtils.cleanPath(mul3.getOriginalFilename());
                String fileName4 = StringUtils.cleanPath(mul4.getOriginalFilename());
                String fileName5 = StringUtils.cleanPath(mul5.getOriginalFilename());


                Path uploadPath1 = Paths.get(UPLOAD_DIR + fileName1);
                Path uploadPath2 = Paths.get(UPLOAD_DIR + fileName2);
                Path uploadPath3 = Paths.get(UPLOAD_DIR + fileName3);
                Path uploadPath4 = Paths.get(UPLOAD_DIR + fileName4);
                Path uploadPath5 = Paths.get(UPLOAD_DIR + fileName5);

                if (!Files.exists(uploadPath1.getParent())) {
                    Files.createDirectories(uploadPath1.getParent());
                }
                if (!Files.exists(uploadPath2.getParent())) {
                    Files.createDirectories(uploadPath2.getParent());
                }
                if (!Files.exists(uploadPath3.getParent())) {
                    Files.createDirectories(uploadPath3.getParent());
                }
                if (!Files.exists(uploadPath4.getParent())) {
                    Files.createDirectories(uploadPath3.getParent());
                }
                if (!Files.exists(uploadPath5.getParent())) {
                    Files.createDirectories(uploadPath3.getParent());
                }

                // Lưu ảnh vào thư mục
                Files.copy(mul1.getInputStream(), uploadPath1, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(mul2.getInputStream(), uploadPath2, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(mul3.getInputStream(), uploadPath3, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(mul4.getInputStream(), uploadPath4, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(mul5.getInputStream(), uploadPath5, StandardCopyOption.REPLACE_EXISTING);

                ImageOfTour imgs = imgOptional.get();
                imgs.setImg1(fileName1);
                imgs.setImg2(fileName2);
                imgs.setImg3(fileName3);
                imgs.setImg4(fileName4);
                imgs.setImg5(fileName5);
                imageOfTourRepository.save(imgs);
                return imgs;
            } catch (IOException e) {
                throw new Error("Khong cap nhat duco anh");
            }
        }
    }

    @Override
    public ImageOfTour getImageByIdtour(Long idTour) {
        return imageOfTourRepository.findImageOfTourByTour_Id(idTour);
    }

    @Override
    public String deleteImage(Long id) {
        Optional<ImageOfTour> imageOfTourOptional = imageOfTourRepository.getImageOfTourById(id);
        if(imageOfTourOptional.isPresent()){
            imageOfTourRepository.deleteById(id);
            return "Xóa thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại ảnh với id: "+ id);
    }

    @Override
    public ImageOfTour getImageById(Long id) {
        return imageOfTourRepository.findImageOfTourById(id);
    }
}
