package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails, Long> {
    boolean existsByCode(String code);
    ServiceDetails getByCodeAndTour_Id(String code, Long idTour);

    List<ServiceDetails> getByTour_Id(Long idTour);

    Optional<ServiceDetails> findServiceDetailsById(Long id) ;
    ServiceDetails getServiceDetailsById(Long id) ;
}
