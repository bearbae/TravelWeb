package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.RouteDTO;
import com.bearcode.travelweb.models.Route;

import java.util.ArrayList;
import java.util.List;

public interface RouteService {
    String addRoute(RouteDTO routeDTO);
    ArrayList<Route> getAllRoutes();
    RouteDTO updateRoute(Long id, RouteDTO routeDTO);
    String deleteRoute(Long id);
    Route findRouteById(Long id);

    List<Route> searchRoutes(String routeCode, String departure, String arrival);
}
