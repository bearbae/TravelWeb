package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.ServiceDetailsDTO;
import com.bearcode.travelweb.models.ServiceDetails;
import com.bearcode.travelweb.repositories.ServiceDetailsRepository;
import com.bearcode.travelweb.services.ServiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/serviceTour")
@RestController
public class ServiceDetailsController {

    @Autowired
    private ServiceDetailsService serviceDetailsService;


    @PostMapping("/add/{id_tour}")
    public ResponseEntity<ServiceDetails> addNew(@PathVariable Long id_tour, @RequestBody ServiceDetailsDTO serviceDetailsDTO){
        return serviceDetailsService.addNewService(id_tour, serviceDetailsDTO);
    }

    @GetMapping("/{code}/{idTour}")
    public ServiceDetails getByCode(@PathVariable String code, @PathVariable Long idTour) {
        return serviceDetailsService.getServiceDetailByCode(code, idTour);
    }

    @GetMapping("/getByIdTour/{idTour}")
    public List<ServiceDetails> getByIdTour(@PathVariable Long idTour){
        return serviceDetailsService.getByIdTour(idTour);
    }
    @PutMapping("/update/{id}")
    public ServiceDetails updateService(@PathVariable Long id, @RequestBody ServiceDetailsDTO serviceDetailsDTO){
        return serviceDetailsService.updateService(id, serviceDetailsDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteService(@PathVariable Long id){
        return serviceDetailsService.deleteService(id);
    }

    @GetMapping("/idService/{id}")
    public ServiceDetails getByid(@PathVariable Long id){
        return serviceDetailsService.getById(id);
    }

}
