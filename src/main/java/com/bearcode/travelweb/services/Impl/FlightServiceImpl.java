package com.bearcode.travelweb.services.Impl;


import com.bearcode.travelweb.DTO.FlightDTO;
import com.bearcode.travelweb.DTO.RoundTripFlightDTO;
import com.bearcode.travelweb.api.FlightSpecification;
import com.bearcode.travelweb.mappers.FlightMapper;
import com.bearcode.travelweb.models.Flight;
import com.bearcode.travelweb.repositories.FlightRepository;
import com.bearcode.travelweb.services.FlightService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FlightServiceImpl implements FlightService {
    @Autowired
    private  FlightRepository flightRepository;
    @Autowired
    private  FlightMapper flightMapper;

    @Override
    public ArrayList<RoundTripFlightDTO> searchAllRoundTripFlights() {
        ArrayList<RoundTripFlightDTO> roundTripFlights = new ArrayList<>();

        // Lấy tất cả chuyến bay trong cơ sở dữ liệu
        List<Flight> flights = flightRepository.findAll();

        for (Flight departureFlight : flights) {
            List<Flight> returnFlights = flightRepository.findAll(Specification
                    .where(FlightSpecification.hasDepartureCity(departureFlight.getArrivalCity()))
                    .and(FlightSpecification.hasArrivalCity(departureFlight.getDepartureCity()))
                    .and(FlightSpecification.hasDepartureDateAfter(departureFlight.getArrivalDate().plusDays(1)))
            );

            for (Flight returnFlight : returnFlights) {
                // Kiểm tra số ghế còn lại trên cả hai chuyến bay
                if (departureFlight.getAvailableSeats() > 0 && returnFlight.getAvailableSeats() > 0) {
                    RoundTripFlightDTO dto = new RoundTripFlightDTO(
                            departureFlight.getFlightNumber(),
                            returnFlight.getFlightNumber(),
                            departureFlight.getDepartureCity(),
                            departureFlight.getArrivalCity(),
                            departureFlight.getDepartureDate(),
                            returnFlight.getDepartureDate(),
                            departureFlight.getDepartureTime(),
                            returnFlight.getDepartureTime(),
                            departureFlight.getPrice().add(returnFlight.getPrice()),
                            departureFlight.getAvailableSeats(), // Số ghế còn lại cho chuyến đi
                            returnFlight.getAvailableSeats()     // Số ghế còn lại cho chuyến về
                    );
                    roundTripFlights.add(dto);
                }
            }
        }

        return roundTripFlights;
    }


    @Override
    public String addFlight(FlightDTO flightDTO) {
        if(flightRepository.existsFlightByFlightNumber(flightDTO.getFlightNumber())){
            return "Chuyến bay đã tồn tại";
        }
        Flight flight = flightMapper.convertToEntity(flightDTO);
        System.out.println(flight);
        flightRepository.save(flight) ;
        return "Thêm chuyến bay thành công!";
    }

    // Lấy danh sách chuyến bay
    @Override
    public ArrayList<Flight> getAllFlight() {
        return new ArrayList<>(flightRepository.findAll());
    }


    @Override
    public ArrayList<Flight> findAllFlightBySearch(String departureCity, String arrivalCity, LocalDate departureDate) {
        Specification<Flight> spec = Specification.where(departureCity != null && !departureCity.isEmpty() ?
                        FlightSpecification.hasDepartureCity(departureCity) : null)
                .and(arrivalCity != null && !arrivalCity.isEmpty() ?
                        FlightSpecification.hasArrivalCity(arrivalCity) : null)
                .and(departureDate != null ?
                        FlightSpecification.hasDepartureDate(departureDate) : null);

        return new ArrayList<>(flightRepository.findAll(spec));
    }


    @Override
    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Optional<Flight> flightOptional = flightRepository.getFlightById(id);
        if(flightOptional.isPresent()){
            Flight flight = flightOptional.get();
            flight.setFlightNumber(flightDTO.getFlightNumber());
            flight.setDepartureCity(flightDTO.getDepartureCity());
            flight.setArrivalCity(flightDTO.getArrivalCity());
            flight.setDepartureDate(flightDTO.getDepartureDate());
            flight.setArrivalDate(flightDTO.getArrivalDate());
            flight.setDepartureTime(flightDTO.getDepartureTime());
            flight.setArrivalTime(flightDTO.getArrivalTime());
            flight.setPrice(flightDTO.getPrice());
            flight.setAvailableSeats(flightDTO.getAvailableSeats());
            flightRepository.save(flight);
            return flightMapper.convertToDTO(flight);
        }
        throw new EntityNotFoundException("Chuyến bay không tồn tại với ID: "+ id);
    }

    @Override
    public String deleteFlight(Long id) {
        Optional<Flight> flightOptional = flightRepository.getFlightById(id);
        if(flightOptional.isPresent()){
            flightRepository.deleteById(id);
            return "Xóa thành công";
        }
        throw new EntityNotFoundException("Chuyến bay không tồn tại với Id: "+ id);
    }

    @Override
    public Flight findFlightById(Long id) {
        return flightRepository.findFlightById(id);
    }

    @Override
    public ArrayList<RoundTripFlightDTO> searchRoundTripFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate) {
        ArrayList<RoundTripFlightDTO> roundTripFlights = new ArrayList<>();

        // Tìm kiếm các chuyến bay chiều đi
        List<Flight> departureFlights = flightRepository.findAll(Specification
                .where(FlightSpecification.hasDepartureCity(departureCity))
                .and(FlightSpecification.hasArrivalCity(arrivalCity))
                .and(FlightSpecification.hasDepartureDate(departureDate))
        );

        for (Flight departureFlight : departureFlights) {
            // Tìm kiếm các chuyến bay chiều về phù hợp
            List<Flight> returnFlights = flightRepository.findAll(Specification
                    .where(FlightSpecification.hasDepartureCity(arrivalCity))
                    .and(FlightSpecification.hasArrivalCity(departureCity))
                    .and(FlightSpecification.hasDepartureDate(returnDate))
            );

            for (Flight returnFlight : returnFlights) {
                // Kiểm tra số ghế còn lại
                if (departureFlight.getAvailableSeats() > 0 && returnFlight.getAvailableSeats() > 0) {
                    RoundTripFlightDTO dto = new RoundTripFlightDTO(
                            departureFlight.getFlightNumber(),
                            returnFlight.getFlightNumber(),
                            departureFlight.getDepartureCity(),
                            departureFlight.getArrivalCity(),
                            departureFlight.getDepartureDate(),
                            returnFlight.getDepartureDate(),
                            departureFlight.getDepartureTime(),
                            returnFlight.getDepartureTime(),
                            departureFlight.getPrice().add(returnFlight.getPrice()),
                            departureFlight.getAvailableSeats(),
                            returnFlight.getAvailableSeats()
                    );
                    roundTripFlights.add(dto);
                }
            }
        }

        return roundTripFlights;
    }


}
