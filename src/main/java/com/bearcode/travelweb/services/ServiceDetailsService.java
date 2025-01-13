package com.bearcode.travelweb.services;


import com.bearcode.travelweb.DTO.ServiceDetailsDTO;
import com.bearcode.travelweb.models.ServiceDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ServiceDetailsService {
    ResponseEntity<ServiceDetails> addNewService(Long idTour, ServiceDetailsDTO serviceDetailsDTO);

    ServiceDetails getServiceDetailByCode(String code, Long Idtour);

    List<ServiceDetails> getByIdTour(Long idTour);

    ServiceDetails updateService(Long id, ServiceDetailsDTO serviceDetailsDTO);

    String deleteService(Long id) ;

    ServiceDetails getById(Long id);

}
