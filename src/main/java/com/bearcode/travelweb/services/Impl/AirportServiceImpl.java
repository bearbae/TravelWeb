package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.AirportDTO;
import com.bearcode.travelweb.mappers.AirportMapper;
import com.bearcode.travelweb.models.Airport;
import com.bearcode.travelweb.repositories.AirportRepository;
import com.bearcode.travelweb.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportMapper airportMapper; // Sử dụng AirportMapper

    @Override
    public String addAirport(AirportDTO airportDTO) {
        if (airportRepository.existsByName(airportDTO.getName())) {
            return "Sân bay đã tồn tại!";
        }
        Airport airport = airportMapper.convertToEntity(airportDTO); // Chuyển đổi sang entity
        airportRepository.save(airport);
        return "Thêm sân bay thành công!";
    }

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @Override
    public AirportDTO updateAirport(Long id, AirportDTO airportDTO) {
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.setName(airportDTO.getName());
            airportRepository.save(airport);
            return airportMapper.convertToDTO(airport); // Chuyển đổi sang DTO
        }
        throw new RuntimeException("Sân bay không tồn tại với ID: " + id);
    }

    @Override
    public String deleteAirport(Long id) {
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if (airportOptional.isPresent()) {
            airportRepository.deleteById(id);
            return "Xóa sân bay thành công!";
        }
        throw new RuntimeException("Sân bay không tồn tại với ID: " + id);
    }

    @Override
    public Airport findAirportById(Long id) {
        return airportRepository.findById(id).orElseThrow(() -> new RuntimeException("Sân bay không tồn tại với ID: " + id));
    }

    @Override
    public List<Airport> searchAirportsByName(String name) {
        return airportRepository.findByNameContainingIgnoreCase(name); // Sử dụng phương thức tìm kiếm trong repository
    }
}