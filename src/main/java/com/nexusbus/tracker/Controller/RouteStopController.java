package com.nexusbus.tracker.Controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbus.tracker.Entities.BusStop;
import com.nexusbus.tracker.Entities.Route;
import com.nexusbus.tracker.Entities.RouteStop;
import com.nexusbus.tracker.Repository.BusStopRepo;
import com.nexusbus.tracker.Repository.RouteRepo;
import com.nexusbus.tracker.Repository.RouteStopRepo;


@RestController
public class RouteStopController {

    private final RouteRepo routeRepo;
    private final BusStopRepo busStopRepo;
    private final RouteStopRepo routeStopRepo;

    public RouteStopController(RouteRepo routeRepo,BusStopRepo busStopRepo,RouteStopRepo routeStopRepo){
        this.routeRepo=routeRepo;
        this.busStopRepo=busStopRepo;
        this.routeStopRepo=routeStopRepo;
    }

    @GetMapping("/route/stop")
    public ResponseEntity<List<RouteStop>> getAllRouteStop() {
        return ResponseEntity.ok(routeStopRepo.findAll());
    }

    @PostMapping("/route/{routeId}/addStop/{stopId}/{sequence}")
    public ResponseEntity<?> addOneStop(@PathVariable Integer routeId, @PathVariable Integer stopId,@PathVariable Integer sequence){

        if(routeStopRepo.existsByRoute_RouteIdAndBusStop_StopId(routeId, stopId)){
            return ResponseEntity.badRequest().body("duplicatee");
        }else{
            Optional<Route> routeOpt =routeRepo.findById(routeId);
            Optional<BusStop> busStopOpt=busStopRepo.findById(stopId);
            if(routeOpt.isEmpty() || busStopOpt.isEmpty()){
                return ResponseEntity.badRequest().body("issue");
            }
            RouteStop newRouteStop=new RouteStop();
            newRouteStop.setRoute(routeOpt.get());
            newRouteStop.setBusStop(busStopOpt.get());
            newRouteStop.setSequence(sequence);
            return ResponseEntity.status(HttpStatus.CREATED).body(routeStopRepo.save(newRouteStop));
        }
    } 
    
    @GetMapping("/route/destination")
    public ResponseEntity<?> routeBySourceAndDest(
        @RequestParam String source , @RequestParam String destination
    ){
        if(source.equals(destination)){
            return ResponseEntity.badRequest().body("Same source and destination");
        }
        return ResponseEntity.ok(routeStopRepo.findBySourceAndDestination(source, destination));
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
