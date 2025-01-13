package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    Boolean existsByName(String name);

    Boolean existsByLocation(String location);

    Optional<Hotel> getHotelById(Long id);

    Hotel findHotelById(Long id);

    ArrayList<Hotel> findByLocationContainingIgnoreCase(String location);

    List<Hotel> getHotelsByAddress(String address) ;
}
