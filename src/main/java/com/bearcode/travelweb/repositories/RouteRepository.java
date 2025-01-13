package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findById(Long id);
    Boolean existsByRouteCode(String routeCode); // Kiểm tra mã chặng bay đã tồn tại

    List<Route> findByRouteCodeContainingAndDepartureAirportContainingAndArrivalAirportContaining(
            String routeCode, String departureAirport, String arrivalAirport);


}
