package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.ServiceDetailsDTO;
import com.bearcode.travelweb.models.ServiceDetails;
import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.repositories.ServiceDetailsRepository;
import com.bearcode.travelweb.repositories.TourRepository;
import com.bearcode.travelweb.services.ServiceDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDetailServiceImpl implements ServiceDetailsService {
    @Autowired
    private ServiceDetailsRepository serviceDetailsRepository;
    @Autowired
    private TourRepository tourRepository;
    @Override
    public ResponseEntity<ServiceDetails> addNewService(Long id_tour,ServiceDetailsDTO serviceDetailsDTO) {
//        if(serviceDetailsRepository.existsByCode(serviceDetailsDTO.getCode())){
//            throw new Error("Dich vu nay da ton tai");
//        } else{
            ServiceDetails serviceDetails = new ServiceDetails();
            serviceDetails.setTimeToGo(serviceDetailsDTO.getTimeToGo());
            serviceDetails.setTimeToBack(serviceDetailsDTO.getTimeToBack());
            serviceDetails.setTime1(serviceDetailsDTO.getTime1());
            serviceDetails.setTime2(serviceDetailsDTO.getTime2());
            serviceDetails.setTime3(serviceDetailsDTO.getTime3());
            serviceDetails.setAddress1(serviceDetailsDTO.getAddress1());
            serviceDetails.setAddress2(serviceDetailsDTO.getAddress2());
            serviceDetails.setAddress3(serviceDetailsDTO.getAddress3());
            serviceDetails.setCode(serviceDetailsDTO.getCode());
            serviceDetails.setVehicle(serviceDetailsDTO.getVehicle());
            serviceDetails.setPricePerAdult(serviceDetailsDTO.getPricePerAdult());
            serviceDetails.setPricePerChild(serviceDetailsDTO.getPricePerChild());
            Tour tour = tourRepository.findTourById(id_tour);
            serviceDetails.setTour(tour);
            serviceDetailsRepository.save(serviceDetails);
            return ResponseEntity.ok(serviceDetails);
//        }
    }

    @Override
    public ServiceDetails getServiceDetailByCode(String code, Long idtour) {
        return serviceDetailsRepository.getByCodeAndTour_Id(code, idtour);
    }

    @Override
    public List<ServiceDetails> getByIdTour(Long idTour) {
        return serviceDetailsRepository.getByTour_Id(idTour);
    }

    @Override
    public ServiceDetails updateService(Long id, ServiceDetailsDTO serviceDetailsDTO) {
        Optional<ServiceDetails> serviceDetailsOptional = serviceDetailsRepository.findServiceDetailsById(id);
        if(!serviceDetailsOptional.isPresent()){
            throw new EntityNotFoundException("Khong tim thay dich vu");
        } else {
            ServiceDetails serviceDetails = serviceDetailsOptional.get() ;
            serviceDetails.setTimeToGo(serviceDetailsDTO.getTimeToGo());
            serviceDetails.setTimeToBack(serviceDetailsDTO.getTimeToBack());
            serviceDetails.setTime1(serviceDetailsDTO.getTime1());
            serviceDetails.setTime2(serviceDetailsDTO.getTime2());
            serviceDetails.setTime3(serviceDetailsDTO.getTime3());
            serviceDetails.setAddress1(serviceDetailsDTO.getAddress1());
            serviceDetails.setAddress2(serviceDetailsDTO.getAddress2());
            serviceDetails.setAddress3(serviceDetailsDTO.getAddress3());
            serviceDetails.setCode(serviceDetailsDTO.getCode());
            serviceDetails.setVehicle(serviceDetailsDTO.getVehicle());
            serviceDetails.setPricePerAdult(serviceDetailsDTO.getPricePerAdult());
            serviceDetails.setPricePerChild(serviceDetailsDTO.getPricePerChild());
            serviceDetailsRepository.save(serviceDetails);
            return serviceDetails ;
        }
    }

    @Override
    public String deleteService(Long id) {
        Optional<ServiceDetails> serviceDetailsOptional = serviceDetailsRepository.findServiceDetailsById(id);
        if (!serviceDetailsOptional.isPresent()) {
            throw new EntityNotFoundException("Khong tim thay dich vu");
        } else {
            serviceDetailsRepository.deleteById(id);
            return "Xoa dich vu thanh cong";
        }
    }

    @Override
    public ServiceDetails getById(Long id) {
        return serviceDetailsRepository.getServiceDetailsById(id);
    }
}
