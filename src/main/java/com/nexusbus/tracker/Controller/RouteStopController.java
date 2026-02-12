package com.nexusbus.tracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbus.tracker.Entities.RouteStop;
import com.nexusbus.tracker.Repository.RouteStopRepo;


@RestController
public class RouteStopController {

    private final RouteStopRepo routeStopRepo;

    public RouteStopController(RouteStopRepo routeStopRepo){
        this.routeStopRepo=routeStopRepo;
    }

    @GetMapping("/route/stop")
    public ResponseEntity<List<RouteStop>> getAllRouteStop() {
        return ResponseEntity.ok(routeStopRepo.findAll());
    }

    @PostMapping("/route/stop")
    public ResponseEntity<?> postRouteStop(@RequestBody RouteStop routeStop) {
        try {
            RouteStop routeStop1=routeStopRepo.save(routeStop);
            return ResponseEntity.ok(routeStop1);
        }catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }        
    }
    
}
