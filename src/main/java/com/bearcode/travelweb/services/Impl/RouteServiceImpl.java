package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.RouteDTO;
import com.bearcode.travelweb.models.Route;
import com.bearcode.travelweb.repositories.RouteRepository;
import com.bearcode.travelweb.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public String addRoute(RouteDTO routeDTO) {
        if(routeRepository.existsByRouteCode(routeDTO.getRouteCode())){
            return "Chặng bay đã tồn tại!";
        }
        Route route = new Route();
        route.setRouteCode(routeDTO.getRouteCode());
        route.setDepartureAirport(routeDTO.getDepartureAirport());
        route.setArrivalAirport(routeDTO.getArrivalAirport());
        routeRepository.save(route);
        return "Thêm chặng bay thành công!";
    }

    @Override
    public ArrayList<Route> getAllRoutes() {
        return new ArrayList<>(routeRepository.findAll());
    }

    @Override
    public RouteDTO updateRoute(Long id, RouteDTO routeDTO) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if(routeOptional.isPresent()){
            Route route = routeOptional.get();
            route.setRouteCode(routeDTO.getRouteCode());
            route.setDepartureAirport(routeDTO.getDepartureAirport());
            route.setArrivalAirport(routeDTO.getArrivalAirport());
            routeRepository.save(route);
            return new RouteDTO(route.getDepartureAirport(), route.getArrivalAirport(), route.getRouteCode());
        }
        throw new RuntimeException("Chặng bay không tồn tại với ID: " + id);
    }

    @Override
    public String deleteRoute(Long id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if(routeOptional.isPresent()){
            routeRepository.deleteById(id);
            return "Xóa chặng bay thành công!";
        }
        throw new RuntimeException("Chặng bay không tồn tại với ID: " + id);
    }

    @Override
    public Route findRouteById(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Chặng bay không tồn tại với ID: " + id));
    }

    @Override
    public List<Route> searchRoutes(String routeCode, String departure, String arrival) {
        // Tạo điều kiện tìm kiếm dựa trên các tham số
        if (routeCode != null) {
            return routeRepository.findByRouteCodeContainingAndDepartureAirportContainingAndArrivalAirportContaining(
                    routeCode, departure, arrival);
        }
        return routeRepository.findAll();
    }

}
