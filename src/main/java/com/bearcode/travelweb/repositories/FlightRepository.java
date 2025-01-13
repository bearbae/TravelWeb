package com.bearcode.travelweb.repositories;


import com.bearcode.travelweb.models.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
    Optional<Flight> getFlightById(Long id);

    Flight findFlightById(Long id);

    Boolean existsFlightByFlightNumber(String flightNumber);

    ArrayList<Flight> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);
}
