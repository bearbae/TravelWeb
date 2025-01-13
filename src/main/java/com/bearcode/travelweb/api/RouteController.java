package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.RouteDTO;
import com.bearcode.travelweb.models.Route;
import com.bearcode.travelweb.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/admin/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<String> addRoute(@RequestBody RouteDTO routeDTO){
        String response = routeService.addRoute(routeDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ArrayList<Route> getAllRoutes(){
        return routeService.getAllRoutes();
    }

    @PutMapping("/update/{id}")
    public RouteDTO updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO){
        return routeService.updateRoute(id, routeDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id){
        return routeService.deleteRoute(id);
    }

    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable Long id){
        return routeService.findRouteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Route>> searchRoutes(
            @RequestParam(required = false) String routeCode,
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String arrival) {

        List<Route> routes = routeService.searchRoutes(routeCode, departure, arrival);
        return ResponseEntity.ok(routes);
    }

}

