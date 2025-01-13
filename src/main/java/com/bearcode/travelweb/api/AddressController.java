package com.bearcode.travelweb.api;


import com.bearcode.travelweb.models.Address;
import com.bearcode.travelweb.models.Room;
import com.bearcode.travelweb.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/admin/address")
public class AddressController {
    private final String UPLOAD_DIR = "src/main/resources/static/uploadsAddress/";

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addAddress(@RequestParam("nameAddress") String name,
                                             @RequestParam("img")MultipartFile mul) {
        if(mul.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Khoong duoc de anh trong");
        } else {
            try{
                String fileName = StringUtils.cleanPath(mul.getOriginalFilename());
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);

                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                Files.copy(mul.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                Address address = new Address();
                address.setNameAddress(name);
                address.setImg(fileName);
                addressRepository.save(address);
                return ResponseEntity.status(HttpStatus.OK).body("Thêm dia diem thành công với id= "+ address.getId());
            } catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi: " + e.getMessage());
            }
        }
    }

    @GetMapping("/getAll")
    public List<Address> getAll(){
        return addressRepository.findAll();
    }
}
